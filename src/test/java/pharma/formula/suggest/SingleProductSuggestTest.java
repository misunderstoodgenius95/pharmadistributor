package pharma.formula.suggest;

import org.junit.jupiter.api.*;
import pharma.formula.suggest.Model.Lots;
import pharma.formula.suggest.Model.SellerOrders;
import pharma.formula.suggest.Model.SuggestConfig;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

class SingleProductSuggestTest {
    SingleProductSuggest singleProductSuggest;

    @BeforeEach
    void setUp() {
        SellerOrders sellerOrders1=new SellerOrders(1,10,Date.valueOf(LocalDate.now()));
        SellerOrders sellerOrders2=new SellerOrders(1,30,Date.valueOf(LocalDate.now()));
        SuggestConfig suggestConfig = new SuggestConfig(180, 500,200);
        Lots lots1= new Lots("ax21",10, Date.valueOf(LocalDate.of(2025,11,1)), 10);
        Lots lots2= new Lots("ax22",11, Date.valueOf(LocalDate.of(2025,12,10)), 100);
        singleProductSuggest=new SingleProductSuggest(List.of(lots1,lots2),suggestConfig,List.of(sellerOrders1,sellerOrders2));
    }

    @Test
    void calculate_average_date() {

        System.out.println(singleProductSuggest.calculateDateofSum());



    }

















    @AfterEach
    void tearDown() {
        singleProductSuggest=null;
    }








    @Nested
     public class ValueHightAndLong {
        SingleProductSuggest singleProductSuggest;

        @BeforeEach
        void setUp() {
            SellerOrders sellerOrders1=new SellerOrders(1,300,Date.valueOf(LocalDate.now()));
            SellerOrders sellerOrders2=new SellerOrders(1,400,Date.valueOf(LocalDate.now()));
            SuggestConfig suggestConfig = new SuggestConfig(180, 500,200);
            Lots lots1 = new Lots("ax21", 10, Date.valueOf(LocalDate.of(2030, 10, 1)), 1000);
            Lots lots2 = new Lots("ax22", 11, Date.valueOf(LocalDate.of(2030, 10, 1)), 1000);
            singleProductSuggest = new SingleProductSuggest(List.of(lots1, lots2), suggestConfig,List.of(sellerOrders1,sellerOrders2));

        }


        @Test
        void calculate_suggest() {
           Assertions.assertFalse(singleProductSuggest.calculate_suggest());


        }
    }

    /**
     * Se le scadenze sono brevi,non sono stati acquistati alcuni prodotti allora-> false
     */
    @Nested
    public class ValueNotHightAndNOtLong {
        SingleProductSuggest singleProductSuggest;

        @BeforeEach
        void setUp() {
            SuggestConfig suggestConfig = new SuggestConfig(180, 500,200);
            SellerOrders sellerOrders1=new SellerOrders(1,20,Date.valueOf(LocalDate.now()));
            SellerOrders sellerOrders2=new SellerOrders(1,20,Date.valueOf(LocalDate.now()));
            Lots lots1 = new Lots("ax21", 10, Date.valueOf(LocalDate.of(2026, 10, 1)), 400);
            Lots lots2 = new Lots("ax22", 11, Date.valueOf(LocalDate.of(2027, 10, 1)), 300);
            singleProductSuggest = new SingleProductSuggest(List.of(lots1, lots2), suggestConfig,List.of(sellerOrders1,sellerOrders2));

        }


        @Test
        void calculate_suggest() {
           Assertions.assertFalse(singleProductSuggest.calculate_suggest());


        }
    }


    @Nested
    public class NotHightNoLong {
        SingleProductSuggest singleProductSuggest;

        @BeforeEach
        void setUp() {
            SuggestConfig suggestConfig = new SuggestConfig(180, 500,200);
            SellerOrders sellerOrders1=new SellerOrders(1,200,Date.valueOf(LocalDate.now()));
            SellerOrders sellerOrders2=new SellerOrders(1,200,Date.valueOf(LocalDate.now()));
            Lots lots1 = new Lots("ax21", 10, Date.valueOf(LocalDate.of(2025, 10, 1)), 40);
            Lots lots2 = new Lots("ax22", 11, Date.valueOf(LocalDate.of(2025, 10, 1)), 30);
            singleProductSuggest = new SingleProductSuggest(List.of(lots1, lots2), suggestConfig,List.of(sellerOrders1,sellerOrders2));

        }


        @Test
        void calculate_suggest() {
            Assertions.assertTrue(singleProductSuggest.calculate_suggest());


        }
    }
    @Nested
    public class AllEmpty {
        SingleProductSuggest singleProductSuggest;

        @BeforeEach
        void setUp() {
            SuggestConfig suggestConfig = new SuggestConfig(180, 500,200);

            singleProductSuggest = new SingleProductSuggest(List.of(), suggestConfig,List.of());

        }


        @Test
        void calculate_suggest() {
            Assertions.assertTrue(singleProductSuggest.calculate_suggest());


        }
    }
}