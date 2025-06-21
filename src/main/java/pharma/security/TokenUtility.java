package pharma.security;

import org.json.JSONObject;
import pharma.javafxlib.Controls.Notification.JsonNotify;


public class TokenUtility {
    private TokenUtility() {
    }

    public static  String extract_token(String jwt){
        return  new JSONObject(jwt).getString("session_jwt");



    }

    public  static String extractPermissions(String json) {
        return new JSONObject(json).getJSONObject("user").getJSONObject("trusted_metadata").getJSONObject("permissions").toString();
    }

   public static String extractRole(String json) {

        return new JSONObject(json).getJSONObject("user").getJSONObject("trusted_metadata").getString("role");
    }
    public  static   String extract_email(String json){
      return new  JSONObject(new JSONObject(json).getJSONObject("user").getJSONArray("emails").get(0).toString()).getString("email");

    }

    /**
     *
     * return true is permission is setting
     *
     * @param operation
     * @param permission
     * @param
     * @return
     */
    public static  boolean check_permission(String operation,String permission,String json_permissions){
       JSONObject jsonObject=new JSONObject(json_permissions);
       if(!jsonObject.has(operation)){
           return false;
       }
       else{
            return   jsonObject.getJSONArray(operation).toList().contains(permission);
       }


    }






    }




