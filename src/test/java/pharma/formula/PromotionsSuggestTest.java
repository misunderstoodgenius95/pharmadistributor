package pharma.formula;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pharma.formula.TrasformValue.calculate_percent_gain;

class PromotionsSuggestTest {





    @Test
    void shouldReturnTwentyPercentGain() {
        // Arrange: Values calculated to yield 20% gain
        double[] sellerPriceHistory = {110, 130.0};
        double[] currentPurchasePrices = {40.0,50.0, 60.0};
        double[] purchasePriceHistory = {90.0,110};
        double currentPrice = 82.0;

        double expectedGain = 20.0;

        // Act
        double actualGain = calculate_percent_gain(
                sellerPriceHistory,
                currentPurchasePrices,
                purchasePriceHistory,
                currentPrice
        );
       double percent= TrasformValue.normalize_percentuages(actualGain);
        System.out.println(percent);
        // Assert
        assertEquals(expectedGain, actualGain,
                "The calculated percent gain should be 20.0");
    }





/*
    @Test
    void average_day() {
        List<Date> dateList = List.of(
                Date.valueOf(LocalDate.of(2023, 1, 10)),
                Date.valueOf(LocalDate.of(2023, 1, 20)),
                Date.valueOf(LocalDate.of(2023, 1, 30)));

        PromotionsSuggest promotionsSuggest=new PromotionsSuggest(dateList);
        System.out.println(promotionsSuggest.average_day());


    }*/


}