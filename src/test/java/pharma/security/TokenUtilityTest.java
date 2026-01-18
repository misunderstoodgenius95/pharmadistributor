package pharma.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pharma.Utility.TokenUtility;


import static org.mockito.Mockito.*;

class TokenUtilityTest {

    @Test
    void extractPermission() {
    }

    @Test
    void Valid_extractRole() {
    String token="aa.bb.cc";


    DecodedJWT decodedJWT=Mockito.mock(DecodedJWT.class);
    Claim claim=Mockito.mock(Claim.class);
    Mockito.when(decodedJWT.getClaim("role")).thenReturn(claim);

    Mockito.when(claim.asString()).thenReturn("ADMIN");
    try(MockedStatic<JWT> jwtMockedStatic=Mockito.mockStatic(JWT.class)){
      jwtMockedStatic.when(()->JWT.decode(token)).thenReturn(decodedJWT);
        String role= TokenUtility.extractRole(token);
        System.out.println(role);
        Assertions.assertEquals("ADMIN",role);
        verify(decodedJWT).getClaim("role");
        verify(claim).asString();
    }
    }

    @Test
    void InValid_extractRoleEmpty() {
      String token=" ";
        try(MockedStatic<JWT> jwtMockedStatic=Mockito.mockStatic(JWT.class)){
            jwtMockedStatic.when(()->JWT.decode(anyString())).thenThrow(JWTDecodeException.class);
            Assertions.assertThrows(JWTDecodeException.class,()->{
             TokenUtility.extractRole(token);
            });



        }
    }

    @Test
    void check_permission() {
    }

    @Test
    void testExtractRole() {
        // Mock the JWT

    }

