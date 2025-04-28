package pharma.config;



import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jdk.jshell.spi.SPIResolutionException;
import org.controlsfx.control.SearchableComboBox;

import java.net.Inet4Address;
import java.security.PublicKey;


public class SimulateEvents {

    private SimulateEvents(){


    }

    public  static  void clickOn(Control control) {
        control.fireEvent(new ActionEvent());

    }
public static  void fireMouseClick(DatePicker datePicker) {

        MouseEvent clickEvent = new MouseEvent(
                MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0,
                MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null
        );
        Event.fireEvent(datePicker, clickEvent);

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
    public static <T> void showControl(ComboBoxBase<T> comboBoxBase){

        comboBoxBase.show();

    }
    public static <T> void showControl(ChoiceBox<T> choiceBox){

        choiceBox.show();

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
    public static  void keyPress( Control control,KeyCode keyCode){
        KeyEvent keyEvent=new KeyEvent(KeyEvent.KEY_PRESSED,
                "","",
                keyCode,
                false,false,false,false);
        control.fireEvent(keyEvent);
        System.out.println("fire key ");

    }
    public static  void setSpinner(Spinner<Integer> spinner,int value){
        spinner.getValueFactory().setValue(value);
    }


}
