package pharma.formula;

import java.util.List;

public class RecommendSystem  extends  KMeans{
    public RecommendSystem(List<ClusterPoint> clusterPoints, int num_cluster) {
        super(clusterPoints, num_cluster);
    }

    public  static  double  gain(double cost, double gain){
        return  cost*(1+gain/100.0);

    }

    public static double adjust_factor(double value){

        if(value>0.5){
                value*=1.10;

        }else if(value<0.5){
            value*=0.90;

        }

        return value;

    }


}
