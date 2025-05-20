package pharma.formula;

import JPath.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import pharma.config.Json.ExtractJson;
import pharma.config.net.ClientHttp;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TrendMarket {
    private ClientHttp clientHttp;
    private URI json_uri;
    public TrendMarket(ClientHttp clientHttp,URI json_uri) {
        this.clientHttp = clientHttp;
        this.json_uri=json_uri;
    }

    public  double extract_trend_market(String pharma_name){
        if(pharma_name==null){

            throw  new IllegalArgumentException("Argument cannot be null!");
        }

        HttpRequest httpRequest= HttpRequest.newBuilder(json_uri).build();
        HttpResponse<String>  response = clientHttp.send(httpRequest);
        if(response.statusCode()!=200){
            throw new HttpStatusException(String.valueOf(response.statusCode()));
        }
        JSONObject jsonObject=ExtractJson.extract_first(response.body(), Query.Like("name",pharma_name));
        if(jsonObject.isEmpty() || !jsonObject.has("trend_market_percentage")){

            return -1.1;
        }
       return  jsonObject.getDouble("trend_market_percentage");




    }

 /*   private static  double query_value(String body){


    }*/

}
