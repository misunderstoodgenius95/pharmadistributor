package pharma.security;

import org.json.JSONObject;


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




