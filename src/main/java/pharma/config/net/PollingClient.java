package pharma.config.net;

import org.json.JSONObject;
import pharma.config.InputValidation;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.*;

public class PollingClient {
    private final ClientHttp clientHttp;
    public PollingClient(ClientHttp clientHttp) {
        this.clientHttp=clientHttp;

    }


   public ScheduledFuture<String>  send(String endpoint) {
        if(endpoint.isEmpty()){
            throw new IllegalArgumentException("Argument is empty");
        }


       HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(endpoint)).timeout(Duration.ofSeconds(20)).build();
       ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

       Callable<String> callable = () -> {
           CompletableFuture<String> response = clientHttp.sendAsync(httpRequest)
                   .thenApply(res -> {
               if (res.statusCode() != 200) {
                   throw new RuntimeException("HTTP Error: " + res.statusCode());
               } else {
                   return res.body();
               }

           }).exceptionally(ex -> {
               throw new RuntimeException(ex);
           });
           return  response.get();

       };

      return scheduler.schedule(callable, 0, TimeUnit.DAYS);
    /*

    ScheduledFuture<V> is an interface that extends both Delayed and Future<V>,
    representing the result of an asynchronous computation scheduled to run after a delay or periodically.
    */


   }
}
