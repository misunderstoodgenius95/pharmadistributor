package pharma.config;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import pharma.Model.FieldData;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CustomDialog<T> extends Dialog<T> {

    private final VBox vbox;
    private ButtonType okButtonType;
    private List<TextField> textFieldList;
    private List<Spinner<Integer>> spinnerList;
   private  List<ChoiceBox<FieldData>> choiceBoxes;

    public CustomDialog(CustomDomainBuilder customDomainBuilder) {

        super();
    this.vbox=customDomainBuilder.vbox;
    this.textFieldList=customDomainBuilder.textFieldList;
    this.spinnerList=customDomainBuilder.spinnerList;
    this.choiceBoxes=customDomainBuilder.list_choice;
        this.getDialogPane().setPrefHeight(400);
        this.getDialogPane().setPrefWidth(400);


        okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE); // inizializzo il bottone ok
        this.getDialogPane().getButtonTypes().addAll(okButtonType,ButtonType.CANCEL); // Lo aggiungo al Dialog
        initModality(Modality.APPLICATION_MODAL);
generate_event();
    }
    private  void generate_event(){
        boolean result=false;
     if(textFieldList.isEmpty()){
        result=choiceBoxes.stream().noneMatch(choice ->choice.getValue()==null);
    }
     else if(choiceBoxes.isEmpty()){

         result=textFieldList.stream().noneMatch(textField -> textField.getText()==null);
     }
     else{
         result=Stream.concat(choiceBoxes.stream().map(ChoiceBox::getValue),
                 textFieldList.stream().map(TextField::getText)
                 ).noneMatch(Objects::isNull);
     }



        
        Button button_ok=(Button)getDialogPane().lookupButton(okButtonType);
        button_ok.addEventFilter(ActionEvent.ACTION,event -> {






        });

    }


     public static   class CustomDomainBuilder {
        private final  String content;
        private  VBox vbox;
         private List<TextField> textFieldList;
         private List<Spinner<Integer>> spinnerList;
        private List<ChoiceBox<FieldData>> list_choice;
        private CustomDomainBuilder(String content) {
            vbox=new VBox();
            vbox.setSpacing(20);
            this.content=content;
        }
        public  CustomDomainBuilder  getBuilder(String content){

            return  new CustomDomainBuilder(content);

        }
         public CustomDomainBuilder add_spinner(int min,int max) {
             Spinner<Integer> spinner = new Spinner<>();
             spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max));
             spinner.setEditable(true);
             spinnerList.add(spinner);
             vbox.getChildren().add(spinner);
             return this;

         }


         public CustomDomainBuilder add_text_field(String placeholder) {
             TextField field = new TextField();
             field.setPromptText(placeholder);
             textFieldList.add(field);
             vbox.getChildren().add(field);
             field.setPadding(new Insets(10, 10, 10, 10));
             field.setFont(new Font("Arial", 20));
             return this;
         }
         public CustomDomainBuilder add_choice_box(ObservableList<FieldData> obs){
            ChoiceBox<FieldData> fieldDataChoiceBox=new ChoiceBox<>(obs);
           list_choice.add(fieldDataChoiceBox);
            return this;
         }
         public CustomDialog<FieldData> build(){
            return new CustomDialog<>(this);

         }








    }


}
