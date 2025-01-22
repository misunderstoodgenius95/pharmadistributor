package pharma.config;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.emailproviders.EmailProvider;
import com.auth0.net.Response;

public class Auth0Client {

    private final AuthAPI authAPI;
    public Auth0Client(String clientId, String clientSecret,String domain)  {
       authAPI= AuthAPI.
                newBuilder(domain,
                        clientId,
                        clientSecret).build();




    }

    public Auth0Client(Auth0Client auth0Client) {
        authAPI = auth0Client.authAPI;
    }
    public Response<TokenHolder> login(String email,String password,String audience) {
        try {
            return authAPI.login(email, password.toCharArray()).setAudience(audience).execute();
        } catch (Auth0Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Response<EmailProvider> user(String token){
        ManagementAPI managementAPI= ManagementAPI.newBuilder("https://auth.apidistro.ct.ws",token).build();

        try {
           return managementAPI.emailProvider().get(new FieldsFilter()).execute();
         // return   managementAPI.users().list(userFilter).execute();

        } catch (Auth0Exception e) {
            throw new RuntimeException(e);
        }


    }








}
