package pharma;

import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import pharma.Model.FieldData;
import pharma.config.InputValidation;
import pharma.config.net.ClientHttp;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class HttpExpireItem {
    private  ClientHttp clientHttp;
    public HttpExpireItem(ClientHttp clientHttp) {
        this.clientHttp=clientHttp;

    }


    public  int post_expire(String uri, ObservableList<FieldData> obs, int expire_days){

           if(obs.isEmpty() || expire_days <= 0) {
               throw new IllegalArgumentException("Argument not correct");
        }

        String body=fieldata_to_json_array(obs,expire_days).toString();


        HttpRequest httpRequest=HttpRequest.newBuilder(URI.create(uri)).
                POST(HttpRequest.BodyPublishers.ofString(body)).setHeader("Content-Type","application/json").build();

            HttpResponse<String> httpResponse=clientHttp.send(httpRequest);
       return httpResponse.statusCode();

    }


    public  static JSONArray fieldata_to_json_array(ObservableList<FieldData> obs, int expiration_days){

        if(obs==null  || expiration_days <=0 || obs.isEmpty()) {
        throw new IllegalArgumentException("Input argument not correct!");

        }
        JSONArray jsonArray=new JSONArray();
        for(FieldData fieldData:obs){
            jsonArray.put(fieldata_to_json(fieldData,expiration_days));


        }


        return jsonArray;
    }

   public static   JSONObject fieldata_to_json(FieldData fieldData, int expiration_days){

        if((fieldData==null ) ||  (fieldData.getElapsed_date()==null) || (expiration_days<=0)){
            throw new IllegalArgumentException("Input argument not correct!");
        }


       DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
       String date=fieldData.getElapsed_date().toLocalDate().format(timeFormatter);

        return new JSONObject().put("lot_id",fieldData.getCode()).
                put("product_id",fieldData.getFarmaco_id()).
                put("expiration_date",date).put("time_of_day", expiration_days);


    }


/*
    String uri="";
        try {
        uri= FileStorage.getProperty("expire_post_notify",new FileReader("expire_item.properties"));
    } catch (
    FileNotFoundException e) {
        throw new RuntimeException(e);
    }
    HttpRequest httpRequest=HttpRequest.newBuilder(URI.create(uri)).
            POST(HttpRequest.BodyPublishers.ofString(Utility.fieldata_to_json_array(obs_table,(int) spinner_id_days.getValue()).toString())).build();

       clientHttp.send(httpRequest);*/
}
