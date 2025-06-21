package pharma.security.Stytch;

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
        this.projectId = projectId;
        this.secret = secret;
        this.clientHttp =new ClientHttp();
        this.endpoint=endpoint;
      stytchRequest=new StytchRequest(EncodeStytch.authentication(projectId,secret));

    }







public HttpResponse<String> create_user(String email, String password,String role,String first_name,String surname) {
    try {






        HttpRequest request=stytchRequest.auth_request(EndPoints.getCreate_user(endpoint), PayLoadStytch.create_user(email,password,role,first_name,surname
        ));


        return clientHttp.send(request);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    public HttpResponse<String> login(String email,String password){
        HttpRequest httpRequest=stytchRequest.auth_request(EndPoints.get_auth(endpoint),PayLoadStytch.get_user(email,password));
            return  clientHttp.send(httpRequest);


    }
 /*   public HttpResponse<String> authenticate_session_token(String token){
    HttpRequest httpRequest=stytchRequest.auth_request(EndPoints.getAuth_token(endpoint),PayLoadStytch.buildAuthPayload_token(token));
     return clientHttp.send(httpRequest);


    }*/
    public HttpResponse<String> authenticate_jwt_token(String token){
        HttpRequest httpRequest=stytchRequest.auth_request(EndPoints.getAuth_token(endpoint),PayLoadStytch.buildAuthPayload_jwt_token(token));
        return clientHttp.send(httpRequest);


    }











}
