package pharma.javafxlib.test;



import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class SimulateEvents {

    private SimulateEvents(){


    }

    public  static  void clickOn(Control control) {
        Platform.runLater(()->control.fireEvent(new ActionEvent()));

    }
public static  void fireMouseClick(DatePicker datePicker) {

        MouseEvent clickEvent = new MouseEvent(
                MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0,
                MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null
        );
     Platform.runLater(()->   Event.fireEvent(datePicker, clickEvent));

    }


    public static void clickOnButton(String query, Scene scene){

       Button button=(Button) scene.lookup(query);
       Platform.runLater(button::fire);
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


    }
    public static  void setSpinner(Spinner<Integer> spinner,int value){
       Platform.runLater(()-> spinner.getValueFactory().setValue(value));
    }
    public static <T> void  setFirstElementChoiceBox(ChoiceBox<T> choiceBox){
 setNElementChoiceBox(choiceBox,0);

    }
    public static <T> void  setNElementChoiceBox(ChoiceBox<T> choiceBox, int n){
        if(choiceBox==null){
            throw new IllegalArgumentException("Choicebox is null");

        }
        if(choiceBox.getItems().isEmpty()){

            throw new IllegalArgumentException("Choicebox empty");
        }

        if (n < 0 || n >= choiceBox.getItems().size()) {
            throw new IndexOutOfBoundsException("Index " + n + " is out of bounds for ChoiceBox size " + choiceBox.getItems().size());
        }





            choiceBox.setValue(choiceBox.getItems().get(n));





    }

    public static<S>  void setCheckBox(ObservableMap<S,CheckBox> map,  S value){

        if (map.containsKey(value)) {
           CheckBox checkBox=map.get(value);
           checkBox.setSelected(true);
           checkBox.fireEvent(new ActionEvent());
            return;
        }

            map.addListener((MapChangeListener<S, CheckBox>) change -> {
                if (change.wasAdded()) {
                    if (change.getKey().equals(value)) {
                        CheckBox checkBox = change.getValueAdded();
                        checkBox.setSelected(true);
                        checkBox.fireEvent(new ActionEvent());
                    }
                }
            });
        }



    public  static void  WaitAddedItemsTable(TableView tableView){

        try {
            WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS,()->
                    tableView.getItems().isEmpty()

            );
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
