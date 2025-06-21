/*
package pharma.chat.Server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.chat.SimpleInMemoryKeystore;
import pharma.config.auth.ExpireToken;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;

import java.io.*;
import java.security.KeyStore;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;

class ServerSocketTest {
    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);


    }

    @Test
    public void test() {


    }

    public  InputStream convert_data_into_memory() {
        try {
            FileInputStream fileStream = new FileInputStream("chacha20-truststore.jks");

// Convert to memory
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[8192];
            int bytesRead;
            while ((bytesRead = fileStream.read(data)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            fileStream.close();
            return new ByteArrayInputStream(buffer.toByteArray());
        } catch (IOException e) {

            e.printStackTrace();
        }
            return null;


    }

    @Test
    public void ValidInMemory(){

        InputStream inputStream=convert_data_into_memory();
        Assertions.assertNotNull(inputStream);
    }


    @Test
    public void  ValidKeyStore(){
    */
/*    FileInputStream fileInputStream= null;
        try {
            fileInputStream = new FileInputStream("chacha20-keystore.jks");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }*//*

        InputStream fileInputStream=convert_data_into_memory();
        KeyStore keyStore= null;
        try {
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(fileInputStream,"password".toCharArray());
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotNull(keyStore);



    }



    @Test
    public  void ValidSocket(){
        try {
            String jsonString = "{\"request_id\":\"request-id-live-823eae09-62ca-4437-8d47-6b7e05e485ed\",\"session\":{\"attributes\":{\"ip_address\":\"\",\"user_agent\":\"\"},\"authentication_factors\":[{\"created_at\":\"2025-06-11T09:47:47Z\",\"delivery_method\":\"knowledge\",\"last_authenticated_at\":\"2025-06-11T09:47:47Z\",\"type\":\"password\",\"updated_at\":\"2025-06-11T09:47:47Z\"}],\"custom_claims\":{},\"expires_at\":\"2025-06-11T13:47:47Z\",\"last_accessed_at\":\"2025-06-11T09:47:47Z\",\"session_id\":\"session-live-2b3955e7-23da-40fa-8c7d-536671129ba5\",\"started_at\":\"2025-06-11T09:47:47Z\",\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\"},\"session_jwt\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NDk2MzU1NjcsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLTJiMzk1NWU3LTIzZGEtNDBmYS04YzdkLTUzNjY3MTEyOWJhNSIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA2LTExVDA5OjQ3OjQ3WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA2LTExVDA5OjQ3OjQ3WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA2LTExVDEzOjQ3OjQ3WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNi0xMVQwOTo0Nzo0N1oifV19LCJpYXQiOjE3NDk2MzUyNjcsImlzcyI6Imh0dHBzOi8vbG9naW4uYXBpbWUub25saW5lIiwibmJmIjoxNzQ5NjM1MjY3LCJzdWIiOiJ1c2VyLWxpdmUtNWFjM2U5YmQtZWEwMy00MmEwLWFkMGItZGZlMjRlZTExMjg2In0.advKHarunjYoLXO7OebglRqkrFOrYqAu_N2YyOmdVpIdS4qrEFMDoEvKjmS55MxA0oUiGkrsktMhaOW5Oo7uKhI_KcGJoN6ExD-Svia-AFdsrpiUhOVE4q8D5neJFTvKCyys5txi3oevsMv1d1sODhkTZIC0noDgS8YYsWx6CaJQj_vRLXk7ZBbhOCVY8nGQiDBnjAiKIANO4u5BTDZgjHPsI61zN9qDdD2Yg3bnr5x_NTlEVZKIHoH65lIB0momBoyUSP-INwEIER8RyH6Rz1rEaqamuK-A1gBg78q7iinIjaG1_FcXwnxmt_Ev7M-a1XhaDvWRsBVxcpytRAqW3Q\",\"session_token\":\"mv54fovj7MPYpYkr4MBqvMW61m47K9NcVPyFCTm145Tk\",\"status_code\":200,\"user\":{\"biometric_registrations\":[],\"created_at\":\"2025-04-08T11:35:48Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"luigi.bianchi@azienda.com\",\"email_id\":\"email-live-5cf4bf17-1059-4962-a7ac-9cdb3d7c98a9\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"luigi\",\"last_name\":\"bianchi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-67052b05-bcd4-46c8-9fe1-510b54ee5ade\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"permissions\":{\"pharma\":[\"create\",\"read\",\"update\"]},\"role\":\"seller\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\",\"webauthn_registrations\":[]},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\"}";
            UserServiceResponse userServiceResponse=new UserServiceResponse(jsonString,200);
            when(userService.authenticate_jwt(Mockito.anyString())).thenReturn(userServiceResponse);

            FileInputStream fileInputStream=new FileInputStream("chacha.p12");
            ServerSocket serverSocket=new ServerSocket(fileInputStream,userService);

            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExpireToken e) {
            throw new RuntimeException(e);
        }


    }


}

*/
