package pharma.Handler;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.config.database.Database;
import pharma.javafxlib.test.SimulateEvents;
import pharma.dao.FarmaciaDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class FarmaciaViewTest {
    private FarmaciaDao farmaciaDao;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Database database;
    @Mock
    private PreparedStatement preparedStatement;
    private  ObservableList<FieldData> observableList;
    private  FarmaciaView farmaciaView;
    @BeforeEach
    public  void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        farmaciaDao=new FarmaciaDao(database);




    }

    @Start
    public void stage(Stage stage){
        VBox vBox=new VBox();
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        observableList= FXCollections.observableArrayList();
    }

    @Test
    public void test(FxRobot robot) {
        Platform.runLater(() -> {
            farmaciaView = new FarmaciaView("Ricerca Casa Farmaceutica", farmaciaDao, observableList);
            farmaciaView.show();



            try {




                when(database.execute_prepared_query(anyString())).thenAnswer(invocation -> {
                    String value = invocation.getArgument(0);
                    System.out.println(value);
                    if (value.contains("comune")) {
                        when(resultSet.next()).thenReturn(true, false);
                        when(resultSet.getInt(1)).thenReturn(1);
                        when(resultSet.getString(2)).thenReturn("Farmacia Collica");
                        when(resultSet.getString(3)).thenReturn("IT1111111");
                        when(resultSet.getString(4)).thenReturn("Via Guido Cavalcanti ");
                        when(resultSet.getInt(5)).thenReturn(98071);
                        when(resultSet.getString(6)).thenReturn("Capo");
                        when(resultSet.getString(7)).thenReturn("ME");
                    } else if(value.contains("partita_iva")){

                        if (value.contains("comune")) {
                            when(resultSet.next()).thenReturn(true, false);
                            when(resultSet.getInt(1)).thenReturn(1);
                            when(resultSet.getString(2)).thenReturn("Farmacia Collica");
                            when(resultSet.getString(3)).thenReturn("IT1111111");
                            when(resultSet.getString(4)).thenReturn("Via Guido Cavalcanti ");
                            when(resultSet.getInt(5)).thenReturn(98071);
                            when(resultSet.getString(6)).thenReturn("Capo");
                            when(resultSet.getString(7)).thenReturn("ME");
                        }
                    }


                    return preparedStatement;

                });

                when(preparedStatement.executeQuery()).thenReturn(resultSet);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            TextField textField = (TextField) farmaciaView.getControlList().getFirst();
            ToggleGroup toggleGroup = farmaciaView.getToggleGroup();
            toggleGroup.selectToggle(toggleGroup.getToggles().get(1));

            textField.setText("Roma");
            Platform.runLater(() -> {

                SimulateEvents.clickOn(farmaciaView.getButtonOK());

                WaitForAsyncUtils.waitForFxEvents();
                Assertions.assertEquals(1, observableList.size());


            });
            //  robot.sleep(400);


        });
    }

    @Test
    public void testExecution(FxRobot robot){
        Platform.runLater(()->{
            farmaciaView = new FarmaciaView("Ricerca Casa Farmaceutica", farmaciaDao, observableList);
            farmaciaView.show();






        });
        robot.sleep(5000);


    }





}