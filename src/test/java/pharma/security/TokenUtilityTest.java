package pharma.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.yaml.snakeyaml.tokens.Token;


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
        String role=TokenUtility.extractRole(token);
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
    void extract_token() {
        String value="{\"request_id\":\"request-id-live-bd370335-0991-4243-acae-11899c65677f\",\"session\":{\"attributes\":{\"ip_address\":\"\",\"user_agent\":\"\"},\"authentication_factors\":[{\"created_at\":\"2025-04-08T17:42:04Z\",\"delivery_method\":\"knowledge\",\"last_authenticated_at\":\"2025-04-08T17:42:04Z\",\"type\":\"password\",\"updated_at\":\"2025-04-08T17:42:04Z\"}],\"custom_claims\":{},\"expires_at\":\"2025-04-08T21:42:04Z\",\"last_accessed_at\":\"2025-04-08T17:52:37Z\",\"session_id\":\"session-live-f6d68a9a-95c6-479b-99ba-f77139511e1b\",\"started_at\":\"2025-04-08T17:42:04Z\",\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\"},\"session_jwt\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NDQxMzUwNTcsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLWY2ZDY4YTlhLTk1YzYtNDc5Yi05OWJhLWY3NzEzOTUxMWUxYiIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA0LTA4VDE3OjQyOjA0WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA0LTA4VDE3OjUyOjM3WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA0LTA4VDIxOjQyOjA0WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNC0wOFQxNzo0MjowNFoifV19LCJpYXQiOjE3NDQxMzQ3NTcsImlzcyI6InN0eXRjaC5jb20vcHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiIsIm5iZiI6MTc0NDEzNDc1Nywic3ViIjoidXNlci1saXZlLTVhYzNlOWJkLWVhMDMtNDJhMC1hZDBiLWRmZTI0ZWUxMTI4NiJ9.K1LkETkp0rXLK5MdyXqiKkJnDkzg9yeGQUyOCZvCauwZzlEshQ5I0MjC7DK7rLNvrGgfUB_oK1EfTjRNRbL-_pDIOF5q3Zwo-OiD56Diy1KeCJagfq_tE41ElqoYGuNBQX-tsPMMrs5BMcyeTwJJlmS02j40jEaD16TU2W40h-WgevAefUr_bketu4osn1kUb0yeKjkAbC_uSmPRLuFeMsACKagF0nGn_pR3i6aoNnrBwkAFOphSNCnMaclw5uNAm7c2FATPrE9szPFDyJC1m_Q58IqOJj8H9PTP9Amg4aV_G4Gnj7ZjGz2xY1DQ8NklOrZ4byFE82N3Ul7sX9nhbA\",\"session_token\":\"\",\"status_code\":200,\"user\":{\"biometric_registrations\":[],\"created_at\":\"2025-04-08T11:35:48Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"luigi.bianchi@azienda.com\",\"email_id\":\"email-live-5cf4bf17-1059-4962-a7ac-9cdb3d7c98a9\",\"verified\":false}],\"external_id\":null,\"name\":{\"first_name\":\"luigi\",\"last_name\":\"bianchi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-67052b05-bcd4-46c8-9fe1-510b54ee5ade\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"operation\":{\"phrama\":[\"create\",\"view\"]},\"role\":\"purchase\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\",\"webauthn_registrations\":[]}}\n";
        String token="eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NDQxMzUwNTcsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLWY2ZDY4YTlhLTk1YzYtNDc5Yi05OWJhLWY3NzEzOTUxMWUxYiIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA0LTA4VDE3OjQyOjA0WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA0LTA4VDE3OjUyOjM3WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA0LTA4VDIxOjQyOjA0WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNC0wOFQxNzo0MjowNFoifV19LCJpYXQiOjE3NDQxMzQ3NTcsImlzcyI6InN0eXRjaC5jb20vcHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiIsIm5iZiI6MTc0NDEzNDc1Nywic3ViIjoidXNlci1saXZlLTVhYzNlOWJkLWVhMDMtNDJhMC1hZDBiLWRmZTI0ZWUxMTI4NiJ9.K1LkETkp0rXLK5MdyXqiKkJnDkzg9yeGQUyOCZvCauwZzlEshQ5I0MjC7DK7rLNvrGgfUB_oK1EfTjRNRbL-_pDIOF5q3Zwo-OiD56Diy1KeCJagfq_tE41ElqoYGuNBQX-tsPMMrs5BMcyeTwJJlmS02j40jEaD16TU2W40h-WgevAefUr_bketu4osn1kUb0yeKjkAbC_uSmPRLuFeMsACKagF0nGn_pR3i6aoNnrBwkAFOphSNCnMaclw5uNAm7c2FATPrE9szPFDyJC1m_Q58IqOJj8H9PTP9Amg4aV_G4Gnj7ZjGz2xY1DQ8NklOrZ4byFE82N3Ul7sX9nhbA";

        String expected=TokenUtility.extract_token(value);
        Assertions.assertEquals(token,expected);
    }

    @Test
    void ValidextractRole() {
        String value="{\"request_id\":\"request-id-live-bd370335-0991-4243-acae-11899c65677f\",\"session\":{\"attributes\":{\"ip_address\":\"\",\"user_agent\":\"\"},\"authentication_factors\":[{\"created_at\":\"2025-04-08T17:42:04Z\",\"delivery_method\":\"knowledge\",\"last_authenticated_at\":\"2025-04-08T17:42:04Z\",\"type\":\"password\",\"updated_at\":\"2025-04-08T17:42:04Z\"}],\"custom_claims\":{},\"expires_at\":\"2025-04-08T21:42:04Z\",\"last_accessed_at\":\"2025-04-08T17:52:37Z\",\"session_id\":\"session-live-f6d68a9a-95c6-479b-99ba-f77139511e1b\",\"started_at\":\"2025-04-08T17:42:04Z\",\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\"},\"session_jwt\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NDQxMzUwNTcsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLWY2ZDY4YTlhLTk1YzYtNDc5Yi05OWJhLWY3NzEzOTUxMWUxYiIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA0LTA4VDE3OjQyOjA0WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA0LTA4VDE3OjUyOjM3WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA0LTA4VDIxOjQyOjA0WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNC0wOFQxNzo0MjowNFoifV19LCJpYXQiOjE3NDQxMzQ3NTcsImlzcyI6InN0eXRjaC5jb20vcHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiIsIm5iZiI6MTc0NDEzNDc1Nywic3ViIjoidXNlci1saXZlLTVhYzNlOWJkLWVhMDMtNDJhMC1hZDBiLWRmZTI0ZWUxMTI4NiJ9.K1LkETkp0rXLK5MdyXqiKkJnDkzg9yeGQUyOCZvCauwZzlEshQ5I0MjC7DK7rLNvrGgfUB_oK1EfTjRNRbL-_pDIOF5q3Zwo-OiD56Diy1KeCJagfq_tE41ElqoYGuNBQX-tsPMMrs5BMcyeTwJJlmS02j40jEaD16TU2W40h-WgevAefUr_bketu4osn1kUb0yeKjkAbC_uSmPRLuFeMsACKagF0nGn_pR3i6aoNnrBwkAFOphSNCnMaclw5uNAm7c2FATPrE9szPFDyJC1m_Q58IqOJj8H9PTP9Amg4aV_G4Gnj7ZjGz2xY1DQ8NklOrZ4byFE82N3Ul7sX9nhbA\",\"session_token\":\"\",\"status_code\":200,\"user\":{\"biometric_registrations\":[],\"created_at\":\"2025-04-08T11:35:48Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"luigi.bianchi@azienda.com\",\"email_id\":\"email-live-5cf4bf17-1059-4962-a7ac-9cdb3d7c98a9\",\"verified\":false}],\"external_id\":null,\"name\":{\"first_name\":\"luigi\",\"last_name\":\"bianchi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-67052b05-bcd4-46c8-9fe1-510b54ee5ade\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"operation\":{\"phrama\":[\"create\",\"view\"]},\"role\":\"purchase\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\",\"webauthn_registrations\":[]}}\n";
        Assertions.assertEquals("purchase",TokenUtility.extractRole(value));


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