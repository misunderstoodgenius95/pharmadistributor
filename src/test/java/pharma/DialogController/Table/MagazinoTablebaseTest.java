package pharma.DialogController.Table;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.WarehouseModel;
import pharma.config.Utility;
import pharma.dao.MagazzinoDao;
import pharma.javafxlib.test.SimulateEvents;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    public void ValidTestInsert(FxRobot robot){
        Platform.runLater(()->{
            when(magazzinoDao.findAll()).thenReturn(List.of(new WarehouseModel(1,"SCILLA","via comunale","Militello","Messina"),new WarehouseModel(2,"Sicilia","Via del calzolaio 29","Rugiate","MO")));
            MagazinoTablebase magazinoTablebase=new MagazinoTablebase("Magazzino",magazzinoDao);
            List<TextField> list= Utility.extract_value_from_list(magazinoTablebase.getMagazzinoHandler().getControlList(), TextField.class);
            SimulateEvents.writeOn(list.getFirst(),"M1");
            SimulateEvents.writeOn(list.get(1),"Contrada Crescienzio 24");
            SimulateEvents.writeOn(list.get(2),"Roma");
            SimulateEvents.writeOn(list.get(3),"Roma");
            SimulateEvents.writeOn(list.get(4),"-18.818886984");
            SimulateEvents.writeOn(list.get(5),"-39.09529473");
            magazinoTablebase.show();
          SimulateEvents.clickOn(magazinoTablebase.getButton_add_warehouse());
            when(magazzinoDao.insert(Mockito.any(WarehouseModel.class))).thenReturn(true);
            WaitForAsyncUtils.waitForFxEvents();
            SimulateEvents.clickOn(magazinoTablebase.getMagazzinoHandler().getButtonOK());

            WaitForAsyncUtils.waitForFxEvents();



            magazinoTablebase.getObservableList().addListener((ListChangeListener<WarehouseModel>) c -> {
                while (c.next()){
                    if(c.wasAdded()){
                        System.out.println(magazinoTablebase.getObservableList().size());
                        assertEquals(3,magazinoTablebase.getObservableList().size());
                    }
                }
            });




        });

       // robot.sleep(100000);


    }




}