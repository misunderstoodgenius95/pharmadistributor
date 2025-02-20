package pharma.Handler;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.controlsfx.control.SearchableComboBox;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import pharma.Model.FieldData;
import pharma.config.*;
import pharma.dao.LottiDao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrdiniHandler  extends DialogHandler{
    private DatePicker date_order;
    private Spinner<Integer> spinner_vat;
    private Button button_add_lots;
    private  Button button_remove_lots;
    private  ObservableList<TextFieldComboBox> list_combomulti;
    private  ObservableList<FieldData> list_remove_fd;
    private  ObservableList<FieldData> list_current_fd;
    public OrdiniHandler( LottiDao lottiDao) {
        super("Aggiungi Ordini", new PopulateChoice(lottiDao));

    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initialize(PopulateChoice populateChoice) {
        list_combomulti=FXCollections.observableArrayList();
        list_remove_fd=FXCollections.observableArrayList();
        list_current_fd=FXCollections.observableArrayList(populateChoice.populate("lotto"));
        list_multichange();
        change_list_remove();
        setMarginButtons();
        add_label("Inserisci Data");
        date_order=add_calendar();
        add_label("Clicca per aggiungere più lotti");


        List<Button> list_buttons=add_buttons_horizontal(new ArrayList<>(Arrays.asList("plus","minus")));

        button_add_lots=list_buttons.stream().filter(button -> button.getText().equals("plus")).toList().getFirst();
        add_action_add_lots(list_current_fd);
        button_remove_lots= list_buttons.stream().filter(button -> button.getText().equals("minus")).toList().getFirst();

        Utility.add_iconButton(button_remove_lots,FontAwesomeSolid.MINUS);
        remove_action_minus_lots();
        setSpaceButton();
        Utility.create_btn(button_add_lots,"add.png");
        SimulateEvents.clickOn(button_add_lots);

    }

    @Override
    protected FieldData get_return_data() {
        return null;
    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        return false;
    }

    private void   add_action_add_lots(ObservableList<FieldData> observableList_lots){

        button_add_lots.setOnAction(event -> {
            Platform.runLater(() -> {
                getDialogPane().getScene().getWindow().sizeToScene();
            });
            TextFieldComboBox<FieldData> textFieldComboBox = add_combox_search_with_textfield(observableList_lots);
            list_combomulti.add(textFieldComboBox);
            textFieldComboBox.setConvert(new ComboxLotsConvert());

        });
    }

    private void list_multichange() {
        list_combomulti.addListener((ListChangeListener<TextFieldComboBox>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    TextFieldComboBox<FieldData> fd = change.getAddedSubList().getFirst();
                    change_listener_combo(fd.getComboBox());
                }
            }
        });
    }

    /*Method for delete element that is selected
     * if the method is inserted into list_remove we can add to list_current
     */
    private  void change_list_remove(){
        list_remove_fd.addListener(new ListChangeListener<FieldData>() {
            @Override
            public void onChanged(Change<? extends FieldData> change) {
                while(change.next()){
                    if(change.wasAdded()){
                        FieldData fieldData=change.getAddedSubList().getFirst();
                        list_current_fd.remove(fieldData);

                    }
                    else if(change.wasRemoved()){
                        FieldData  fieldData=change.getRemoved().getFirst();
                        list_current_fd.add(fieldData);
                    }


                }
            }
        });
    }

    private  void change_listener_combo(ComboBox<FieldData> comboBox){
        comboBox.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) ->{
            System.out.println("old: "+comboBox.getConverter().toString(oldValue)+" new: "+comboBox.getConverter().toString(newValue));


             if(oldValue!=null && newValue!=null){

                 list_remove_fd.remove(oldValue);
                 list_combomulti.stream().filter(textFieldComboBox -> !textFieldComboBox.getComboBox().equals(comboBox)).
                         forEach(textFieldComboBox -> {
                             textFieldComboBox.getComboBox().getItems().add(oldValue);
                         });
            }
            list_remove_fd.add(newValue);
            // we Can delete this FieldData to the all the list of Combox that is different to the selected
            list_combomulti.stream().filter(textFieldComboBox -> !textFieldComboBox.getComboBox().equals(comboBox)).
                    forEach(textFieldComboBox -> {
                        textFieldComboBox.getComboBox().getItems().remove(newValue);

                    });




        });


    }
    private void   remove_action_minus_lots(){
        button_remove_lots.setOnAction(event -> {

            if(list_combomulti.size()>1){
               TextFieldComboBox<FieldData> fd_c=list_combomulti.getLast();

                getVbox().getChildren().remove(fd_c);
                getControlList().remove(fd_c);
                list_combomulti.remove(fd_c);

            }
        });


    }
    private  void setMarginButtons(){
        ButtonType button_cancel=getDialogPane().getButtonTypes().get(1);
        Button cancelBtn = (Button) getDialogPane().lookupButton(button_cancel);
        HBox.setMargin(getButton(), new Insets(0, 0, 20, 0));
        HBox.setMargin(cancelBtn, new Insets(0, 0, 20, 0));
    }
    private  void setSpaceButton(){
        HBox hBox= (HBox) button_remove_lots.getParent();
        hBox.setSpacing(400);
    }






}
