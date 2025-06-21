package pharma.formula;

import org.apache.commons.text.diff.StringsComparator;


import javax.management.monitor.StringMonitor;
import javax.management.monitor.StringMonitorMBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TrasformValue {

    public static double calculus_trend(double input) {

        if ((input < 0) ||  (input >1 )) {
            throw new IllegalArgumentException(" value is not 0<x>=1 ");
        }

       double t =(input-SuggestionFormula.MIDDLE.getValue());
        return Math.round(t*1000.0)/1000.0;

    }

  public static double normalize_percentuages(double input) {

      if(input<-100.0 || input>100.0){
          throw  new IllegalArgumentException("value is not between -100.0 to 100.0");
      }
      return (100-input)/200.0;


    }
    public  static  double  gain(double cost, double gain){
        return  TrasformValue.trim_two(cost*(1+(gain/100.0)));

    }

    /**
     *Aumento o decremento il costo a secondo del fattore normalizzato [0-1]
     *
     * @param cost
     * @param normalize_factor
     * @param
     * @return
     */
    public static double adjust_factor_two(double cost, double normalize_factor) {
        if (normalize_factor < 0 || normalize_factor > 2) {
            throw new IllegalArgumentException("normalize_factor must be in range [0, 2]");
        }

        if (normalize_factor > 1.0) {
            double percent_factor = 1-(TrasformValue.round_one(normalize_factor - 1.0));
        } else if (normalize_factor < 1.0) {
            double percent_factor = TrasformValue.round_one(1.0 - normalize_factor);
            return cost * (1 + percent_factor);
        }

        return cost;
    }
    public static double adjust_factor(double cost,double normalize_factor){
        if(normalize_factor<0 || normalize_factor>1){
            throw new IllegalArgumentException("Factor cannot in range between 0 to  1");
        }
       if(normalize_factor>0.5){
           double percent_factor=TrasformValue.round_one(normalize_factor-0.5);
            return cost*(1-percent_factor);
        }
       else if(normalize_factor<0.5){
           double percent_factor=TrasformValue.round_one(0.5-normalize_factor);
            return cost*(1+(percent_factor));
        }
        return cost;
    }
    public static double adjust_factor(double normalize_factor){
        if(normalize_factor<0 || normalize_factor>1){
            throw new IllegalArgumentException("Factor cannot in range between 0 to  1");
        }

            return TrasformValue.round_one(1-normalize_factor)*100;


    }




    protected static double normalizeValue(double stock, double ref) {
        double raw = 0.5 - (stock - ref) / (2.0 * ref);
        double clamped = Math.max(0.0, Math.min(1.0, raw));
        return Math.round(clamped * 10) / 10.0;

    }
    protected static double normalizeValue2(double stock, double ref) {
        double raw = 1.0 - (stock - ref)/ref;
        double clamped = Math.max(0.0, Math.min(2.0, raw));
        return Math.round(clamped * 10) / 10.0;

    }


    public  static double cost(double cost,double percent_factor){

        return cost*(1-(percent_factor/100));
    }
    public static  double trim_two(double value){
        BigDecimal bigDecimal=new BigDecimal(value);
         return bigDecimal.setScale(2, RoundingMode.DOWN).doubleValue();

    }
/*    public static  double trim_one(double value){
        BigDecimal bigDecimal=new BigDecimal(value);
        return bigDecimal.setScale(1, RoundingMode.DOWN).doubleValue();

    }*/
    public static  double round_one(double value){
        return Math.round(value * 10.0) / 10.0;

    }



    public static double calculate_percent_gain(double[] sellerPriceHistory,
                                                double[] currentPurchasePrices,
                                                double[] purchasePriceHistory,
                                                double currentPrice) {
        double avgSellerPriceHistory = average(sellerPriceHistory);

        double avgCurrentPurchasePrice = average(currentPurchasePrices);

        double avgPurchasePriceHistory = average(purchasePriceHistory);

        double priceDifference = (avgSellerPriceHistory - avgPurchasePriceHistory) / 2.0;
        // Only calculate gain if there's a positive price difference
        if (priceDifference > 0) {
            // Calculate current price difference
            double currentPriceDifference = (currentPrice - avgCurrentPurchasePrice) / 2.0;
            // Calculate global difference
            double global_difference = priceDifference - (currentPriceDifference / 2.0);
            // Calculate percentage gain
            double percentGain = (global_difference / priceDifference) * 100.0;
            // Return the percentage gain rounded to two decimal places
            return TrasformValue.trim_two(percentGain);
        }

        return 0.0;
    }
    public static double average(double[] array) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array can be with length 0 or null");
        }
        return Arrays.stream(array).sum() / array.length;


    }
    public static int   average(int[] array) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array can be with length 0 or null");
        }
        return Arrays.stream(array).sum() / array.length;


    }

    public static long calculateAverageEpoch(List<Date> dates) {
        if (dates == null || dates.isEmpty()) {
            throw new IllegalArgumentException("Date list cannot be null or empty");
        }

         return dates.stream().mapToLong(java.util.Date::getTime).sum()/dates.size();
    }
    public  static  long remaining_day(long epoch){
        return ChronoUnit.DAYS.between(
                LocalDate.now(),
                Instant.ofEpochMilli(epoch).atZone(ZoneId.systemDefault()).toLocalDate()
        );

    }

    protected static int percentageFromNormalized(double normalizedValue) {
        return (int) Math.round((normalizedValue - 1.0) * 100);
    }

    /**
     * Max 1.0 urgency
     * Min 0.0 no urgency
     * @param daysUntilExpiration
     * @param threshold
     * @return
     */
   public static double calculateExpirationUrgency(long daysUntilExpiration,double threshold) {
        if (daysUntilExpiration <= 0) return 1.0; // Expired - maximum urgency
        if (daysUntilExpiration >= threshold) return 0.0;  // 180+ days, no urgency

        // Exponential urgency increase as expiration approaches
        double urgency = Math.pow((threshold - daysUntilExpiration) / threshold, 2);
       return  TrasformValue.round_one(urgency);
    }

    public static double calculateStockandExpireUrgency(int stockQuantity, long daysUntilExpiration, double avgDailySales) {
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

        return Math.round(stockUrgency * 100.0) / 100.0; // Round to 2 decimal places
    }

















}
