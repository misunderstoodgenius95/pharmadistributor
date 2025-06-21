package pharma.security.Stytch.conf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class EncodeStytchTest {



    @Test
    void ValidtestAuthentication() {

        String actual=EncodeStytch.authentication("project-test-4acd22c0-ef21-4294-97b1-38d96e7a77c2","secret-live-BWW4Lql0pdxl7JqobBndAhqPzIG7BzQlC5U=");
        String expected=Base64.getEncoder().encodeToString("project-test-4acd22c0-ef21-4294-97b1-38d96e7a77c2:secret-live-BWW4Lql0pdxl7JqobBndAhqPzIG7BzQlC5U=".getBytes());
        Assertions.assertEquals(expected,actual);
    }
}