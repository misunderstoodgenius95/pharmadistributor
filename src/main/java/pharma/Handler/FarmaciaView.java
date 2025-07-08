package pharma.Handler;

import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import pharma.Model.FieldData;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.javafxlib.RadioOptions;
import pharma.dao.FarmaciaDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FarmaciaView  extends CustomDialog<FieldData> {
    private  ToggleGroup toggleGroup;
    private TextField field_search;
    private  FarmaciaDao farmaciaDao;
    private  ObservableList<FieldData> observableList;
    public FarmaciaView(String content, FarmaciaDao farmaciaDao, ObservableList<FieldData> observableList) {
        super(content);
        getDialogPane().setHeaderText("Ricerca Casa Farmaceutica");
        getDialogPane().setPrefWidth(600);
        getDialogPane().setPrefHeight(600);
       List<RadioOptions> list=new ArrayList<>(Arrays.asList(new RadioOptions("ragione_sociale","Anagrafica Cliente"),
                new RadioOptions("comune","Comune"),
               new RadioOptions("p_iva","Partita Iva"))
       );
        toggleGroup=add_radios(list,Mode.Horizontal);
        field_search=add_text_field("Inserisci");
        listener_radio();
        this.farmaciaDao=farmaciaDao;
        this.observableList=observableList;
        listener_btn();

    }

    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }

    public void listener_radio(){
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue instanceof  RadioButton radioButton){
                if(radioButton.getId().equals("nothing")){
                    field_search.setVisible(false);
                    field_search.setText("nothing");
                }else {
                    field_search.setText("");
                    field_search.setVisible(true);
                    field_search.setPromptText("Inserisci");
                    field_search.setPromptText(field_search.getPromptText() + " " + radioButton.getText());

                }


            }
        });
    }

    public void listener_btn(){
      getButtonOK().setOnAction(event -> {
          RadioButton radioButton=(RadioButton) toggleGroup.selectedToggleProperty().getValue();
          List<FieldData> list=farmaciaDao.findByParameter(farmaciaDao.buildQueryasParameter(radioButton.getId()),field_search.getText());
          observableList.setAll(list);

      });


    }








}
