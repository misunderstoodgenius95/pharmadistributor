package pharma.security;

import org.json.JSONArray;
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
       JSONObject jsonObject= extactObjectFromJsonArray(json);
       if(jsonObject.has("user")){
           return jsonObject.getJSONObject("user").getJSONObject("trusted_metadata").getString("role");
       }else{
           return jsonObject.getJSONObject("trusted_metadata").getString("role");
       }

    }
/*    public static String extract_enabled(String json){


    }*/
    public  static   String extract_email(String json){

        if(json==null ||json.isEmpty()){
            throw new IllegalArgumentException("Json is empty or null");
        }
        JSONObject jsonObject=extactObjectFromJsonArray(json);

        if(jsonObject.has("user")){

         return new JSONObject(jsonObject.getJSONObject("user").getJSONArray("emails").get(0).toString()).getString("email").toString();

        }else{
            return new  JSONObject(jsonObject.getJSONArray("emails").get(0).toString()).getString("email");

        }



    }



    private  static  JSONObject extactObjectFromJsonArray(String json){
        if(json==null ||json.isEmpty()){
            throw new IllegalArgumentException("Json is empty or null");
        }
        String  trimmed_json=json.trim();
        String first=trimmed_json.substring(0,1);
        if(first.equals("[")){
            return new JSONArray(json).getJSONObject(0);
        }else{
            return new JSONObject(json);
        }
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




