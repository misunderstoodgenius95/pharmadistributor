package pharma.security.Stytch.conf;

import pharma.config.InputValidation;

import java.util.Base64;

public class EncodeStytch {


    public static String authentication(String projectId, String secret){
        if(!InputValidation.validate_stytch_project_id(projectId) || !InputValidation.validate_stytch_secret_key(secret)){
            throw new IllegalArgumentException("Argument not correct");
        }

        String credentials = projectId +":"+ secret;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }


    public static  String decode(String value){

         return new String(Base64.getDecoder().decode(value));
    }
}
