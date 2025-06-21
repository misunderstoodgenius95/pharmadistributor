import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pharma.StockEmergencyCalculator;

import static org.junit.jupiter.api.Assertions.*;

public class StockEmergencyCalculatorTest {

    // Test constants
    private static final double DELTA = 0.001; // Tolerance for double comparisons
    private static final double DEFAULT_THRESHOLD = 180.0;

    @Test
    @DisplayName("Calculate emergency level with normal values")
    public void testCalculateEmergencyLevel_NormalValues() {
        // Test with moderate stock and expiration
        double result = StockEmergencyCalculator.calculateEmergencyLevel(100, 90);
        assertTrue(result >= 0.0 && result <= 1.0, "Emergency level should be between 0 and 1");
        
        // Test with high urgency scenario
        double highUrgency = StockEmergencyCalculator.calculateEmergencyLevel(500, 10);
        assertTrue(highUrgency > 0.5, "High stock with near expiration should have high urgency");
        
        // Test with low urgency scenario
        double lowUrgency = StockEmergencyCalculator.calculateEmergencyLevel(10, 200);
        assertTrue(lowUrgency < 0.3, "Low stock with distant expiration should have low urgency");
    }

    @Test
    @DisplayName("Calculate emergency level with edge cases")
    public void testCalculateEmergencyLevel_EdgeCases() {
        // Test with zero stock
        double zeroStock = StockEmergencyCalculator.calculateEmergencyLevel(0, 100);
        assertEquals(0.0, zeroStock, DELTA, "Zero stock should result in zero urgency");
        
        // Test with expired product
        double expired = StockEmergencyCalculator.calculateEmergencyLevel(100, 0);
        assertTrue(expired > 0.5, "Expired product should have high urgency");
        
        // Test with negative days (expired)
        double negativeExpiry = StockEmergencyCalculator.calculateEmergencyLevel(50, -10);
        assertTrue(negativeExpiry > 0.5, "Negative expiry days should indicate high urgency");
    }

    @ParameterizedTest
    @DisplayName("Calculate expiration urgency with various days")
    @CsvSource({
        "0, 1.0",      // Expired
        "-5, 1.0",     // Expired (negative days)
        "180, 0.0",    // At threshold
        "200, 0.0",    // Beyond threshold
        "90, 0.3",     // Half way to threshold (approximate)
        "45, 0.6",     // Quarter way to threshold (approximate)
        "1, 1.0"       // Almost expired
    })
    public void testCalculateExpirationUrgency(long days, double expectedApprox) {
        double result = StockEmergencyCalculator.calculateExpirationUrgency(days, DEFAULT_THRESHOLD);
        assertTrue(result >= 0.0 && result <= 1.0, "Urgency should be between 0 and 1");
        
        if (days <= 0) {
            assertEquals(1.0, result, DELTA, "Expired items should have maximum urgency");
        } else if (days >= DEFAULT_THRESHOLD) {
            assertEquals(0.0, result, DELTA, "Items beyond threshold should have no urgency");
        }
    }

    @Test
    @DisplayName("Test expiration urgency calculation precision")
    public void testExpirationUrgency_Precision() {
        // Test that values are rounded to 1 decimal place
        double result = StockEmergencyCalculator.calculateExpirationUrgency(100, DEFAULT_THRESHOLD);
        double rounded = Math.round(result * 10.0) / 10.0;
        assertEquals(rounded, result, DELTA, "Result should be rounded to 1 decimal place");
    }

    @ParameterizedTest
    @DisplayName("Calculate stock urgency with various scenarios")
    @CsvSource({
        "0, 100, 0.0",     // No stock
        "100, 200, 0.0",   // Plenty of time to sell
        "100, 90, 0.0",    // Can sell in time with room to spare
        "200, 45, 0.4",    // Tight timeline
        "500, 30, 0.8",    // Very tight timeline
        "1000, 10, 0.9"    // Nearly impossible timeline
    })
    public void testCalculateStockUrgency(int stock, long days, double expectedRange) {
        double result = StockEmergencyCalculator.calculateStockUrgency(stock, days);
        assertTrue(result >= 0.0 && result <= 1.0, "Stock urgency should be between 0 and 1");
        
        // Check if result is in expected range (within 0.2 tolerance)
        if (expectedRange == 0.0) {
            assertTrue(result <= 0.2, "Low urgency scenario should have low result");
        } else if (expectedRange >= 0.8) {
            assertTrue(result >= 0.6, "High urgency scenario should have high result");
        }
    }

