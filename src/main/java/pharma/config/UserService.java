package pharma.config;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.Response;

public class UserService {

    private final AuthAPI authAPI;

    public UserService(AuthAPI authAPI) {
        this.authAPI = authAPI;

    }

    public Response<TokenHolder> authenticate(String username, String password, String audience) throws Auth0Exception {

        if(username ==null|| !InputValidation.validate_email(username)){
            throw new IllegalArgumentException("Username cannot be null or empty");

        }
        if(password == null || !InputValidation.validate_password(password)) {
            throw new IllegalArgumentException("Password cannot be null or empty");

        }

        try {

           return authAPI.login(username, password.toCharArray()).setAudience(audience).execute();
        } catch (Auth0Exception e) {
            throw new Auth0Exception(e.getMessage());
        }


    }

}




