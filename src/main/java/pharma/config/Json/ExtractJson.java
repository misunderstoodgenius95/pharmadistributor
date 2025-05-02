package pharma.config.Json;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;
import java.util.Objects;

public class ExtractJson {

    public static JSONObject extract_first(String json,String path){
        if(json==null || path==null){
            throw  new IllegalArgumentException("Argument cannot be null");
        }
        if(json.isEmpty() || path.isEmpty()){
            throw  new IllegalArgumentException("Argument cannot be empty");
        }





        List<String> list = JsonPath.read(json,path);
        JSONArray jsonArray=new JSONArray(list);
        if(jsonArray.isEmpty()){
            return new JSONObject();
        }else {

            return jsonArray.getJSONObject(0);
        }


    }

}