    @Test
    void extract_role() {
     String token="{\"session_jwt\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay10ZXN0LWJhY2ExNzc2LTdhMzktNDcxZS1hMDQ1LWRjZjc0YTk0NDc5YiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC10ZXN0LWVhZDcwNzdjLWUyNWYtNGZlMS1iYTYzLTNlNTk3MmFiMzRlYyJdLCJleHAiOjE3NTkzMzcwNTIsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi10ZXN0LWE2OWQzNmE1LTY1MTktNGFkOC05YTk5LTliNzcyMzJiMzFjOSIsInN0YXJ0ZWRfYXQiOiIyMDI1LTEwLTAxVDE2OjM5OjEyWiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTEwLTAxVDE2OjM5OjEyWiIsImV4cGlyZXNfYXQiOiIyMDI1LTEwLTAxVDIwOjM5OjEyWiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0xMC0wMVQxNjozOToxMloifV19LCJpYXQiOjE3NTkzMzY3NTIsImlzcyI6Imh0dHBzOi8vZGVsaWNpb3VzLW5vc2UtOTI5OC5jdXN0b21lcnMuc3R5dGNoLmRldiIsIm5iZiI6MTc1OTMzNjc1Miwic3ViIjoidXNlci10ZXN0LTg2MzJmODhkLWMyMDktNGMxYy1iOGY0LTczYjI5MGEzMDBhZSJ9.XwPQL61mDrCKleQHJi-ubAhi3qP4I5cUjxBjJRM6_cc04h6Xlf8b2BbepT81L02rKRKAStXEi5fE78DBJuCdDC4qsEjy1rBN3c7xFrhoaRdzoPahSL7wqcln_a_d-spTWBBdJa_QQPf4rJ-mO-832sWMQ7f-3oxMUqic2wl-a9Dz5U5-1Kes5eufvUYYaTRHApz_NXI_0KnPiG4AvEX8F0oIlrtOikObI-w3fDDrQ33XoGOAwwJQhXaJiNwGbcxXg8NFcUW4DciUjqhS3Gua35MYNkd1FrEobFBzLZLJQnKu6l-JU0x_gJP0-uvOMcfh2trwXcXYwrYmioRrEFRNtg\",\"session_token\":\"b8s3rIK3YtPEfAO3tZoy9qgLzSq0vLE4zMZr8isKOINf\",\"status_code\":200,\"user_id\":\"user-test-8632f88d-c209-4c1c-b8f4-73b290a300ae\",\"session\":{\"expires_at\":\"2025-10-01T20:39:12Z\",\"user_id\":\"user-test-8632f88d-c209-4c1c-b8f4-73b290a300ae\",\"authentication_factors\":[{\"updated_at\":\"2025-10-01T16:39:12Z\",\"delivery_method\":\"knowledge\",\"created_at\":\"2025-10-01T16:39:12Z\",\"type\":\"password\",\"last_authenticated_at\":\"2025-10-01T16:39:12Z\"}],\"session_id\":\"session-test-a69d36a5-6519-4ad8-9a99-9b77232b31c9\",\"started_at\":\"2025-10-01T16:39:12Z\",\"attributes\":{\"ip_address\":\"\",\"user_agent\":\"\"},\"last_accessed_at\":\"2025-10-01T16:39:12Z\",\"custom_claims\":{}},\"request_id\":\"request-id-test-019470ef-7003-4605-9c22-6464ca4100ab\",\"user\":{\"lock_expires_at\":null,\"is_locked\":false,\"roles\":[\"stytch_user\",\"purchase\"],\"created_at\":\"2025-10-01T13:19:43Z\",\"crypto_wallets\":[],\"external_id\":null,\"totps\":[],\"biometric_registrations\":[],\"emails\":[{\"email_id\":\"email-test-4cf02215-8693-4c59-8ab5-2d5c547a8850\",\"verified\":true,\"email\":\"luigi.neri_azienda@proton.me\"}],\"phone_numbers\":[],\"password\":{\"password_id\":\"password-test-d496e01f-cf5d-4201-8fe3-2fb9778c2c47\",\"requires_reset\":false},\"trusted_metadata\":{\"is_enable\":true},\"user_id\":\"user-test-8632f88d-c209-4c1c-b8f4-73b290a300ae\",\"lock_created_at\":null,\"webauthn_registrations\":[],\"name\":{\"last_name\":\"Neri\",\"middle_name\":\"\",\"first_name\":\"Luigi\"},\"untrusted_metadata\":{},\"providers\":[],\"status\":\"active\"},\"user_device\":null}";
        String expected=TokenUtility.extractRole(token);

        Assertions.assertEquals("purchase",expected);
    }



    @Test
     void ValidExtactToken(){
        String value="{\"request_id\":\"request-id-live-ceb6c4a2-6424-42cf-b9c6-6b61d7a32757\",\"session\":null,\"session_jwt\":\"\",\"session_token\":\"\",\"status_code\":200,\"user\":{\"biometric_registrations\":[],\"created_at\":\"2025-04-08T11:35:48Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"luigi.bianchi@azienda.com\",\"email_id\":\"email-live-5cf4bf17-1059-4962-a7ac-9cdb3d7c98a9\",\"verified\":false}],\"external_id\":null,\"name\":{\"first_name\":\"luigi\",\"last_name\":\"bianchi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-67052b05-bcd4-46c8-9fe1-510b54ee5ade\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"operation\":{\"phrama\":[\"create\",\"view\"]},\"role\":\"purchase\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\",\"webauthn_registrations\":[]},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\"}\n";
        Assertions.assertFalse(TokenUtility.extract_token(value).isEmpty());



    }

