package pharma.config.auth;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pharma.Model.Session;
import pharma.Model.User;
import pharma.Storage.FileStorage;
import pharma.config.PathConfig;
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

class UserGatewayTest {
    @Mock
    private StytchClient stytchClient;
    @Mock
    private HttpResponse<String> httpResponse_user;
    private UserGateway userGateway;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userGateway = new UserGateway(stytchClient);
    }

    @Test
    void ValidSearchUser() {
        when(httpResponse_user.statusCode()).thenReturn(200);
        when(stytchClient.get_users()).thenReturn(httpResponse_user);

        Assertions.assertEquals(200, userGateway.searchUsers().getStatus());
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

        User user = userGateway.get_user_byRole("seller");
        Assertions.assertEquals(1, user.getResults().size());
        // Assertions.assertEquals("paolobiasin.rossi@example.com",user.getResults().getFirst().getEmails().getFirst().getEmail());


    }


    @AfterEach
    public void tearDown() {
        stytchClient = null;
        userGateway = null;
    }

    @Test
    public void SearchIntegrate() throws FileNotFoundException {

        HashMap<String, String> hashMap_json =
                FileStorage.getProperties(List.of("project_id", "secret", "url"), new FileReader(PathConfig.STYTCH_CONF.getValue()));
        UserGateway userGateway = new UserGateway(new StytchClient(hashMap_json.get("project_id"), hashMap_json.get("secret"), hashMap_json.get("url")));
        User user = userGateway.get_user_byRole("seller");
        Assertions.assertEquals(1, user.getResults().size());
        /*    UserGatewayResponse ur=userGateway.searchUser();

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


        HashMap<String, String> hashMap_json =
                FileStorage.getProperties(List.of("project_id", "secret", "url"), new FileReader(PathConfig.STYTCH_CONF.getValue()));
        UserGateway userGateway = new UserGateway(new StytchClient(hashMap_json.get("project_id"), hashMap_json.get("secret"), hashMap_json.get("url")));
        UserGatewayResponse ur = userGateway.user_revocate("user-live-3927dadb-5d0b-4e0e-9738-dda318f29270", true);
        System.out.println(ur.getBody());
        //Assertions.assertEquals(200,ur.getStatus());


    }

    @Test
    void user_update_role() {


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
                user = new User(list);
            });
        }

        @Test
        void VoidSizeTwo() {
            Assertions.assertEquals(2, user.getResults().size());
        }

        @Test
        public void ValidFirstEmailOne() {
            // Assertions.assertEquals(2,UserGateway.extract_Session_role(user,"seller").getResults().size());


        }


    }


    @Nested
    class UserbyEmail {
        UserGateway userGateway;
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
            userGateway = new UserGateway(stytchClient);
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
            user = userGateway.searchUserByEmail("flaviana.buccho@azienda.com");
        }

        @Test
        public void TestValid() {


            Assertions.assertEquals("user-live-3927dadb-5d0b-4e0e-9738-dda318f29270", user.getResults().getFirst().getUser_id());


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

            Assertions.assertEquals("seller", user.getResults().getFirst().getTrustedMetadata().getRole());
        }

        @Test
        void ValidIsEnable() {

            Assertions.assertTrue(user.getResults().getFirst().getTrustedMetadata().isIs_enable());
        }

        @Test
        void ValidInstant() {

            Instant instant = Instant.parse("2025-06-30T09:40:08Z");
            Assertions.assertEquals(instant, user.getResults().getFirst().getLast_access());
        }


    }


    @Test
    public void ValidExtractResults() {
        String json = "{\"request_id\":\"request-id-live-47c98ff5-4bf6-4824-b82b-76fba9bc7162\",\"results\":[{\"biometric_registrations\":[],\"created_at\":\"2025-06-28T07:13:18Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"paola.rossi@example.com\",\"email_id\":\"email-live-506fb0ad-7f76-4d46-9029-62fd71313eba\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"paola\",\"last_name\":\"rossi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-16bbb443-b755-4276-8a0a-804ec0a4298f\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"role\":\"admin\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-a462acc5-230a-49e3-bb13-9f664e8cc6d1\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-06-17T16:58:29Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"marco@farmaciamia.com\",\"email_id\":\"email-live-0c0bda3b-1184-43d2-b6e5-a8290daea8d0\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"marco\",\"last_name\":\"f\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-4dc07aa3-72c6-4f51-973b-85ef64907b6d\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"role\":\"pharmacist\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-d12c0411-7a47-4fa8-86d2-f3504ba3e72c\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-05-31T16:39:30Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"neri.pier99o@azienda.com\",\"email_id\":\"email-live-de2ba07f-0ccd-4b2b-8de9-198ae46b0d72\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"\",\"last_name\":\"\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-eb5bd0a6-2b05-4090-a281-a2eeafc25482\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{},\"untrusted_metadata\":{},\"user_id\":\"user-live-6e661a7b-129d-4a91-805b-31e062c40ca4\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-04-09T17:35:52Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"flaviana.buccho@azienda.com\",\"email_id\":\"email-live-9ffbe8f9-a68f-4c38-9b7c-de72b325038e\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"flaviana\",\"last_name\":\"buccho\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-bdfd3e70-f76b-4554-bde8-b72dcee0183d\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"role\":\"seller\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-3927dadb-5d0b-4e0e-9738-dda318f29270\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-04-08T11:35:48Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"luigi.bianchi@azienda.com\",\"email_id\":\"email-live-5cf4bf17-1059-4962-a7ac-9cdb3d7c98a9\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"luigi\",\"last_name\":\"bianchi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-67052b05-bcd4-46c8-9fe1-510b54ee5ade\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"permissions\":{\"pharma\":[\"create\",\"read\",\"update\"]},\"role\":\"purchase\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\",\"webauthn_registrations\":[]},{\"biometric_registrations\":[],\"created_at\":\"2025-04-07T11:57:39Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"donlucadj@gmail.com\",\"email_id\":\"email-live-88e58d98-7c44-4be6-8247-5332e8707a62\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"Luca Giuseppe Cali\u00F2\",\"last_name\":\"Cali\u00F2\",\"middle_name\":\"\"},\"password\":null,\"phone_numbers\":[],\"providers\":[],\"roles\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{},\"untrusted_metadata\":{},\"user_id\":\"user-live-b079ae67-d122-4604-a30a-2bdda002be6b\",\"webauthn_registrations\":[]}],\"results_metadata\":{\"next_cursor\":null,\"total\":6},\"status_code\":200}\n";
        System.out.println(UserGateway.extract_results(json));

    }


    @Nested
    class Deserialization_object {
        Session session;

        @BeforeEach
        public void setUp() {


            String jsonContent = null;
            try {
                jsonContent = Files.readString(Paths.get("src/main/java/json/fileuser.json"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            session = UserGateway.deserialization_object(jsonContent, Session.class).get();
        }

        @Test
        void ValidDeserialization_objectNumber() throws FileNotFoundException {


            Assertions.assertEquals(3, session.getSessions().size());

        }

        @Test
        void ValidDeserialization_object_number_get_variable() {
            String value = session.getSessions().getFirst().getAuthenticationFactor().getFirst().getLast_authenticated_at();
            Assertions.assertEquals("2025-06-30T09:26:19Z", value);

        }


        @Test
        void recent_last_access() {
            Instant instant_expected = Instant.parse("2025-06-30T09:40:08Z");
            Assertions.assertEquals(instant_expected, UserGateway.recent_last_access(session));

        }
    }

    @Nested
    class SessionNoPresent {
        Session session;

        @BeforeEach
        public void setUp() {


            String jsonContent = null;
            try {
                jsonContent = Files.readString(Paths.get("src/main/java/json/file3_not.json"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            session = UserGateway.deserialization_object(jsonContent, Session.class).get();
        }


        @Test
        void recent_last_access() {
            Instant instant_expected = Instant.parse("2025-06-30T09:40:08Z");
            Assertions.assertEquals(instant_expected, UserGateway.recent_last_access(session).get());

        }
    }


    @Test
    public void ValidIsEnabled(){
        String jsonResponse = """
{
  "request_id": "request-id-test-c3267f8e-cb0d-49a2-90ed-eb4bdbcdaa07",
  "session": {
    "attributes": {
      "ip_address": "",
      "user_agent": ""
    },
    "authentication_factors": [
      {
        "created_at": "2025-11-12T10:35:49Z",
        "delivery_method": "knowledge",
        "last_authenticated_at": "2025-11-12T10:35:49Z",
        "type": "password",
        "updated_at": "2025-11-12T10:35:49Z"
      }
    ],
    "custom_claims": {},
    "expires_at": "2025-11-12T14:35:49Z",
    "last_accessed_at": "2025-11-12T10:35:49Z",
    "roles": ["stytch_user", "admin"],
    "session_id": "session-test-46aea77c-e522-478e-8263-4fabc6fe8653",
    "started_at": "2025-11-12T10:35:49Z",
    "user_id": "user-test-5c360d12-bd52-4454-806d-efb45aac5333"
  },
  "session_jwt": "eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay10ZXN0LWJhY2ExNzc2LTdhMzktNDcxZS1hMDQ1LWRjZjc0YTk0NDc5YiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC10ZXN0LWVhZDcwNzdjLWUyNWYtNGZlMS1iYTYzLTNlNTk3MmFiMzRlYyJdLCJleHAiOjE3NjI5NDQwNDksImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi10ZXN0LTQ2YWVhNzdjLWU1MjItNDc4ZS04MjYzLTRmYWJjNmZlODY1MyIsInN0YXJ0ZWRfYXQiOiIyMDI1LTExLTEyVDEwOjM1OjQ5WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTExLTEyVDEwOjM1OjQ5WiIsImV4cGlyZXNfYXQiOiIyMDI1LTExLTEyVDE0OjM1OjQ5WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0xMS0xMlQxMDozNTo0OVoifV0sInJvbGVzIjpbInN0eXRjaF91c2VyIiwiYWRtaW4iXX0sImlhdCI6MTc2Mjk0Mzc0OSwiaXNzIjoiaHR0cHM6Ly9kZWxpY2lvdXMtbm9zZS05Mjk4LmN1c3RvbWVycy5zdHl0Y2guZGV2IiwibmJmIjoxNzYyOTQzNzQ5LCJzdWIiOiJ1c2VyLXRlc3QtNWMzNjBkMTItYmQ1Mi00NDU0LTgwNmQtZWZiNDVhYWM1MzMzIn0.ovl_ow58mkcEPIU0LjxzMW7oicfSGGR2KNIeckzBvaPrXGVJTb99AVgTx7rXS_UqMqW_Xr_ACtN6GXniHH4Ik2GFX9bHb1nIZe9CjnTh7-MF4JI49v_F1kl14CmZypsryEhodMQo3d0GkU5xv8HqIuAhT7gyfuJVLWKbC2koOgAdOVn4r-YWhU2JS1HMssxU57Gl9LrkFurf2ikpMV0mYXGG2uHhODwSAMRA_T2-yFgB4hlBFph8fT4OEZSwxI4D41VOslVaJDhCTjAjDQE0g6D-_uJ8JQqi5-ZcS3vKZtzpQjOYDHFqrALiQDg0wckbdxb6kPS1yuaAHnDDKDlm7g",
  "session_token": "YETL_X_iqfAvBnirW_soXULiHB1RX59g2nm5jaVIOzb_",
  "status_code": 200,
  "user": {
    "biometric_registrations": [],
    "created_at": "2025-11-12T10:22:37Z",
    "crypto_wallets": [],
    "emails": [
      {
        "email": "annalisa.carone@proton.me",
        "email_id": "email-test-4a287944-e82f-4366-93f8-4a54b185101a",
        "verified": true
      }
    ],
    "external_id": null,
    "is_locked": false,
    "lock_created_at": null,
    "lock_expires_at": null,
    "name": {
      "first_name": "annalisa",
      "last_name": "carone",
      "middle_name": ""
    },
    "password": {
      "password_id": "password-test-7c243e07-cd61-4c2f-9ce8-bafe54b686d6",
      "requires_reset": false
    },
    "phone_numbers": [],
    "providers": [],
    "roles": ["stytch_user", "admin"],
    "status": "active",
    "totps": [],
    "trusted_metadata": {
      "is_enable": true
    },
    "untrusted_metadata": {},
    "user_id": "user-test-5c360d12-bd52-4454-806d-efb45aac5333",
    "webauthn_registrations": []
  },
  "user_device": null,
  "user_id": "user-test-5c360d12-bd52-4454-806d-efb45aac5333"
}
""";
        JSONObject jsonObject=new JSONObject(jsonResponse);
        String user_json=jsonObject.getJSONObject("user").toString();
 User user= UserGateway.deserialization_object(user_json,User.class).get();
        System.out.println(user.getTrustedMetadata().isIs_enable());
  /*     JSONObject jsonObject=new JSONObject(jsonResponse);
        String session=jsonObject.getJSONObject("user").getJSONObject("trusted_metadata").toString();
        System.out.println(session);*/





    }



}







