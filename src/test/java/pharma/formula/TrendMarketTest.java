package pharma.formula;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.config.net.ClientHttp;


import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.when;

class TrendMarketTest {
    @Mock
    private ClientHttp clientHttp;
    @Mock
    private HttpResponse<String> httpResponse;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void Validextract_trend_market() throws URISyntaxException {
        JSONObject xanax = new JSONObject();

        xanax.put("category", "Ansiolitici");
        xanax.put("typology", "Compresse");
        xanax.put("dosage", "5mg");
        xanax.put("name", "Xanax");
        xanax.put("trend_market_percentage", 8.5);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(xanax.toString());
        when(clientHttp.send(Mockito.any(HttpRequest.class))).thenReturn(httpResponse);
        TrendMarket trendMarket=new TrendMarket(clientHttp,new URI("https://localhost:8080"));
        double result=trendMarket.extract_trend_market("xanax");
        Assertions.assertEquals(8.5,result);





    }
    @Test
    void Invalidextract_trend_market() throws URISyntaxException {
        JSONObject xanax = new JSONObject();

        xanax.put("category", "Ansiolitici");
        xanax.put("typology", "Compresse");
        xanax.put("dosage", "5mg");
        xanax.put("name", "Tachiprina");
        xanax.put("trend_market_percentage", 8.5);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(xanax.toString());
        when(clientHttp.send(Mockito.any(HttpRequest.class))).thenReturn(httpResponse);
        TrendMarket trendMarket=new TrendMarket(clientHttp,new URI("https://localhost:8080"));
        double result=trendMarket.extract_trend_market("xanax");
        Assertions.assertNotEquals(8.5,result);





    }




}