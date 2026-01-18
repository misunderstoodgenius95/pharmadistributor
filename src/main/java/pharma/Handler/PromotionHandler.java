package pharma.Handler;

import pharma.Model.PromotionData;
import pharma.config.CatConf.CatConf;
import pharma.config.PathConfig;
import pharma.dao.*;
import pharma.Service.Promotion.PromotionsSuggestService;

import java.sql.Date;
import java.util.List;

import static pharma.Service.Promotion.PromotionsSuggestService.*;


public class PromotionHandler {

    private PurchaseOrderDetailDao p_detail;
    private SellerOrderDetails s_detail;
    private LottiDao lottiDao;
    private LotAssigmentDao assigmentDao;
    private SellerPriceDao s_dao;
    private PromotionsSuggestService suggestService;
    private CatConf catConf_promotion;
    public PromotionHandler( PurchaseOrderDetailDao p_detail, SellerOrderDetails s_detail, LottiDao lottiDao, LotAssigmentDao assigmentDao, SellerPriceDao s_dao) {
        this.p_detail = p_detail;
        this.s_detail = s_detail;                                
        this.lottiDao = lottiDao;
        this.assigmentDao = assigmentDao;
        this.s_dao = s_dao;
        this.suggestService=new PromotionsSuggestService();
        catConf_promotion=new CatConf(PathConfig.CAT_PROMOTION.getValue());

    }

    public  int   calculate_discount(int farmaco_id){

        double[] currentPurchasePrices=productPrice(p_detail.findByProductPrice(farmaco_id));
        double[]sellerPriceHistory=productPrice(s_detail.findbyProduct(farmaco_id));
        List<Date> current_date=current_date(lottiDao.findbyDate(farmaco_id));

        int[] current_stock_quantity=current_qty(assigmentDao.findQuantitybyFarmacoId(farmaco_id));
        double current_price=s_dao.findCurrentPricebyFarmaco(farmaco_id);
        int min_stock=catConf_promotion.get_value_integer("medium_stock_item");
        int min_day=catConf_promotion.get_value_integer("min_day_expire");

        PromotionData promotionData=new PromotionData(currentPurchasePrices,sellerPriceHistory,current_date,current_stock_quantity,current_price,min_stock,min_day);
         return suggestService.get_promotion_discount(promotionData);


    }
}
