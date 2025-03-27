package pharma.config;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import org.controlsfx.control.SearchableComboBox;

import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.partitioningBy;

public class CustomDialog<T> extends Dialog<T> {
    private final VBox vbox;
    private  Button button_ok;
    private ButtonType okButtonType;
    public enum Validation{Email,Password,Vat,Double_Digit,Lotto_code}
    public enum  Mode{Horizontal,Vertical}
    public ObservableList<Control> getControlList() {
        return controlList;
    }

    private ObservableList<Control> controlList;
    public CustomDialog(String content) {
        super();
        controlList=FXCollections.observableArrayList();
        getDialogPane().getStyleClass().add("custom_dialog");
        setTitle(content);
        getDialogPane().setHeaderText("CustomDialog");
        vbox = new VBox();
       vbox.setSpacing(20);
     this.getDialogPane().setPrefHeight(400);
     this.getDialogPane().setPrefWidth(400);
        this.getDialogPane().setContent(vbox);



        okButtonType = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE); // inizializzo il bottone ok
       this.getDialogPane().getButtonTypes().addAll(okButtonType,ButtonType.CANCEL); // Lo aggiungo al Dialog
        button_ok = (Button) getDialogPane().lookupButton(okButtonType);
        initModality(Modality.APPLICATION_MODAL);

