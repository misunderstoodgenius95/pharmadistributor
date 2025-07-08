package pharma.Handler.Table;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.bytebuddy.implementation.auxiliary.MethodCallProxy;
import net.jodah.failsafe.internal.util.Assert;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.config.database.Database;
import pharma.dao.LottiDao;
import pharma.javafxlib.test.SimulateEvents;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import  static  pharma.javafxlib.test.SimulateEvents.*;
@ExtendWith(ApplicationExtension.class)
@SuppressWarnings({"unchecked", "deprecation"})
class LotTableCustomTest {
    private  LotTableCustom lotTableCustom;
    private LottiDao lottiDao;

    @Mock
    private ResultSet resultSet;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Database database;
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
        lottiDao=new LottiDao(database,"lotto");
        ArgumentCaptor<String> argumentCaptor=ArgumentCaptor.forClass(String.class);
        when(database.execute_prepared_query(argumentCaptor.capture())).thenReturn(preparedStatement);
when(preparedStatement.executeQuery()).thenReturn(resultSet);
       doAnswer(invocationOnMock -> {
            String parameter=invocationOnMock.getArgument(1);
          // System.out.println(parameter);

if(parameter.contains("a")) {


    when(resultSet.next()).thenReturn(true, true,false);
    when(resultSet.getString("id")).thenReturn("aaa","bbb");
    when(resultSet.getDate("production_date")).thenReturn(Date.valueOf(LocalDate.of(2024, 10, 01)));
    when(resultSet.getDate("elapsed_date")).thenReturn(Date.valueOf(LocalDate.of(2025, 10, 01)));
    when(resultSet.getInt("quantity")).thenReturn(300);
    when(resultSet.getString("nome")).thenReturn("Amuchina","Asprina");
    when(resultSet.getString("tipologia")).thenReturn("Compresse");
    when(resultSet.getString("misura")).thenReturn("100mg");
    when(resultSet.getString("casa_farmaceutica")).thenReturn("Angelini");
    when(resultSet.getDouble("price")).thenReturn(6.50);
    } else if(parameter.contains("t")) {


                when(resultSet.next()).thenReturn(true, false);
                when(resultSet.getString("id")).thenReturn("aaa");
                when(resultSet.getDate("production_date")).thenReturn(Date.valueOf(LocalDate.of(2024, 10, 01)));
                when(resultSet.getDate("elapsed_date")).thenReturn(Date.valueOf(LocalDate.of(2025, 10, 01)));
                when(resultSet.getInt("quantity")).thenReturn(300);
                when(resultSet.getString("nome")).thenReturn("Tachipirina");
                when(resultSet.getString("tipologia")).thenReturn("Compresse");
                when(resultSet.getString("misura")).thenReturn("100mg");
                when(resultSet.getString("casa_farmaceutica")).thenReturn("Angelini");
                when(resultSet.getDouble("price")).thenReturn(6.50);
            }




            return resultSet;



        }).when(preparedStatement).setString(eq(1),anyString());


Platform.runLater(()->{

    lotTableCustom = new LotTableCustom("Scegli Lotto", lottiDao);
    lotTableCustom.show();



});



    }
        @Test
    public void test(FxRobot fx){
      Platform.runLater(()->{


      });

        fx.sleep(10000000);
    }

    @Test
    public  void ValidDisableTest(FxRobot robot) {


        Platform.runLater(() -> {


            Assertions.assertTrue(lotTableCustom.getTextField_search().isDisable());
        });

    }
            @Test
            public  void ValidChoiceboxNome() {
        Platform.runLater(() -> {
            setFirstElementChoiceBox(lotTableCustom.getChoiceBox());
            Assertions.assertEquals(lotTableCustom.getChoiceBox().getItems().getFirst(), lotTableCustom.getChoiceBox().getValue());
        });
    }
//3
    @Test
    public  void ValidTextFildIsNotDisable(FxRobot robot) {
        Platform.runLater(() -> {
            setFirstElementChoiceBox(lotTableCustom.getChoiceBox());
            Assertions.assertFalse(lotTableCustom.getTextField_search().isDisable());
        });

    }
    @Test
    public  void ValidInsertRow(FxRobot robot) {
        Platform.runLater(() -> {
            setFirstElementChoiceBox(lotTableCustom.getChoiceBox());
            writeOn(lotTableCustom.getTextField_search(), "a");
           Assertions.assertEquals(2,lotTableCustom.getTableLot().getItems().size());



    });

    }


    @Test
    public  void ValidVerifySelectedCheckBox(FxRobot robot) {

        Platform.runLater(() -> {
            setFirstElementChoiceBox(lotTableCustom.getChoiceBox());
            writeOn(lotTableCustom.getTextField_search(), "a");
            ObservableMap<FieldData,CheckBox> map = lotTableCustom.getMapCheckBox();

             SimulateEvents.setCheckBox(map,lotTableCustom.getTableLot().getItems().getFirst());
             SimulateEvents.setCheckBox(map,lotTableCustom.getTableLot().getItems().get(1));

            lotTableCustom.getButtonOK().setOnAction(event -> {
                System.out.println("size"+lotTableCustom.getSelectedRows().size());

            });
             SimulateEvents.clickOn(lotTableCustom.getButtonOK());

            /* lotTableCustom.getSelectedRows().addListener(new SetChangeListener<FieldData>() {
                 @Override
                 public void onChanged(Change<? extends FieldData> change) {
                 if(change.wasAdded()){
                     System.out.println("Added!");

                     System.out.println(change.getElementAdded());
                 }
                 }
             });


             */
        });


    }

    @Test
    void check_validateProperty() {


    }













          /*  writeOn(lotTableCustom.getTextField_search(), "a");
            try {
                WaitForAsyncUtils.waitFor(2,TimeUnit.SECONDS,()->
                !lotTableCustom.getTableLot().getItems().isEmpty()

                );
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }

            ObservableMap<FieldData,CheckBox> map = lotTableCustom.getMapCheckBox();
            SimulateEvents.setCheckBox(map,lotTableCustom.getTableLot().getItems().get(0));
            //  Verify selected chekbox into rows

            SimulateEvents.setCheckBox(map,lotTableCustom.getTableLot().getItems().get(1));

          //  Assertions.assertEquals(2,lotTableCustom.getSelectedRows().size());






            // Simulate Choicebox*/

      //  });









     //  robot.sleep(10000000);


   // }



}