package pharma.formula;

import org.json.JSONArray;
import pharma.config.net.ClientHttp;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TrendMarket extends  TrasformValue {
    private ClientHttp clientHttp;
    private URI json_uri;
    public TrendMarket(ClientHttp clientHttp,URI json_uri) {
        this.clientHttp = clientHttp;
        this.json_uri=json_uri;
    }

    public  double extract_trend_market(String pharma_name,String tipology,String unit_misure){
        HttpRequest httpRequest= HttpRequest.newBuilder(json_uri).build();
        HttpResponse<String>  response = clientHttp.send(httpRequest);
        return  0.0;


    }

 /*   private static  double query_value(String body){


    }*/

}
