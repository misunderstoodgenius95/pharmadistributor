package pharma.test2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.config.auth.ExpireToken;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;

import java.util.concurrent.Executors;

import static org.mockito.Mockito.when;

class ChatServerTest {
    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);


    }
    @Test
    public void test() throws ExpireToken {

        String jsonString_seller = "{\"request_id\":\"request-id-live-823eae09-62ca-4437-8d47-6b7e05e485ed\",\"session\":{\"attributes\":{\"ip_address\":\"\",\"user_agent\":\"\"},\"authentication_factors\":[{\"created_at\":\"2025-06-11T09:47:47Z\",\"delivery_method\":\"knowledge\",\"last_authenticated_at\":\"2025-06-11T09:47:47Z\",\"type\":\"password\",\"updated_at\":\"2025-06-11T09:47:47Z\"}],\"custom_claims\":{},\"expires_at\":\"2025-06-11T13:47:47Z\",\"last_accessed_at\":\"2025-06-11T09:47:47Z\",\"session_id\":\"session-live-2b3955e7-23da-40fa-8c7d-536671129ba5\",\"started_at\":\"2025-06-11T09:47:47Z\",\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\"},\"session_jwt\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NDk2MzU1NjcsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLTJiMzk1NWU3LTIzZGEtNDBmYS04YzdkLTUzNjY3MTEyOWJhNSIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA2LTExVDA5OjQ3OjQ3WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA2LTExVDA5OjQ3OjQ3WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA2LTExVDEzOjQ3OjQ3WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNi0xMVQwOTo0Nzo0N1oifV19LCJpYXQiOjE3NDk2MzUyNjcsImlzcyI6Imh0dHBzOi8vbG9naW4uYXBpbWUub25saW5lIiwibmJmIjoxNzQ5NjM1MjY3LCJzdWIiOiJ1c2VyLWxpdmUtNWFjM2U5YmQtZWEwMy00MmEwLWFkMGItZGZlMjRlZTExMjg2In0.advKHarunjYoLXO7OebglRqkrFOrYqAu_N2YyOmdVpIdS4qrEFMDoEvKjmS55MxA0oUiGkrsktMhaOW5Oo7uKhI_KcGJoN6ExD-Svia-AFdsrpiUhOVE4q8D5neJFTvKCyys5txi3oevsMv1d1sODhkTZIC0noDgS8YYsWx6CaJQj_vRLXk7ZBbhOCVY8nGQiDBnjAiKIANO4u5BTDZgjHPsI61zN9qDdD2Yg3bnr5x_NTlEVZKIHoH65lIB0momBoyUSP-INwEIER8RyH6Rz1rEaqamuK-A1gBg78q7iinIjaG1_FcXwnxmt_Ev7M-a1XhaDvWRsBVxcpytRAqW3Q\",\"session_token\":\"mv54fovj7MPYpYkr4MBqvMW61m47K9NcVPyFCTm145Tk\",\"status_code\":200,\"user\":{\"biometric_registrations\":[],\"created_at\":\"2025-04-08T11:35:48Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"luigi.bianchi@azienda.com\",\"email_id\":\"email-live-5cf4bf17-1059-4962-a7ac-9cdb3d7c98a9\",\"verified\":false}],\"external_id\":null,\"is_locked\":false,\"lock_created_at\":null,\"lock_expires_at\":null,\"name\":{\"first_name\":\"luigi\",\"last_name\":\"bianchi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-67052b05-bcd4-46c8-9fe1-510b54ee5ade\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"permissions\":{\"pharma\":[\"create\",\"read\",\"update\"]},\"role\":\"seller\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\",\"webauthn_registrations\":[]},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\"}";
        String jwt_pharmacist="eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NTAxNzk4MDksImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLTRhMmU1OGZmLTM2MzItNGI1OS1iNDA2LWI1YjUyYzU2ZjhkZSIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA2LTE3VDE2OjU4OjI5WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA2LTE3VDE2OjU4OjI5WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA2LTE3VDIwOjU4OjI5WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNi0xN1QxNjo1ODoyOVoifV19LCJpYXQiOjE3NTAxNzk1MDksImlzcyI6Imh0dHBzOi8vbG9naW4uYXBpbWUub25saW5lIiwibmJmIjoxNzUwMTc5NTA5LCJzdWIiOiJ1c2VyLWxpdmUtZDEyYzA0MTEtN2E0Ny00ZmE4LTg2ZDItZjM1MDRiYTNlNzJjIn0.SToO-a2Bk1YmkAiGp07q7mNEwEL6hvQphv3b11CQrY5CCUEGmt4IEl87FkbYDKTFBZVJL5l4fhgKPl2pFXlNJZ_2Kqvz3qyKNGTGVVQ0t7TEy9no4F6byiv6W_nrs3M04LGT_20Tu9ooSq3wu09bVvJlS_IZhfOEdGNLCXbOvTbAkn8Kjqmh91XAwHMToKg_UKFz1XoMeLFNGhXIvzcVjIUFnAEetHio65wFp7QDckjEph9otZUsW4bHlR1Jt7PuCh8k1kjF1b4TUminAqnXDI2jHjjQb2rlPIw_zgztnQvKLBmdMneHnZLHHPSnQESOPc4CsCGJxhe50ucFAx7DNA";
        String token_seller="eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NTAwODM2NzgsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLTcyZmI3ZGY0LTVkMmItNDNmOC05YWY1LTMyZDU1ZmNmNzNkZiIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA2LTE2VDE0OjE2OjE4WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA2LTE2VDE0OjE2OjE4WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA2LTE2VDE4OjE2OjE4WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNi0xNlQxNDoxNjoxOFoifV19LCJpYXQiOjE3NTAwODMzNzgsImlzcyI6Imh0dHBzOi8vbG9naW4uYXBpbWUub25saW5lIiwibmJmIjoxNzUwMDgzMzc4LCJzdWIiOiJ1c2VyLWxpdmUtNWFjM2U5YmQtZWEwMy00MmEwLWFkMGItZGZlMjRlZTExMjg2In0.0XShP1XWlc8a4HMUSLm1EEmnyGpblUpekI62Qmm9YE4VyjVdASYr6N5grQeXvZxIxOtlpQ25NCbDhb3ehSdjDO-eaSdn2mThg8D59FTROhOEBlySBqEPaB1J_WyDHE2VmvnsVqIiructpUcTeCRBky50VXRjwhyQC_8vD94CcMkaTrqLzPHJAmm_eajYyN8srO_iccccgqDWv_S57EQMaPQZSGG7HoILJsQe9mX2IA9hq6L8Is5p44liKEN76gosRXPN_uouoCp5VtMJLF4c50zaP52IaIWeLs7x8zqiNQCt9zseeozhXUn0f0DIJcJfYes9zlTv80a2gh87z-skcg\n";
        String json_pharmacist = "{" +
                "\"email_id\":\"email-live-0c0bda3b-1184-43d2-b6e5-a8290daea8d0\"," +
                "\"request_id\":\"request-id-live-1920c5c3-b823-4607-bf39-08987d761591\"," +
                "\"session\":{" +
                "\"attributes\":{" +
                "\"ip_address\":\"\"," +
                "\"user_agent\":\"\"" +
                "}," +
                "\"authentication_factors\":[{" +
                "\"created_at\":\"2025-06-17T16:58:29Z\"," +
                "\"delivery_method\":\"knowledge\"," +
                "\"last_authenticated_at\":\"2025-06-17T16:58:29Z\"," +
                "\"type\":\"password\"," +
                "\"updated_at\":\"2025-06-17T16:58:29Z\"" +
                "}]," +
                "\"custom_claims\":{}," +
                "\"expires_at\":\"2025-06-17T20:58:29Z\"," +
                "\"last_accessed_at\":\"2025-06-17T16:58:29Z\"," +
                "\"session_id\":\"session-live-4a2e58ff-3632-4b59-b406-b5b52c56f8de\"," +
                "\"started_at\":\"2025-06-17T16:58:29Z\"," +
                "\"user_id\":\"user-live-d12c0411-7a47-4fa8-86d2-f3504ba3e72c\"" +
                "}," +
                "\"session_jwt\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NTAxNzk4MDksImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLTRhMmU1OGZmLTM2MzItNGI1OS1iNDA2LWI1YjUyYzU2ZjhkZSIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA2LTE3VDE2OjU4OjI5WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA2LTE3VDE2OjU4OjI5WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA2LTE3VDIwOjU4OjI5WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNi0xN1QxNjo1ODoyOVoifV19LCJpYXQiOjE3NTAxNzk1MDksImlzcyI6Imh0dHBzOi8vbG9naW4uYXBpbWUub25saW5lIiwibmJmIjoxNzUwMTc5NTA5LCJzdWIiOiJ1c2VyLWxpdmUtZDEyYzA0MTEtN2E0Ny00ZmE4LTg2ZDItZjM1MDRiYTNlNzJjIn0.SToO-a2Bk1YmkAiGp07q7mNEwEL6hvQphv3b11CQrY5CCUEGmt4IEl87FkbYDKTFBZVJL5l4fhgKPl2pFXlNJZ_2Kqvz3qyKNGTGVVQ0t7TEy9no4F6byiv6W_nrs3M04LGT_20Tu9ooSq3wu09bVvJlS_IZhfOEdGNLCXbOvTbAkn8Kjqmh91XAwHMToKg_UKFz1XoMeLFNGhXIvzcVjIUFnAEetHio65wFp7QDckjEph9otZUsW4bHlR1Jt7PuCh8k1kjF1b4TUminAqnXDI2jHjjQb2rlPIw_zgztnQvKLBmdMneHnZLHHPSnQESOPc4CsCGJxhe50ucFAx7DNA\"," +
                "\"session_token\":\"VhfqFG7CNdJnAcMAlA8UMKc0DzeYZc5ak8YskV3aHivt\"," +
                "\"status_code\":200," +
                "\"user\":{" +
                "\"biometric_registrations\":[]," +
                "\"created_at\":\"2025-06-17T16:58:29Z\"," +
                "\"crypto_wallets\":[]," +
                "\"emails\":[{" +
                "\"email\":\"marco@farmaciamia.com\"," +
                "\"email_id\":\"email-live-0c0bda3b-1184-43d2-b6e5-a8290daea8d0\"," +
                "\"verified\":false" +
                "}]," +
                "\"external_id\":null," +
                "\"is_locked\":false," +
                "\"lock_created_at\":null," +
                "\"lock_expires_at\":null," +
                "\"name\":{" +
                "\"first_name\":\"marco\"," +
                "\"last_name\":\"f\"," +
                "\"middle_name\":\"\"" +
                "}," +
                "\"password\":{" +
                "\"password_id\":\"password-live-4dc07aa3-72c6-4f51-973b-85ef64907b6d\"," +
                "\"requires_reset\":false" +
                "}," +
                "\"phone_numbers\":[]," +
                "\"providers\":[]," +
                "\"status\":\"active\"," +
                "\"totps\":[]," +
                "\"trusted_metadata\":{" +
                "\"role\":\"pharmacist\"" +
                "}," +
                "\"untrusted_metadata\":{}," +
                "\"user_id\":\"user-live-d12c0411-7a47-4fa8-86d2-f3504ba3e72c\"," +
                "\"webauthn_registrations\":[]" +
                "}," +
                "\"user_id\":\"user-live-d12c0411-7a47-4fa8-86d2-f3504ba3e72c\"" +
                "}";


                when(userService.authenticate_jwt(Mockito.anyString())).thenAnswer(answer -> {
                            String jwt_value = answer.getArgument(0);
                            if (jwt_value.equals(token_seller)) {
                                return new UserServiceResponse(jsonString_seller, 200);
                            } else if (jwt_value.equals(jwt_pharmacist)) {
                                return new UserServiceResponse(json_pharmacist, 200);
                            }
                            return null;

                        });
        when(userService.authenticate_jwt(Mockito.anyString())).thenAnswer(answer -> {
            String jwt_value = answer.getArgument(0);
            if (jwt_value.equals(token_seller)) {
                return new UserServiceResponse(jsonString_seller, 200);
            } else if (jwt_value.equals(jwt_pharmacist)) {
                return new UserServiceResponse(json_pharmacist, 200);
            }
            return null;

        });



        ChatServer chatServer=new ChatServer(userService);
    }


}