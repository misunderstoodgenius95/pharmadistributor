package pharma.formula;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import pharma.Model.WarehouseModel;

import static org.assertj.core.api.Assertions.assertThat;

class HarvesinFormulaTest {



    @Test
    void calculate_harvesinDistance() {

        WarehouseModel warehouseModel1 =new WarehouseModel(1,"piano",new PGgeometry(new Point(40.7128 ,-74.0060)));
        WarehouseModel warehouseModel2 =new WarehouseModel(2,"piano",new PGgeometry(new Point(30.7128 ,-64.0060)));
        Point point1= (Point) warehouseModel1.getpGgeometry().getGeometry();
        Point point2= (Point) warehouseModel2.getpGgeometry().getGeometry();
        double actual=HarvesinFormula.calculate_harvesinDistance(point1.getX(),point1.getY(),point2.getX(),point2.getY());
        assertThat(actual).isCloseTo(1430.24,Percentage.withPercentage(1.0));


    }
}