package pharma.oldest;

import org.json.JSONObject;
import pharma.config.HttpJsonClient;
import pharma.config.InputValidation;
import pharma.config.UserServiceResponse;

import java.net.http.HttpResponse;
import java.util.HashMap;

public class UserService {
    private final HttpJsonClient jsonClient;
    private  HashMap<String,String> json_params;
private String url;
    public UserService(HttpJsonClient jsonClient,HashMap<String,String> json_params,String url)  {
        this.jsonClient=jsonClient;
        this.json_params=json_params;
        this.url=url;
    }

    public UserServiceResponse authenticate(String username, String password) {

            if(username ==null|| !InputValidation.validate_email(username)){
                throw new IllegalArgumentException("Username cannot be null or empty");

            }
            if(password == null || !InputValidation.validate_password(password)) {
                throw new IllegalArgumentException("Password cannot be null or empty");

            }
            json_params.put("password",password);
            json_params.put("username",username);
            JSONObject jsonObject=new JSONObject(json_params);
            HttpResponse<String> response=jsonClient.post(url,jsonObject);

            return new UserServiceResponse(response.body(),response.statusCode());

    }
    public void authorization(){


    }






}




