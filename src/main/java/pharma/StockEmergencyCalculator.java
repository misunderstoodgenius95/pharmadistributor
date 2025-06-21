package pharma;

public class StockEmergencyCalculator {
    // Constants for calculations
    private static final double EXPIRATION_WEIGHT = 0.7;
    private static final double STOCK_WEIGHT = 0.3;
    private static final double DEFAULT_EXPIRATION_THRESHOLD = 180.0; // 180 days
    private static final double DEFAULT_STOCK_TURNOVER_DAYS = 45.0; // Expected to sell stock in 45 days

    /**
     * Calculate the emergency level based on stock quantity and days until expiration
     * Returns a value between 0.0 (no emergency) and 1.0 (critical emergency)
     *
     * @param stockQuantity Current stock quantity
     * @param daysUntilExpiration Days until product expires
     * @return Emergency level (0.0 - 1.0)
     */
    public static double calculateEmergencyLevel(int stockQuantity, long daysUntilExpiration) {
        // Calculate individual urgency factors
        double expirationUrgency = calculateExpirationUrgency(daysUntilExpiration, DEFAULT_EXPIRATION_THRESHOLD);
        double stockUrgency = calculateStockUrgency(stockQuantity, daysUntilExpiration);

        // Calculate composite urgency with weighted factors
        return calculateCompositeUrgency(expirationUrgency, stockUrgency);
    }

    /**
     * Calculate expiration urgency (0-1 scale)
     * 0.0 = no urgency (far from expiration)
     * 1.0 = maximum urgency (expired or very close to expiration)
     */
    public static double calculateExpirationUrgency(long daysUntilExpiration, double threshold) {
        if (daysUntilExpiration <= 0) return 1.0; // Expired - maximum urgency
        if (daysUntilExpiration >= threshold) return 0.0; // Far from expiration - no urgency

        // Exponential urgency increase as expiration approaches
        double urgency = Math.pow((threshold - daysUntilExpiration) / threshold, 2);
        return Math.round(urgency * 10.0) / 10.0; // Round to 1 decimal place
    }

    /**
     * Calculate stock urgency based on quantity and time available (0-1 scale)
     * Considers how quickly the stock needs to be sold before expiration
     */
    public static double calculateStockUrgency(int stockQuantity, long daysUntilExpiration) {
        // Estimate reasonable daily sales rate based on stock quantity
        double estimatedDailySales = Math.max(1.0, stockQuantity / DEFAULT_STOCK_TURNOVER_DAYS);

        // Calculate how many days it would take to sell all stock at current velocity
        double daysToSellStock = stockQuantity / estimatedDailySales;

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
     * Expiration is weighted more heavily than stock level
     */
    public static double calculateCompositeUrgency(double expirationUrgency, double stockUrgency) {
        double composite = (expirationUrgency * EXPIRATION_WEIGHT) + (stockUrgency * STOCK_WEIGHT);
        return Math.round(composite * 1000.0) / 1000.0; // Round to 3 decimal places
    }

    /**
     * Get a descriptive urgency level based on the numerical value
     */
    public static String getUrgencyLevel(double urgency) {
        if (urgency >= 0.85) return "CRITICAL";
        if (urgency >= 0.65) return "HIGH";
        if (urgency >= 0.40) return "MEDIUM";
        if (urgency >= 0.20) return "LOW";
        return "MINIMAL";
    }

    /**
     * Calculate the recommended discount percentage based on urgency
     * @param urgency The calculated urgency level (0-1)
     * @param originalPrice The original product price
     * @param costPrice The cost price (to ensure minimum markup)
     * @return Recommended discount percentage
     */
    public static double calculateRecommendedDiscount(double urgency, double originalPrice, double costPrice) {
        double minPrice = costPrice * 1.05; // Minimum 5% markup to avoid loss
        double maxDiscount = Math.min(70.0, ((originalPrice - minPrice) / originalPrice) * 100.0);

        if (urgency >= 0.85) {
            return Math.min(maxDiscount, 40 + (urgency - 0.85) * 200); // Scale up from 40-70%
        } else if (urgency >= 0.65) {
            return Math.min(maxDiscount, 25 + (urgency - 0.65) * 150); // Scale up from 25-40%
        } else if (urgency >= 0.40) {
            return Math.min(maxDiscount, 10 + (urgency - 0.40) * 100); // Scale up from 10-25%
        } else if (urgency >= 0.20) {
            return Math.min(maxDiscount, 5 + (urgency - 0.20) * 50); // Scale up from 5-10%
        } else {
            return Math.min(maxDiscount, urgency * 20); // Scale up to 5% max
        }
    }

    /**
     * Calculate the suggested price based on urgency and original price
     */
    public static double calculateSuggestedPrice(double originalPrice, double costPrice, double urgency) {
        double discountPercentage = calculateRecommendedDiscount(urgency, originalPrice, costPrice);
        double suggestedPrice = originalPrice * (1 - discountPercentage / 100.0);
        return Math.max(suggestedPrice, costPrice * 1.05); // Don't go below cost + 5% markup
    }
}