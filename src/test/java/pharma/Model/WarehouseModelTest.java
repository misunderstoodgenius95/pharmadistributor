package pharma.Model;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class WarehouseModelTest {


    @Test
    public void calculate_distance() throws SQLException {

        WarehouseModel warehouseModel1 =new WarehouseModel(1,"piano",new PGgeometry(new Point(40.7128 ,-74.0060)));
        WarehouseModel warehouseModel2 =new WarehouseModel(2,"piano",new PGgeometry(new Point(30.7128 ,-64.0060)));
        Point point1=(Point) warehouseModel1.getpGgeometry().getGeometry();
        Point point2=(Point) warehouseModel2.getpGgeometry().getGeometry();
        System.out.println( point1.distance(point2));


    }

}