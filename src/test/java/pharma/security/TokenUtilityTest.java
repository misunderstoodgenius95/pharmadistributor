package pharma.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.impl.ClaimsHolder;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
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
}