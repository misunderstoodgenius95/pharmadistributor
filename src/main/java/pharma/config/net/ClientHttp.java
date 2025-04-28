package pharma.config.net;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;

public class ClientHttp {
    private HttpClient client;
    public ClientHttp() {


        try {
            client = HttpClient.newBuilder().sslContext(SSLContext.getDefault()).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public HttpResponse<String> send(HttpRequest httpRequest){
            HttpResponse<String> response=null;
        try {

            response=client.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return  response;

    }



}
