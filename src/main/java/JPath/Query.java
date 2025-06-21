package JPath;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;
import java.util.Map;

public class Query {


    private  Query(){


    }
    public  static String equal(String key, String value){
        if(key==null || value==null){
            throw  new IllegalArgumentException("Argument is null");

        }
        if(key.isEmpty() || value.isEmpty()){
            throw  new IllegalArgumentException("Argument is Empty");

        }
      return "$[?(@." + StringEscapeUtils.escapeJava(key) + " == "+ "'" + StringEscapeUtils.escapeJava(value)+ "'"+")]";
    }

    /**
     *
     *
     * @param key The name of  String
     * @param value Like value
     * @return
     */
    public  static String Like(String key, String value){
        if(key==null || value==null){
            throw  new IllegalArgumentException("Argument is null");

        }
        if(key.isEmpty() || value.isEmpty()){
            throw  new IllegalArgumentException("Argument is Empty");

        }
        //$[?(@.name =~ /(?i).*mak.*/)]
        return "$[?(@." + StringEscapeUtils.escapeJava(key) + " =~ /(?i).*"+StringEscapeUtils.escapeJava(value)+".*/)]";
    }

    public  static  List<Map<String, Object>> filterUsersByRole(String stytchJsonResponse, String role) {
        String jsonPath = String.format("$.results[?(@.trusted_metadata.role == '%s')]", role);
        return JsonPath.read(stytchJsonResponse, jsonPath);
    }







}
