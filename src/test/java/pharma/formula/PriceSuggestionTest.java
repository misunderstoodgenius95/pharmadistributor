package pharma.formula;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.print.DocFlavor;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PriceSuggestionTest {
    @Mock
    private  TrendMarket trendMarket;
    @Mock
    private  KMeans kMeans;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);



    }

    @Test
    void suggest_price() {
        HashMap<String, String> hashMap=new HashMap<>();
        hashMap.put("gain","0.20");
        hashMap.put()


        PriceSuggestion suggestion=new PriceSuggestion(kMeans,3,hashMap,trendMarket);


    }
}