    @Test
    void testCheck_permission() {
        String extract = "{ \"request_id\":\"request-id-live-97c55986-0f30-4755-be02-2549bf5b93e4\","
                + "\"session\":{"
                + "\"attributes\":{\"ip_address\":\"\",\"user_agent\":\"\"},"
                + "\"authentication_factors\":[{"
                + "\"created_at\":\"2025-06-04T16:30:30Z\","
                + "\"delivery_method\":\"knowledge\","
                + "\"last_authenticated_at\":\"2025-06-04T16:30:30Z\","
                + "\"type\":\"password\","
                + "\"updated_at\":\"2025-06-04T16:30:30Z\""
                + "}],"
                + "\"custom_claims\":{},"
                + "\"expires_at\":\"2025-06-04T20:30:30Z\","
                + "\"last_accessed_at\":\"2025-06-04T16:31:25Z\","
                + "\"session_id\":\"session-live-6d5cf602-7e9e-40be-9744-331d168d23d4\","
                + "\"started_at\":\"2025-06-04T16:30:30Z\","
                + "\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\""
                + "},"
                + "\"session_jwt\":\"eyJhbGciOi...zFBZqhjB6q-BA\","
                + "\"session_token\":\"\","
                + "\"status_code\":200,"
                + "\"user\":{"
                + "\"biometric_registrations\":[],"
                + "\"created_at\":\"2025-04-08T11:35:48Z\","
                + "\"crypto_wallets\":[],"
                + "\"emails\":[{"
                + "\"email\":\"luigi.bianchi@azienda.com\","
                + "\"email_id\":\"email-live-5cf4bf17-1059-4962-a7ac-9cdb3d7c98a9\","
                + "\"verified\":false"
                + "}],"
                + "\"external_id\":null,"
                + "\"is_locked\":false,"
                + "\"lock_created_at\":null,"
                + "\"lock_expires_at\":null,"
                + "\"name\":{"
                + "\"first_name\":\"luigi\","
                + "\"last_name\":\"bianchi\","
                + "\"middle_name\":\"\""
                + "},"
                + "\"password\":{"
                + "\"password_id\":\"password-live-67052b05-bcd4-46c8-9fe1-510b54ee5ade\","
                + "\"requires_reset\":false"
                + "},"
                + "\"phone_numbers\":[],"
                + "\"providers\":[],"
                + "\"status\":\"active\","
                + "\"totps\":[],"
                + "\"trusted_metadata\":{"
                + "\"permissions\":{\"pharma\":[\"create\",\"read\",\"update\"]},"
                + "\"role\":\"purchase\""
                + "},"
                + "\"untrusted_metadata\":{},"
                + "\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\","
                + "\"webauthn_registrations\":[]"
                + "}"
                + "}";
      //  System.out.println(TokenUtility.extractRole(extract));
        String actual=TokenUtility.extract_email(extract);
        Assertions.assertEquals("luigi.bianchi@azienda.com",actual);




    }

    @Test
    public void  ValidTestIsEnable(){
        StringBuilder json = new StringBuilder();
        json.append("[{")
                .append("\"biometric_registrations\":[],")
                .append("\"created_at\":\"2025-04-09T17:35:52Z\",")
                .append("\"crypto_wallets\":[],")
                .append("\"emails\":[{")
                .append("\"email\":\"flaviana.buccho@azienda.com\",")
                .append("\"email_id\":\"email-live-9ffbe8f9-a68f-4c38-9b7c-de72b325038e\",")
                .append("\"verified\":false")
                .append("}],")
                .append("\"external_id\":null,")
                .append("\"is_locked\":false,")
                .append("\"lock_created_at\":null,")
                .append("\"lock_expires_at\":null,")
                .append("\"name\":{")
                .append("\"first_name\":\"flaviana\",")
                .append("\"last_name\":\"buccho\",")
                .append("\"middle_name\":\"\"")
                .append("},")
                .append("\"password\":{")
                .append("\"password_id\":\"password-live-bdfd3e70-f76b-4554-bde8-b72dcee0183d\",")
                .append("\"requires_reset\":false")
                .append("},")
                .append("\"phone_numbers\":[],")
                .append("\"providers\":[],")
                .append("\"roles\":[],")
                .append("\"status\":\"active\",")
                .append("\"totps\":[],")
                .append("\"trusted_metadata\":{\"role\":\"seller\", \"is_enable\":true}")
                .append("\"untrusted_metadata\":{},")
                .append("\"user_id\":\"user-live-3927dadb-5d0b-4e0e-9738-dda318f29270\",")
                .append("\"webauthn_registrations\":[]")
                .append("}]");
        System.out.println(json);


    }
}