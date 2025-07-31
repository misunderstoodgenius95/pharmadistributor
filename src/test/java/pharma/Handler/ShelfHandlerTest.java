package pharma.Handler;

import algo.ShelfInfo;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.controlsfx.control.SearchableComboBox;
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
import pharma.dao.ShelfDao;
import pharma.javafxlib.test.SimulateEvents;

import javax.xml.crypto.Data;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ShelfHandlerTest {

    private  ShelfHandler shelfHandler;
    @Mock
    private MagazzinoDao magazzinoDao;
    @Mock
    private ShelfDao shelfDao;
    @Start
    public void  start(Stage stage){
        VBox vBox=new VBox();
        Scene scene=new Scene(vBox);
        stage.setScene(scene);




    }
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void ValidValidationForms(FxRobot robot){
        Platform.runLater(()->{

            Warehouse warehouse=new Warehouse(1,"A10",new PGgeometry(new Point(18.111999,20.008888)),"Via Crescenzio","Roma","Roma",List.of());
            when(magazzinoDao.findAll()).thenReturn((List<Warehouse>) List.of(warehouse));
            shelfHandler=new ShelfHandler("Inserisci Ripiani", new ArrayList<>(Arrays.asList(magazzinoDao,shelfDao)));
            TextField t_code=Utility.extract_value_from_list(shelfHandler.getControlList(), TextField.class).getFirst();
            SearchableComboBox<FieldData> s_combox=Utility.extract_value_from_list(shelfHandler.getControlList(),SearchableComboBox.class).getFirst();
            SimulateEvents.writeOn(t_code,"A1200");
            SimulateEvents.setFirstElementSearchableBox(s_combox);


            shelfHandler.show();
            SimulateEvents.clickOn(shelfHandler.getButtonOK());

        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertTrue(shelfHandler.isCheck_validate());



      //  robot.sleep(50000000);
    }
    @Test
    public void InvalidValidationForms(FxRobot robot){
        Platform.runLater(()->{
            Warehouse warehouse=new Warehouse(1,"A10",new PGgeometry(new Point(18.111999,20.008888)),"Via Crescenzio","Roma","Roma",List.of());
            when(magazzinoDao.findAll()).thenReturn(List.of(warehouse));
            shelfHandler=new ShelfHandler("Inserisci Ripiani", new ArrayList<>(Arrays.asList(magazzinoDao,shelfDao)));
            TextField t_code=Utility.extract_value_from_list(shelfHandler.getControlList(), TextField.class).getFirst();
            SearchableComboBox<FieldData> s_combox=Utility.extract_value_from_list(shelfHandler.getControlList(),SearchableComboBox.class).getFirst();
            SimulateEvents.writeOn(t_code,"A1200");
            shelfHandler.show();
            SimulateEvents.clickOn(shelfHandler.getButtonOK());

        });
    WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertFalse(shelfHandler.isCheck_validate());



        // robot.sleep(50000000);
    }
    @Test
    public void ValidFormInsert(FxRobot robot){
        Platform.runLater(()->{
            Warehouse warehouse=new Warehouse(1,"A10",new PGgeometry(new Point(18.111999,20.008888)),"Via Crescenzio","Roma","Roma",List.of());
            when(magazzinoDao.findAll()).thenReturn(List.of(warehouse));
            shelfHandler=new ShelfHandler("Inserisci Ripiani", new ArrayList<>(Arrays.asList(magazzinoDao,shelfDao)));
            TextField t_code=Utility.extract_value_from_list(shelfHandler.getControlList(), TextField.class).getFirst();
            SearchableComboBox<FieldData> s_combox=Utility.extract_value_from_list(shelfHandler.getControlList(),SearchableComboBox.class).getFirst();
            SimulateEvents.writeOn(t_code,"A1200");
            SimulateEvents.setFirstElementSearchableBox(s_combox);
            SimulateEvents.clickOn(shelfHandler.getButtonOK());
            when(shelfDao.insert(Mockito.any(ShelfInfo.class))).thenReturn(true);

            shelfHandler.execute();

        });
     //   WaitForAsyncUtils.waitForFxEvents();
    //    Assertions.assertTrue(shelfHandler.isCond());



         robot.sleep(50000000);
    }
    @Test
    public void ValidFormInsertIntegration(FxRobot robot){
        Platform.runLater(()->{

            Properties properties = null;
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MagazzinoDao magazzinoDao_real=new MagazzinoDao(Database.getInstance(properties));
            ShelfDao shelfDao_real=new ShelfDao(Database.getInstance(properties));
            FieldData f1=FieldData.FieldDataBuilder.getbuilder().
                    setId(1).setcode("A10").setStreet("Via Crescenzio").setComune("Roma").setLatitude(18.111999).setLongitude(20.008888).build();

            shelfHandler=new ShelfHandler("Inserisci Ripiani", new ArrayList<>(Arrays.asList(magazzinoDao_real,shelfDao_real)));
            TextField t_code=Utility.extract_value_from_list(shelfHandler.getControlList(), TextField.class).getFirst();
            SearchableComboBox<FieldData> s_combox=Utility.extract_value_from_list(shelfHandler.getControlList(),SearchableComboBox.class).getFirst();
            SimulateEvents.writeOn(t_code,"A1200");
            SimulateEvents.setFirstElementSearchableBox(s_combox);
            SimulateEvents.clickOn(shelfHandler.getButtonOK());

            shelfHandler.execute();

        });
           WaitForAsyncUtils.waitForFxEvents();
            Assertions.assertTrue(shelfHandler.isCond());



        robot.sleep(50000000);
    }



}