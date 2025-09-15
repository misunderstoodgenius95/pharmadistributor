package pharma.Handler;

import com.networknt.schema.JsonSchemaException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.Model.Warehouse;
import pharma.Storage.FileStorage;
import pharma.config.Utility;
import pharma.config.database.Database;
import pharma.dao.MagazzinoDao;
import pharma.javafxlib.test.SimulateEvents;

import java.io.FileReader;
import java.io.IOException;
import java.lang.foreign.PaddingLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class MagazzinoHandlerTest {
    @Mock
    private MagazzinoDao magazzinoDao;
    private MagazzinoHandler magazzinoHandler;
    @Start
    public void setUp(Stage stage){
        Scene scene=new Scene(new VBox(),600,700);
        stage.setScene(scene);
        stage.show();



    }

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);



    }
    @Test
    public void ValiValidation(FxRobot robot){
        Platform.runLater(()->{
            magazzinoHandler=new MagazzinoHandler("Aggiungi Magazzino", List.of(magazzinoDao), FXCollections.observableList(List.of()));
            List<TextField> list=Utility.extract_value_from_list(magazzinoHandler.getControlList(), TextField.class);
            SimulateEvents.writeOn(list.getFirst(),"M1");
            SimulateEvents.writeOn(list.get(1),"Contrada Crescienzio 24");
            SimulateEvents.writeOn(list.get(2),"Roma");
            SimulateEvents.writeOn(list.get(3),"Roma");
            SimulateEvents.writeOn(list.get(4),"-18.818886984");
            SimulateEvents.writeOn(list.get(5),"-39.09529473");
            //SimulateEvents.clickOn(magazzinoHandler.getButtonOK());
            magazzinoHandler.show();
            SimulateEvents.clickOn(magazzinoHandler.getButtonOK());
        });
       // robot.sleep(500000);
       WaitForAsyncUtils.waitForFxEvents();
       Assertions.assertTrue(magazzinoHandler.isCheck_validate());


    }
    @Test
    public void inValiValidation(FxRobot robot){
        Platform.runLater(()->{
            magazzinoHandler=new MagazzinoHandler("Aggiungi Magazzino", List.of(magazzinoDao),FXCollections.observableArrayList(List.of()));
            List<TextField> list=Utility.extract_value_from_list(magazzinoHandler.getControlList(), TextField.class);
            SimulateEvents.writeOn(list.getFirst(),"M1");
          //  SimulateEvents.writeOn(list.get(1),"Contrada Crescienzio 24");
            SimulateEvents.writeOn(list.get(2),"Roma");
            SimulateEvents.writeOn(list.get(3),"Roma");
            SimulateEvents.writeOn(list.get(4),"-18.818886984");
            SimulateEvents.writeOn(list.get(5),"-39.09529473");
            //SimulateEvents.clickOn(magazzinoHandler.getButtonOK());
            magazzinoHandler.show();
            SimulateEvents.clickOn(magazzinoHandler.getButtonOK());
        });
        //  robot.sleep(500000);
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertFalse(magazzinoHandler.isCheck_validate());


    }
    @Test
    public void ValidInsertionIntegration(FxRobot robot){
        Platform.runLater(()->{
            Properties properties = null;
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            MagazzinoDao magazzino_real=new MagazzinoDao(Database.getInstance(properties));
            magazzinoHandler=new MagazzinoHandler("Aggiungi Magazzino", List.of(magazzino_real),FXCollections.observableList(List.of()));
            List<TextField> list=Utility.extract_value_from_list(magazzinoHandler.getControlList(), TextField.class);
            SimulateEvents.writeOn(list.getFirst(),"M1");
            SimulateEvents.writeOn(list.get(1),"Contrada Crescienzio 24");
            SimulateEvents.writeOn(list.get(2),"Roma");
            SimulateEvents.writeOn(list.get(3),"Roma");
            SimulateEvents.writeOn(list.get(4),"-18.818886984");
            SimulateEvents.writeOn(list.get(5),"-39.09529473");
            SimulateEvents.clickOn(magazzinoHandler.getButtonOK());
            magazzinoHandler.execute();


        });
       // robot.sleep(500000);
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertTrue(magazzinoHandler.isCond());





    }
    @Test
    public void ValidInsertion(FxRobot robot){
        Platform.runLater(()->{
            magazzinoHandler=new MagazzinoHandler("Aggiungi Magazzino", List.of(magazzinoDao),FXCollections.observableList(List.of()));
            List<TextField> list=Utility.extract_value_from_list(magazzinoHandler.getControlList(), TextField.class);
            SimulateEvents.writeOn(list.getFirst(),"M1");
            SimulateEvents.writeOn(list.get(1),"Contrada Crescienzio 24");
            SimulateEvents.writeOn(list.get(2),"Roma");
            SimulateEvents.writeOn(list.get(3),"Roma");
            SimulateEvents.writeOn(list.get(4),"-18.818886984");
            SimulateEvents.writeOn(list.get(5),"-39.09529473");
            SimulateEvents.clickOn(magazzinoHandler.getButtonOK());
            when(magazzinoDao.insert(any(Warehouse.class))).thenReturn(true);
            magazzinoHandler.execute();


        });

         WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertTrue(magazzinoHandler.isCond());





    }


}