package pharma.Handler;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdk.jshell.execution.Util;
import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.WarehouseModel;
import pharma.config.Utility;
import pharma.dao.LotAssigmentDao;
import pharma.dao.LotAssigmentShelvesDao;
import pharma.dao.MagazzinoDao;
import pharma.javafxlib.test.SimulateEvents;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class LottoStorageSearchHandlerTest {
    @Mock
    private MagazzinoDao magazzinoDao;
    private LottoStorageSearchHandler storageSearchHandler;
    @Mock
    private LotAssigmentDao lotAssigmentDao;
    @Mock
    private LotAssigmentShelvesDao shelvesDao;
    @Start
    public void start(Stage stage){
        MockitoAnnotations.openMocks(this);
        Scene scene=new Scene(new VBox());
        stage.setScene(scene);
        stage.show();
       storageSearchHandler= new LottoStorageSearchHandler("Ricerca per Lotto o Per Scaffale", List.of(magazzinoDao,lotAssigmentDao,shelvesDao));

    }

    @Test
    public void  test(FxRobot robot){
        Platform.runLater(()->{
            when(magazzinoDao.findAll()).thenReturn(List.of( new WarehouseModel(1,"M1","Via delle piante","Roma","ROMA"),
                    new WarehouseModel(2,"M2","Via delle rose","Ravenna","RA"),
            new WarehouseModel(3,"M3","Contrada San Pietro","Rimini","RN")));

             storageSearchHandler.show();




        });
        robot.sleep(36000000);


    }
    @Test
    public void  ValidTestLots(FxRobot robot){
        Platform.runLater(()->{
            when(magazzinoDao.findAll()).thenReturn(List.of( new WarehouseModel(1,"M1","Via delle piante","Roma","ROMA"),
                    new WarehouseModel(2,"M2","Via delle rose","Ravenna","RA"),
                    new WarehouseModel(3,"M3","Contrada San Pietro","Rimini","RN")));

            storageSearchHandler.show();
            TextField textField_text=Utility.extract_value_from_list(storageSearchHandler.getControlList(), TextField.class).getFirst();
            SimulateEvents.writeOn(textField_text,"A20X");

            WaitForAsyncUtils.waitForFxEvents();
          SimulateEvents.clickOn(storageSearchHandler.getButtonOK());

        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertTrue(storageSearchHandler.isCheck_validate());
        robot.sleep(36000000);

    }
    @Test
    public void  InValidtestLots(FxRobot robot){
        Platform.runLater(()->{
            when(magazzinoDao.findAll()).thenReturn(List.of( new WarehouseModel(1,"M1","Via delle piante","Roma","ROMA"),
                    new WarehouseModel(2,"M2","Via delle rose","Ravenna","RA"),
                    new WarehouseModel(3,"M3","Contrada San Pietro","Rimini","RN")));

             storageSearchHandler.show();
            TextField textField_text=Utility.extract_value_from_list(storageSearchHandler.getControlList(), TextField.class).getFirst();
            SimulateEvents.writeOn(textField_text,"");

            WaitForAsyncUtils.waitForFxEvents();
            SimulateEvents.clickOn(storageSearchHandler.getButtonOK());


        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertFalse(storageSearchHandler.isCheck_validate());
        robot.sleep(36000000);

    }
}