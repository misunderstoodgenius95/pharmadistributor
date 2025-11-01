package pharma.config.net;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

public class ClientHttp {
    private HttpClient client;
    public ClientHttp() {


        try {
            client = HttpClient.newBuilder().sslContext(SSLContext.getDefault()).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> send(HttpRequest httpRequest)  {
        HttpResponse<String> response=null;
        try {

            response=client.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        } catch ( InterruptedException e) {
            e.printStackTrace();
        }
                catch (IOException e) {
                // Check if the cause is a ConnectException (wrapped in IOException)
                if (e.getMessage() != null &&
                        (e.getMessage().contains("Connection refused") ||
                                e.getMessage().contains("Network is unreachable") ||
                                e.getMessage().contains("No route to host"))) {
                   e.printStackTrace();
                }
                // Other IO errors


            }

        return  response;

    }

    public CompletableFuture<HttpResponse<String>> sendAsync(HttpRequest httpRequest){


        return client.sendAsync(httpRequest,HttpResponse.BodyHandlers.ofString());


    }




}
