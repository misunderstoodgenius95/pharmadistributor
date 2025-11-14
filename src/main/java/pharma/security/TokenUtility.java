package pharma.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import pharma.javafxlib.Controls.Notification.JsonNotify;

import java.util.Base64;


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
       System.out.println(jsonObject.toString());
       if(jsonObject.has("user")) {
           JSONArray jsonArray = jsonObject.getJSONObject("user").getJSONArray("roles");
           if (jsonArray.length() > 1) {
               return jsonArray.get(1).toString();
           }


       }
       return  "";
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
    public static String extract_sub(String jwt )  {
        String[] parts = jwt.split("\\.");
        byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
        String payload = new String(decodedBytes);

        // Parse with Jackson
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Extract values
        return node.get("sub").asText();
    }
    public  static  String extractEmailByUserId(String body){

        JSONObject jsonObject=new JSONObject(body);
        return jsonObject.getJSONArray("emails").getJSONObject(0).getString("email");

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




