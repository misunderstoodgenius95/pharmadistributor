package pharma.config;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import pharma.Model.FieldData;

public class TextFieldComboBox<T> extends  Control {



    private  ComboBox<T> comboBox;
    private TextField textField;
    private VBox vBox;
    private  ObservableList<T> observable;
    public TextFieldComboBox(ObservableList<T> observableList) {

        vBox=new VBox();
        observable=observableList;
        comboBox=new ComboBox<>();
        comboBox.getItems().setAll(observableList);

        textField=new TextField();
        textField.setPromptText("Cerca Lotti");
       // vBox.setPrefWidth(Double.MAX_VALUE);
        comboBox.setPrefWidth(Double.MAX_VALUE);
        setFont();


        vBox.getChildren().addAll(textField,comboBox);
        vBox.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID,new CornerRadii(3),new BorderWidths(3))));
        initialize();
    }

    private void initialize(){

        FilteredList<T> filteredList=new FilteredList<>(observable, p->true);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(value->{
                System.out.println(newValue);
                if(newValue ==null|| newValue.isEmpty()){
                    return  true;

                }
                return comboBox.getConverter().toString(value).toLowerCase().contains(newValue.toLowerCase());
            });
            comboBox.setItems(FXCollections.observableArrayList(filteredList));
           comboBox.show();

        });
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TextFieldComboBoxSkin(this);
    }


    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public void setConvert(StringConverter<T> stringConverter){
            comboBox.setConverter(stringConverter);
    }


    public VBox getvBox() {
        return vBox;
    }

    private  void setFont(){
      comboBox.setStyle("-fx-font-size: 15px");
      textField.setStyle("-fx-font-size: 15px");
  }
}
