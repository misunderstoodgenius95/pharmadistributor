package pharma.formula;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.List;


public  class KMeans {
    private  List<ClusterPoint> clusterPoints;
    private  KMeansPlusPlusClusterer<ClusterPoint> cluster;
    private   List<CentroidCluster<ClusterPoint>> centroidClusterList;

    public KMeans(List<ClusterPoint> clusterPoints,int num_cluster) {

        this.clusterPoints=clusterPoints;
        cluster =new KMeansPlusPlusClusterer<>(num_cluster,1000,new EuclideanDistance());
        centroidClusterList=cluster.cluster(clusterPoints);

    }

    /**
     * Using this for prediction of  middle value
     * @return
     */
    public double get_max_centroid(){
            return centroidClusterList.stream().
                    mapToDouble(centroid->centroid.getCenter().getPoint()[0]).max().orElse(0.0);

    }










}
