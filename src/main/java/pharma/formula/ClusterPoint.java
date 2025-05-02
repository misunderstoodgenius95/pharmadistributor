package pharma.formula;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class ClusterPoint implements Clusterable {

    private  double[] point;

    public ClusterPoint(double[] point) {
        this.point = point;
    }

    @Override
    public double[] getPoint() {
        return point;
    }
}
