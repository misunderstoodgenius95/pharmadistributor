package pharma.Utility;

import pharma.constant.SuggestionFormula;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class TrasformValue {
    private TrasformValue() {
        throw new AssertionError("Cannot instantiate utility class");
    }
    public static double calculus_trend(double input) {

        if ((input < 0) ||  (input >1 )) {
            throw new IllegalArgumentException(" value is not 0<x>=1 ");
        }

       double t =(input- SuggestionFormula.MIDDLE.getValue());
        return Math.round(t*1000.0)/1000.0;

    }

  public static double normalize_percentuages(double input) {

      if(input<-100.0 || input>100.0){
          throw  new IllegalArgumentException("value is not between -100.0 to 100.0");
      }
      return (100-input)/100.0;


    }
    public  static  double  gain(double cost, double gain){
        return  TrasformValue.trim_two(cost*(1+(gain/100.0)));

    }
    public static double round_percentuages(double value){
        return value/100;
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

    /**
     * The algorithm can be
     * @param cost
     * @param normalize_factor  it is between 0 and 1
     * @return
     */
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


    /**
     * When stock == ref: result = 0.5
     * When stock < ref: result > 0.5 (approaches 1.0)
     * When stock > ref: result < 0.5 (approaches 0.0)
     * @param stock
     * @param ref
     * @return
     */
    public static double normalizeValue(double stock, double ref) {
        double raw = 0.5 - (stock - ref) / (2.0 * ref);
        double clamped = Math.max(0.0, Math.min(1.0, raw));
        return Math.round(clamped * 10) / 10.0;

    }
    public static double normalizeValue2(double stock, double ref) {
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


    /**
     *
     * @param sellerPriceHistory
     * @param currentPurchasePrices
     * @param purchasePriceHistory
     * @param currentPrice
     * @return
     * 0.0 no Profict
     * 0.5 Very Low Profict
     * 0.7-0.9 Acceptable
     * 0.9-1.0 Good

     */
    public static double calculate_percent_gain(double[] sellerPriceHistory,
                                                double[] currentPurchasePrices,
                                                double[] purchasePriceHistory,
                                                double currentPrice) {
        double avgSellerPriceHistory = average(sellerPriceHistory);
        double avgCurrentPurchasePrice = average(currentPurchasePrices);
        double avgPurchasePriceHistory = average(purchasePriceHistory);
        // if it gain with this product
        double priceDifferenceHistory = (avgSellerPriceHistory - avgPurchasePriceHistory) / 2.0;
        // Only calculate gain if there's a positive price difference
        if (priceDifferenceHistory > 0) {
            // Current potential profit margin
            double currentMargin=currentPrice-avgCurrentPurchasePrice;
            // Calculate the gain between current margin and history difference
            double percentGain=(currentMargin/priceDifferenceHistory);
         if(percentGain>1){
             return 1;
         } if(percentGain<0){
             return 0;
            }

            return TrasformValue.trim_two(percentGain);





            //return TrasformValue.trim_two(percentGain);
        }

        return 0.0;
    }
    public static double average(double[] array) {

        if (array == null || array.length == 0) {
           return 0;
        }
        return Arrays.stream(array).sum() / array.length;


    }


    /**
     * Middle of  int value
     * @param array
     * @return
     */
    public static int   average(int[] array) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array can be with length 0 or null");
        }
        return Arrays.stream(array).sum() / array.length;


    }

    public static long calculateAverageEpoch(List<Date> dates) {
        if (dates == null || dates.isEmpty()) {
           return LocalDate.now().toEpochDay();
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


















}
