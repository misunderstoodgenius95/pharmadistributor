package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Handler.DialogHandler;
import pharma.Model.FieldData;
import pharma.Stages;
import pharma.javafxlib.test.SimulateEvents;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(ApplicationExtension.class)
class LottiTest {

    private FXMLLoader loader;

    @Start
    public void start(Stage primaryStage) throws IOException {
        Stages stage = new Stages();
        loader=stage.load("/subpanel/lotti.fxml");

        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.show();


    }
    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{
            Button button= robot.lookup("#btn_id_add").queryButton();
            SimulateEvents.clickOn(button);
            Lotti lotti=loader.getController();
            DialogHandler<FieldData> dialogHandler=lotti.getDialogHandler();
            List<Control> list_combo=dialogHandler.getControlList().stream().filter(control-> control instanceof SearchableComboBox).toList();
    /*        SearchableComboBox<FieldData> searchableComboBox = (SearchableComboBox<FieldData>) list_combo.getFirst();
            searchableComboBox.setValue(searchableComboBox.getItems().get(1));*/
            List<Control> c=dialogHandler.getControlList().stream().filter(control-> control instanceof DatePicker).toList();
           DatePicker  datePicker_1= (DatePicker) c.get(0);
             DatePicker datePicker_2=(DatePicker) c.get(1);
            datePicker_1.setValue(LocalDate.of(2023,1,10));
            datePicker_2.setValue(LocalDate.of(2025,1,10));
            List<Control>textField=dialogHandler.getControlList().stream().filter(control-> control instanceof TextField).toList();
            List<Control> list_spinner=dialogHandler.getControlList().stream().filter(control-> control instanceof Spinner).toList();
            System.out.println("size"+list_spinner.size());
            Spinner<Integer> spinner=(Spinner<Integer>) list_spinner.getFirst();
            SimulateEvents.setSpinner(spinner,20);
            Spinner<Integer> spinner_qty= (Spinner<Integer>) list_spinner.get(1);
            SimulateEvents.setSpinner(spinner_qty,200);
            SimulateEvents.writeOn((TextField) textField.getFirst(),"b8188j");
            SimulateEvents.writeOn((TextField) textField.get(1),"10.50");
            dialogHandler.execute();
           // SimulateEvents.clickOn(dialogHandler.getButtonOK());



        });
       robot.sleep(400000000);

    }

    @Test
    public void check(FxRobot robot){
        Platform.runLater(()->{


        });
        robot.sleep(4000000);
    }

}