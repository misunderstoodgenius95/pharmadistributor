package pharma.Controller.subpanel;

import com.auth0.net.Telemetry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import pharma.Model.FieldData;
import pharma.Stages;
import pharma.config.Database;
import pharma.config.SimulateEvents;
import pharma.config.Utility;
import pharma.dao.DetailDao;

import javax.print.attribute.standard.JobImpressionsSupported;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@ExtendWith(ApplicationExtension.class)
class DettagliTest {
    private Scene scene;
    @Start
    public void start(Stage primaryStage) throws IOException {
        Stages stage = new Stages();
        Parent parent = stage.load_fxml("/subpanel/dettagli.fxml");

        scene = new Scene(parent);

        primaryStage.setScene(scene);
        primaryStage.show();


    }
    @Test
    public void TestSelect(FxRobot robot) {
        Platform.runLater(() -> {
            ChoiceBox<String> fd = robot.lookup("#select_detail").queryAs(ChoiceBox.class);
            SimulateEvents.simulate_selected_items(fd, Utility.Tipologia);
            Assertions.assertEquals(Utility.Tipologia,fd.getSelectionModel().getSelectedItem());
            robot.sleep(2000);
            SimulateEvents.simulate_selected_items(fd, Utility.Categoria);
            Assertions.assertEquals(Utility.Categoria,fd.getSelectionModel().getSelectedItem());
            robot.sleep(4000);
        });
        robot.sleep(4000);
    }


    @Test
    public void TestInvalidClickButtonNotMisureFiledEmpty(FxRobot robot){


        Platform.runLater(() -> {
            ChoiceBox<String> fd = robot.lookup("#select_detail").queryAs(ChoiceBox.class);



       //     SimulateEvents.simulate_selected_items(fd, Utility.Tipologia);
            Button btn=robot.lookup("#btn_click").queryButton();


            SimulateEvents.clickOn(btn);



        });
        robot.sleep(4000);
    }
    @Test
    public void TestValidClickButtonNotMisure(FxRobot robot){

        Platform.runLater(() -> {
           ChoiceBox<String> fd = robot.lookup("#select_detail").queryAs(ChoiceBox.class);
            Button btn=robot.lookup("#btn_click").queryButton();
           TextField tf1=robot.lookup("#detail_field_1").queryAs(TextField.class);
           SimulateEvents.simulate_selected_items(fd, Utility.Tipologia);

          SimulateEvents.writeOn(tf1,"Supposte");


            SimulateEvents.clickOn(btn);



        });
        robot.sleep(4000);
    }
    @Test
    public void TestSimulateListView(FxRobot robot){

        Platform.runLater(() -> {
            ChoiceBox<String> fd = robot.lookup("#select_detail").queryAs(ChoiceBox.class);


            SimulateEvents.simulate_selected_items(fd, Utility.Categoria);
            ListView<String> listView=robot.lookup("#list_view").queryAs(ListView.class);
           // ObservableList<String> observableList= FXCollections.observableArrayList("Antibiotici","Antidepressivi","AntiInfiammatori");
            listView.getItems().addAll("Antibiotici","Antidepressivi","AntiInfiammatori");

        });
        robot.sleep(4000);
    }
    @Test
    public void TestSimulateListViewWithMockito(FxRobot robot) throws SQLException {
        Database database=Mockito.mock(Database.class);
        DetailDao detailDao=new DetailDao(database);
        ResultSet resultSet= Mockito.mock(ResultSet.class);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true,true,true,false);
        Mockito.when(resultSet.getString(2)).thenReturn("Antibiotico").thenReturn("AntiSpasminico").thenReturn("AntiVitrali");
        detailDao.setTable_name(Utility.Categoria);
        List<FieldData> result_list_fd = detailDao.findAll();
        Platform.runLater(() -> {
            ChoiceBox<String> fd = robot.lookup("#select_detail").queryAs(ChoiceBox.class);


            SimulateEvents.simulate_selected_items(fd, Utility.Categoria);
            ListView<String> listView=robot.lookup("#list_view").queryAs(ListView.class);
            List<String> list = result_list_fd.stream().map(FieldData::getNome).toList();


            listView.getItems().addAll(list);

        });
        robot.sleep(4000);
    }

    @Test
    public void TestSimulateListViewWithMockitoChange(FxRobot robot) throws SQLException {
        Database database=Mockito.mock(Database.class);
        DetailDao detailDao=new DetailDao(database);
        ResultSet resultSet= Mockito.mock(ResultSet.class);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true,true,true,false);
        Mockito.when(resultSet.getString(2)).thenReturn("Antibiotico").thenReturn("AntiSpasminico").thenReturn("AntiVitrali");
        detailDao.setTable_name(Utility.Categoria);
        List<FieldData> result_list_fd = detailDao.findAll();
        Platform.runLater(() -> {
            ChoiceBox<String> fd = robot.lookup("#select_detail").queryAs(ChoiceBox.class);


            SimulateEvents.simulate_selected_items(fd, Utility.Categoria);
            ListView<String> listView=robot.lookup("#list_view").queryAs(ListView.class);
            List<String> list = result_list_fd.stream().map(FieldData::getNome).toList();


            listView.getItems().addAll(list);

        });
        robot.sleep(4000);
    }










}