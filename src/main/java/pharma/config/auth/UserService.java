package pharma.config.auth;


import org.json.HTTP;
import pharma.config.InputValidation;
import pharma.security.Stytch.StytchClient;
import pharma.security.TokenUtility;

import java.net.http.HttpResponse;

public class UserService {

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
       HttpResponse<String> response=stytchClient.authenticate_jwt_token(jwt);
       switch (response.statusCode()) {
           case 200 -> {
               return new UserServiceResponse(response.body(), response.statusCode());
           }
           case 400->{
               throw new  ExpireToken("Invalid parse jwt token");

           }
           case 404->{
               throw  new ExpireToken("Invalid session jwt token");
           }
           default -> {
               throw new ExpireToken("Authentication failed: " + response.body());
           }

        }




    }



}




