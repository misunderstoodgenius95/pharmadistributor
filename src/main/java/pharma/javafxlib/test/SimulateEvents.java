package pharma.javafxlib.test;



import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.SearchableComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class SimulateEvents {

    private static final Logger log = LoggerFactory.getLogger(SimulateEvents.class);

    private SimulateEvents() {


    }

    public static void clickOn(Control control) {
        Platform.runLater(() -> control.fireEvent(new ActionEvent()));

    }

    // because we are firing the event directly at the button, not at a specific pixel.
    MouseEvent clickEvent = new MouseEvent(
            MouseEvent.MOUSE_CLICKED, // Event Type
            0, 0,                     // Local coordinates (relative to button)
            0, 0,                     // Scene coordinates
            MouseButton.PRIMARY,      // Which button was clicked
            1,                        // Click count
            false, false, false, false, // Modifier keys (shift, ctrl, alt, meta)
            true,                     // Primary button down
            false,                    // Middle button down
            false,                    // Secondary button down
            false,                    // Is synthesized
            false,                    // Is popup trigger
            false, null                      // PickResult
    );

    public static void fireMouseClick(DatePicker datePicker) {

        MouseEvent clickEvent = new MouseEvent(
                MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0,
                MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null
        );
        Platform.runLater(() -> Event.fireEvent(datePicker, clickEvent));

    }
    public boolean isVisible(Control control){

         return control.isVisible();
    }


    public static void clickOnButton(String query, Scene scene) {

        Button button = (Button) scene.lookup(query);
        Platform.runLater(button::fire);
        if (button.getParent() instanceof AnchorPane) {
            System.out.println("ok");
        } else {
            System.out.println("nok");
        }
    }

    public static void openContextMenu(TableView<FieldData> tableview, int row_number) {


        tableview.layout(); // Ensure the TableView has laid out its children

        TableRow<FieldData> tableRow = (TableRow<FieldData>) tableview.lookupAll(".table-row-cell")
                .stream()
                .filter(node -> node instanceof TableRow)
                .skip(row_number).
                findFirst().orElseThrow(() -> new IllegalArgumentException("TableRow is not found!"));

        // Obtain the coordinate for point 0,0 of RowTable
        double point_x = tableRow.localToScene(0, 0).getX();
        double point_y = tableRow.localToScene(0, 0).getY();
        SimulateEvents.RightMouseClickMouse(tableRow);
        WaitForAsyncUtils.waitForFxEvents();
        tableRow.getContextMenu().show(tableRow, point_x, point_y);
/*
           MenuItem menuItem = tableRow.getContextMenu().getItems().getFirst();

           menuItem.fire();
           WaitForAsyncUtils.waitForFxEvents();
           tableRow.getContextMenu().hide();
*/


    }

    public static void fireItemMenu(TableView<FieldData> tableView, int num_row, int num_voice) {


        TableRow<FieldData> tableRow = (TableRow<FieldData>) tableView.lookupAll(".table-row-cell")
                .stream()
                .filter(node -> node instanceof TableRow)
                .skip(num_row).
                findFirst().orElseThrow(() -> new IllegalArgumentException("TableRow is not found!"));
        if (tableRow.getContextMenu() != null) {

            ContextMenu contextMenu = tableRow.getContextMenu();
            if (contextMenu.getItems().isEmpty()) {
                throw new IllegalArgumentException("Context Menu Empty");

            }
            if (num_voice < 0 || num_voice >= contextMenu.getItems().size()) {
                throw new IllegalArgumentException("Index Out bound");

            } else {
                contextMenu.getItems().get(num_voice).fire();
                tableRow.getContextMenu().hide();

            }
        }


    }


    public static <T> void showControl(ComboBoxBase<T> comboBoxBase) {
        comboBoxBase.show();


    }

    public static <T> void showControl(ChoiceBox<T> choiceBox) {

        choiceBox.show();

    }

    public static void openDatePicker(DatePicker datePicker) {
        datePicker.show();
    }

    public static void writeOn(String query, String text, Scene scene) {
        TextField field = (TextField) scene.lookup(query);
        field.setText(text);

    }

    /**
     * @param choiceBox List that can simulate
     * @param value     selected value  that it simulate choice of user.
     */
    public static <T> void simulate_selected_items(ChoiceBox<T> choiceBox, T value) {
        if (choiceBox == null || value == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        choiceBox.getSelectionModel().select(value);
    }

    public static void writeOn(TextField textField, String text) {
        if (text.isEmpty() || textField == null) {
            throw new IllegalArgumentException("Argoument cannoot be null");
        }
        textField.setText(text);

    }

    public static void keyPress(Control control, KeyCode keyCode) {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED,
                "", "",
                keyCode,
                false, false, false, false);
        control.fireEvent(keyEvent);


    }


    public static void RightMouseClickMouse(Control control) {
        MouseEvent rightClickEvent = new MouseEvent(
                MouseEvent.MOUSE_CLICKED,    // Event type
                0, 0,                        // X, Y (local coordinates)
                0, 0,                        // Screen X, Y
                MouseButton.SECONDARY,        // Mouse button (SECONDARY = right click)
                1,                            // Click count
                false, false, false, false,  // Modifiers (Shift, Ctrl, Alt, Meta)
                false,                        // Primary button down (left click)
                false,                        // Middle button down
                true,                         // Secondary button down (right click)
                false,                        // Synthesized event (e.g., touch-to-mouse)
                true,                         // Popup trigger (triggers context menu)
                false,                        // Still since press (for drag events)
                null                          // PickResult (optional)
        );
        control.fireEvent(rightClickEvent);
    }

    public static void PrimaryMouseClickMouse(Control control) {
        MouseEvent primaryMouseClicked = new MouseEvent(
                MouseEvent.MOUSE_CLICKED,    // Event type
                0, 0,                        // X, Y (local coordinates)
                0, 0,                        // Screen X, Y
                MouseButton.PRIMARY,        // Mouse button
                1,                            // Click count
                false, false, false, false,  // Modifiers (Shift, Ctrl, Alt, Meta)
                true,                        // Primary button down (left click)
                false,                        // Middle button down
                false,                         // Secondary button down (right click)
                false,                        // Synthesized event (e.g., touch-to-mouse)
                true,                         // Popup trigger (triggers context menu)
                false,                        // Still since press (for drag events)
                null                          // PickResult (optional)
        );
        control.fireEvent(primaryMouseClicked);
    }

    public static void setSpinner(Spinner<Integer> spinner, int value) {
        Platform.runLater(() -> spinner.getValueFactory().setValue(value));
    }
    public static void setSpinnerDouble(Spinner<Double> spinner, double value) {
        Platform.runLater(() -> spinner.getValueFactory().setValue(value));
    }


    public static <T> void setFirstElementSearchableBox(SearchableComboBox<T> searchableBox) {
        searchableBox.setValue(searchableBox.getItems().getFirst());
    }

    public static <T> void setFirstElementChoiceBox(ChoiceBox<T> choiceBox) {
        setNElementChoiceBox(choiceBox, 0);

    }

    public static <T> void setNElementChoiceBox(ChoiceBox<T> choiceBox, int n) {
        if (choiceBox == null) {
            throw new IllegalArgumentException("Choicebox is null");

        }
        if (choiceBox.getItems().isEmpty()) {

            throw new IllegalArgumentException("Choicebox empty");
        }

        if (n < 0 || n >= choiceBox.getItems().size()) {
            throw new IndexOutOfBoundsException("Index " + n + " is out of bounds for ChoiceBox size " + choiceBox.getItems().size());
        }


        choiceBox.setValue(choiceBox.getItems().get(n));


    }

    public static <S> void setCheckBox(ObservableMap<S, CheckBox> map, S value) {

        if (map.containsKey(value)) {
            CheckBox checkBox = map.get(value);
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

    public static <S> void setRadioBox(ObservableMap<S, RadioButton> map, S value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }

        map.addListener((MapChangeListener<S, RadioButton>) change -> {
            if (change.wasAdded()) {
                if (change.getKey().equals(value)) {
                    RadioButton radioButton = change.getValueAdded();
                    radioButton.setSelected(true);
                    radioButton.fireEvent(new ActionEvent());

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

/*    public static boolean Check_last_elem_add(ObservableList<FieldData> list){
        AtomicBoolean atomicBoolean=new AtomicBoolean(false);
        list.addListener((ListChangeListener<FieldData>) c -> {
       *//*     while (c.next()) {*//*
                if (c.wasRemoved()) {

                    System.out.println("removed");
                    atomicBoolean.set(true);
                }
           // }
        });
        System.out.println(list.size());
        return  atomicBoolean.get();

    }
    public static boolean Check_last_elem_added(ObservableList<FieldData> list){
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            future.get(2, TimeUnit.SECONDS);


             return SimulateEvents.Check_last_elem_add(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }*/
    public static int  CheckRemovedElemList(ObservableList<FieldData> list){
        AtomicInteger value = new AtomicInteger();
        PauseTransition pauseTransition=new PauseTransition(Duration.seconds(2));
        pauseTransition.setOnFinished(event ->{
            value.set(list.size());
            System.out.println("list"+list.size());
        });
        pauseTransition.play();
        return value.get();

    }





}
