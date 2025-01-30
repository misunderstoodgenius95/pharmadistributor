package pharma.config;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.Response;
import com.auth0.net.TokenRequest;

import com.sun.source.tree.ModuleTree;
import kotlin.jvm.internal.unsafe.MonitorKt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.plugins.MockitoPlugins;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    private static AuthAPI authAPI;
    private static Response<TokenHolder> tokenHolder_response;
    private static TokenHolder tokenHolder;
    private static TokenRequest tokenRequest;
    @BeforeAll
    static void setUp() throws Auth0Exception {
       authAPI= mock(AuthAPI.class);
       tokenHolder_response= mock(Response.class);
       tokenHolder = mock(TokenHolder.class);
       tokenRequest = mock(TokenRequest.class);
        AuthAPI authAPI= mock(AuthAPI.class);




    }
    @Test
    void valid_email_password() throws Auth0Exception {
        Mockito.when(tokenHolder.getAccessToken()).thenReturn("token");
        Mockito.when(tokenHolder_response.getBody()).thenReturn(tokenHolder);
        Mockito.when(tokenHolder_response.getStatusCode()).thenReturn(200);


        Mockito.when(authAPI.login(Mockito.anyString(), Mockito.any(char[].class))).thenReturn(tokenRequest);



        Mockito.when(tokenRequest.setAudience(Mockito.anyString())).thenReturn(tokenRequest);
        Mockito.when(tokenRequest.execute()).thenReturn(tokenHolder_response);
        try(MockedStatic<InputValidation> input_validation_mock=Mockito.mockStatic(InputValidation.class)) {
            input_validation_mock.when(() -> InputValidation.validate_email(anyString())).thenReturn(true);
            input_validation_mock.when(() -> InputValidation.validate_password(anyString())).thenReturn(true);

            UserService userService = new UserService(authAPI);
            Response<TokenHolder> response = userService.authenticate("user@example.com", "@5&17Vhm5QGp", "http://example.com/api/auth");
            Assertions.assertEquals(200, response.getStatusCode());

        }

    }

    @Test
    void Invalid_email_password() throws Auth0Exception {
        Mockito.when(tokenHolder.getAccessToken()).thenReturn("token");
        Mockito.when(tokenHolder_response.getBody()).thenReturn(tokenHolder);
        Mockito.when(tokenHolder_response.getStatusCode()).thenReturn(401);


        Mockito.when(authAPI.login(Mockito.anyString(), Mockito.any(char[].class))).thenReturn(tokenRequest);



        Mockito.when(tokenRequest.setAudience(Mockito.anyString())).thenReturn(tokenRequest);
        Mockito.when(tokenRequest.execute()).thenReturn(tokenHolder_response);
        try(MockedStatic<InputValidation> input_validation_mock=Mockito.mockStatic(InputValidation.class)) {
            input_validation_mock.when(() -> InputValidation.validate_email(anyString())).thenReturn(true);
            input_validation_mock.when(() -> InputValidation.validate_password(anyString())).thenReturn(true);

            UserService userService = new UserService(authAPI);
            Response<TokenHolder> response = userService.authenticate("user@example.com", "@5&17Vhm5QGp", "http://example.com/api/auth");
            Assertions.assertEquals(401, response.getStatusCode());

        }

    }




}