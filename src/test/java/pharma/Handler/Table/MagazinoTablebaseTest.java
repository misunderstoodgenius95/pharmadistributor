package pharma.Handler.Table;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Controller.subpanel.Magazzino;
import pharma.Model.Warehouse;
import pharma.dao.MagazzinoDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class MagazinoTablebaseTest {
    @Mock
    MagazzinoDao magazzinoDao;
    @Start
    public void start(Stage stage){
        MockitoAnnotations.openMocks(this);
        Scene scene=new Scene(new VBox());
        stage.setScene(scene);
        stage.show();



    }


    @Test
    public void Test(FxRobot robot){
        Platform.runLater(()->{
        when(magazzinoDao.findAll()).thenReturn(List.of(new Warehouse(1,"SCILLA","via comunale","Militello","Messina"),new Warehouse(2,"Sicilia","Via del calzolaio 29","Rugiate","MO")));
           MagazinoTablebase magazinoTablebase=new MagazinoTablebase("Magazzino",magazzinoDao);
           magazinoTablebase.show();





        });
        robot.sleep(100000);


    }
}