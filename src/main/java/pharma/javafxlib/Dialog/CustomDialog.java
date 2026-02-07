package pharma.javafxlib.Dialog;

import com.dlsc.gemsfx.YearMonthPicker;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import org.controlsfx.control.SearchableComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Model.FieldData;
import pharma.config.InputValidation;

import pharma.config.Utility;
import pharma.javafxlib.Controls.TextFieldComboBox;
import pharma.javafxlib.FileChoseOption;
import pharma.javafxlib.RadioOptions;
import pharma.javafxlib.graphs.Histogram;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class CustomDialog<T> extends Dialog<T> {
    private static final Logger log = LoggerFactory.getLogger(CustomDialog.class);
    private final VBox vbox;
    private  Button button_ok;
    private ButtonType okButtonType;
    public enum Validation{Email,Password,Vat,Double_Digit,Lotto_code,Cap,lng,lat}
    public enum  Mode{Horizontal,Vertical}
    private BooleanProperty check_validate;
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
        this.check_validate=new SimpleBooleanProperty();
        okButtonType = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE); // inizializzo il bottone ok
        this.getDialogPane().getButtonTypes().addAll(okButtonType,ButtonType.CANCEL); // Lo aggiungo al Dialog
        button_ok = (Button) getDialogPane().lookupButton(okButtonType);
        button_ok.setDefaultButton(false);
        initModality(Modality.APPLICATION_MODAL);

        get_event();

    }


    public ButtonType getButton_click(){
        return okButtonType;

    }

    /**
     * Method that it used for verificate if it filled all controls.
     * @return
     */
    public boolean isCheck_validate() {
        return check_validate.get();
    }

    public BooleanProperty check_validateProperty() {
        return check_validate;
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
                    } else if (textField.getUserData() != null) {
                        FieldData fieldData= (FieldData) textField.getUserData();
                        if(fieldData!=null) {
                            boolean validation=!InputValidation.get_validation(fieldData.getNome(),textField.getText());
                            if(validation) {
                                Utility.create_alert(Alert.AlertType.WARNING, "", "Errore: "+textField.getPromptText());
                                check_validate.setValue(false);
                            }else{
                                check_validate.setValue(true);
                            }
                            return validation;
                        }

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
                    return searchableComboBox.getValue() == null;
                }else if( couple instanceof TextFieldComboBox){
                        TextFieldComboBox<T> textFieldComboBox=(TextFieldComboBox<T>) couple;
                        return  textFieldComboBox.getChoiceBox().getValue()==null;

                }
               else if(couple instanceof TableView<?>){
                   TableView<?> tableView=(TableView<?>) couple;
                   return  tableView.getItems().isEmpty();

                }
                return false;
            });

            if (validate) {
                Utility.create_alert(Alert.AlertType.WARNING, "Attenzione!", " Riempire tutti campi!");
                check_validate.setValue(false);
                event.consume();
            }else{
                check_validate.setValue(true);
            }

        });




    }
    public  <K> ListView<K> addListView(){
        ListView<K> listView=new ListView<>();
        vbox.getChildren().add(listView);
        return listView;

    }
    public TextArea addTextArea(){
        TextArea textArea=new TextArea();
        vbox.getChildren().add(textArea);
        return textArea;
    }

    public PieChart addPieChart(){
        PieChart chart=new PieChart();
        vbox.getChildren().add(chart);

        return  chart;
    }
    public FileChoseOption add_file_to_target_path(String target_path, List<FileChooser.ExtensionFilter> supported_extensions){
        Button button_insert=addButton("Inserisci File");
        Button button_upload=addButton("Carica File");
        button_upload.setDisable(true);
TextField textField=new TextField("");
getControlList().add(textField);

        AtomicReference<File> selectedFile= new AtomicReference<>();
        FileChooser fileChooser=new FileChooser();
        button_insert.setOnAction((event -> {

            fileChooser.setTitle("Inserisci File");
            button_insert.setText("File selezionato!");


            fileChooser.getExtensionFilters().addAll(supported_extensions);
         selectedFile.set(fileChooser.showOpenDialog(getDialogPane().getScene().getWindow()));
            button_upload.setDisable(false);
        }));
        button_upload.setOnAction(event -> {

            FileOutputStream fileOutputStream = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                fileOutputStream = new FileOutputStream(target_path+selectedFile.get().getName());
                bufferedInputStream = new BufferedInputStream(new FileInputStream(selectedFile.get()));
                fileOutputStream.write(bufferedInputStream.readAllBytes());

            } catch (IOException e) {
                log.info(e.getMessage());
            } finally {
                try {
                    assert fileOutputStream != null;
                    fileOutputStream.close();
                    assert bufferedInputStream != null;
                    bufferedInputStream.close();
                } catch (IOException e) {
                   log.info(e.getMessage());
                }
            }
            textField.setText("File Selezionato");
        });


        return new FileChoseOption(button_insert,button_upload,fileChooser);

    }

    public YearMonthPicker add_month_picker(){

        YearMonthPicker yearMonthPicker = new YearMonthPicker();
        vbox.getChildren().add(yearMonthPicker);
        controlList.add(yearMonthPicker);
        return yearMonthPicker;

    }
    public  WebView add_web_page(String url){
        WebView webView = new WebView();
        WebEngine engine=webView.getEngine();

        engine.load(url);

   //     engine.load("http://esamebasicalio.altervista.org/map.php?lat="+lat+"&"+"lng="+lng);
// Disable security manager for tile loading
        vbox.getChildren().add(webView);
        return webView;


    }
    public<K> TreeTableView<K> add_tree_table(){
        TreeTableView<K> treeTableView=new TreeTableView<>();
        vbox.getChildren().add(treeTableView);
        controlList.add(treeTableView);
        return treeTableView;

    }

    public  Histogram addHistogram(String labelX,String labelY,String titleHistogram){
        Histogram histogram=new Histogram(labelX,labelY,titleHistogram);
        vbox.getChildren().add(histogram.getBarChart());
        return histogram;

    }


    public  ToggleGroup add_group(){
         return new ToggleGroup();
    }
    public RadioButton add_radio_horizantal(String label,HBox hBox,ToggleGroup toggleGroup){
        RadioButton radioButton = new RadioButton(label);
        hBox.getChildren().add(radioButton);
        radioButton.setToggleGroup(toggleGroup);
        return  radioButton;

    }
    public ToggleGroup add_radios(List<RadioOptions> options, Mode mode){

        ToggleGroup group=new ToggleGroup();

        Pane pane=mode.equals(Mode.Vertical)?new VBox(15): new HBox(15);

        options.forEach(option-> {
            RadioButton radioButton = new RadioButton(option.getLabel());
            radioButton.setId(option.getId());
            radioButton.setFont(new Font(15));
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
    public Spinner<Double> add_spinner_double() {
        Spinner<Double> spinner = new Spinner<>();
       spinner.setValueFactory( new SpinnerValueFactory.DoubleSpinnerValueFactory((Double)1.1, (Double) 2000.79, (Double) 1.1, (Double) 0.1));
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
    public<K> TableView<K> add_tableCustom(){
        TableView<K> tableView=new TableView<>();
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
    public ChoiceBox<String> add_choiceBox(String value){
        ChoiceBox<String> choiceBox=new ChoiceBox<>();
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

    /**
     * This method can create an horizontal label
     * @param labelText
     * @param result
     * @param container_label
     * @param container_text
     * @return
     */
    public TextField createLabeledTextField(String labelText, String result, HBox container_label,HBox container_text) {
        add_label(labelText, container_label);
        TextField textField = add_text_field(container_text);
        textField.setText(result);
        textField.setDisable(true);
        return textField;
    }
    public TextField createLabeledTextField(String labelText, String result, GridPane gridPane,int  label_point_x,int  label_point_y, int text_point_x,int text_point_y) {
        Label label=add_label(labelText);
        TextField textField = add_text_field("");
        textField.setText(result);
        textField.setEditable(false);
        gridPane.add(label,label_point_x,label_point_y);
        gridPane.add(textField,text_point_x,text_point_y);
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
    public GridPane add_gridpane(int space){
        GridPane gridPane=new GridPane();
        gridPane.setPadding(new Insets(space));  // Space around the grid
        gridPane.setHgap(10);  // Horizontal gap between columns
        gridPane.setVgap(10);  // Vertical gap between rows

        vbox.getChildren().add(gridPane);
        return gridPane;
    }

    public DatePicker add_calendar(){
        DatePicker datePicker=new DatePicker();
        controlList.add(datePicker);
        vbox.getChildren().add(datePicker);
        datePicker.getStyleClass().add("datepicker");
        return datePicker;
    }
    public <K> SearchableComboBox add_SearchComboBox (K value){
    SearchableComboBox<K> searchableComboBox=new SearchableComboBox<>();
        vbox.getChildren().add(searchableComboBox);
       searchableComboBox.setStyle("-fx-font-size: 16px");
        searchableComboBox.setValue(value);
        controlList.add(searchableComboBox);
        return searchableComboBox;

    }
    public <K>SearchableComboBox add_SearchComboBoxs (K value){
        SearchableComboBox<K> searchableComboBox=new SearchableComboBox<>();
        vbox.getChildren().add(searchableComboBox);
        searchableComboBox.setStyle("-fx-font-size: 16px");
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
    public <K> TextFieldComboBox addComboWithTextNoValidation(ObservableList<K> value_control){
        TextFieldComboBox<K> textFieldComboBox=new TextFieldComboBox<>(value_control);
        vbox.getChildren().add(textFieldComboBox);
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
        field.setUserData(FieldData.FieldDataBuilder.getbuilder().setUUid(UUID.randomUUID()).setNome(validation.name()).build());



        controlList.add(field);
        vbox.getChildren().add(field);

        field.setPadding(new Insets(10, 10, 10, 10));
        field.setFont(new Font("Arial", 16));
       return  field;
    }



}

