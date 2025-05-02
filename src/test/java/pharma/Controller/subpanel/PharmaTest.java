package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.Stages;
import pharma.javafxlib.test.SimulateEvents;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
class PharmaTest {
    private Scene scene;
    @Start
    public void start(Stage primaryStage) throws IOException {
        Stages stage = new Stages();
        Parent parent = stage.load_fxml("/subpanel/pharma.fxml");

         scene = new Scene(parent);

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    @Test
    void add_pharma_action()throws  Exception {
        FxRobot robot = new FxRobot();

        //  fxRobot.clickOn("#button_click");
        //   Button button=fxRobot.lookup("#button_click").queryAs(Button.class);
        // Button button= (Button) scene.lookup("#add_pharma_action");

        // Button button =fxRobot.lookup("#add_pharma_action").queryButton();
        Platform.runLater(() -> {
            System.out.println( Stage.getWindows());
           SimulateEvents.clickOnButton("#button_click", scene);



            //SimulateEvents.writeOn(f1,"Ciao");

        });
robot.sleep(10000);

    }
    @Test
    public void setTable(FxRobot robot){
        TableView<FieldData> tableView=robot.lookup("#table_id").queryAs(TableView.class);
        FieldData f_1=FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Bayer").setSigla("Ba").setPartita_iva("It1111").build();
        FieldData f_2=FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Novartis").setSigla("NO").setPartita_iva("IT222").build();
        FieldData f_3=FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Angelini").setSigla("ANG").setPartita_iva("IT333").build();

        ObservableList<FieldData> observableList = FXCollections.observableArrayList(f_1,f_2,f_3);
        tableView.setItems(observableList);

       robot.sleep(4000);

    }





}