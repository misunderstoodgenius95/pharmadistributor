package pharma.Service.Promotion;


import pharma.Model.FieldData;
import pharma.Model.PromotionData;
import pharma.Utility.TrasformValue;

import java.sql.Date;
import java.util.*;

public class PromotionsSuggestService {



    public   PromotionsSuggestService() {
    }

    public int get_promotion_discount(PromotionData promotionData) {
        double gain=TrasformValue.calculate_percent_gain(promotionData.getSellerPriceHistory(),promotionData.getCurrentPurchasePrices(),promotionData.getSellerPriceHistory(), promotionData.getCurrent_price());
        // value to 0 to 1.
        double value_gain_normalized = TrasformValue.normalize_percentuages(gain);
        if (value_gain_normalized == 0) {
            return 0;
        } else {
            long remaining = TrasformValue.remaining_day(TrasformValue.calculateAverageEpoch(promotionData.getCurrent_date()));

            double remaining_date = TrasformValue.normalizeValue(remaining, promotionData.getMin_day_expire());

       int stock = TrasformValue.average(promotionData.getCurrent_stock_quantity());
            double remaing_stock = TrasformValue.normalizeValue(stock,promotionData.getMedium_stock_item());
            double average = TrasformValue.average(new double[]{remaing_stock, remaining_date});
             return get_discount(average);

        }

    }


    private static int get_discount(double average) {
        if (average >= 1.0) {
            return 50;
        }
        if (average >= 0.9) {
            return 40;
        }
        if (average >= 0.5) {
            return 40;
        }
        if (average >= 0.4) {
            return 25;
        }
        if (average > 0.1) {
            return 10;
        }
        return 0;
    }
    public static double[] productPrice(List<FieldData> list){
        return list.stream().mapToDouble(FieldData::getPrice).toArray();


    }

    public static  List<Date> current_date(List<FieldData>list){

        return  list.stream().map(FieldData::getElapsed_date).toList();
    }
    public static  int[] current_qty(List<FieldData> list){
        if(list.isEmpty()){
            return new  int[]{0};
        }
        return  list.stream().mapToInt(FieldData::getQuantity).toArray();
    }


}






