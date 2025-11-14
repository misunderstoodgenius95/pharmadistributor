package pharma.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.dao.*;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
class WarehouseControllerTest {
    @Mock
    private MagazzinoDao magazzinoDao;
    @Mock
    private ShelfDao shelfDao;
    @Mock
    private ShelvesDao shelvesDao;
    @Mock
    private LotAssigmentDao assigmentDao;
    @Mock
    private LotAssigmentShelvesDao assigmentShelvesDao;

    @Start
    public void start(Stage stage) throws IOException {
        MockitoAnnotations.openMocks(this);
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/warehouse.fxml"));
        Warehouse warehouse=new Warehouse(magazzinoDao,shelfDao,shelvesDao,assigmentDao,assigmentShelvesDao);
        loader.setController(warehouse);
        Scene scene=new Scene(loader.load());
       stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.show();




    }



    @Test
    public void test(FxRobot robot){

        robot.sleep(10000000);


    }

}