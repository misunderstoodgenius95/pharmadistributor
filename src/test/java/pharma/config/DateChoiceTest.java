package pharma.config;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;

import java.sql.Date;
import java.time.LocalDate;

@ExtendWith(ApplicationExtension.class)
public class DateChoiceTest{

private CustomDialog<FieldData> customDialog;
    private DatePicker datePicker;

    @Start
    public void start (Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
      customDialog=new CustomDialog<>("Aggiungi Lotto");
        datePicker=customDialog.add_calendar();
        customDialog.show();
    }
    @Test
    void test(FxRobot robot){

        Platform.runLater(()->{

        /*   SimulateEvents.openDatePicker(datePicker);
            System.out.println(datePicker.getValue());
           datePicker.setValue(LocalDate.now());
            System.out.println(datePicker.getValue());
            Date.valueOf(datePicker.getValue());*/
            SimulateEvents.clickOn(customDialog.getButton());
        });
        robot.sleep(40000);
    }








}
