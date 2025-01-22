package pharma.config;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpJsonClient {
    HttpClient httpClient;

    public HttpJsonClient() {

        this.httpClient = HttpClient.newBuilder().build();

    }

    public HttpResponse<String> post(String url, JSONObject params)  {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).
                header("Content-Type", "application/json").
                POST(HttpRequest.BodyPublishers.ofString(params.toString())).build();

        try {
            return  httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
   
    public void get(String uri, String token){




    }



}
