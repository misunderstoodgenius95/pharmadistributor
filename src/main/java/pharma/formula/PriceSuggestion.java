package pharma.formula;

import pharma.config.Utility;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class PriceSuggestion {
    private  HashMap<String,String> properties;
    private TrendMarket trendMarket;
    private  KMeans kmeans;
    public PriceSuggestion(KMeans kmeans, int num_cluster, HashMap<String,String> properties, TrendMarket trendMarket) {
        this.kmeans=kmeans;
        this.properties=properties;
        this.trendMarket=trendMarket;
    }

    public  double suggest_price(double medium_lots,double medium_day)  {
        // suggest_price=mean_purchase_lots()+ gain()+trendMarket+StockLevel+duration;


             double avg_price_lots=kmeans.get_max_centroid();


        double price_gain_result=TrasformValue.gain(avg_price_lots, Utility.getRequiredDoubleFromMap(properties,"gain"));

        double trend_market_normalize=TrasformValue.normalize_percentuages(trendMarket.extract_trend_market("name"));

        double price_result_trend_market=TrasformValue.adjust_factor(price_gain_result,trend_market_normalize);




        double normalize_stock=TrasformValue.normalizeValue(medium_lots,Utility.getRequiredDoubleFromMap(properties,"medium_stock_item"));

        double price_result_stock=TrasformValue.adjust_factor(price_result_trend_market,normalize_stock);

        double normalize_day=TrasformValue.normalizeValue(medium_day,Utility.getRequiredDoubleFromMap(properties,"min_day_expire"));

         return TrasformValue.adjust_factor(price_result_stock,normalize_day);





    }

    public double calculate_price_centroid(){
        double avg_price_lots=kmeans.get_max_centroid();


        return TrasformValue.gain(avg_price_lots, Utility.getRequiredDoubleFromMap(properties,"gain"));

    }
    public double calculate_trend_market(double price_gain_result){
        double trend_market_normalize=TrasformValue.normalize_percentuages(trendMarket.extract_trend_market("name"));

        return  TrasformValue.adjust_factor(price_gain_result,trend_market_normalize);
    }

    public double calulate_stock(double medium_lots,double price_result_trend_market ){
        double normalize_stock=TrasformValue.normalizeValue(medium_lots,Utility.getRequiredDoubleFromMap(properties,"medium_stock_item"));

        return  TrasformValue.adjust_factor(price_result_trend_market,normalize_stock);

    }
    public  double calculate_day(double medium_day,double price_result_stock){
        double normalize_day=TrasformValue.normalizeValue(medium_day,Utility.getRequiredDoubleFromMap(properties,"min_day_expire"));

        return TrasformValue.adjust_factor(price_result_stock,normalize_day);


    }









}
