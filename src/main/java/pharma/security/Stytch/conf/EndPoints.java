package pharma.security.Stytch.conf;

import pharma.dao.GenericDaoAble;

public class EndPoints {



    private   static  final String create_user="/v1/passwords";
    private static  final  String auth_email_password="/v1/passwords/authenticate";
    private  static  final String auth_token="/v1/sessions/authenticate";
    private static  final String search_users="/v1/users/search";
    private static final  String user_revoke="/v1/sessions/revoke";
    private  static  final  String user_session="/v1/sessions";
    private static  final  String user_update="/v1/users/";
    private static  final  String reset_password_start="/v1/passwords/email/reset/start";
    private static final  String uri_reset="/reset-password";
    private static final String uri_search_id="/v1/users/";


    public static String getSearch_userById(String endpoint){

        return endpoint+uri_search_id;
    }


    public static String getCreate_user(String endpoint){

        return endpoint+create_user;
    }


    public static String get_auth(String endpoint){

        return endpoint+auth_email_password;
    }
    public  static  String getAuth_token(String endpoint){
        return  endpoint+auth_token;
    }

    public static String getSearch_users(String endpoint){
        return  endpoint+search_users;
    }

    public static String getUser_session(String endpoint){
        return endpoint+user_session;
    }
    public static String getUser_update(String endpoint,String user_id){
        return endpoint+user_update+user_id;

    }
    public static String getReset_password_start(String endpoint){
        return endpoint+reset_password_start;
    }

    public static String getUriReset(String endpoint){
        return endpoint+uri_reset;
    }





}
