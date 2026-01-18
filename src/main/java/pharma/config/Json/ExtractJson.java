package pharma.config.Json;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.List;

public class ExtractJson {

    public static JSONObject extract_first(String json, String query){
        if(json==null || query==null){
            throw  new IllegalArgumentException("Argument cannot be null");
        }
        if(json.isEmpty()){
            throw  new IllegalArgumentException("Argument cannot be empty");
        }

        List<String> list = JsonPath.read(json,query);
        JSONArray jsonArray=new JSONArray(list);
        if(jsonArray.isEmpty()){
            return new JSONObject();
        }else {
            return jsonArray.getJSONObject(0);
        }


    }
  /*  public static JSONObject extract_all(String json, String query){



    }*/

}
