package pharma.formula;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pharma.Service.ClusterPoint;

class ClusterPointTest {

    @Test
    @DisplayName("ValidGetPoint")
    public void ValidGetPoint(){
            double[] value={10.50,20.50,60.60};
            ClusterPoint clousterPoint=new ClusterPoint(value);
            Assertions.assertEquals(value,clousterPoint.getPoint());
        }
}