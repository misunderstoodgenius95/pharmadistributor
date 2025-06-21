package pharma;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PromotionPriceCalculator {

    // Price suggestion result class
    public static class PriceSuggestion {
        private final double suggestedPrice;
        private final double discountPercentage;
        private final String strategy;
        private final String urgencyLevel;
        private final String reasoning;
        private final double expectedRevenue;

        public PriceSuggestion(double suggestedPrice, double discountPercentage, String strategy,
                               String urgencyLevel, String reasoning, double expectedRevenue) {
            this.suggestedPrice = suggestedPrice;
            this.discountPercentage = discountPercentage;
            this.strategy = strategy;
            this.urgencyLevel = urgencyLevel;
            this.reasoning = reasoning;
            this.expectedRevenue = expectedRevenue;
        }

        // Getters
        public double getSuggestedPrice() { return suggestedPrice; }
        public double getDiscountPercentage() { return discountPercentage; }
        public String getStrategy() { return strategy; }
        public String getUrgencyLevel() { return urgencyLevel; }
        public String getReasoning() { return reasoning; }
        public double getExpectedRevenue() { return expectedRevenue; }

        @Override
        public String toString() {
            return String.format("Price: $%.2f (%.1f%% off) | Strategy: %s | Urgency: %s\nRevenue: $%.2f | %s",
                    suggestedPrice, discountPercentage, strategy, urgencyLevel, expectedRevenue, reasoning);
        }
    }

    /**
     * Calculate optimal promotion price based on expiration date and stock quantity
     */
    public static PriceSuggestion calculatePromotionPrice(
            double originalPrice,
            double costPrice,
            LocalDate expirationDate,
            int stockQuantity) {

        // Calculate days until expiration
        long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);

        // Calculate urgency factors (0-1 scale)
        double expirationUrgency = calculateExpirationUrgency(daysUntilExpiration);
        double stockUrgency = calculateStockUrgency(stockQuantity, daysUntilExpiration);

        // Calculate composite urgency score (0-1 scale)
        double compositeUrgency = calculateCompositeUrgency(expirationUrgency, stockUrgency);

        // Determine pricing strategy
        return determinePricingStrategy(originalPrice, costPrice, daysUntilExpiration,
                stockQuantity, compositeUrgency, expirationUrgency, stockUrgency);
    }

    /**
     * Calculate expiration urgency (0-1 scale)
     */
    private static double calculateExpirationUrgency(long daysUntilExpiration) {
        if (daysUntilExpiration <= 0) return 1.0; // Expired - maximum urgency
        if (daysUntilExpiration >= 180) return 0.0;  // 180+ days, no urgency

        // Exponential urgency increase as expiration approaches
        double urgency = Math.pow((180.0 - daysUntilExpiration) / 180.0, 2);
        return Math.round(urgency * 1000.0) / 1000.0; // Round to 3 decimal places
    }

    /**
     * Calculate stock urgency based on quantity and time available (0-1 scale)
     */
  public  static double calculateStockUrgency(int stockQuantity, long daysUntilExpiration) {
        // Estimate reasonable daily sales rate based on stock quantity
        // Assumption: normal products should sell within 30-60 days under regular conditions
        double estimatedDailySales = Math.max(1.0, stockQuantity / 45.0); // 45-day baseline turnover
        return 1-estimatedDailySales;
      // return calculateStockUrgencyWithVelocity(stockQuantity, daysUntilExpiration, estimatedDailySales);
    }

    /**
     * Helper method to calculate stock urgency with known velocity
     */
    private static double calculateStockUrgencyWithVelocity(int stockQuantity, long daysUntilExpiration,
                                                            double avgDailySales) {
        // Calculate how many days it would take to sell all stock at current velocity
        double daysToSellStock = stockQuantity / avgDailySales;

        // Calculate the ratio: how much time we need vs how much time we have
        double timeRatio = daysToSellStock / Math.max(1, daysUntilExpiration);

        // Convert ratio to 0-1 urgency scale
        double stockUrgency;

        if (timeRatio <= 0.5) {
            // We can sell stock in half the available time - very low urgency
            stockUrgency = 0.0;
        } else if (timeRatio <= 1.0) {
            // We can just barely sell stock in time - linear increase from 0 to 0.4
            stockUrgency = (timeRatio - 0.5) * 0.8; // Maps 0.5-1.0 to 0.0-0.4
        } else if (timeRatio <= 2.0) {
            // We need up to 2x the available time - moderate to high urgency
            stockUrgency = 0.4 + (timeRatio - 1.0) * 0.4; // Maps 1.0-2.0 to 0.4-0.8
        } else {
            // We need more than 2x the available time - very high urgency
            stockUrgency = 0.8 + Math.min(0.2, (timeRatio - 2.0) * 0.1); // Approaches 1.0
        }

        return Math.round(stockUrgency * 1000.0) / 1000.0; // Round to 3 decimal places
    }

    /**
     * Calculate composite urgency with weighted factors (0-1 scale)
     */
    private static double calculateCompositeUrgency(double expirationUrgency, double stockUrgency) {
        // Expiration is more critical than stock level
        double composite = (expirationUrgency * 0.7) + (stockUrgency * 0.3);
        return Math.round(composite * 1000.0) / 1000.0; // Round to 3 decimal places
    }

    /**
     * Determine optimal pricing strategy
     */
    private static PriceSuggestion determinePricingStrategy(double originalPrice, double costPrice,
                                                            long daysUntilExpiration, int stockQuantity,
                                                            double compositeUrgency, double expirationUrgency,
                                                            double stockUrgency) {

        double minPrice = costPrice * 1.05; // Minimum 5% markup to avoid loss
        double maxDiscount = Math.min(70.0, ((originalPrice - minPrice) / originalPrice) * 100.0);

        String strategy;
        String urgencyLevel;
        double discountPercentage;
        String reasoning;

        // Critical urgency (0.85-1.0)
        if (compositeUrgency >= 0.85) {
            strategy = "EMERGENCY LIQUIDATION";
            urgencyLevel = "CRITICAL";
            discountPercentage = Math.min(maxDiscount, 40 + (compositeUrgency - 0.85) * 200); // Scale up from 0-1 to percentage
            reasoning = String.format("Critical: %d days left, %d units. Risk of total loss! (Urgency: %.3f)",
                    daysUntilExpiration, stockQuantity, compositeUrgency);
        }
        // High urgency (0.65-0.84)
        else if (compositeUrgency >= 0.65) {
            strategy = "AGGRESSIVE CLEARANCE";
            urgencyLevel = "HIGH";
            discountPercentage = Math.min(maxDiscount, 25 + (compositeUrgency - 0.65) * 150); // Scale up
            reasoning = String.format("High urgency: %d days, %d units. Quick action needed. (Urgency: %.3f)",
                    daysUntilExpiration, stockQuantity, compositeUrgency);
        }
        // Medium urgency (0.40-0.64)
        else if (compositeUrgency >= 0.40) {
            strategy = "PROMOTIONAL PRICING";
            urgencyLevel = "MEDIUM";
            discountPercentage = Math.min(maxDiscount, 10 + (compositeUrgency - 0.40) * 100); // Scale up
            reasoning = String.format("Moderate pressure: %d days remaining, %d units in stock. (Urgency: %.3f)",
                    daysUntilExpiration, stockQuantity, compositeUrgency);
        }
        // Low urgency (0.20-0.39)
        else if (compositeUrgency >= 0.20) {
            strategy = "GENTLE DISCOUNT";
            urgencyLevel = "LOW";
            discountPercentage = Math.min(maxDiscount, 5 + (compositeUrgency - 0.20) * 50); // Scale up
            reasoning = String.format("Low urgency: %d days available, manageable stock (%d units). (Urgency: %.3f)",
                    daysUntilExpiration, stockQuantity, compositeUrgency);
        }
        // Minimal urgency (0.0-0.19)
        else {
            strategy = "MAINTAIN PRICE";
            urgencyLevel = "MINIMAL";
            discountPercentage = Math.min(maxDiscount, compositeUrgency * 20); // Scale up to percentage
            reasoning = String.format("No urgency: %d days left, low stock (%d units). Hold pricing. (Urgency: %.3f)",
                    daysUntilExpiration, stockQuantity, compositeUrgency);
        }

        // Calculate suggested price
        double suggestedPrice = originalPrice * (1 - discountPercentage / 100.0);
        suggestedPrice = Math.max(suggestedPrice, minPrice); // Don't go below cost

        // Estimate expected revenue (simple calculation)
        double expectedRevenue = suggestedPrice * stockQuantity *
                calculateExpectedSalesRate(discountPercentage);

        return new PriceSuggestion(
                Math.round(suggestedPrice * 100.0) / 100.0,
                Math.round(discountPercentage * 10.0) / 10.0,
                strategy,
                urgencyLevel,
                reasoning,
                Math.round(expectedRevenue * 100.0) / 100.0
        );
    }

    /**
     * Estimate sales rate multiplier based on discount percentage (returns 0-1 scale)
     */
    private static double calculateExpectedSalesRate(double discountPercentage) {
        // Simple model: higher discount = higher sales rate
        if (discountPercentage >= 50) return 0.95; // 95% of stock likely to sell
        if (discountPercentage >= 30) return 0.85; // 85% likely to sell
        if (discountPercentage >= 15) return 0.70; // 70% likely to sell
        if (discountPercentage >= 5) return 0.60;  // 60% likely to sell
        return 0.50; // 50% at regular price
    }

    /**
     * Generate multiple pricing scenarios
     */
    public static List<PriceSuggestion> generatePricingScenarios(
            double originalPrice, double costPrice, LocalDate expirationDate,
            int stockQuantity) {

        List<PriceSuggestion> scenarios = new ArrayList<>();

        // Primary suggestion
        scenarios.add(calculatePromotionPrice(originalPrice, costPrice, expirationDate, stockQuantity));

        // Conservative scenario (lower discount)
        PriceSuggestion primary = scenarios.get(0);
        double conservativeDiscount = Math.max(0, primary.getDiscountPercentage() - 10);
        double conservativePrice = originalPrice * (1 - conservativeDiscount / 100.0);
        scenarios.add(new PriceSuggestion(
                Math.round(conservativePrice * 100.0) / 100.0,
                conservativeDiscount,
                "CONSERVATIVE " + primary.getStrategy(),
                "LOWER_RISK",
                "Conservative approach: " + primary.getReasoning(),
                conservativePrice * stockQuantity * calculateExpectedSalesRate(conservativeDiscount)
        ));

        // Aggressive scenario (higher discount)
        double aggressiveDiscount = Math.min(70, primary.getDiscountPercentage() + 15);
        double aggressivePrice = Math.max(costPrice * 1.05,
                originalPrice * (1 - aggressiveDiscount / 100.0));
        scenarios.add(new PriceSuggestion(
                Math.round(aggressivePrice * 100.0) / 100.0,
                aggressiveDiscount,
                "AGGRESSIVE " + primary.getStrategy(),
                "HIGHER_VELOCITY",
                "Aggressive approach: " + primary.getReasoning(),
                aggressivePrice * stockQuantity * calculateExpectedSalesRate(aggressiveDiscount)
        ));

        return scenarios;
    }

    // Helper method
    private static double calculateAverage(double[] values) {
        if (values == null || values.length == 0) return 0.0;
        double sum = 0.0;
        for (double value : values) sum += value;
        return sum / values.length;
    }

    /**
     * Demonstrate stock urgency calculations with different scenarios
     */
    public static void demonstrateStockUrgency() {
        System.out.println("=== STOCK URGENCY EXAMPLES ===");

        long daysUntilExp = 30; // 30 days until expiration

        int[] stockLevels = {50, 180, 360, 720}; // Different stock quantities

        for (int stock : stockLevels) {
            double urgency = calculateStockUrgency(stock, daysUntilExp);
            double estimatedDailySales = Math.max(1.0, stock / 45.0); // Using 45-day baseline
            double daysToSell = stock / estimatedDailySales;

            System.out.printf("Stock: %d units | Est. daily sales: %.1f | Days to sell: %.1f | Time available: %d | Urgency: %.3f%n",
                    stock, estimatedDailySales, daysToSell, daysUntilExp, urgency);
        }
        System.out.println();

        System.out.println("=== PROMOTION PRICE CALCULATOR (0-1 Scale, 180-day baseline) ===\n");

    // Example scenarios
    double originalPrice = 50.0;
    double costPrice = 25.0;
    int stockQuantity = 100;

    // Scenario 1: 3 days until expiration
    LocalDate expiration1 = LocalDate.now().plusDays(3);
        System.out.println("Scenario 1: 3 days until expiration, 100 units");
    PriceSuggestion suggestion1 = calculatePromotionPrice(originalPrice, costPrice,
            expiration1, stockQuantity);
        System.out.println(suggestion1);
        System.out.println();

    // Scenario 3: 60 days until expiration (medium term)
    LocalDate expiration3 = LocalDate.now().plusDays(60);
        System.out.println("Scenario 2: 60 days until expiration, 100 units");
    PriceSuggestion suggestion3 = calculatePromotionPrice(originalPrice, costPrice,
            expiration3, stockQuantity);
        System.out.println(suggestion3);
        System.out.println();

    // Scenario 4: 150 days until expiration (long term)
    LocalDate expiration4 = LocalDate.now().plusDays(150);
        System.out.println("Scenario 3: 150 days until expiration, 100 units");
    PriceSuggestion suggestion4 = calculatePromotionPrice(originalPrice, costPrice,
            expiration4, stockQuantity);
        System.out.println(suggestion4);
        System.out.println();

    // Multiple scenarios
        System.out.println("=== MULTIPLE PRICING SCENARIOS ===");
    List<PriceSuggestion> scenarios = generatePricingScenarios(originalPrice, costPrice,
            expiration1, stockQuantity);
        for (int i = 0; i < scenarios.size(); i++) {
        System.out.println("Option " + (i + 1) + ":");
        System.out.println(scenarios.get(i));
        System.out.println();
    }
}
}