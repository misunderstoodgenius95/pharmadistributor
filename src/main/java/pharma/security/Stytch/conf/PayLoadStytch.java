package pharma.security.Stytch.conf;

import org.json.JSONArray;
import org.json.JSONObject;
import pharma.config.InputValidation;
import pharma.javafxlib.Controls.Notification.JsonNotify;



public class PayLoadStytch {
    public static String  get_user(String email, String password) {
    if(!InputValidation.validate_email(email) || !InputValidation.validate_password(password)  ) {
        throw new IllegalArgumentException("Email or password are not valid");
    }
        return new JSONObject()
                .put("email", email)
                .put("password", password).put("session_duration_minutes", 240).toString();
    }
        public static String create_user(String email, String password,String first_name, String surname ) {
            if(!InputValidation.validate_email(email) || !InputValidation.validate_password(password)  ){
                throw new IllegalArgumentException("Email or password are not valid");
            }
            return new JSONObject(get_user(email,password)).put("trusted_metadata",new JSONObject().put("is_enable",true))
                    .put("name",new JSONObject().put("first_name",first_name).put("last_name",surname)).toString();
        }
    public static String create_user_pharmacist(String email, String password,String role,String first_name, String surname,int pharma_id ) {
        if(!InputValidation.validate_email(email) || !InputValidation.validate_password(password)  ){
            throw new IllegalArgumentException("Email or password are not valid");
        }
        return new JSONObject(get_user(email,password)).put("trusted_metadata",new JSONObject().put("role",role).put("is_enable",true).put("pharma_id",pharma_id)
                )
                .put("name",new JSONObject().put("first_name",first_name).put("last_name",surname)).toString();
    }




    public static JSONObject buildAuthPayload_token(String token) {
        return new JSONObject()
                .put("session_token", token).put("session_duration_minutes", 240);
    }

    public static String buildAuthPayload_jwt_token(String token) {
        return new JSONObject()
                .put("session_jwt", token).put("session_duration_minutes", 240).toString();
    }
    public  static String buildSearch_All(){
        return new JSONObject().put("limit",1000).put("cursor","").toString();


    }
    public static String buildSearchByEmail(String email){


       JSONObject query= new JSONObject().put("operator","AND").
               put("operands",new JSONArray().put(new JSONObject().put( "filter_name","email_address").put("filter_value",new JSONArray().put(email))));
       return  new JSONObject(buildSearch_All()).
                put("query",query).toString();
    }


    public static JSONObject buildUpdateTustedMetadataIsEnable(boolean status){

        return new JSONObject().put("trusted_metadata",new JSONObject().put("is_enable",status));
    }
    public static JSONObject buildUpadateRole(String role){
        return new JSONObject().put("roles",new JSONArray().put(role));
    }



    public static String buildSession(String user_id){
        return new JSONObject().put("user_id",user_id).toString();
    }

    public static JSONObject reset_password(String email,String url_reset){

             return new JSONObject().put("email",email).put("reset_password_redirect_url",url_reset);

    }






}
