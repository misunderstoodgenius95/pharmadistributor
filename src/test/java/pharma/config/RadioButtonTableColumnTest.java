package pharma.config;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;
import pharma.javafxlib.test.SimulateEvents;

@ExtendWith(ApplicationExtension.class)
class RadioButtonTableColumnTest {
@Start
public void start(Stage primaryStage) {
    // Create the TableView
    TableView<Person> tableView = new TableView<>();
    ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("John Doe", "john@example.com","1"),
            new Person("Jane Smith", "jane@example.com","2"),
            new Person("Bob Johnson", "bob@example.com","3")
    );
    tableView.setItems(data);




    RadioButtonTableColumn<Person> actionColumn = new RadioButtonTableColumn<>() {
        @Override
        protected void onButtonClick(Person rowData) {
            // Custom behavior for the button click
            System.out.println("Custom action for: " + rowData.getId());
        }
    };


    // Create a custom button column

    // Add columns to the TableView

    tableView.getColumns().addAll(TableUtility.generate_column_string("Email","email"),
            TableUtility.generate_column_string("Name","name"),actionColumn);
    // Set up the scene
    VBox root = new VBox(tableView);
    Scene scene = new Scene(root, 400, 300);
    primaryStage.setTitle("TableView with Custom Button Example");
    primaryStage.setScene(scene);
    primaryStage.show();
    }
    @Test
    public void me(FxRobot robot){
    robot.sleep(4000);
    RadioButton radioButton=robot.lookup(".radio_click").queryAs(RadioButton.class);

    SimulateEvents.clickOn(radioButton);


    }





}