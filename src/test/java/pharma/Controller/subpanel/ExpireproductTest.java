package pharma.Controller.subpanel;

import com.sun.javafx.scene.traversal.SubSceneTraversalEngine;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.awaitility.core.StartEvaluationEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import pharma.Model.FieldData;
import pharma.Stages;
import pharma.javafxlib.test.CheckLastItemAdded;
import pharma.javafxlib.test.SimulateEvents;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class ExpireproductTest {
    private Scene scene;

    @Start
    public void start(Stage primaryStage) throws IOException {
        Stages stage = new Stages();
        Parent parent = stage.load_fxml("/subpanel/expireproduct.fxml");
        scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();


    }




    @Test
    public void ValidTestRemoveFirstElementByContextMenu(FxRobot robot) {
        TableView<FieldData> tableView = robot.lookup("#table_id").queryAs(TableView.class);
        FieldData data1 = FieldData.FieldDataBuilder.getbuilder().setLotto_id("L001")
                .setNome("Paracetamolo")
                .setNome_categoria("Antidolorifico")
                .setNome_tipologia("Compresse")
                .setUnit_misure("mg")
                .setNome_principio_attivo("Paracetamolo")
                .setNome_casa_farmaceutica("Pharma S.p.A").
                setQuantity(50).setProduction_date(Date.valueOf(LocalDate.of(2023, 01, 15))).
                setElapsed_date(Date.valueOf(LocalDate.of(2025, 01, 15))).
                setAvailability(300)
                .build();
        FieldData data2 = FieldData.FieldDataBuilder.getbuilder()
                .setLotto_id("L002")
                .setNome("Ibuprofene")
                .setNome_categoria("Antinfiammatorio")
                .setNome_tipologia("Compresse")
                .setUnit_misure("mg")
                .setNome_principio_attivo("Ibuprofene")
                .setNome_casa_farmaceutica("Salute SRL")
                .setQuantity(30)
                .setProduction_date(Date.valueOf(LocalDate.of(2023, 5, 10)))
                .setElapsed_date(Date.valueOf(LocalDate.of(2025, 5, 10)))
                .setAvailability(150)
                .build();

        FieldData data3 = FieldData.FieldDataBuilder.getbuilder()
                .setLotto_id("L003")
                .setNome("Amoxicillina")
                .setNome_categoria("Antibiotico")
                .setNome_tipologia("Capsule")
                .setUnit_misure("mg")
                .setNome_principio_attivo("Amoxicillina")
                .setNome_casa_farmaceutica("Medicina Generale")
                .setQuantity(20)
                .setProduction_date(Date.valueOf(LocalDate.of(2023, 3, 20)))
                .setElapsed_date(Date.valueOf(LocalDate.of(2024, 3, 20)))
                .setAvailability(0)
                .build();
        Platform.runLater(() -> {
            ObservableList<FieldData> observableList = FXCollections.observableArrayList(data1, data2, data3);
            tableView.setItems(observableList);
            SimulateEvents.keyPress(tableView, KeyCode.ENTER);
            SimulateEvents.openContextMenu(tableView, 0);
            SimulateEvents.fireItemMenu(tableView, 0, 0);




        });

    }

    @Test
    public void ValidInsert(FxRobot robot) {
        TableView<FieldData> tableView = robot.lookup("#table_id").queryAs(TableView.class);
        Spinner<Integer> spinner=robot.lookup("#spinner_id_days").queryAs(Spinner.class);
        Button  button_send=robot.lookup("#btn_send").queryAs(Button.class);
        FieldData data1 = FieldData.FieldDataBuilder.getbuilder().setLotto_id("L001")
                .setNome("Paracetamolo")
                .setNome_categoria("Antidolorifico")
                .setNome_tipologia("Compresse")
                .setUnit_misure("mg")
                .setNome_principio_attivo("Paracetamolo")
                .setNome_casa_farmaceutica("Pharma S.p.A").
                setQuantity(50).setProduction_date(Date.valueOf(LocalDate.of(2023, 01, 15))).
                setElapsed_date(Date.valueOf(LocalDate.of(2025, 01, 15))).
                setAvailability(300)
                .build();
        FieldData data2 = FieldData.FieldDataBuilder.getbuilder()
                .setLotto_id("L0002")
                .setNome("Ibuprofene")
                .setNome_categoria("Antinfiammatorio")
                .setNome_tipologia("Compresse")
                .setUnit_misure("mg")
                .setNome_principio_attivo("Ibuprofene")
                .setNome_casa_farmaceutica("Salute SRL")
                .setQuantity(30)
                .setProduction_date(Date.valueOf(LocalDate.of(2023, 5, 10)))
                .setElapsed_date(Date.valueOf(LocalDate.of(2025, 5, 10)))
                .setAvailability(150)
                .build();

        FieldData data3 = FieldData.FieldDataBuilder.getbuilder()
                .setLotto_id("L0003")
                .setNome("Amoxicillina")
                .setNome_categoria("Antibiotico")
                .setNome_tipologia("Capsule")
                .setUnit_misure("mg")
                .setNome_principio_attivo("Amoxicillina")
                .setNome_casa_farmaceutica("Medicina Generale")
                .setQuantity(20)
                .setProduction_date(Date.valueOf(LocalDate.of(2023, 3, 20)))
                .setElapsed_date(Date.valueOf(LocalDate.of(2024, 3, 20)))
                .setAvailability(0)
                .build();
        Platform.runLater(() -> {
            ObservableList<FieldData> observableList = FXCollections.observableArrayList(data1, data2, data3);
            tableView.setItems(observableList);
            SimulateEvents.keyPress(tableView, KeyCode.ENTER);
            SimulateEvents.openContextMenu(tableView, 0);
            SimulateEvents.fireItemMenu(tableView, 0, 0);
            SimulateEvents.setSpinner(spinner,30);
            SimulateEvents.clickOn(button_send);
            SimpleIntegerProperty simpleIntegerProperty= (SimpleIntegerProperty) button_send.getUserData();
        simpleIntegerProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Assertions.assertEquals(201,newValue);
            }
        });







        });

    }

    @Test
    void test(FxRobot robot){
        Platform.runLater(()->{



        });
        robot.sleep(40000);


    }
}