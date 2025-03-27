package pharma.Handler;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.Database;
import pharma.config.SimulateEvents;
import pharma.dao.FarmacoDao;
import pharma.dao.LottiDao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(ApplicationExtension.class)
class LottiDialogHandlerTest {
    private FarmacoDao farmacoDao;
    private Database database;
    private LottiDao lottiDao;
    private  LottiDialogHandler dialogHandler;
    private  DatePicker datePicker_1;
    private  DatePicker datePicker_2;
    @Start
    public void start(Stage stage){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();

    }
    @BeforeEach
    public void setUp() throws SQLException {

        ResultSet resultSet=Mockito.mock(ResultSet.class);
        database= Mockito.mock(Database.class);
        Mockito.when(resultSet.next()).thenReturn(true,true,false);
        Mockito.when(resultSet.getInt(1)).thenReturn(1,2);
        Mockito.when(resultSet.getString(2)).thenReturn("Tachipirina","Debridat");
        Mockito.when(resultSet.getString(3)).thenReturn("Febbre","Colite");
        Mockito.when(resultSet.getString(4)).thenReturn("Antiinfiammatorio","Antiinfiammatorio");
        Mockito.when(resultSet.getString(5)).thenReturn("Compresse","Compresse");
        Mockito.when(resultSet.getString(6)).thenReturn("100mg","10gr");
        Mockito.when(resultSet.getString(7)).thenReturn("Paracetamolo","trimebutina");
        Mockito.when(resultSet.getString(8)).thenReturn("Angelini","Alfasigma");
        farmacoDao=new FarmacoDao("farmaco",database);
        lottiDao=new LottiDao(database,"lotto");
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        ObservableList<FieldData> obs = FXCollections.observableArrayList();




    }
    @Test
    public void INvalidTest(FxRobot robot){
        Platform.runLater(()-> {
            ObservableList<FieldData> obs = FXCollections.observableArrayList();
            LottiDialogHandler dialogHandler = new LottiDialogHandler("Aggiungi Lotti", lottiDao, farmacoDao, obs);
            dialogHandler.execute();
            SimulateEvents.clickOn(dialogHandler.getButtonOK());
        });
        robot.sleep(5000);
    }

    @Test
    public void ValidInsertNotDataNotSelectable(FxRobot fxrobot){

        Platform.runLater(()-> {
            ObservableList<FieldData> obs = FXCollections.observableArrayList();
            dialogHandler = new LottiDialogHandler("Aggiungi Lotti", lottiDao, farmacoDao, obs);


            List<Control> c=dialogHandler.getControlList().stream().filter(control-> control instanceof DatePicker).toList();
            datePicker_1= (DatePicker) c.get(0);
            datePicker_2=(DatePicker) c.get(1);

            datePicker_1.setValue(LocalDate.of(2024,10,1));

            //datePicker_2.setValue(LocalDate.of(2023,10,10));
           // Assertions.assertNotEquals(datePicker_2.getValue(),LocalDate.of(2023,10,10));

            dialogHandler.execute();







        });
        fxrobot.sleep(40000);
    }

    @Test
    public void ValidData(FxRobot robot){
        Platform.runLater(()->{
            ObservableList<FieldData> obs = FXCollections.observableArrayList();
            LottiDialogHandler dialogHandler = new LottiDialogHandler("Aggiungi Lotti", lottiDao, farmacoDao, obs);

            List<Control> c=dialogHandler.getControlList().stream().filter(control-> control instanceof DatePicker).toList();
            datePicker_1= (DatePicker) c.get(0);
            datePicker_2=(DatePicker) c.get(1);
            datePicker_1.setValue(LocalDate.of(2023,1,10));
            datePicker_2.setValue(LocalDate.of(2025,1,10));
            TextField textField= (TextField) dialogHandler.getControlList().stream().filter(control-> control instanceof TextField).toList().getFirst();

           List<Control> list_spinner=dialogHandler.getControlList().stream().filter(control-> control instanceof Spinner).toList();
            List<Control> list_combo=dialogHandler.getControlList().stream().filter(control-> control instanceof SearchableComboBox).toList();
            SearchableComboBox<FieldData> searchableComboBox = (SearchableComboBox<FieldData>) list_combo.getFirst();
          FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setId(1).setNome("Tachipirina").setDescription("Febbre").
                    setNome_categoria("Antiinfiammatorio").setNome_tipologia("Compresse").setUnit_misure("100mg").setNome_principio_attivo("Paracetamolo").
                    setNome_casa_farmaceutica("Angelini").build();
            searchableComboBox.setValue(fieldData);
            SimulateEvents.writeOn(textField,"M20773");
            Spinner<Integer> spinner=(Spinner<Integer>) list_spinner.getFirst();
        SimulateEvents.setSpinner(spinner,20);




            dialogHandler.execute();
            SimulateEvents.clickOn(dialogHandler.getButtonOK());


        });
        robot.sleep(5000);
    }

}