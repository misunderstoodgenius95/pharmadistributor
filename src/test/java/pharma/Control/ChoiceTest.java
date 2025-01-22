package pharma.Control;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.config.SimulateEvents;

import java.lang.management.PlatformManagedObject;
@ExtendWith(ApplicationExtension.class)
public class ChoiceTest{
    private  ChoiceBox<String> choice;
    private Button button;
    private String test;
        @Start
        public void start (Stage primaryStage) throws Exception {
            VBox vBox = new VBox();
            Scene scene = new Scene(vBox);
            choice = new ChoiceBox<>();
            button = new Button("Ciao");
            button.setOnAction(s -> {

                System.out.println("Clicked");
            });
            choice.getItems().addAll("One", "Two");
            choice.getStyleClass().add("choice_box");
            vBox.getChildren().addAll(choice, button);
            primaryStage.setScene(scene);
            primaryStage.show();


        }

        @Test
        public void testMe(){
            Platform.runLater(()->{
                //button.fire();
                SimulateEvents.simulate_selected_items(choice,"Two");



                System.out.println(choice.getValue());
            });

    }




}