        get_event();

    }
    public ButtonType getButton_click(){
        return okButtonType;

    }

    public Button getButtonOK() {
        return button_ok;
    }

    public VBox getVbox() {
        return vbox;
    }


    private void get_event(){
        button_ok.addEventFilter(ActionEvent.ACTION, event -> {

            if (!button_ok.isFocused()) {
                event.consume(); // Prevent the action from firing when losing focus
            }

            if (controlList.isEmpty()) {
                return;
            }

            boolean validate;
            validate = controlList.stream().anyMatch(couple -> {
                if (couple instanceof TextField textField) {
                    if (textField.getText().isEmpty()) {

                        return true;
                    } else if (textField.getId() != null) {

                        return  !InputValidation.get_validation(textField.getId().split("-")[0], textField.getText());

                    }
                    return false;
                } else if (couple instanceof ChoiceBox) {


                    ChoiceBox<T> choiceBox = (ChoiceBox<T>) couple;
                    return choiceBox.getSelectionModel().isEmpty();
                }else if(couple instanceof DatePicker){
                    DatePicker datePicker=(DatePicker)couple;
                    return datePicker.getValue() == null;
                }
                else if(couple instanceof SearchableComboBox){

                    SearchableComboBox<T> searchableComboBox=(SearchableComboBox<T>) couple;
                    return searchableComboBox.getSelectionModel().isEmpty();
                }else if( couple instanceof  TextFieldComboBox){
                        TextFieldComboBox<T> textFieldComboBox=(TextFieldComboBox<T>) couple;
                        return  textFieldComboBox.getChoiceBox().getSelectionModel().isEmpty();

                }
               else if(couple instanceof TableView<?>){
                   TableView<?> tableView=(TableView<?>) couple;
                   return  tableView.getItems().isEmpty();

                }
                return false;
            });

            if (validate) {
                Utility.create_alert(Alert.AlertType.WARNING, "Attenzione!", " Riempire tutti campi!");
                event.consume();
            }

        });




    }

    public ToggleGroup add_radios(List<String> list,Mode mode){

        ToggleGroup group=new ToggleGroup();

        Pane pane=mode.equals(Mode.Vertical)?new VBox(15): new HBox(15);

        list.forEach(value-> {
            RadioButton radioButton = new RadioButton(value);
            pane.getChildren().add(radioButton);
            radioButton.setToggleGroup(group);
        });

        vbox.getChildren().add(pane);

        return group;
    }
    public Spinner<Integer> add_spinner() {
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));
        spinner.setEditable(true);
        vbox.getChildren().add(spinner);
        controlList.add(spinner);

        return spinner;

    }
    public TableView<T> add_table(){
        TableView<T> tableView=new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        vbox.getChildren().add(tableView);

        controlList.add(tableView);
        return  tableView;

    }
    public ChoiceBox<T> add_choiceBox(T value){
        ChoiceBox<T> choiceBox=new ChoiceBox<>();
        vbox.getChildren().add(choiceBox);
        choiceBox.setValue(value);
        choiceBox.setPrefWidth(500);
        choiceBox.setStyle("-fx-font-size: 16px");
        controlList.add(choiceBox);
        return  choiceBox;
    }
    public Label add_label(String text){
        Label label=new Label(text);
        label.setFont(new Font(16));
        vbox.getChildren().add(label);
        return label;
    }
    public Label add_label(String text,HBox hBox){
        Label label=new Label(text);
        label.setFont(new Font(16));
        hBox.getChildren().add(label);
        return label;
    }
    public TextField createLabeledTextField(String labelText, String result, HBox container_label,HBox container_text) {
        add_label(labelText, container_label);
        TextField textField = add_text_field(container_text);
        textField.setText(result);
        textField.setDisable(true);
        return textField;
    }

    public TextField add_text_field(String placeholder) {

        TextField field = new TextField();
        field.setPromptText(placeholder);
        vbox.getChildren().add(field);
        field.setPadding(new Insets(10, 10, 10, 10));
        field.setFont(new Font("Arial", 15));
        controlList.add(field);

        return field;
    }
    public TextField add_text_field(HBox hBox) {

        TextField field = new TextField();

        hBox.getChildren().add(field);
        field.setPadding(new Insets(10, 10, 10, 10));
        field.setFont(new Font("Arial", 15));
        controlList.add(field);

        return field;
    }
    public HBox add_hbox(double spacing){

        HBox hBox=new HBox(spacing);
        vbox.getChildren().add(hBox);
        return  hBox;
    }

    public DatePicker add_calendar(){
        DatePicker datePicker=new DatePicker();
        controlList.add(datePicker);
        vbox.getChildren().add(datePicker);
        datePicker.getStyleClass().add("datepicker");
        return datePicker;
    }
    public SearchableComboBox add_SearchComboBox (T value){
    SearchableComboBox<T> searchableComboBox=new SearchableComboBox<>();
        vbox.getChildren().add(searchableComboBox);
        searchableComboBox.setValue(value);
        controlList.add(searchableComboBox);
        return searchableComboBox;

    }
    public SearchableComboBox add_SearchComboBox (String value){
        SearchableComboBox<String> searchableComboBox=new SearchableComboBox<>();
        vbox.getChildren().add(searchableComboBox);
        searchableComboBox.setValue(value);
        controlList.add(searchableComboBox);
        return searchableComboBox;

    }
    public <K> TextFieldComboBox add_combox_search_with_textfield(ObservableList<K> value_control){
       TextFieldComboBox<K> textFieldComboBox=new TextFieldComboBox<>(value_control);
       vbox.getChildren().add(textFieldComboBox);
       controlList.add(textFieldComboBox);
       return  textFieldComboBox;


        
    }

    public Button  addButton(String value){
        Button button=new Button(value);
            vbox.getChildren().add(button);
            return button;

    }
    public List<Button> add_buttons_horizontal(List<String>list) {
            List<Button> buttons=new ArrayList<>();
            HBox hBox=new HBox();
            list.forEach( value->{

            Button button=new Button(value);
            buttons.add(button);


            });
            hBox.getChildren().addAll(buttons);
            vbox.getChildren().add(hBox);
            return  buttons;
    }

    /*
    public void  add_Button(String type_event, T value_init, ObservableList<T> value_control){
        Button button=new Button();
        Utility.create_btn(button,"add.png");
        vbox.getChildren().add(button);

        if(type_event.equals("ComboBox")){
            button.setOnAction((event -> {

                        Platform.runLater(() -> {
                                    getDialogPane().getScene().getWindow().sizeToScene();
                                });
             ComboBox<T> comboBox=add_combox_search_with_textfield(value_control);
            comboBox.valueProperty().addListener(new ChangeListener<T>() {
                @Override
                public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                    System.out.println("olvalue: "+oldValue+" new value: "+newValue);
                }
            });

            }));
        }
        SimulateEvents.clickOn(button);
    }

     */
    public TextField add_text_field_with_validation(String value,Validation validation) {
        TextField field = new TextField();
        field.setPromptText(value);
        switch (validation){
            case Email:{
                field.setId("Email"+"-"+ UUID.randomUUID());
                break;
            }
            case Password:{
                field.setId("Password"+"-"+UUID.randomUUID());
                break;
            }
            case Vat:{
                field.setId("Vat"+"-"+UUID.randomUUID());
                break;
            }
            case Double_Digit:{
                System.out.println("digit");
                field.setId(Validation.Double_Digit+"-"+UUID.randomUUID());
                break;
            }
            case Lotto_code:{

                field.setId("Lotto_code"+"-"+UUID.randomUUID());
                break;
            }

        }
        controlList.add(field);
        vbox.getChildren().add(field);

        field.setPadding(new Insets(10, 10, 10, 10));
        field.setFont(new Font("Arial", 16));
       return  field;
    }



}

