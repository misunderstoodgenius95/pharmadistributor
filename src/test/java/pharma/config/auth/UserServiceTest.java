package pharma.config.auth;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pharma.Model.Session;
import pharma.Model.User;
import pharma.Storage.FileStorage;
import pharma.security.Stytch.StytchClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private StytchClient stytchClient;
    @Mock
    private HttpResponse<String> httpResponse_user;
    private UserService userService;
    @BeforeEach
    public  void  setUp(){
        MockitoAnnotations.openMocks(this);
        userService=new UserService(stytchClient);
    }
    @Test
    void ValidSearchUser() {
        when(httpResponse_user.statusCode()).thenReturn(200);
        when(stytchClient.get_users()).thenReturn(httpResponse_user);

        Assertions.assertEquals(200,userService.searchUser().getStatus());
    }



    @Test
    void get_user_byRole() {
        String jsonContent_user = null;
        try {
            jsonContent_user = Files.readString(Paths.get("src/main/java/json/searchall.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        when(httpResponse_user.body()).thenReturn(jsonContent_user);
        when(httpResponse_user.statusCode()).thenReturn(200);
        when(stytchClient.get_users()).thenReturn(httpResponse_user);

            User user = userService.get_user_byRole("seller");
            Assertions.assertEquals(1,user.getResults().size());
               // Assertions.assertEquals("paolobiasin.rossi@example.com",user.getResults().getFirst().getEmails().getFirst().getEmail());


    }


    @AfterEach
    public void tearDown(){
        stytchClient=null;
        userService=null;
    }
    @Test
    public void SearchIntegrate() throws FileNotFoundException {

        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader("stytch.properties"));
        UserService userService=new UserService(new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url")));
        User user = userService.get_user_byRole("seller");
        Assertions.assertEquals(1,user.getResults().size());
        /*    UserServiceResponse ur=userService.searchUser();

        System.out.println("before: "+ur.getBody());*/

 /*       List<Map<String,Object>> extract=Query.filterUsersByRole(ur.getBody(),"seller");
        System.out.println("after: "+extract);
        System.out.println("email: "+TokenUtility.extract_email(extract.toString()));
        System.out.println("role: "+TokenUtility.extractRole(extract.toString()));
*/




        //Assertions.assertEquals(200,ur.getStatus());
    }


    @Test
    void user_revocate() throws FileNotFoundException {


        HashMap<String,String> hashMap_json=
                FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader("stytch.properties"));
        UserService userService=new UserService(new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url")));
        UserServiceResponse ur=userService.user_revocate("user-live-3927dadb-5d0b-4e0e-9738-dda318f29270",true);
        System.out.println(ur.getBody());
        //Assertions.assertEquals(200,ur.getStatus());






    }




    @Nested
    class SessionRole {
        List<User.Results> list = new ArrayList<>();
        User user;
        @BeforeEach
        public void setUp() {

            List<String> roles = List.of("seller_one@example.com", "seller_two@example.com", "purchase_three@example.com", "purchase_for@example.com");
            roles.forEach(role -> {
                User.Results results = new User.Results();
                User.TrustedMetadata usertrussted = new User.TrustedMetadata();
                String[] splitted = role.split("_");
                usertrussted.setRole(splitted[0]);
                results.setTrustedMetadata(usertrussted);
                User.Emails emails = new User.Emails(splitted[1]);
                results.setEmails(List.of(emails));
                results.setTrustedMetadata(usertrussted);
                list.add(results);
                 user=new User(list);
            });
        }

        @Test
        void VoidSizeTwo() {
            Assertions.assertEquals(2, user.getResults().size());
        }
        @Test
        public void ValidFirstEmailOne(){
            Assertions.assertEquals(2,UserService.extract_Session_role(user,"seller").getResults().size());



        }


    }


    @Nested
    class UserbyEmail {
        UserService userService;
        @Mock
        private HttpResponse<String> httpResponse_session;
        @Mock
        private HttpResponse<String> httpResponse_search;
        @Mock
        private StytchClient stytchClient;
        User user;
        @BeforeEach
        public void SetUp() throws FileNotFoundException {
            MockitoAnnotations.openMocks(this);
            userService=new UserService(stytchClient);
            String jsonContent_user = null;
            try {
                jsonContent_user = Files.readString(Paths.get("src/main/java/json/fileuser.json"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String jsonContent_session = null;
            try {
                jsonContent_session = Files.readString(Paths.get("src/main/java/json/file3.json"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }




            when(httpResponse_session.body()).thenReturn(jsonContent_session);
            when(httpResponse_session.statusCode()).thenReturn(200);
            when(stytchClient.get_session(anyString())).thenReturn(httpResponse_session);
            when(httpResponse_user.body()).thenReturn(jsonContent_user);
            when(httpResponse_user.statusCode()).thenReturn(200);
            when(stytchClient.get_user_by_email(anyString())).thenReturn(httpResponse_user);
            user = userService.searchUserByEmail("flaviana.buccho@azienda.com");
        }

        @Test
        public void TestValid(){


            Assertions.assertEquals("user-live-3927dadb-5d0b-4e0e-9738-dda318f29270",user.getResults().getFirst().getUser_id());



        }


        @Test
        void ValidTrustedMetadata() {
            Assertions.assertEquals("seller", user.getResults().getFirst().getTrustedMetadata().getRole());
        }
        @Test
        void ValidEmail() {

            Assertions.assertNotNull(user.getResults().getFirst().getEmails().getFirst().getEmail());
        }
        @Test
        void ValidTrusted() {

            Assertions.assertNotNull(user.getResults().getFirst().getTrustedMetadata());
        }
        @Test
        void ValidRole() {

            Assertions.assertEquals("seller",user.getResults().getFirst().getTrustedMetadata().getRole());
        }
        @Test
        void ValidIsEnable() {

            Assertions.assertTrue(user.getResults().getFirst().getTrustedMetadata().isIs_enable());
        }
        @Test
        void ValidInstant(){

            Instant instant=Instant.parse("2025-06-30T09:40:08Z");
            Assertions.assertEquals(instant,user.getResults().getFirst().getLast_access());
        }




    }






    @Test
    public void ValidExtractResults(){
        String json= "{\"request_id\":\"request-id-live-47c98ff5-4bf6-4824-b82b-76fba9bc7162\",\"results\":[{\"biometric_registrations\":[],\"created_at\":\"2025-06-28T07:13:18Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"paola.rossi@example.com\",\"email_id\":\"email-live-506fb0ad-7f76-4d46-9029-62fd71313eba\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"paola\",\"last_name\":\"rossi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-16bbb443-b755-4276-8a0a-804ec0a4298f\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"role\":\"admin\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-a462acc5-230a-49e3-bb13-9f664e8cc6d1\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-06-17T16:58:29Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"marco@farmaciamia.com\",\"email_id\":\"email-live-0c0bda3b-1184-43d2-b6e5-a8290daea8d0\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"marco\",\"last_name\":\"f\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-4dc07aa3-72c6-4f51-973b-85ef64907b6d\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"role\":\"pharmacist\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-d12c0411-7a47-4fa8-86d2-f3504ba3e72c\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-05-31T16:39:30Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"neri.pier99o@azienda.com\",\"email_id\":\"email-live-de2ba07f-0ccd-4b2b-8de9-198ae46b0d72\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"\",\"last_name\":\"\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-eb5bd0a6-2b05-4090-a281-a2eeafc25482\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{},\"untrusted_metadata\":{},\"user_id\":\"user-live-6e661a7b-129d-4a91-805b-31e062c40ca4\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-04-09T17:35:52Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"flaviana.buccho@azienda.com\",\"email_id\":\"email-live-9ffbe8f9-a68f-4c38-9b7c-de72b325038e\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"flaviana\",\"last_name\":\"buccho\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-bdfd3e70-f76b-4554-bde8-b72dcee0183d\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"role\":\"seller\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-3927dadb-5d0b-4e0e-9738-dda318f29270\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-04-08T11:35:48Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"luigi.bianchi@azienda.com\",\"email_id\":\"email-live-5cf4bf17-1059-4962-a7ac-9cdb3d7c98a9\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"luigi\",\"last_name\":\"bianchi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-67052b05-bcd4-46c8-9fe1-510b54ee5ade\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"permissions\":{\"pharma\":[\"create\",\"read\",\"update\"]},\"role\":\"purchase\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-04-07T11:57:39Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"donlucadj@gmail.com\",\"email_id\":\"email-live-88e58d98-7c44-4be6-8247-5332e8707a62\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"Luca Giuseppe Cali\u00F2\",\"last_name\":\"Cali\u00F2\",\"middle_name\":\"\"},\"password\":null,\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{},\"untrusted_metadata\":{},\"user_id\":\"user-live-b079ae67-d122-4604-a30a-2bdda002be6b\",\"webauthn_registrations\":[]}],\"results_metadata\":{\"next_cursor\":null,\"total\":6},\"status_code\":200}\n";
        System.out.println(UserService.extract_results(json));

    }




    @Nested
    class Deserialization_object {
        Session session;

        @BeforeEach
        public void setUp() {


            String jsonContent = null;
            try {
                jsonContent = Files.readString(Paths.get("src/main/java/json/file3.json"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            session = UserService.deserialization_object(jsonContent, Session.class).get();
        }

        @Test
        void ValidDeserialization_objectNumber() throws FileNotFoundException {


            Assertions.assertEquals(3, session.getSessions().size());

        }
        @Test
        void ValidDeserialization_object_number_get_variable(){
            String value=session.getSessions().getFirst().getAuthenticationFactor().getFirst().getLast_authenticated_at();
            Assertions.assertEquals("2025-06-30T09:26:19Z",value);

        }


        @Test
        void recent_last_access() {
        Instant instant_expected= Instant.parse( "2025-06-30T09:40:08Z");
        Assertions.assertEquals(instant_expected,UserService.recent_last_access(session));

        }
    }






}