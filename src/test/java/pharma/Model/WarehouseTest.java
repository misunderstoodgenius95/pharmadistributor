package pharma.Model;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {


    @Test
    public void calculate_distance() throws SQLException {

        Warehouse warehouse1=new Warehouse(1,"piano",new PGgeometry(new Point(40.7128 ,-74.0060)));
        Warehouse warehouse2=new Warehouse(2,"piano",new PGgeometry(new Point(30.7128 ,-64.0060)));
        Point point1=(Point) warehouse1.getpGgeometry().getGeometry();
        Point point2=(Point) warehouse2.getpGgeometry().getGeometry();
        System.out.println( point1.distance(point2));


    }

}