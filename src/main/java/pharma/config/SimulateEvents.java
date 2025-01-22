package pharma.config;



import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Control;


public class SimulateEvents {

    private SimulateEvents(){


    }

    public  static  void clickOn(Control control) {
        control.fireEvent(new ActionEvent());

    }
    public static <T> void simulate_selected_items(ChoiceBox<T> choiceBox,T value){
        choiceBox.getSelectionModel().select(value);
    }
}
