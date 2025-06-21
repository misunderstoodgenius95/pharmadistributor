package pharma;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PromotionPriceCalculatorTest {

    @Test
    void demonstrateStockUrgency() {
        System.out.println(500.0/180.0);
        double estimatedDailySales = Math.max(1.0,500.0/180.0);
        System.out.println(estimatedDailySales);
    }


    @Test
    @DisplayName("Test with zero stock quantity")
    void testCalculateStockUrgency_zeroStock() {
        int stockQuantity = 0;
        long daysUntilExpiration = 300; // This parameter is currently unused by the method
        double expectedUrgency = 1.0; // Math.max(1.0, 0 / 45.0) = Math.max(1.0, 0.0) = 1.0
        double actualUrgency = PromotionPriceCalculator.calculateStockUrgency(stockQuantity, daysUntilExpiration);
        assertEquals(expectedUrgency, actualUrgency,
                "Urgency for zero stock should be 1.0");
    }


    @Test
    @DisplayName("Test with large stock quantity")
    void testCalculateStockUrgency_largeStock() {
        int stockQuantity = 4500;
        long daysUntilExpiration = 100;
        // expectedUrgency = Math.max(1.0, 4500 / 45.0) = Math.max(1.0, 100.0) = 100.0
        double expectedUrgency = 100.0;
        double actualUrgency = PromotionPriceCalculator.calculateStockUrgency(stockQuantity, daysUntilExpiration);
        assertEquals(expectedUrgency, actualUrgency,
                "Urgency for stock = 4500 should be 100.0");
    }


}