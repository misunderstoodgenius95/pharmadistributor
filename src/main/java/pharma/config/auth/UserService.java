package pharma.config.auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.Response;
import pharma.config.InputValidation;
import pharma.security.Stytch.StytchClient;

import java.net.http.HttpResponse;

public class UserService {

    private StytchClient stytchClient;

    public UserService(StytchClient stytchClient) {
        this.stytchClient = stytchClient;
    }

    public UserServiceResponse authenticate(String email, String password) throws Auth0Exception {

        if (email == null || !InputValidation.validate_email(email)) {
            throw new IllegalArgumentException("Username cannot be null or empty");

        }
        if (password == null || !InputValidation.validate_password(password)) {
            throw new IllegalArgumentException("Password cannot be null or empty");

        }

        HttpResponse<String> response = stytchClient.login(email, password);
        return new UserServiceResponse(response.body(), response.statusCode());

    }

}




