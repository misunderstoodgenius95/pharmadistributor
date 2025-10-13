package pharma.formula;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.Controller.subpanel.SuggestPurchase;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.config.net.ClientHttp;
import pharma.dao.PurchaseOrderDetailDao;
import pharma.dao.SuggestPriceConfigDao;
import pharma.dao.SuggestPriceDao;

import javax.print.DocFlavor;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class PriceSuggestionTest {
    @Mock
    private  TrendMarket trendMarket;
    @Mock
    private SuggestPriceConfigDao suggestPriceConfigDao;
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
         /*   suggestion=new PriceSuggestion(hashMap,trendMarket,suggestPriceConfigDao);*/
        }
        @Test
        public void  ValidPriceCentroid(){
            when(kMeans.get_max_centroid()).thenReturn(45.25);
            Assertions.assertEquals(45.25,suggestion.calculate_price_centroid(kMeans));
        }
        @Test
        public void ValidGain(){
            Assertions.assertEquals(54.31,suggestion.gain(45.26));

        }
        @Test
        public  void ValidCalulateTrendMarket(){
            when(trendMarket.extract_trend_market(Mockito.anyInt())).thenReturn(20.0);
            Assertions.assertEquals(10.0,suggestion.calculate_trend_market(54.31,346));



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
        when(suggestPriceConfigDao.findById(anyInt())).thenReturn(FieldData.FieldDataBuilder.getbuilder().setFarmaco_id(340).setMedium_lots(300).setMedium_day(200).build());
        KMeans kMeans=new KMeans(List.of(new ClusterPoint(new double[]{ 6.60,5.50})),1);
        when(trendMarket.extract_trend_market(Mockito.anyInt())).thenReturn(20.0);
//        PriceSuggestion suggestion=new PriceSuggestion(hashMap,trendMarket,suggestPriceConfigDao);
       // Assertions.assertEquals(6.39,suggestion.suggest_price(kMeans,340));

      /*  PriceSuggestion suggestion=new PriceSuggestion(kMeans,hashMap,trendMarket,340);*/
      //  System.out.println("suggestion"+suggestion.suggest_price(600,300));



    }
    @Test
    void Integration_suggest_price() throws URISyntaxException {
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, String> hashMap=new HashMap<>();
        hashMap.put("gain","0.20");
        hashMap.put("min_day_expire","180");
        hashMap.put("medium_stock_item","550");
        URI uri=new URI("https://gist.githubusercontent.com/misunderstoodgenius95/4006133ef46f0a0459094df99d6baa18/raw/8f34fbaf0779b38505370a08282f4293f74e33c2/trendmarket.json");
        TrendMarket trendMarket=new TrendMarket(new ClientHttp(),uri);
        SuggestPriceConfigDao configDao=new SuggestPriceConfigDao(Database.getInstance(properties));
        PurchaseOrderDetailDao po_detail=new PurchaseOrderDetailDao(Database.getInstance(properties));
        PriceSuggestion suggestion=new PriceSuggestion(hashMap,trendMarket,configDao,po_detail);
        System.out.println("suggestion"+suggestion.suggest_price(340));

       // PriceSuggestion suggestion=new PriceSuggestion(hashMap,trendMarket,340);

//      Assertions.assertEquals(8.59,suggestion.suggest_price(600,300));



    }
}