package pharma.security.Stytch;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pharma.Storage.FileStorage;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;
import pharma.config.net.ClientHttp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StytchClientTest {
    @Mock
    private HttpResponse<String> stringHttpResponse;
    private StytchClient stytchClient;
    @Mock
    private ClientHttp clientHttp;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        stytchClient=new StytchClient("project-test-4acd22c0-ef21-4294-97b1-38d96e7a77c2",
                "secret-test-ed1zX4Tr-3_zWFncQ_yEkm6o3yzeyJ8vr8Y=","https://login.apime.online",clientHttp);

    }



    @Nested
    class Login{
        @Test
        void Validlogin() {

            when(stringHttpResponse.statusCode()).thenReturn(200);
            when(clientHttp.send(any(HttpRequest.class))).thenReturn(stringHttpResponse);
            int status=stytchClient.login("user@example.com","&6hF%@&yvBE").statusCode();
            Assertions.assertEquals(200,status);


        }
        @Test
            void InvaliLogin(){
            when(stringHttpResponse.statusCode()).thenReturn(200);
            when(clientHttp.send(any(HttpRequest.class))).thenReturn(stringHttpResponse);
           Assertions.assertThrows(IllegalArgumentException.class,()-> stytchClient.login("userm","&6hF%@&yvBE"));


        }
    }

    @Nested
     class  CreateUser{
        @Test
        public void ValidRegister(){
            when(stringHttpResponse.statusCode()).thenReturn(200);
            when(clientHttp.send(any(HttpRequest.class))).thenReturn(stringHttpResponse);
            int status=stytchClient.create_user("user@example.com","&6hF%@&yvBE","seller","a","b").statusCode();
            Assertions.assertEquals(200,status);
        }
        @Test
        public void InValidRegister(){
            when(stringHttpResponse.statusCode()).thenReturn(400);
            when(clientHttp.send(any(HttpRequest.class))).thenReturn(stringHttpResponse);
            int status=stytchClient.create_user("user@example.com","&6hF%@&yvBE","seller","a","b").statusCode();
            Assertions.assertEquals(400,status);
        }
        @Test
        public void InValidRegisterArgument(){


            when(stringHttpResponse.statusCode()).thenReturn(400);
            when(clientHttp.send(any(HttpRequest.class))).thenReturn(stringHttpResponse);
            Assertions.
                    assertThrows(IllegalArgumentException.class,()->stytchClient.create_user("user@example","&6hF%@&yvBE","seller","a","b").statusCode());
        }

    }

    @Test
    void Validget_user_by_email() throws FileNotFoundException {
        when(stringHttpResponse.statusCode()).thenReturn(200);
        when(clientHttp.send(any(HttpRequest.class))).thenReturn(stringHttpResponse);
        Assertions.assertEquals(200,stytchClient.get_user_by_email("flaviana.buccho@azienda.com").statusCode());


    }


    @AfterEach
    public  void tearDown(){
        clientHttp=null;
        stytchClient=null;

    }
    @Test
    public void ValidIntegrationCreateUser() throws FileNotFoundException {
        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader("stytch.properties"));
        UserService userService=new UserService(new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url")));
       UserServiceResponse ur=userService.register("paola.rossi@example.com","=1Ii2ga*1m9g","admin","paola","rossi");
       Assertions.assertEquals(200,ur.getStatus());


    }

    @Test
    void get_user_by_email() throws FileNotFoundException {
        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader("stytch.properties"));
            stytchClient=new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url"));
            HttpResponse<String> response=stytchClient.get_user_by_email("flaviana.buccho@azienda.com");
        System.out.println(response.body());
    }

    @Test
    void ValidGetSession() throws FileNotFoundException {
        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader("stytch.properties"));
        stytchClient=new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url"));
        HttpResponse<String> response=stytchClient.get_session("user-live-3927dadb-5d0b-4e0e-9738-dda318f29270");
        System.out.println(response.body());

    }











}