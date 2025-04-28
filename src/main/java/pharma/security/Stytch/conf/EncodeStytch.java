package pharma.security.Stytch.conf;

import java.util.Base64;

public class EncodeStytch {


    public static String authentication(String projectId, String secret){

        String credentials = projectId +":"+ secret;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }


    public static  String decode(String value){

         return new String(Base64.getDecoder().decode(value));
    }
}
