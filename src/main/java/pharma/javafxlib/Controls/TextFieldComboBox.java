package pharma.javafxlib.Controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class TextFieldComboBox<T> extends  Control {



    private ChoiceBox choiceBox;
    private TextField textField;
    private VBox vBox;
    private  ObservableList<T> observable;
    public TextFieldComboBox(ObservableList<T> observableList) {

        vBox=new VBox();
        observable=observableList;
     choiceBox=new ChoiceBox(observableList);




        textField=new TextField();

       // vBox.setPrefWidth(Double.MAX_VALUE);
       choiceBox.setPrefWidth(Double.MAX_VALUE);
        setFont();


        vBox.getChildren().addAll(textField,choiceBox);
        vBox.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID,new CornerRadii(3),new BorderWidths(3))));
        initialize();
    }

    public TextField getTextField() {
        return textField;
    }

    public void setpromptValue(String text) {
        textField.setPromptText(text);
    }

    private void initialize(){

        FilteredList<T> filteredList=new FilteredList<>(observable, p->true);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(value-> {

                        if (newValue == null || newValue.isEmpty()) {
                            return true;

                        }
                        return value.toString().toLowerCase().contains(newValue.toLowerCase());
                    });
           choiceBox.setItems(FXCollections.observableArrayList(filteredList));
           choiceBox.show();


    });
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TextFieldComboBoxSkin(this);
    }



    public void setConvert(StringConverter<T> stringConverter){
            choiceBox.setConverter(stringConverter);
    }

public StringConverter<T> getConvert(){

       return  choiceBox.getConverter();
}
    public VBox getvBox() {
        return vBox;
    }

    private  void setFont(){
    choiceBox.setStyle("-fx-font-size: 15px");
      textField.setStyle("-fx-font-size: 15px");
  }

    public ChoiceBox getChoiceBox() {
        return choiceBox;
    }


}

