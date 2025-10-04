package pharma.config.auth;


import com.auth0.jwt.interfaces.Payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import pharma.Model.Session;
import pharma.Model.User;
import pharma.config.InputValidation;
import pharma.security.Stytch.StytchClient;
import pharma.security.Stytch.conf.PayLoadStytch;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private StytchClient stytchClient;

    public UserService(StytchClient stytchClient) {
        this.stytchClient = stytchClient;
    }

    public UserServiceResponse authenticate(String email, String password) {

        if (email == null || !InputValidation.validate_email(email)) {
            throw new IllegalArgumentException("Username cannot be null or empty");

        }
        if (password == null || !InputValidation.validate_password(password)) {
            throw new IllegalArgumentException("Password cannot be null or empty");

        }

        HttpResponse<String> response = stytchClient.login(email, password);
        return new UserServiceResponse(response.body(), response.statusCode());

    }

    public UserServiceResponse authenticate_jwt(String jwt) throws ExpireToken {
        if (jwt == null || jwt.isEmpty()) {
            throw new IllegalArgumentException("jwt not correct");
        }
        HttpResponse<String> response = stytchClient.authenticate_jwt_token(jwt);
        switch (response.statusCode()) {
            case 200 -> {
                return new UserServiceResponse(response.body(), response.statusCode());
            }
            case 400 -> {
                throw new ExpireToken("Invalid parse jwt token");

            }
            case 404 -> {
                throw new ExpireToken("Invalid session jwt token");
            }
            default -> {
                throw new ExpireToken("Authentication failed: " + response.body());
            }

        }

    }

    public UserServiceResponse register(String email, String password, String role, String first_name, String surname) {


        HttpResponse<String> response_create = stytchClient.create_user(email, password, first_name, surname);
        JSONObject jsonObject=new JSONObject(response_create.body());
        if(jsonObject.has("error_type")) {
            String error_type = new JSONObject(response_create.body()).get("error_type").toString();
            if (error_type.equals("duplicate_email")) {
                return new UserServiceResponse("Unprocessable Entity", 429);
            }
            return new UserServiceResponse(response_create.body(), response_create.statusCode());
        }else if(response_create.statusCode()==200) {
            String user_id=jsonObject.get("user_id").toString();
                user_update_role(user_id,role);
             HttpResponse<String> response_reset=stytchClient.reset_password_start(email,"http://localhost:3000");

            return new UserServiceResponse(response_reset.body(), response_reset.statusCode());

        }
        return new UserServiceResponse(response_create.body(), response_create.statusCode());

    }

    public UserServiceResponse searchUsers() {
        HttpResponse<String> response = stytchClient.get_users();
        return new UserServiceResponse(response.body(), response.statusCode());

    }

    public User searchUserByEmail(String email) {
        HttpResponse<String> json_user_by_search = stytchClient.get_user_by_email(email);
        if (json_user_by_search.statusCode() == 200) {
            User user = deserialization_object(json_user_by_search.body(), User.class).get();
            return add_latest_access(user);


        }
        return  null;
    }


    public static <T> Optional<T> deserialization_object(String json, Class<T> tclass) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("json is null or empty");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {

            return Optional.of(objectMapper.readValue(json, tclass));
        } catch (JsonProcessingException e) {
            logger.warning(e.getMessage());

        }
        return Optional.empty();


    }

    public User get_user_byRole(String role) {
        if (role == null && role.isEmpty()) {
            throw new IllegalArgumentException("role is null or empty");
        }
        HttpResponse<String> response = stytchClient.get_users();
        logger.info(response.body());
        User user = deserialization_object(response.body(), User.class).get();
        return  extract_Session_role(user,role);



    }

    public UserServiceResponse user_revocate(String user_id, boolean status) {
        if (!InputValidation.validate_stytch_user_id(user_id)) {
            throw new IllegalArgumentException("User id  is not valid");
        }
        HttpResponse<String> response=stytchClient.update_user(user_id, PayLoadStytch.buildUpdateTustedMetadataIsEnable(status));
        return new UserServiceResponse(response.body(),response.statusCode());

    }


    public UserServiceResponse user_update_role(String user_id, String role) {
        if (!InputValidation.validate_stytch_user_id(user_id)) {
            throw new IllegalArgumentException("User id  is not valid");
        }
        HttpResponse<String> response=stytchClient.update_user(user_id, PayLoadStytch.buildUpadateRole(role));
        return new UserServiceResponse(response.body(),response.statusCode());

    }


    public User add_latest_access( User user){

        if(!user.getResults().isEmpty()) {


            HttpResponse<String> response = stytchClient.get_session(user.getResults().getFirst().getUser_id());
            if (response.statusCode() == 200) {
                String json_session = response.body();
                Session session = deserialization_object(json_session, Session.class).get();
                Instant instant = recent_last_access(session);
                user.getResults().getFirst().setLast_access(instant);
            }
        }
        return  user;

    }

    public  User extract_Session_role(User user,String role){
        user.getResults().getFirst().getRoles().forEach(System.out::println);
            user.getResults().removeIf(result -> !(result.getRoles().contains(role)));
            return add_latest_access(user);




    }








    /**
     *
     * Can be obtein the last timestamp acess
     * @param session_model
     * @return
     */
   public static Instant recent_last_access(Session session_model){
        if(session_model.getSessions().isEmpty()){
            throw new IllegalArgumentException("Cannot compare because the list is empty");
        }

       Instant recent_instant=null;
        for(Session.Sessions session:session_model.getSessions()){
            for(Session.AuthenticationFactor  a_factor:session.getAuthenticationFactor()){
                Instant curent_instant=Instant.parse(a_factor.getLast_authenticated_at());
                if(recent_instant==null){
                    recent_instant=curent_instant;
                }else {
                    if(curent_instant.isAfter(recent_instant)){
                        recent_instant=curent_instant;

                    }
                }



            }

        }
        return recent_instant;

    }








    public  static String extract_results(String body){

        return new JSONObject(body).getJSONArray("results").toString();
    }









}




