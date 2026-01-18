package pharma.DialogController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.database.Database;
import pharma.dao.PharmaDao;

import java.rmi.AccessException;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@ExtendWith(ApplicationExtension.class)
class PharmaDialogControllerBaseTest {
    private PharmaDao pharmaDao;
    private Database database;
    ObservableList<FieldData> obs_fieldData;
    @BeforeEach
    void setup() throws SQLException {
        database= Mockito.mock(Database.class);
        pharmaDao=new PharmaDao(database);
        obs_fieldData= FXCollections.observableArrayList();



    }
    @Start
    public void start(Stage stage){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();


    }
    @Test
    public void insert(FxRobot robot) throws SQLException {
        PreparedStatement preparedStatement=Mockito.mock(PreparedStatement.class);
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setPartita_iva("11111").setSigla("MM").setAnagrafica_cliente("Melarini").build();

        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);


        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);

        boolean value=pharmaDao.insert(fieldData);
        Assertions.assertTrue(value);
        Platform.runLater(()-> {


            PharmaDialogControllerBase pharmaDialogHandler = new PharmaDialogControllerBase("Aggiungi Casa F", pharmaDao, obs_fieldData);
            try {
                pharmaDialogHandler.setOperation(DialogControllerBase.Mode.Insert, null);


            } catch (AccessException e) {
                throw new RuntimeException(e);
            }


        });
        robot.sleep(4000);
    }
@Test
     void InvalidUpdateNULLField() throws AccessException {

    Platform.runLater(()-> {
        PharmaDialogControllerBase pharmaDialogHandler = new PharmaDialogControllerBase("Aggiungi Casa farmaceutica", pharmaDao, obs_fieldData);
        try {
            pharmaDialogHandler.setOperation(DialogControllerBase.Mode.Update, null);
        } catch (AccessException e) {
            throw new RuntimeException(e);
        }
    });
}

    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{
            PharmaDialogControllerBase pharmaDialogHandler = new PharmaDialogControllerBase("Aggiungi Casa farmaceutica", pharmaDao, obs_fieldData);
            pharmaDialogHandler.show();





        });
        robot.sleep(5000);


    }






}

