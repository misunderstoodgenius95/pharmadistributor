package pharma.config.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pharma.Model.User;
import pharma.security.Stytch.StytchClient;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class UserGatewayStytchTest {
    private UserGateway userGateway;
    private StytchClient stytchClient;
    @BeforeEach
    public void setUp(){
        String project_id="project-test-ead7077c-e25f-4fe1-ba63-3e5972ab34ec";
        String secret="secret-test-GJ9p83rxjv8pT7WNQnoqLBaPgikz4ZV1UB8=";
        String endpoint="https://delicious-nose-9298.customers.stytch.dev";
        stytchClient= new StytchClient(project_id,secret,endpoint);
        userGateway =new UserGateway(stytchClient);
    }
    @Test
    @Timeout(value =1,unit = TimeUnit.SECONDS)
    public void CreateUser(){
        UserGatewayResponse userGatewayResponse = userGateway.register("luigi.neris@apime.online","r4AWoPBR=_+Q","pharmacist","eleonora_mali","r");
        Assertions.assertEquals(200, userGatewayResponse.getStatus());

    }
    @Test
    public void CreateUserDuplicate(){
          UserGatewayResponse userGatewayResponse = userGateway.register("debby@example.com","Xt!NDTIqoAS9","seller","debby","r");
          Assertions.assertEquals(429, userGatewayResponse.getStatus());



    }

    @Test
    public void searchUserbyEmail(){
        User user_previous = userGateway.searchUserByEmail("elisa.scalzi@mailfence.com");
        Assertions.assertEquals(200,user_previous.getStatus());
    }
    @Test
    public void ValidTestIntegration(){

        UserGatewayResponse ur= userGateway.user_update_role("user-test-30041b3c-02d5-46fa-beed-2fcb661b7388","seller");
        Assertions.assertEquals(200,ur.getStatus());
    }
    @Test
    void user_revocate() throws FileNotFoundException {


        User user_previous = userGateway.searchUserByEmail("debby@example.com");
        System.out.println(":value"+user_previous.getResults().getFirst().getTrustedMetadata().isIs_enable());
        if (user_previous.getResults().getFirst().getTrustedMetadata().isIs_enable())
        {

             userGateway.user_revocate("user-test-30041b3c-02d5-46fa-beed-2fcb661b7388", false);
            User user = userGateway.searchUserByEmail("debby@example.com");
            Assertions.assertFalse(user.getResults().getFirst().getTrustedMetadata().isIs_enable());
        }else {
            boolean value = false;
            Assertions.assertTrue(value);
        }

    }

    @Test
    public void  NotFoundEmailUser(){
        User user= userGateway.searchUserByEmail("user@example.com");
        Assertions.assertNotNull(user.getResults());



    }
    @Test
    public void get(){
        UserGatewayResponse ur= userGateway.searchUsers();
        System.out.println(ur.getBody());
    }

    @Test
    public void Valid(){
        UserGatewayResponse ur= userGateway.authenticate("ivanbutera@proton.me","ywQ1d@waw41");
Assertions.assertEquals(200,ur.getStatus());
    }


    @Test
    @Timeout(value=2,unit = TimeUnit.SECONDS)
    public void TestLoginPerformance(){
        UserGatewayResponse ur= userGateway.authenticate("ivanbutera@proton.me","ywQ1d@waw41");
        Assertions.assertEquals(200,ur.getStatus());
    }














}
