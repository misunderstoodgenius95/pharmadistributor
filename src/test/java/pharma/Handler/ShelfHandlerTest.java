package pharma.Handler;

import algoWarehouse.ShelfInfo;
import algoWarehouse.ShelvesCapacity;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
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
import pharma.Model.WarehouseModel;
import pharma.Storage.FileStorage;
import pharma.config.Utility;
import pharma.config.database.Database;
import pharma.dao.MagazzinoDao;
import pharma.dao.ShelfDao;
import pharma.dao.ShelvesDao;
import pharma.javafxlib.test.SimulateEvents;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ShelfHandlerTest {

    private  ShelfHandler shelfHandler;
    @Mock
    private MagazzinoDao magazzinoDao;
    @Mock
    private ShelfDao shelfDao;
    @Mock
    private ShelvesDao shelvesDao;
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

            WarehouseModel warehouseModel =new WarehouseModel(1,"A10",new PGgeometry(new Point(18.111999,20.008888)),"Via Crescenzio","Roma","Roma",List.of());
            when(magazzinoDao.findAll()).thenReturn((List<WarehouseModel>) List.of(warehouseModel));
            shelfHandler=new ShelfHandler("Inserisci Ripiani", new ArrayList<>(Arrays.asList(magazzinoDao,shelfDao,shelvesDao)), FXCollections.observableArrayList());
            TextField t_code=Utility.extract_value_from_list(shelfHandler.getControlList(), TextField.class).getFirst();
            SearchableComboBox<FieldData> s_combox=Utility.extract_value_from_list(shelfHandler.getControlList(),SearchableComboBox.class).getFirst();
            SimulateEvents.writeOn(t_code,"A1200");
            SimulateEvents.setFirstElementSearchableBox(s_combox);


            shelfHandler.show();
            SimulateEvents.clickOn(shelfHandler.getButtonOK());

        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertTrue(shelfHandler.isCheck_validate());


        robot.sleep(50000000);
    }
    @Test
    public void InvalidValidationForms(FxRobot robot){
        Platform.runLater(()->{
            WarehouseModel warehouseModel =new WarehouseModel(1,"A10",new PGgeometry(new Point(18.111999,20.008888)),"Via Crescenzio","Roma","Roma",List.of());
            when(magazzinoDao.findAll()).thenReturn(List.of(warehouseModel));
            shelfHandler=new ShelfHandler("Inserisci Ripiani", new ArrayList<>(Arrays.asList(magazzinoDao,shelfDao)),FXCollections.observableArrayList());
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
            WarehouseModel warehouseModel =new WarehouseModel(1,"A10",new PGgeometry(new Point(18.111999,20.008888)),"Via Crescenzio","Roma","Roma",List.of());
            when(magazzinoDao.findAll()).thenReturn(List.of(warehouseModel));
            shelfHandler=new ShelfHandler("Inserisci Ripiani", new ArrayList<>(Arrays.asList(magazzinoDao,shelfDao,shelvesDao)),FXCollections.observableArrayList());
            List<Spinner> list=Utility.extract_value_from_list(shelfHandler.getControlList(), Spinner.class);
            TextField t_code=Utility.extract_value_from_list(shelfHandler.getControlList(), TextField.class).getFirst();
            SearchableComboBox<FieldData> s_combox=Utility.extract_value_from_list(shelfHandler.getControlList(),SearchableComboBox.class).getFirst();
            SimulateEvents.setSpinner(list.get(4),2);
            SimulateEvents.writeOn(t_code,"A1200");
            SimulateEvents.setFirstElementSearchableBox(s_combox);

            when(shelfDao.insert(Mockito.any(ShelfInfo.class))).thenReturn(true);
            when(shelvesDao.insert(Mockito.any(ShelvesCapacity.class))).thenReturn(true,true);

            shelfHandler.execute();

        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertFalse(shelfHandler.isCond());



         robot.sleep(50000000);
    }
    @Test
    public void InValidFormInsert(FxRobot robot){
        Platform.runLater(()->{
            WarehouseModel warehouseModel =new WarehouseModel(1,"A10",new PGgeometry(new Point(18.111999,20.008888)),"Via Crescenzio","Roma","Roma",List.of());
            when(magazzinoDao.findAll()).thenReturn(List.of(warehouseModel));
            shelfHandler=new ShelfHandler("Inserisci Ripiani", new ArrayList<>(Arrays.asList(magazzinoDao,shelfDao,shelvesDao)),FXCollections.observableArrayList());
            List<Spinner> list=Utility.extract_value_from_list(shelfHandler.getControlList(), Spinner.class);
            TextField t_code=Utility.extract_value_from_list(shelfHandler.getControlList(), TextField.class).getFirst();
            SearchableComboBox<FieldData> s_combox=Utility.extract_value_from_list(shelfHandler.getControlList(),SearchableComboBox.class).getFirst();
            SimulateEvents.writeOn(t_code,"A1200");
            SimulateEvents.setFirstElementSearchableBox(s_combox);
            SimulateEvents.setSpinner(list.get(4),2);
            when(shelfDao.insert(Mockito.any(ShelfInfo.class))).thenReturn(true);
            when(shelvesDao.insert(Mockito.any(ShelvesCapacity.class))).thenReturn(true,false);

            shelfHandler.execute();

        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertFalse(shelfHandler.isCond());



        //robot.sleep(50000000);
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
            ShelvesDao shelvesDao_real=new ShelvesDao(Database.getInstance(properties));
            FieldData f1=FieldData.FieldDataBuilder.getbuilder().
                    setId(1).setcode("A10").setStreet("Via Crescenzio").setComune("Roma").setLatitude(18.111999).setLongitude(20.008888).build();
            shelfHandler=new ShelfHandler("Inserisci Ripiani", new ArrayList<>(Arrays.asList(magazzinoDao_real,shelfDao_real,shelvesDao_real)),FXCollections.observableArrayList());
            List<Spinner> list=Utility.extract_value_from_list(shelfHandler.getControlList(), Spinner.class);
            SimulateEvents.setSpinner(list.get(4),2);

            TextField t_code=Utility.extract_value_from_list(shelfHandler.getControlList(), TextField.class).getFirst();
            SearchableComboBox<FieldData> s_combox=Utility.extract_value_from_list(shelfHandler.getControlList(),SearchableComboBox.class).getFirst();
            SimulateEvents.writeOn(t_code,"A1202");
            SimulateEvents.setFirstElementSearchableBox(s_combox);
            SimulateEvents.clickOn(shelfHandler.getButtonOK());

            shelfHandler.execute();

        });
           WaitForAsyncUtils.waitForFxEvents();
            Assertions.assertTrue(shelfHandler.isCond());



        //robot.sleep(50000000);
    }



}