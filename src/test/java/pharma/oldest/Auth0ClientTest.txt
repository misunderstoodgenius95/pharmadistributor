package pharma.oldest;

import org.junit.jupiter.api.Test;

class Auth0ClientTest {

    @Test
    void login() {
        /*Auth0Client auth0Client=new Auth0Client();
        Response<TokenHolder> tokenHolderResponse = auth0Client.login();
        System.out.println(tokenHolderResponse.getBody().getAccessToken());

         */
    }

    @Test void get_scope(){

      /*  Auth0Client auth0Client=new Auth0Client();
        Response<TokenHolder> tokenHolderResponse = auth0Client.login();

    tokenHolderResponse.getBody().get



       */

    }

    @Test
    void getUser() {

      /*  Auth0Client auth0Client=new Auth0Client();
        Response<TokenHolder> tokenHolderResponse = auth0Client.login();
        String token=tokenHolderResponse.getBody().getAccessToken();
        System.out.println(auth0Client.user(token).getStatusCode());

       */
    }
}