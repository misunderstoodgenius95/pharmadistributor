package pharma.security.Stytch.conf;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.accessibility.AccessibleStateSet;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;


class StytchRequestTest {

    private String validEncodedAuth;
    private String validProjectId;
    private String validSecret;
    private StytchRequest stytchRequest;
    @BeforeEach
    void setUp() {
        validProjectId = "project-test-12345678-1234-5678-9abc-123456789012";
        validSecret = "secret-test-AbC123XyZ789+/=_-1234567890";
        validEncodedAuth = Base64.getEncoder().encodeToString((validProjectId + ":" + validSecret).getBytes(StandardCharsets.UTF_8));
        stytchRequest = new StytchRequest(validEncodedAuth);
    }
    @Nested
    class ValidTest {
        private  HttpRequest request;
        @BeforeEach
        public void setUp() {
        String json_object = new JSONObject().put("email", "user@example.com").put("password", "B!jdH6a5N$g1").put("session_duration_minutes", 240).toString();
        StytchRequest stytchRequest = new StytchRequest(validEncodedAuth);
        request = stytchRequest.auth_request("https://login.apime.online/v1/passwords", json_object);
        }

        @Test
        public  void ValidNull(){
            Assertions.assertNotNull(request);

        }
        @Test
        public void ValidUri(){
            Assertions.assertEquals(URI.create("https://login.apime.online/v1/passwords"),request.uri());
        }
        @Test
        public void ValidPost(){
            Assertions.assertEquals("POST",request.method());
        }

    }

}