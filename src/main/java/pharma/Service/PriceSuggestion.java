package pharma.Service;

import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Model.FieldData;

import pharma.Utility.TrasformValue;
import pharma.config.Utility;
import pharma.dao.PurchaseOrderDetailDao;
import pharma.dao.SuggestPriceConfigDao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PriceSuggestion {
    private static final Logger log = LoggerFactory.getLogger(PriceSuggestion.class);
    private  HashMap<String,String> properties;
    private TrendMarket trendMarket;
    private  PurchaseOrderDetailDao p_dao;
    private SuggestPriceConfigDao suggestPriceConfigDao;
    public PriceSuggestion(HashMap<String,String> properties, TrendMarket trendMarket, SuggestPriceConfigDao suggestPriceConfigDao, PurchaseOrderDetailDao p_dao) {
        this.properties=properties;
        this.trendMarket=trendMarket;
        this.suggestPriceConfigDao = suggestPriceConfigDao;
        this.p_dao=p_dao;

    }

    /**
     *
     *

     */
    public  double suggest_price(int productId)  {
        FieldData fd=suggestPriceConfigDao.findById(productId);
        List<FieldData> prices=p_dao.findByProductPrice(productId);
        double[]array_prices=prices.stream().mapToDouble(FieldData::getPrice).toArray();
        if(array_prices.length==0){
            array_prices= new double[]{1.1,1.1};
        }
        KMeans  kmeans=new KMeans(List.of(new ClusterPoint(array_prices)),1);
        double price_avg=calculate_price_centroid(kmeans);

        double price_gain=gain(price_avg);

        double price_trend_avg=calculate_trend_market(price_gain,productId);
        double stock_avg=calculate_stock( fd.getMedium_lots(),price_trend_avg);
        return calculate_day(fd.getMedium_day(),stock_avg);


    }
    @TestOnly
    public double calculate_price_centroid(KMeans kmeans){
        return kmeans.get_max_centroid();
    }

    /**
     * Add the gain
     * @param base_price
     * @return
     */
    @TestOnly
    public double gain(double base_price){
        double  gain_global= Utility.getRequiredDoubleFromMap(properties,"gain");
        return TrasformValue.gain(base_price,gain_global );
    }

    /**
     * Considerate the trend market for value in basis
     * @param base_price
     * @return
     */
    @TestOnly
    public double calculate_trend_market(double  base_price,int product_id){
        // the value can be -100 to 100
        double trend_market=trendMarket.extract_trend_market(product_id);

        double trend_market_normalize=TrasformValue.normalize_percentuages(trend_market);

        return  TrasformValue.adjust_factor( base_price,trend_market_normalize)+base_price;
    }

    /**
     *This algorithm calculates a stock-related value by normalizing lot
     * sizes against a global stock threshold and then adjusting it based on market trends.
     * @param medium_lots
     * @param price_result_trend_market
     * @return
     */
    @TestOnly
    public double calculate_stock(double medium_lots,double price_result_trend_market ){
        double global_stock=Utility.getRequiredDoubleFromMap(properties,"medium_stock_item");

        double normalize_stock=TrasformValue.normalizeValue(medium_lots,global_stock);

        return  TrasformValue.trim_two(TrasformValue.adjust_factor(price_result_trend_market,normalize_stock));

    }

    /**
     * Calculate the  medium price for the day.
     * @param medium_day
     * @param price_result_stock
     * @return
     */
    @TestOnly
    public  double calculate_day(double medium_day,double price_result_stock){
        double normalize_day=TrasformValue.normalizeValue(medium_day,Utility.getRequiredDoubleFromMap(properties,"min_day_expire"));
        return TrasformValue.trim_two(TrasformValue.adjust_factor(price_result_stock,normalize_day));
    }









}