    @Test
    @DisplayName("Test stock urgency precision")
    public void testStockUrgency_Precision() {
        double result = StockEmergencyCalculator.calculateStockUrgency(150, 60);
        double rounded = Math.round(result * 1000.0) / 1000.0;
        assertEquals(rounded, result, DELTA, "Result should be rounded to 3 decimal places");
    }

    @ParameterizedTest
    @DisplayName("Calculate composite urgency with weighted factors")
    @CsvSource({
        "0.0, 0.0, 0.0",
        "1.0, 0.0, 0.7",   // Only expiration urgency
        "0.0, 1.0, 0.3",   // Only stock urgency
        "1.0, 1.0, 1.0",   // Maximum urgency
        "0.5, 0.5, 0.5",   // Equal moderate urgency
        "0.8, 0.2, 0.62"   // High expiration, low stock
    })
    public void testCalculateCompositeUrgency(double expirationUrgency, double stockUrgency, double expected) {
        double result = StockEmergencyCalculator.calculateCompositeUrgency(expirationUrgency, stockUrgency);
        assertEquals(expected, result, DELTA, "Composite urgency calculation incorrect");
        assertTrue(result >= 0.0 && result <= 1.0, "Composite urgency should be between 0 and 1");
    }

    @Test
    @DisplayName("Test composite urgency precision")
    public void testCompositeUrgency_Precision() {
        double result = StockEmergencyCalculator.calculateCompositeUrgency(0.123, 0.456);
        double rounded = Math.round(result * 1000.0) / 1000.0;
        assertEquals(rounded, result, DELTA, "Result should be rounded to 3 decimal places");
    }

    @ParameterizedTest
    @DisplayName("Get urgency level descriptions")
    @CsvSource({
        "0.95, CRITICAL",
        "0.85, CRITICAL",
        "0.84, HIGH",
        "0.65, HIGH",
        "0.64, MEDIUM",
        "0.40, MEDIUM",
        "0.39, LOW",
        "0.20, LOW",
        "0.19, MINIMAL",
        "0.0, MINIMAL"
    })
    public void testGetUrgencyLevel(double urgency, String expectedLevel) {
        String result = StockEmergencyCalculator.getUrgencyLevel(urgency);
        assertEquals(expectedLevel, result, "Urgency level description is incorrect");
    }

    @Test
    @DisplayName("Calculate recommended discount for various urgency levels")
    public void testCalculateRecommendedDiscount() {
        double originalPrice = 100.0;
        double costPrice = 50.0;
        
        // Test CRITICAL urgency
        double criticalDiscount = StockEmergencyCalculator.calculateRecommendedDiscount(0.9, originalPrice, costPrice);
        assertTrue(criticalDiscount >= 40.0, "Critical urgency should have high discount");
        assertTrue(criticalDiscount <= 50.0, "Discount should not exceed maximum safe range");
        
        // Test MINIMAL urgency
        double minimalDiscount = StockEmergencyCalculator.calculateRecommendedDiscount(0.1, originalPrice, costPrice);
        assertTrue(minimalDiscount >= 0.0 && minimalDiscount <= 5.0, "Minimal urgency should have low discount");
        
        // Test that discount doesn't cause selling below cost
        double highCostDiscount = StockEmergencyCalculator.calculateRecommendedDiscount(0.9, 100.0, 95.0);
        assertTrue(highCostDiscount < 10.0, "Discount should not cause selling below cost + 5%");
    }

    @Test
    @DisplayName("Calculate recommended discount edge cases")
    public void testCalculateRecommendedDiscount_EdgeCases() {
        // Test when cost price equals original price
        double noMarginDiscount = StockEmergencyCalculator.calculateRecommendedDiscount(0.9, 100.0, 100.0);
        assertEquals(0.0, noMarginDiscount, DELTA, "No discount when no margin available");
        
        // Test when cost price is higher than original price
        double negativMarginDiscount = StockEmergencyCalculator.calculateRecommendedDiscount(0.9, 50.0, 100.0);
        assertEquals(0.0, negativMarginDiscount, DELTA, "No discount when cost exceeds price");
    }

