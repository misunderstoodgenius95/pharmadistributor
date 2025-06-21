package pharma.security.Stytch;

import com.auth0.json.mgmt.client.Client;
import pharma.config.InputValidation;
import pharma.security.Stytch.conf.EncodeStytch;
import pharma.security.Stytch.conf.EndPoints;
import pharma.security.Stytch.conf.PayLoadStytch;
import pharma.config.net.ClientHttp;
import pharma.security.Stytch.conf.StytchRequest;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class StytchClient {

    private String projectId;
    private String secret;
    private ClientHttp clientHttp;
    private  String endpoint;
    private StytchRequest stytchRequest;
    public StytchClient(String projectId, String secret, String endpoint) {
        if(!InputValidation.validate_stytch_project_id(projectId ) || !InputValidation.validate_stytch_secret_key(secret)){
            throw new IllegalArgumentException("Project_id or secret key not valid");
        }
        this.projectId = projectId;
        this.secret = secret;
        this.clientHttp =new ClientHttp();
        this.endpoint=endpoint;
      stytchRequest=new StytchRequest(EncodeStytch.authentication(projectId,secret));
    }
    //Only for test only
    public StytchClient(String projectId, String secret, String endpoint, ClientHttp clientHttp) {
        if(!InputValidation.validate_stytch_project_id(projectId ) || !InputValidation.validate_stytch_secret_key(secret)){
            throw new IllegalArgumentException("Project_id or secret key not valid");
        }
        this.projectId = projectId;
        this.secret = secret;
        this.clientHttp =clientHttp;
        this.endpoint=endpoint;
        stytchRequest=new StytchRequest(EncodeStytch.authentication(projectId,secret));
    }




    public HttpResponse<String> create_user(String email, String password,String role,String first_name,String surname) {
                if(!InputValidation.validate_email(email ) ||! InputValidation.validate_password(password) || role==null || role.isEmpty()
                || first_name==null ||first_name.isEmpty()|| surname==null || surname.isEmpty()){
                    throw new IllegalArgumentException("field not correct!");
                }
        try {
                HttpRequest request=stytchRequest.auth_request(EndPoints.getCreate_user(endpoint),
                            PayLoadStytch.create_user(email,password,role,first_name,surname));

                return clientHttp.send(request);
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
        }
    }

    public HttpResponse<String> login(String email,String password){
        if(!InputValidation.validate_email(email ) ||! InputValidation.validate_password(password)){
            throw new IllegalArgumentException("email and password not valid!");
        }
            HttpRequest httpRequest=stytchRequest.auth_request(EndPoints.get_auth(endpoint),PayLoadStytch.get_user(email,password));
            return  clientHttp.send(httpRequest);


    }
 /*   public HttpResponse<String> authenticate_session_token(String token){
    HttpRequest httpRequest=stytchRequest.auth_request(EndPoints.getAuth_token(endpoint),PayLoadStytch.buildAuthPayload_token(token));
     return clientHttp.send(httpRequest);


    }*/
    public HttpResponse<String> authenticate_jwt_token(String token){
        if(token.isEmpty()){
            throw new IllegalArgumentException("token is empty");
        }
        HttpRequest httpRequest=stytchRequest.auth_request(EndPoints.getAuth_token(endpoint),PayLoadStytch.buildAuthPayload_jwt_token(token));
        return clientHttp.send(httpRequest);
    }

    public HttpResponse<String> get_users(){
        return clientHttp.send(stytchRequest.search_all(EndPoints.getSearch_users(endpoint),PayLoadStytch.buildSearch_All()));


    }

    public HttpResponse<String> get_user_by_email(String email){
        if(!InputValidation.validate_email(email )){
            throw new IllegalArgumentException("email  not valid!");
        }

        return  clientHttp.send(stytchRequest.search_by_email(EndPoints.getSearch_users(endpoint),PayLoadStytch.buildSearchByEmail(email)));

    }


    public HttpResponse<String> revoke_user(String session_id){
        if(session_id.isEmpty()){
            throw new IllegalArgumentException("User id  is not present");
        }
        return clientHttp.send(stytchRequest.revoke_user(EndPoints.getRevoke(endpoint),PayLoadStytch.buildRevoke(session_id)));


    }
    public HttpResponse<String> get_session(String user_id){

        if(user_id.isEmpty()){
            throw new IllegalArgumentException("User id not present!");
        }
        return clientHttp.send(stytchRequest.session_user(EndPoints.getUser_session(endpoint),user_id));
    }












}
