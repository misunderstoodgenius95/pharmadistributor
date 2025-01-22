package pharma.oldest;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;

public class CustomDialog<T> extends Dialog<T> {
    private final VBox vbox;
    private ButtonType okButtonType;

    public CustomDialog(String content) {
        super();
        setTitle(content);
        vbox = new VBox();
       vbox.setSpacing(20);
     this.getDialogPane().setPrefHeight(400);
     this.getDialogPane().setPrefWidth(400);

        this.getDialogPane().setContent(vbox);

        okButtonType = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE); // inizializzo il bottone ok
       this.getDialogPane().getButtonTypes().addAll(okButtonType,ButtonType.CANCEL); // Lo aggiungo al Dialog
        initModality(Modality.APPLICATION_MODAL);
    }

    public Spinner<Integer> add_spinner() {
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));
        spinner.setEditable(true);
        vbox.getChildren().add(spinner);
        return spinner;

    }

    public TextField add_text_field(String placeholder) {

        TextField field = new TextField();
        field.setPromptText(placeholder);
        vbox.getChildren().add(field);
        field.setPadding(new Insets(10, 10, 10, 10));
        field.setFont(new Font("Arial", 20));
        return field;
    }


















}

