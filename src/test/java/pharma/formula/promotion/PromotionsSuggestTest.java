package pharma.formula.promotion;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PromotionsSuggestTest {

    @Test
    void get_promotion_discount() {

        double[] sellerPriceHistory = {110, 130.0};
        double[] currentPurchasePrices = {40.0,50.0, 60.0};
        double[] purchasePriceHistory = {90.0,110};
        double currentPrice = 82.0;




        List<Date> dateList = List.of(
                Date.valueOf(LocalDate.of(2023, 1, 10)),
                Date.valueOf(LocalDate.of(2023, 1, 20)),
                Date.valueOf(LocalDate.of(2023, 1, 30)));

        int[] stock_qty=new int[]{150,200,300,100};
        PromotionsSuggest promotionsSuggest=new PromotionsSuggest(sellerPriceHistory,purchasePriceHistory,dateList,stock_qty,30);
        System.out.println(promotionsSuggest.get_promotion_discount(currentPurchasePrices,currentPrice));



    }
}