package pharma.security.Stytch.conf;

public class EndPoints {



    private   static  final String create_user="/v1/passwords";
    private static  final  String auth_email_password="/v1/passwords/authenticate";
    private  static  final String auth_token="/v1/sessions/authenticate";
    public static String getCreate_user(String endpoint){

        return endpoint+create_user;
    }
    public static String get_auth(String endpoint){

        return endpoint+auth_email_password;
    }
    public  static  String getAuth_token(String endpoint){
        return  endpoint+auth_token;



    }




}
