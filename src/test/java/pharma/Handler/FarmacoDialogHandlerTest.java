package pharma.Handler;

import com.sun.javafx.collections.NonIterableChange;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.config.Database;
import pharma.config.PopulateChoice;
import pharma.config.SimulateEvents;
import pharma.dao.DetailDao;
import pharma.dao.FarmacoDao;
import pharma.dao.PharmaDao;

import javax.xml.crypto.Data;

import java.rmi.AccessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class FarmacoDialogHandlerTest {
    private FarmacoDao farmacoDao;
    private Database database;
    private PreparedStatement preparedStatement;
    private DetailDao detailDao;
    private PharmaDao pharmaDao;
    @Start
    public void start(Stage stage){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();


    }
    @BeforeEach
    public void setUp() throws SQLException {
        preparedStatement=Mockito.mock(PreparedStatement.class);
        database= Mockito.mock(Database.class);
        farmacoDao=new FarmacoDao("farmaco",database);
         detailDao=new DetailDao(database);
         pharmaDao=new PharmaDao(database);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);


        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void ValidTestwithChoiceboxNotMockito(FxRobot robot) throws AccessException {
        Platform.runLater(()->{
            ObservableList<FieldData> obs_fieldData= FXCollections.observableArrayList();
            FarmacoDialogHandler farmacoDialogHandler=new FarmacoDialogHandler("Aggiungi Farmaco",obs_fieldData,farmacoDao,detailDao,pharmaDao);
            try {
                farmacoDialogHandler.setOperation(DialogHandler.Mode.Insert,null);

            } catch (AccessException e) {
                throw new RuntimeException(e);
            }

 TextField textField_nome= (TextField) farmacoDialogHandler.getVbox().getChildren().get(0);
            textField_nome.setText("Tachipirina");
            TextField textField_descrizione= (TextField) farmacoDialogHandler.getVbox().getChildren().get(1);
            textField_descrizione.setText("Febbre");
            ChoiceBox<FieldData> fieldDataChoiceBox_categoria=(ChoiceBox<FieldData>) farmacoDialogHandler.getVbox().getChildren().get(2);
            fieldDataChoiceBox_categoria.setValue(FieldData.FieldDataBuilder.getbuilder().setNome("Antiinfiammatorio").build());
            ChoiceBox<FieldData> fieldDataChoiceBox_tipologia=(ChoiceBox<FieldData>) farmacoDialogHandler.getVbox().getChildren().get(3);
            fieldDataChoiceBox_tipologia.setValue(FieldData.FieldDataBuilder.getbuilder().setNome("Compresse").build());

            ChoiceBox<FieldData> fieldDataChoiceBox_miusura=(ChoiceBox<FieldData>) farmacoDialogHandler.getVbox().getChildren().get(4);
            fieldDataChoiceBox_miusura.setValue(FieldData.FieldDataBuilder.getbuilder().setNome("100mg").build());
            ChoiceBox<FieldData> fieldDataChoiceBox_principio_attivo=(ChoiceBox<FieldData>) farmacoDialogHandler.getVbox().getChildren().get(5);
            fieldDataChoiceBox_principio_attivo.setValue(FieldData.FieldDataBuilder.getbuilder().setNome("Paracetamolo").build());
            ChoiceBox<FieldData> fieldDataChoiceBox_casa_f=(ChoiceBox<FieldData>) farmacoDialogHandler.getVbox().getChildren().get(6);
            fieldDataChoiceBox_casa_f.setValue(FieldData.FieldDataBuilder.getbuilder().setNome("Melarini").build());
            farmacoDialogHandler.execute();
            robot.sleep(4000);
        });
    robot.sleep(40000);

    }
    @Test
    public void ValidTestWithChoiceMockito(FxRobot robot) {
        Platform.runLater(() -> {



                ObservableList<FieldData> obs_fieldData = FXCollections.observableArrayList();
                FarmacoDialogHandler farmacoDialogHandler = new FarmacoDialogHandler("Aggiungi Farmaco", obs_fieldData, farmacoDao, detailDao, pharmaDao);
                try {
                    farmacoDialogHandler.setOperation(DialogHandler.Mode.Insert, null);
                } catch (AccessException e) {
                    throw new RuntimeException(e);
                }
                ResultSet resultSet = Mockito.mock(ResultSet.class);
                try {
                    Mockito.when(resultSet.next()).thenReturn(true, false);
                    Mockito.when(resultSet.getString(2)).thenReturn("Ciao");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                farmacoDialogHandler.execute();
                Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
                Mockito.verify(database).execute(Mockito.anyString());


            });
            robot.sleep(40000);

        }



}