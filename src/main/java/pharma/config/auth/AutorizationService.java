package pharma.config.auth;

import pharma.security.Stytch.StytchClient;
import pharma.security.TokenUtility;

import java.net.http.HttpResponse;

public class AutorizationService {
   private StytchClient stytchClient;

    public AutorizationService(StytchClient stytchClient) {
        this.stytchClient = stytchClient;
    }

    public boolean authorization(String jwt,String permission,String operation) {
        HttpResponse<String> response = stytchClient.authenticate_jwt_token(jwt);
        if (response.statusCode() != 200) {
            return false;

        } else {
            return TokenUtility.check_permission(operation, permission, TokenUtility.extractPermissions(response.body()));

        }
    }







}
