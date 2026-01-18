package pharma.Service.suggest;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pharma.Controller.subpanel.SellerOrder;
import pharma.Model.Lots;
import pharma.Model.SellerOrders;
import pharma.Model.SuggestConfig;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SingleProductSuggestTest {

    @Nested
    public class NotPurchase {
        SingleProductSuggest singleProductSuggest;
        @BeforeEach
         public void setUp() {
            List<Lots> list = List.of(

                    new Lots("ax11", 1, Date.valueOf(LocalDate.of(2027, 10, 1)), 100),
                    new Lots("ax12", 2, Date.valueOf(LocalDate.of(2027, 10, 2)), 200),
                    new Lots("ax12", 2, Date.valueOf(LocalDate.of(2027, 10, 2)), 200));
            SuggestConfig suggestConfig = new SuggestConfig(180, 500,200);
            List<SellerOrders> sellerOrders=List.of(
                    new SellerOrders(1,100,Date.valueOf(LocalDate.of(2027,10,1))),
            new SellerOrders(2,130,Date.valueOf(LocalDate.of(2027,2,1))));
           singleProductSuggest = new SingleProductSuggest(list, suggestConfig,sellerOrders);
        }

       @Test
        public void NotPurchaseTest(){
           Assertions.assertFalse(singleProductSuggest.calculate_suggest());
       }
    }
    @Nested
    public class Purchase {
        SingleProductSuggest singleProductSuggest;
        @BeforeEach
        public void setUp() {
            List<Lots> list = List.of(

                    new Lots("ax11", 1, Date.valueOf(LocalDate.of(2027, 10, 1)), 100));
            SuggestConfig suggestConfig = new SuggestConfig(180, 500,200);
            List<SellerOrders> sellerOrders=List.of(new SellerOrders(1,100,Date.valueOf(LocalDate.of(2027,10,1))));
            singleProductSuggest = new SingleProductSuggest(list, suggestConfig,sellerOrders);
        }

        @Test
        public void NotPurchaseTest(){
            Assertions.assertTrue(singleProductSuggest.calculate_suggest());



        }
    }
    @Nested
    public class NOtPurchaseWithMooreLOts {
        SingleProductSuggest singleProductSuggest;
        @BeforeEach
        public void setUp() {
            List<Lots> list = List.of(

                    new Lots("ax11", 1, Date.valueOf(LocalDate.of(2027, 10, 1)), 100),
            new Lots("ax12", 3, Date.valueOf(LocalDate.of(2027, 10, 1)), 100),
            new Lots("ax13", 15, Date.valueOf(LocalDate.of(2027, 10, 1)), 500));

            SuggestConfig suggestConfig = new SuggestConfig(180, 500,200);
            List<SellerOrders> sellerOrders=List.of(new SellerOrders(1,100,Date.valueOf(LocalDate.of(2027,10,1))));
            singleProductSuggest = new SingleProductSuggest(list, suggestConfig,sellerOrders);
        }

        @Test
        public void NotPurchaseTest(){
            Assertions.assertFalse(singleProductSuggest.calculate_suggest());



        }
    }
}