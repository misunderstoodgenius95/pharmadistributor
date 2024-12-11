package pharma.config;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class CustomDialog<T> extends Dialog<T> {
    private final VBox vbox;
    private ButtonType okButtonType;

    public CustomDialog(String content) {
        super();
        setTitle(content);
        vbox = new VBox();
        this.getDialogPane().setContent(vbox);
        getDialogPane().getButtonTypes().addAll(okButtonType);
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
        return field;
    }


    public  Dialog<T>  get_dialog() {
return this;
    }















}

