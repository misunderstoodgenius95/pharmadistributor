package pharma.security.Stytch;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pharma.Storage.FileStorage;
import pharma.config.PathConfig;
import pharma.config.net.ClientHttp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

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
            int status=stytchClient.create_user("user@example.com","&6hF%@&yvBE","a","b").statusCode();
            Assertions.assertEquals(200,status);
        }
        @Test
        public void InValidRegister(){
            when(stringHttpResponse.statusCode()).thenReturn(400);
            when(clientHttp.send(any(HttpRequest.class))).thenReturn(stringHttpResponse);
            int status=stytchClient.create_user("user@example.com","&6hF%@&yvBE","a","b").statusCode();
            Assertions.assertEquals(400,status);
        }
        @Test
        public void InValidRegisterArgument(){


            when(stringHttpResponse.statusCode()).thenReturn(400);
            when(clientHttp.send(any(HttpRequest.class))).thenReturn(stringHttpResponse);
            Assertions.
                    assertThrows(IllegalArgumentException.class,()->stytchClient.create_user("user@example","&6hF%@&yvBE","a","b").statusCode());
        }

    }

    @Test
    void Validget_user_by_email() throws FileNotFoundException {
        when(stringHttpResponse.statusCode()).thenReturn(200);
        when(clientHttp.send(any(HttpRequest.class))).thenReturn(stringHttpResponse);
        Assertions.assertEquals(200,stytchClient.get_user_by_email("flaviana.buccho@azienda.com").statusCode());


    }


    @AfterEach
    public  void tearDown() throws FileNotFoundException {
        clientHttp=null;
        stytchClient=null;
        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader(PathConfig.STYTCH_CONF.getValue()));
        stytchClient=new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url"));

    }
  /*  @Test
    public void ValidIntegrationCreateUser() throws FileNotFoundException {
       UserGatewayResponse ur=userService.register("paola.rossi@example.com","=1Ii2ga*1m9g","admin","paola","rossi");
       Assertions.assertEquals(200,ur.getStatus());


    }*/

    @Test
    void get_user_by_email()  {

            HttpResponse<String> response=stytchClient.get_user_by_email("flaviana.buccho@azienda.com");
        System.out.println(response.body());
    }

    @Test
    void ValidGetSession() throws FileNotFoundException {
        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader(PathConfig.STYTCH_CONF.getValue()));
        stytchClient=new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url"));
        HttpResponse<String> response=stytchClient.get_session("user-test-bb4e0246-9886-4af1-b841-67f09f1a265c");
        Assertions.assertEquals(200,response.statusCode());
    }
    @Test
    void ValidSession() throws FileNotFoundException {
        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader(PathConfig.STYTCH_CONF.getValue()));
        stytchClient=new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url"));
        HttpResponse<String> response=stytchClient.get_session("user-test-bb4e0246-9886-4af1-b841-67f09f1a265c");
        System.out.println(response.body());
    }



    @Test
    void ValidTestReset() throws FileNotFoundException {
        String project_id="project-test-ead7077c-e25f-4fe1-ba63-3e5972ab34ec";
        String secret="secret-test-GJ9p83rxjv8pT7WNQnoqLBaPgikz4ZV1UB8=";
        String endpoint="https://delicious-nose-9298.customers.stytch.dev";
       StytchClient stytchClient= new StytchClient(project_id,secret,endpoint);


        HashMap<String,String> hashMap_json=
              FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader(PathConfig.STYTCH_CONF.getValue()));
        stytchClient=new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url"));
         HttpResponse<String> response=stytchClient.reset_password_start("luigi.neri_azienda@proton.me","http://localhost:3000");
        System.out.println(response.body());






    }



    @Test
    void ValidloginIntegration() {
        String project_id="project-test-ead7077c-e25f-4fe1-ba63-3e5972ab34ec";
        String secret="secret-test-GJ9p83rxjv8pT7WNQnoqLBaPgikz4ZV1UB8=";
        String endpoint="https://delicious-nose-9298.customers.stytch.dev";
        StytchClient stytchClient= new StytchClient(project_id,secret,endpoint);

        int status=stytchClient.login("luigi.neri_azienda@proton.me","&6hF%@&yvBE").statusCode();
        Assertions.assertEquals(200,status);


    }

    @Test
    void ValidloginIntegrationAdmin() {
        String project_id="project-test-ead7077c-e25f-4fe1-ba63-3e5972ab34ec";
        String secret="secret-test-GJ9p83rxjv8pT7WNQnoqLBaPgikz4ZV1UB8=";
        String endpoint="https://delicious-nose-9298.customers.stytch.dev";
        StytchClient stytchClient= new StytchClient(project_id,secret,endpoint);

        int status=stytchClient.login("annalisa.carone@proton.me","3g9w*5NpB7g").statusCode();
        Assertions.assertEquals(200,status);


    }
    @Test
    void ValidIntegrationAUthenticationJwt() throws IOException {
        String project_id="project-test-ead7077c-e25f-4fe1-ba63-3e5972ab34ec";
        String secret="secret-test-GJ9p83rxjv8pT7WNQnoqLBaPgikz4ZV1UB8=";
        String endpoint="https://delicious-nose-9298.customers.stytch.dev";
        StytchClient stytchClient= new StytchClient(project_id,secret,endpoint);

        String jwt=FileStorage.getProperty("jwt",new FileReader("jwt.properties"));
        System.out.println(jwt);







    }


    @Test
    void TestResponseTimeLess3second() {
        String project_id="project-test-ead7077c-e25f-4fe1-ba63-3e5972ab34ec";
        String secret="secret-test-GJ9p83rxjv8pT7WNQnoqLBaPgikz4ZV1UB8=";
        String endpoint="https://delicious-nose-9298.customers.stytch.dev";
        long time_start=System.nanoTime();
        StytchClient stytchClient= new StytchClient(project_id,secret,endpoint);
        stytchClient.login("annalisa.carone@proton.me","3g9w*5NpB7g").statusCode();
        long  time_end=System.nanoTime();
        long duration=time_end-time_start;
        double duration_ms = duration / 1_000_000.0;
        org.assertj.core.api.Assertions.assertThat(duration_ms).isLessThan(3000);
    }



















}