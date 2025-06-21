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
        double price_avg=calculate_price_centroid();
        double price_gain=gain(price_avg);
        double price_trend_avg=calculate_trend_market(price_gain);
        double stock_avg=calculate_stock( medium_lots,price_trend_avg);
        return calculate_day(medium_day,stock_avg);
    }

    public double calculate_price_centroid(){
        return kmeans.get_max_centroid();
    }

    public double gain(double base_price){
        double  gain_global=Utility.getRequiredDoubleFromMap(properties,"gain");
        return TrasformValue.gain(base_price,gain_global );
    }
    public double calculate_trend_market(double  base_price){
        double trend_market=trendMarket.extract_trend_market("name");
        double trend_market_normalize=TrasformValue.normalize_percentuages(trend_market);

        return  TrasformValue.adjust_factor( base_price,trend_market_normalize);
    }

    public double calculate_stock(double medium_lots,double price_result_trend_market ){
        double global_stock=Utility.getRequiredDoubleFromMap(properties,"medium_stock_item");

        double normalize_stock=TrasformValue.normalizeValue(medium_lots,global_stock);
        System.out.println(normalize_stock);

        return  TrasformValue.trim_two(TrasformValue.adjust_factor(price_result_trend_market,normalize_stock));

    }
    public  double calculate_day(double medium_day,double price_result_stock){
        double normalize_day=TrasformValue.normalizeValue(medium_day,Utility.getRequiredDoubleFromMap(properties,"min_day_expire"));
        System.out.println(normalize_day);
        return TrasformValue.trim_two(TrasformValue.adjust_factor(price_result_stock,normalize_day));


    }









}
