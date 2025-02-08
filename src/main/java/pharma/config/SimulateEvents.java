package pharma.config;



import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


public class SimulateEvents {

    private SimulateEvents(){


    }

    public  static  void clickOn(Control control) {
        control.fireEvent(new ActionEvent());

    }


    public static void clickOnButton(String query, Scene scene){

       Button button=(Button) scene.lookup(query);
       button.fire();
       if(button.getParent() instanceof AnchorPane) {
           System.out.println("ok");
       }else{
           System.out.println("nok");
       }


    }
    public static  void openDatePicker(DatePicker datePicker){
        datePicker.show();
    }
    public static  void writeOn(String query,String text, Scene scene){
        TextField field= (TextField) scene.lookup(query);
        field.setText(text);

    }
    /**
     *
     *
     * @param choiceBox List that can simulate
     * @param value  selected value  that it simulate choice of user.

     */
    public static <T> void simulate_selected_items(ChoiceBox<T> choiceBox,T value){
        if(choiceBox==null || value==null)
        {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        choiceBox.getSelectionModel().select(value);
    }
    public static  void writeOn(TextField textField,String text){
        if(text.isEmpty() || textField==null){
            throw new IllegalArgumentException("Argoument cannoot be null");
        }
        textField.setText(text);

    }


}
