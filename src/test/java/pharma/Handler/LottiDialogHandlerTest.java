package pharma.Handler;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.database.Database;
import pharma.config.SimulateEvents;
import pharma.config.Utility;
import pharma.dao.FarmacoDao;
import pharma.dao.LottiDao;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class LottiDialogHandlerTest {
    private FarmacoDao farmacoDao;
    private Database database;
    private LottiDao lottiDao;
    private  LottiDialogHandler dialogHandler;
    private  DatePicker datePicker_1;
    private  DatePicker datePicker_2;
  @Mock
  private PreparedStatement preparedStatement;
    @Start
    public void start(Stage stage){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();

    }
    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        ResultSet resultSet=Mockito.mock(ResultSet.class);
        database= Mockito.mock(Database.class);
        when(resultSet.next()).thenReturn(true, true, true, true, true, true, true, true, true, false); // 9 rows
        when(resultSet.getInt(1)).thenReturn(59, 61, 60, 65, 66, 64, 63, 62, 67);
        when(resultSet.getString(2)).thenReturn("Tachipirina", "Debridat", "Amuchina", "Xanax", "Lucen", "Daparox", "Zoloft", "Sertralina EG", "Tachifludec");
        when(resultSet.getString(3)).thenReturn("Febbre", "Colite", "aa", "Depressione", "Gastrite", "DEpressione", "AntiDepressione", "Depressione", "Influenza");
        when(resultSet.getString(4)).thenReturn("Antibiotico", "Antibiotico", "Antibiotico", "Ansiolitici", "Gastroprotettore", "AntiDepressivo", "AntiDepressivo", "AntiDepressivo", "Antinfluenzale");
        when(resultSet.getString(5)).thenReturn("Supposte", "Supposte", "Supposte", "Compresse", "Compresse", "Compresse", "Compresse", "Compresse", "Compresse");
        when(resultSet.getString(6)).thenReturn("120ml", "120ml", "120ml", "5mg", "50mg", "50mg", "50mg", "50mg", "200mg");
        when(resultSet.getString(7)).thenReturn("Paracetamolo", "Paracetamolo", "Paracetamolo", "Alprazolam", "Esomeprazolo", "Paroxetina", "Sertralina", "Sertralina", "Paracetamolo");
        when(resultSet.getString(8)).thenReturn("Bayer", "Novartis", "Bayer", "Pfizer", "EG", "Mylan", "Pfizer", "EG", "Zentiva");
        when(resultSet.getInt(9)).thenReturn(20, 18, 30, 30, 18, 30, 28, 30, 10);

        farmacoDao=new FarmacoDao(database);
        lottiDao=new LottiDao(database,"lotto");
        when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
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
            Button button_table=Utility.extract_value_from_list(dialogHandler.getVbox().getChildren(), Button.class).getFirst();

          FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setId(1).setNome("Tachipirina").setDescription("Febbre").
                    setNome_categoria("Antiinfiammatorio").setNome_tipologia("Compresse").setUnit_misure("100mg").setNome_principio_attivo("Paracetamolo").
                    setNome_casa_farmaceutica("Angelini").build();
            SimulateEvents.writeOn(textField,"M20773");
           SimulateEvents.clickOn(button_table);

           Stage.getWindows();
            try {

                when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement);
                when(preparedStatement.executeUpdate()).thenReturn(1);


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            dialogHandler.execute();
           try {
                verify(preparedStatement).setString(1,"M20773");
                verify(preparedStatement).setInt(2,59);
                verify(preparedStatement).setDate(3, Date.valueOf(LocalDate.of(2023,1,10)));
        verify(preparedStatement).setDate(4,Date.valueOf(LocalDate.of(2025,1,10)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            //  SimulateEvents.clickOn(dialogHandler.getButtonOK());*/


        });
        robot.sleep(500000);
    }






}