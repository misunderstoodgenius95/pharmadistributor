package pharma.config.net;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class PollingClientTest {
    @Mock
    private  HttpResponse<String> httpResponse;
    @Mock
    private ClientHttp clientHttp;

    private PollingClient pollingClient;
    @BeforeEach
    public  void setUp(){
        MockitoAnnotations.openMocks(this);
        pollingClient=new PollingClient(clientHttp);
    }


    @Test
    void ValidSend() {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("product_id","1200");
        jsonObject.put("lot_id","a100");
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("product_id","1200");
        jsonObject2.put("lot_id","a100");
        JSONObject jsonObject3=new JSONObject();
        jsonObject3.put("product_id","1200");
        jsonObject3.put("lot_id","a100");
        JSONArray jsonArray=new JSONArray().put(jsonObject).put(jsonObject2).put(jsonObject3);
        System.out.println(jsonArray.toString());



        when(httpResponse.body()).thenReturn(jsonArray.toString());
        when(httpResponse.statusCode()).thenReturn(200);
        CompletableFuture<HttpResponse<String>> future=CompletableFuture.completedFuture(httpResponse);
        when(clientHttp.sendAsync(any(HttpRequest.class))).thenReturn(future);
        ScheduledFuture<String> result=pollingClient.send("http://localhost:3000/notify");
        try {
            String  expected=result.get(2, TimeUnit.SECONDS);
            Assertions.assertEquals(jsonArray.toString(),expected);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}