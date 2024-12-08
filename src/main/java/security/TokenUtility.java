package security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;


public class TokenUtility {
    private TokenUtility() {
    }

    public  static String[] extractPermission(String jwt) {
        DecodedJWT jwdecoder = JWT.decode(jwt);
        return jwdecoder.getClaim("permissions").as(String[].class);
    }

    public static String extractRole(String jwt) {
        DecodedJWT jwdecoder = JWT.decode(jwt);
        return jwdecoder.getClaim("role").asString();
    }


    public static boolean check_permission( String  token, String permission_rules, String mode) {
       if (token == null || permission_rules == null || mode == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }


        String[] permissions = extractPermission(token);
       if (permissions == null) {
            return false; // Return false if no permissions are extracted
        }


        for(String permission : permissions) {
            if(permission.equals(permission_rules+":"+mode)) {

        return true;

            }

        }

return false;
    }



    }




