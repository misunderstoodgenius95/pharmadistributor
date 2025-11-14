package pharma.config.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.Model.User;
import pharma.Storage.FileStorage;
import pharma.security.Stytch.StytchClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class UserServiceStytchTest {
    private UserService userService;
    private StytchClient stytchClient;
    @BeforeEach
    public void setUp(){
        String project_id="project-test-ead7077c-e25f-4fe1-ba63-3e5972ab34ec";
        String secret="secret-test-GJ9p83rxjv8pT7WNQnoqLBaPgikz4ZV1UB8=";
        String endpoint="https://delicious-nose-9298.customers.stytch.dev";
        stytchClient= new StytchClient(project_id,secret,endpoint);
        userService=new UserService(stytchClient);
    }
    @Test
    public void CreateUser(){
        UserServiceResponse userServiceResponse=userService.register("luigi.neri@apime.online","r4AWoPBR=_+Q","pharmacist","eleonora_mali","r");
        Assertions.assertEquals(200,userServiceResponse.getStatus());

    }
    @Test
    public void CreateUserDuplicate(){
          UserServiceResponse userServiceResponse=userService.register("debby@example.com","Xt!NDTIqoAS9","seller","debby","r");
          Assertions.assertEquals(429,userServiceResponse.getStatus());



    }

    @Test
    public void searchUserbyEmail(){
        User user_previous = userService.searchUserByEmail("elisa.scalzi@mailfence.com");
        Assertions.assertEquals(200,user_previous.getStatus());
    }
    @Test
    public void ValidTestIntegration(){

        UserServiceResponse ur=userService.user_update_role("user-test-30041b3c-02d5-46fa-beed-2fcb661b7388","seller");
        Assertions.assertEquals(200,ur.getStatus());
    }
    @Test
    void user_revocate() throws FileNotFoundException {


        User user_previous = userService.searchUserByEmail("debby@example.com");
        System.out.println(":value"+user_previous.getResults().getFirst().getTrustedMetadata().isIs_enable());
        if (user_previous.getResults().getFirst().getTrustedMetadata().isIs_enable())
        {

             userService.user_revocate("user-test-30041b3c-02d5-46fa-beed-2fcb661b7388", false);
            User user = userService.searchUserByEmail("debby@example.com");
            Assertions.assertFalse(user.getResults().getFirst().getTrustedMetadata().isIs_enable());
        }else {
            boolean value = false;
            Assertions.assertTrue(value);
        }

    }

    @Test
    public void  NotFoundEmailUser(){
        User user=userService.searchUserByEmail("user@example.com");
        Assertions.assertNotNull(user.getResults());



    }
    @Test
    public void get(){
        UserServiceResponse ur=userService.searchUsers();
        System.out.println(ur.getBody());
    }

    @Test
    public void Valid(){
        UserServiceResponse ur=userService.authenticate("ivanbutera@proton.me","ywQ1d@waw41");
Assertions.assertEquals(200,ur.getStatus());
    }

        //Assertions.assertEquals(200,ur.getStatus());













}
