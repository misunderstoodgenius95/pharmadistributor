package pharma.config;


import com.fasterxml.jackson.annotation.JsonAlias;
import okhttp3.internal.http.HttpHeaders;
import org.json.HTTP;
import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HttpJsonClientTest {
    private HttpClient mockhttpClient;
    private HttpResponse<String> mock_response;
    private HttpJsonClient jsonClient;

    @BeforeEach
    public void setup() {

        mockhttpClient = mock(HttpClient.class);
        mock_response = mock(HttpResponse.class);
        jsonClient = new HttpJsonClient() {
            {
                this.httpClient = mockhttpClient;
            }

        };

    }

    @Test
    public void post() throws IOException, InterruptedException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "luca");
        jsonObject.put("password", "12345678");

        when(mock_response.statusCode()).thenReturn(200);
        when(mockhttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).
                thenReturn(mock_response);

        //Act


        HttpResponse<String> httpResponse = jsonClient.post("http://example.com", jsonObject);
        Assertions.assertEquals(200, httpResponse.statusCode());

    }
@Test
    public void  test_real() {

    HashMap<String,String> hashMap=new HashMap<>();
    hashMap.put("client_id","p14pZzboKJeCcIjfmkb3nyTJoD14mf1r");
    hashMap.put("client_secret","p6t-4gyYZAarQo2-H3Jygpk00I0esL0UMO4V869WXGQ_4_HJXMYgP9xlUphK3wsa");
    hashMap.put("grant_type","password");
    hashMap.put("audience","https://distroapi.com");
    hashMap.put("username","lucagcalio95@live.com");
    hashMap.put("password","ULnWFkRh2w$QB&QQ7nrD");
    HttpJsonClient jsonClient1=new HttpJsonClient();
    HttpResponse<String> response=jsonClient1.post("https://dev-md003sye8lbs8k7g.us.auth0.com/oauth/token",new JSONObject(hashMap));
Assertions.assertEquals(response.statusCode(),200);
    }
    @Test
    void testPostWithValidCredentials() throws Exception {
        // Arrange
        HttpClient mockHttpClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        // Simulate 200 OK response for valid credentials
        when(mockResponse.statusCode()).thenReturn(400);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        HttpJsonClient client = new HttpJsonClient();
        client.httpClient = mockHttpClient;

        String validCredentials = "{ \"username\": \"validUser\", \"password\": \"validPass\" }";

        // Act
        HttpResponse<String> response = client.post("https://example.com/login", new JSONObject(validCredentials));

        // Assert
        assertEquals(400, response.statusCode());
        System.out.println(response.statusCode());
    }




}