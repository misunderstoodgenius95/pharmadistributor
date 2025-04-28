package pharma.Handler;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import pharma.Model.FieldData;
import pharma.config.InputValidation;
import pharma.config.Utility;
import pharma.dao.DetailDao;

import java.util.List;


public class DetailHandler {
    private TextField misure;
    private TextField unit;

    private DetailDao detailDao;
    private ListView<String> listView;
    private ChoiceBox<String> choiceBox;

    public DetailHandler(TextField misure, TextField unit, DetailDao detailDao, ListView<String> listView, ChoiceBox<String> choiceBox) {
        this.misure = misure;
        this.unit = unit;

        this.detailDao = detailDao;
        this.listView = listView;
        this.choiceBox = choiceBox;
        change_choice();
    }

    private void change_choice() {


        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ((newValue != null) && (!newValue.isEmpty()) && (!newValue.equals("Seleziona valore"))) {


                if (newValue.equalsIgnoreCase(Utility.Misura)) {
                    unit.setVisible(true);

                    misure.setPromptText("Inserisci Misura");
                    unit.setPromptText("Inserisci unità");


                } else {
                    misure.setPromptText("Inserisci nome");
                    unit.setVisible(false);

                }
                // Switch table
                detailDao.setTable_name(choiceBox.getValue());
                List<FieldData> fieldDataList = detailDao.findAll();
                listView.getItems().setAll(FXCollections.observableArrayList(listMapping(fieldDataList, choiceBox.getValue())));

            }


        });

    }

    public void setInsertHandler() {
        boolean result = false;
        FieldData fieldData = null;
        if (unit.isVisible()) {
            if ((InputValidation.filled_text(unit.getText())) && (InputValidation.validate_digit(misure.getText()))) {
                detailDao.setTable_name(choiceBox.getValue());
                fieldData = FieldData.FieldDataBuilder.getbuilder()
                        .setUnit_misure(unit.getText())
                        .setMisure(Integer.parseInt(misure.getText())).build();

            }
        } else if(InputValidation.filled_text(misure.getText())) {
                fieldData = FieldData.FieldDataBuilder.getbuilder().setNome(misure.getText())
                        .build();
                detailDao.setTable_name(choiceBox.getValue());
            }

        result = detailDao.insert(fieldData);
        Handler.showAlert(result, "valore già inserito");
        if (result) {
            listView.getItems().add(operator_mapping(fieldData, choiceBox.getValue()));
        }




    }


    //For each element into the list execute operator_mapping that create
    private List<String> listMapping(List<FieldData> fd_list, String mode) {

        return fd_list.stream().
                map(fieldData -> operator_mapping(fieldData, mode)
                ).toList();


    }

    private String operator_mapping(FieldData fieldData, String mode) {

        return (mode.equals(Utility.Misura)) ? ("Misura: " + fieldData.getMisure() + fieldData.getUnit_misure())  : fieldData.getNome();

    }
}
