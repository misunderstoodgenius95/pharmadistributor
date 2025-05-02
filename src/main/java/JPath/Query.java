package JPath;

import org.apache.commons.text.StringEscapeUtils;

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
    public  static String like(String key, String value){
        if(key==null || value==null){
            throw  new IllegalArgumentException("Argument is null");

        }
        if(key.isEmpty() || value.isEmpty()){
            throw  new IllegalArgumentException("Argument is Empty");

        }
        return "$[?(@." + StringEscapeUtils.escapeJava(key) + " == "+ "'" + StringEscapeUtils.escapeJava(value)+ "'"+")]";
    }




}
