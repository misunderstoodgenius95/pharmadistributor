package pharma.formula;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.print.DocFlavor;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    void testSuggest_price() {
    }


    @Nested
     class ValidSuggstPart{
        private PriceSuggestion suggestion;
        @BeforeEach
        void suggest_price() {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("gain", "20");
            hashMap.put("min_day_expire", "180");
            hashMap.put("medium_stock_item", "550");
            suggestion=new PriceSuggestion(kMeans,3,hashMap,trendMarket);


        }
        @Test
        public void  ValidPriceCentroid(){
            when(kMeans.get_max_centroid()).thenReturn(45.25);
            Assertions.assertEquals(45.25,suggestion.calculate_price_centroid());
        }
        @Test
        public void ValidGain(){
            Assertions.assertEquals(54.31,suggestion.gain(45.26));

        }
        @Test
        public  void ValidCalulateTrendMarket(){
            when(trendMarket.extract_trend_market(Mockito.anyString())).thenReturn(20.0);
            Assertions.assertEquals(10.0,suggestion.calculate_trend_market(54.31));



        }

        @Test
        public  void Calculate_Stock(){
          Assertions.assertEquals(59.74,suggestion.calculate_stock(400,54.31));

        }
        @Test
        void calculate_day() {
            Assertions.assertEquals(53.76,suggestion.calculate_day(200,59.74));
        }






    }




    @Test
    void suggest_price() {
        HashMap<String, String> hashMap=new HashMap<>();
        hashMap.put("gain","0.20");
        hashMap.put("min_day_expire","180");
        hashMap.put("medium_stock_item","550");


        PriceSuggestion suggestion=new PriceSuggestion(kMeans,3,hashMap,trendMarket);
        System.out.println("sggestion"+suggestion.suggest_price(600,300));



    }
}