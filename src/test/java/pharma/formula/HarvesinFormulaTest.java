package pharma.formula;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pharma.Model.Warehouse;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HarvesinFormulaTest {



    @Test
    void calculate_harvesinDistance() {

        Warehouse warehouse1=new Warehouse(1,"piano",new PGgeometry(new Point(40.7128 ,-74.0060)));
        Warehouse warehouse2=new Warehouse(2,"piano",new PGgeometry(new Point(30.7128 ,-64.0060)));
        Point point1= (Point) warehouse1.getpGgeometry().getGeometry();
        Point point2= (Point) warehouse2.getpGgeometry().getGeometry();
        double actual=HarvesinFormula.calculate_harvesinDistance(point1.getX(),point1.getY(),point2.getX(),point2.getY());
        assertThat(actual).isCloseTo(1430.24,Percentage.withPercentage(1.0));


    }
}