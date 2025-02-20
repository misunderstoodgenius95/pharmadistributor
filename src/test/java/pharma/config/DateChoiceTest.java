package pharma.config;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.controlsfx.control.SearchableComboBox;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.Date;
import java.time.LocalDate;

@ExtendWith(ApplicationExtension.class)
public class DateChoiceTest{

private CustomDialog<String> customDialog;
    private DatePicker datePicker;
    private SearchableComboBox<String>searchableComboBox;
    @Start
    public void start (Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
        customDialog=new CustomDialog<>("Aggiungi Lotto");
        datePicker=customDialog.add_calendar();
        searchableComboBox=customDialog.add_SearchComboBox("Value");
        customDialog.show();
    }


    @Test
    void ValidValidationDatepicker(FxRobot robot){

        Platform.runLater(()->{

          SimulateEvents.openDatePicker(datePicker);
            System.out.println(datePicker.getValue());
           datePicker.setValue(LocalDate.now());
            System.out.println(datePicker.getValue());
            Date.valueOf(datePicker.getValue());
            SimulateEvents.clickOn(customDialog.getButton());
        });
        robot.sleep(40000);
    }
    @Test
    void InValidValidationDatepicker(FxRobot robot){

        Platform.runLater(()->{

            SimulateEvents.openDatePicker(datePicker);

            SimulateEvents.clickOn(customDialog.getButton());
        });
        robot.sleep(4000);
    }




    @Test
    public void InvalidSelectionTestSearchAble(FxRobot robot){
        Platform.runLater(()->{
            customDialog.getVbox().getChildren().remove(datePicker);
            SearchableComboBox<String> s_cbox = customDialog.add_SearchComboBox("Scegli il valore");
            ObservableList<String> obs= FXCollections.observableArrayList("One","Two","Three");
            s_cbox.getItems().addAll(obs);
           SimulateEvents.clickOn(customDialog.getButton());
        });
        robot.sleep(40000);

    }



    @Test
    public void ValidSelectionTestSearchAble(FxRobot robot){
        Platform.runLater(()->{
            customDialog.getControlList().remove(datePicker);
            customDialog.getVbox().getChildren().remove(datePicker);
            searchableComboBox.getItems().addAll(FXCollections.observableArrayList("One","Two"));
            searchableComboBox.setValue("One");

                //SimulateEvents.clickOn(customDialog.getButton());



        });
       robot.sleep(4000);

    }

    @Test
    public void ValidSelectionTestSearchAbleWithShow(FxRobot robot){
        Platform.runLater(()->{
            customDialog.getControlList().remove(datePicker);
            customDialog.getVbox().getChildren().remove(datePicker);
            searchableComboBox.getItems().addAll(FXCollections.observableArrayList("One","Two"));
            SimulateEvents.showControl(searchableComboBox);
          SimulateEvents.keyPress(searchableComboBox, KeyCode.DOWN);
          SimulateEvents.keyPress(searchableComboBox,KeyCode.ENTER);
            //SimulateEvents.clickOn(customDialog.getButton());



        });
        robot.sleep(40000);

    }
    @Test
    public void testFeldLotto(FxRobot robot){
        Platform.runLater(()->{
            customDialog.getControlList().remove(datePicker);
            customDialog.getVbox().getChildren().remove(datePicker);
            customDialog.getControlList().remove(searchableComboBox);
            customDialog.getVbox().getChildren().remove(searchableComboBox);

            TextField textField=customDialog.add_text_field_with_validation("Aggiungi Lotti", CustomDialog.Validation.Lotto_code);
            SimulateEvents.writeOn(textField,"M20773");
            System.out.println(textField.getId());



            SimulateEvents.clickOn(customDialog.getButton());
        });
       robot.sleep(4000);

    }










}
