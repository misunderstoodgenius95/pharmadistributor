package pharma.formula.promotion;


import pharma.Model.FieldData;
import pharma.dao.*;
import pharma.formula.TrasformValue;

import java.sql.Date;
import java.util.*;

public class PromotionsSuggest {

/*    private double[] sellerPriceHistory;
    private double[] purchasePriceHistory;*/
  //  private List<Date> current_date;
/*    private int[] current_stock_quantity;*/
    private  PromotionConfigDao p_conf;
    private PurchaseOrderDetailDao p_detail;
    private SellerOrderDetails s_detail;
    private LottiDao lottiDao;
    private LotAssigmentDao assigmentDao;
    private SellerPriceDao s_dao;
/*
    public PromotionsSuggest(double[] sellerPriceHistory, double[] purchasePriceHistory, List<Date> current_date, int[] current_stock_quantity, PromotionConfigDao p_conf) {
        this.sellerPriceHistory = sellerPriceHistory;
        this.purchasePriceHistory = purchasePriceHistory;
        this.current_date = current_date;
        this.current_stock_quantity = current_stock_quantity;
        this.p_conf=p_conf;
    }
*/

    public PromotionsSuggest(SellerOrderDetails s_detail, PurchaseOrderDetailDao p_detail, PromotionConfigDao p_conf, LottiDao lottiDao, LotAssigmentDao assigmentDao,SellerPriceDao s_dao) {
        this.s_detail = s_detail;
        this.p_detail = p_detail;
        this.p_conf = p_conf;
        this.lottiDao = lottiDao;
        this.assigmentDao = assigmentDao;
        this.s_dao=s_dao;

    }



    public int get_promotion_discount(int farmaco_id) {
           double[] currentPurchasePrices=productPrice(p_detail.findByProductPrice(farmaco_id));
            double[]sellerPriceHistory=productPrice(s_detail.findbyProduct(farmaco_id));
            List<Date> current_date=current_date(lottiDao.findbyDate(farmaco_id));

            int[] current_stock_quantity=current_qty(assigmentDao.findQuantitybyFarmacoId(farmaco_id));
            double current_price=s_dao.findCurrentPricebyFarmaco(farmaco_id);





        FieldData fieldData = p_conf.findAll().getFirst();
        // value to 0 to 1.
        double value_gain_normalized = TrasformValue.normalize_percentuages(TrasformValue.calculate_percent_gain(sellerPriceHistory, currentPurchasePrices,  currentPurchasePrices, current_price));

        if (value_gain_normalized == 0) {
            return 0;
        } else {

            long remaining = TrasformValue.remaining_day(TrasformValue.calculateAverageEpoch(current_date));

            double remaining_date = TrasformValue.normalizeValue(remaining, fieldData.getMin_day_expire());

       int stock = TrasformValue.average(current_stock_quantity);
            double remaing_stock = TrasformValue.normalizeValue(stock, fieldData.getMedium_stock_item());
            double average = TrasformValue.average(new double[]{remaing_stock, remaining_date});
             return get_discount(average);

        }

    }


    private static int get_discount(double average){
        if (average == 1) {
            return 50;
        }
        if (average == 0) {
            return 0;
        }
        if (average == 0.5) {
            return 25;
        }
        if (average > 0.1 || average < 0.4) {
            return 10;
        }
        if (average < 0.9 || average > 0.5) {
            return 40;
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











        /*       long remaining_day= TrasformValue.normalizeValue()TTrasformValue.calculateAverageEpoch(current_date);*/














}






