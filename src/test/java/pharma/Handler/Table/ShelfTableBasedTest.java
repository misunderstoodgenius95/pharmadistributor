package pharma.Handler.Table;

import algo.ShelfInfo;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.WarehouseModel;
import pharma.dao.MagazzinoDao;
import pharma.dao.ShelfDao;
import pharma.dao.ShelvesDao;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ShelfTableBasedTest {
    @Mock
    private ShelfDao shelfDao;
    @Mock
    private MagazzinoDao magazzinoDao;
    private ShelfTableBased shelfTableBased;
    @Mock
    private ShelvesDao shelvesDao;
    @Start
    public void start(Stage stage){
        Scene scene=new Scene(new VBox());
        stage.setScene(scene);
        stage.show();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void  ValidInsert(FxRobot robot){
        Platform.runLater(()->{
             ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("a22")
                    .setLenght(102).
                     setNome_magazzino("VOLDEMORT")
                    .setHeight(100)
                    .setDeep(40)
                    .setWeight(60)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(List.of())
                    .build();

           ShelfInfo shelfInfo2 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("a22").setNome_magazzino("GRIFONDORO")
                    .setLenght(102)
                    .setHeight(100)
                    .setDeep(40)
                    .setWeight(60)
                    .setNum_rip(4)
                    .setShelf_thickness(20)
                    .setShelvesCapacities(List.of())
                    .build();
            WarehouseModel warehouseModel1 =new WarehouseModel(1,"GRIFONDORO",new PGgeometry(new Point(40.7128 ,-74.0060)));
            WarehouseModel warehouseModel2 =new WarehouseModel(2,"SERPEVERDE",new PGgeometry(new Point(30.7128 ,-64.0060)));
            when(shelfDao.findAll()).thenReturn(List.of(shelfInfo1,shelfInfo2));
when(shelfDao.insert(Mockito.any(ShelfInfo.class))).thenReturn(true);
            when(magazzinoDao.findAll()).thenReturn(List.of(warehouseModel1, warehouseModel2));
            shelfTableBased =new ShelfTableBased("Visualizza Scaffale", magazzinoDao,shelfDao,shelvesDao);
            shelfTableBased.show();



        });
        robot.sleep(40000);

    }

}