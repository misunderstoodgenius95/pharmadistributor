package pharma.Control;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.javafxlib.test.SimulateEvents;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


    @Test
    void test(){
        ObservableList<String> obs= FXCollections.observableArrayList("hello","ciao","hello");
        List<ChoiceBox<String>> choiceBoxes=new ArrayList<>();
        ChoiceBox<String> choiceBox_1=new ChoiceBox<>(obs);
        SimulateEvents.simulate_selected_items(choiceBox_1,"ciao");
        choiceBoxes.add(choiceBox_1);
        ChoiceBox<String> choiceBox_2=new ChoiceBox<>(FXCollections.observableArrayList("One","Two","Three"));
      //  choiceBoxes.add(choiceBox_2);
       // SimulateEvents.simulate_selected_items(choiceBox_2,"Three");
        System.out.println();
        boolean value=choiceBoxes.stream().anyMatch(choice -> choice.getValue() == null);
        System.out.println(value);
    }
    @Test
    void test_stream(){
        ObservableList<String> obs= FXCollections.observableArrayList("hello","ciao","hello");
        List<ChoiceBox<String>> choiceBoxes=new ArrayList<>();
        ChoiceBox<String> choiceBox=new ChoiceBox<>(obs);
        SimulateEvents.simulate_selected_items(choiceBox,"ciao");
        choiceBoxes.add(choiceBox);
        ChoiceBox<String> choice_me=new ChoiceBox<>(FXCollections.observableArrayList("One","Two","Three"));
        choiceBoxes.add(choice_me);
        SimulateEvents.simulate_selected_items(choiceBox,"Three");
        boolean value=choiceBoxes.stream().noneMatch(choice -> choice.getValue() == null);
        System.out.println(value);
    }
    @Test
    public void test_me(){


        String password="Password"+"-"+ UUID.randomUUID();

        System.out.println(password.split("-")[0]);
    }





}
