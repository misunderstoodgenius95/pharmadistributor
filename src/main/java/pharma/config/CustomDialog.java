package pharma.config;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomDialog<T> extends Dialog<T> {
    private final VBox vbox;
    private  Button button_ok;
    private ButtonType okButtonType;
    public enum Validation{Email,Password,Vat}
    private List<Control> controlList;
    public CustomDialog(String content) {
        super();
        controlList=new ArrayList<>();
        getDialogPane().getStyleClass().add("custom_dialog");
        setTitle(content);
        getDialogPane().setHeaderText("CustomDialog");
        vbox = new VBox();
       vbox.setSpacing(20);
     this.getDialogPane().setPrefHeight(400);
     this.getDialogPane().setPrefWidth(400);

        this.getDialogPane().setContent(vbox);
        ObservableList<Control> controlObservableList= FXCollections.observableList(controlList);


        okButtonType = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE); // inizializzo il bottone ok
       this.getDialogPane().getButtonTypes().addAll(okButtonType,ButtonType.CANCEL); // Lo aggiungo al Dialog
        button_ok = (Button) getDialogPane().lookupButton(okButtonType);
        initModality(Modality.APPLICATION_MODAL);
        get_event();

    }
    public ButtonType getButton_click(){
        return okButtonType;

    }
    private void get_event(){
        button_ok.addEventFilter(ActionEvent.ACTION, event -> {
            if (controlList.isEmpty()) {
                return;
            }

            boolean validate;
            validate = controlList.stream().anyMatch(couple -> {
                if (couple instanceof TextField textField) {
                    if (textField.getText().isEmpty()) {
                        return true;
                    } else if (textField.getId() != null) {
                        return !InputValidation.get_validation(textField.getId().split("-")[0], textField.getText());

                    }
                    return false;
                } else if (couple instanceof ChoiceBox<?>) {
                    ChoiceBox<T> choiceBox = (ChoiceBox<T>) couple;
                    return choiceBox.getValue() == null;
                }
                return false;
            });

            if (validate) {
                Utility.create_alert(Alert.AlertType.WARNING, "Attenzione!", " Riempire tutti campi!");
                event.consume();
            }
        });




    }
    public Spinner<Integer> add_spinner() {
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));
        spinner.setEditable(true);
        vbox.getChildren().add(spinner);
        controlList.add(spinner);

        return spinner;

    }

    public TextField add_text_field(String placeholder) {

        TextField field = new TextField();
        field.setPromptText(placeholder);
        vbox.getChildren().add(field);
        field.setPadding(new Insets(10, 10, 10, 10));
        field.setFont(new Font("Arial", 20));
        controlList.add(field);

        return field;
    }
    public TextField add_text_field_with_validation(String value,Validation validation) {

        TextField field = new TextField();
        controlList.add(field);

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

        }

        vbox.getChildren().add(field);
        field.setPadding(new Insets(10, 10, 10, 10));
        field.setFont(new Font("Arial", 20));
       return  field;
    }

















}

