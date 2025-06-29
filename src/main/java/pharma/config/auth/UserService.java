package pharma.config.auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import pharma.Model.Session;
import pharma.Model.User;
import pharma.config.InputValidation;
import pharma.security.Stytch.StytchClient;

import java.net.http.HttpResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

public class UserService {
    private static final Logger logger= Logger.getLogger(UserService.class.getName());
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


        HttpResponse<String> response = stytchClient.create_user(email, password, role, first_name, surname);
        return  new UserServiceResponse(response.body(),response.statusCode());

    }
    public  UserServiceResponse searchUser(){
       HttpResponse<String> response=stytchClient.get_users();
        return  new UserServiceResponse(response.body(), response.statusCode());

    }
   public  User searchUserByEmail(String email) {
       HttpResponse<String> json_user_by_search = stytchClient.get_user_by_email(email);
       if (json_user_by_search.statusCode() == 200) {
           User user = deserialization_object(json_user_by_search.body(), User.class).get();
            HttpResponse<String> response=stytchClient.get_session(user.getResults().getFirst().getUser_id());
        if(response.statusCode()==200){
            String json_session=response.body();
            Session session= deserialization_object(json_session, Session.class).get();
            Instant instant=recent_last_access(session);
            user.setLast_access(instant);
       }
           return user;
       }
        return null;

   }



  public  static  <T> Optional<T> deserialization_object(String json, Class<T> tclass){
        if(json==null || json.isEmpty()){
            throw new IllegalArgumentException("json is null or empty");
        }
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {

              return Optional.of(objectMapper.readValue(json,tclass));
        } catch (JsonProcessingException e) {
            logger.warning(e.getMessage());

        }
        return Optional.empty();



    }
    public User get_user_byrole(String role){
        if(role==null && role.isEmpty()){
            throw new IllegalArgumentException("role is null or empty");
        }
        HttpResponse<String> response=stytchClient.get_users();
        User user=deserialization_object(response.body(),User.class).get();

        return user;

    }

    public static User extract_Session_role(User user,String role){

        user.getResults().removeIf(result -> !result.getTrustedMetadata().getRole().equals(role));
        return user;


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




