package pharma.formula;

import pharma.Storage.FileStorage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

public class PriceSuggestion extends  RecommendSystem{
    private Properties properties;

    public PriceSuggestion(List<ClusterPoint> clusterPoints, int num_cluster, Properties properties) {
        super(clusterPoints, num_cluster);
        this.properties=properties;
    }

    public  double suggest_price()  {
        // suggest_price=mean_purchase_lots()+ gain()+trendMarket+StockLevel+duration;

        double avg_price_lots=get_max_centroid();
        double gain_result=gain(avg_price_lots, Double.parseDouble(properties.getProperty("gain")));


        return 0.1;



    }





}