    @Test
    @DisplayName("Calculate suggested price")
    public void testCalculateSuggestedPrice() {
        double originalPrice = 100.0;
        double costPrice = 60.0;
        
        // Test normal case
        double suggestedPrice = StockEmergencyCalculator.calculateSuggestedPrice(originalPrice, costPrice, 0.5);
        assertTrue(suggestedPrice < originalPrice, "Suggested price should be less than original");
        assertTrue(suggestedPrice >= costPrice * 1.05, "Suggested price should maintain minimum markup");
        
        // Test high urgency case
        double highUrgencPrice = StockEmergencyCalculator.calculateSuggestedPrice(originalPrice, costPrice, 0.9);
        assertTrue(highUrgencPrice < suggestedPrice, "Higher urgency should result in lower price");
        assertTrue(highUrgencPrice >= costPrice * 1.05, "Should maintain minimum markup even at high urgency");
    }

    @Test
    @DisplayName("Calculate suggested price maintains minimum markup")
    public void testCalculateSuggestedPrice_MinimumMarkup() {
        double originalPrice = 100.0;
        double costPrice = 98.0; // Very high cost
        
        double suggestedPrice = StockEmergencyCalculator.calculateSuggestedPrice(originalPrice, costPrice, 1.0);
        double expectedMinPrice = costPrice * 1.05;
        
        assertEquals(expectedMinPrice, suggestedPrice, DELTA, "Should maintain minimum 5% markup");
    }
/*

    @ParameterizedTest
    @DisplayName("Integration test with realistic pharmacy scenarios")
    @CsvSource({
        "50, 30, 25.0, 15.0",      // Medium stock, month to expiry
        "200, 7, 100.0, 60.0",     // High stock, week to expiry  
        "10, 180, 50.0, 30.0",     // Low stock, long expiry
        "500, 1, 200.0, 120.0",    // Very high stock, expires tomorrow
        "0, 100, 75.0, 45.0"       // No stock (edge case)
    })
    public void testIntegrationScenarios(int stock, long days, double originalPrice, double costPrice) {
        // Calculate emergency level
  */
/*      double emergencyLevel = StockEmergencyCalculator.calculateEmergencyLevel(stock, days);
        assertTrue(emergencyLevel >= 0.0 && emergency <= 1.0, "Emergency level should be valid");
        *//*

        // Get urgency description
        String urgencyLevel = StockEmergencyCalculator.getUrgencyLevel(emergencyLevel);
        assertNotNull(urgencyLevel, "Urgency level description should not be null");
        assertTrue(urgencyLevel.matches("CRITICAL|HIGH|MEDIUM|LOW|MINIMAL"), "Should return valid urgency level");
        
        // Calculate suggested price
        double suggestedPrice = StockEmergencyCalculator.calculateSuggestedPrice(originalPrice, costPrice, emergencyLevel);
        assertTrue(suggestedPrice > 0, "Suggested price should be positive");
        assertTrue(suggestedPrice >= costPrice * 1.05, "Should maintain minimum markup");
        assertTrue(suggestedPrice <= originalPrice, "Suggested price should not exceed original");
    }
*/

    @Test
    @DisplayName("Test mathematical properties and constraints")
    public void testMathematicalProperties() {
        // Test that higher stock with same expiration leads to higher urgency
        double lowStock = StockEmergencyCalculator.calculateEmergencyLevel(50, 30);
        double highStock = StockEmergencyCalculator.calculateEmergencyLevel(500, 30);
        assertTrue(highStock >= lowStock, "Higher stock should have equal or higher urgency with same expiration");
        
        // Test that shorter expiration with same stock leads to higher urgency
        double longExpiry = StockEmergencyCalculator.calculateEmergencyLevel(100, 150);
        double shortExpiry = StockEmergencyCalculator.calculateEmergencyLevel(100, 15);
        assertTrue(shortExpiry > longExpiry, "Shorter expiration should have higher urgency");
        
        // Test monotonicity of urgency levels
        assertTrue(StockEmergencyCalculator.getUrgencyLevel(0.1).equals("MINIMAL"));
        assertTrue(StockEmergencyCalculator.getUrgencyLevel(0.3).equals("LOW"));
        assertTrue(StockEmergencyCalculator.getUrgencyLevel(0.5).equals("MEDIUM"));
        assertTrue(StockEmergencyCalculator.getUrgencyLevel(0.7).equals("HIGH"));
        assertTrue(StockEmergencyCalculator.getUrgencyLevel(0.9).equals("CRITICAL"));
    }
}