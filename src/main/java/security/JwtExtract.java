package security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtExtract {
    private  JwtExtract() {
    }

    public static String[] extractPermission(String jwt) {
        DecodedJWT jwdecoder = JWT.decode(jwt);
        return jwdecoder.getClaim("permissions").asArray(String.class);
    }

    public static String extractRole(String jwt) {
        DecodedJWT jwdecoder = JWT.decode(jwt);
        return jwdecoder.getClaim("role").asString();
    }

    }




