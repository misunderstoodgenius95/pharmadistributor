package pharma.formula.promotion;


import pharma.formula.TrasformValue;

import java.sql.Date;
import java.util.*;

public class PromotionsSuggest {

    private  double[] sellerPriceHistory;
    private   double[] purchasePriceHistory;
    private List<Date> current_date;
    private int[] current_stock_quantity;
    private  double avgDailySales;
    public PromotionsSuggest(double[] sellerPriceHistory, double[] purchasePriceHistory, List<Date> current_date, int[] current_stock_quantity,double avgDailySales) {
        this.sellerPriceHistory = sellerPriceHistory;
        this.purchasePriceHistory = purchasePriceHistory;
        this.current_date = current_date;
        this.current_stock_quantity = current_stock_quantity;
        this.avgDailySales=avgDailySales;

}

    public PromotionsSuggest(List<Date> current_date) {
        this.current_date = current_date;
    }

    public double  get_promotion_discount(double[] currentPurchasePrices, double current_price){

        double value_gain_normalized=TrasformValue.normalize_percentuages(TrasformValue.calculate_percent_gain(sellerPriceHistory,currentPurchasePrices,purchasePriceHistory,current_price));
        long remaining=TrasformValue.remaining_day(TrasformValue.calculateAverageEpoch(current_date));
        int stock=TrasformValue.average(current_stock_quantity);
        double urgent_stock_expire=TrasformValue.calculateStockandExpireUrgency(stock,remaining,avgDailySales);
        return  TrasformValue.adjust_factor(TrasformValue.average(new double[]{value_gain_normalized,urgent_stock_expire}));









  /*       long remaining_day= TrasformValue.normalizeValue()TTrasformValue.calculateAverageEpoch(current_date);*/






    }










}






