package pharma.DialogController.Table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import pharma.Model.FieldData;
import pharma.config.InputValidation;
import pharma.dao.LottiDao;
import pharma.javafxlib.CustomTableView.CheckBoxTableColumn;

import java.util.*;

public class TableCustom extends LottoTableBase {
    private ChoiceBox<String> choiceBox;
    private TextField textField_search;;
    private  LottiDao lottiDao;
    private ObservableList<FieldData> observableList;
    private ObservableSet<FieldData> selected_fds;
    private  CheckBoxTableColumn<FieldData> col_checkbox;


    public TableCustom(String content, LottiDao lottiDao) {
        super(content);
        getDialogPane().setPrefHeight(900);
        getDialogPane().setPrefWidth(1200);
        this.lottiDao=lottiDao;
        choiceBox=add_choiceBox("Seleziona");
        col_checkbox=add_check_box_column();



        textField_search=add_text_field("");
        swap(getVbox().getChildren());
        getControlList().removeAll(choiceBox,textField_search);
        selected_fds =FXCollections.observableSet();


        observableList=FXCollections.observableArrayList();



        choiceBox.getItems().setAll(FXCollections.observableArrayList("Nome","Tipologia","Categoria","Principio_Attivo","Data_di_scadenza"));
        textField_search.setDisable(true);
        listener_choicebox();
        listener_textfield();
    }
    private void swap(ObservableList<Node> children){
        Node node1 = children.get(0);
        Node node2 = children.get(1);
        Node node3=children.get(2);
        children.removeAll(node1, node2,node3);
        children.add(0, node2);
        children.add(1, node3);
        children.add(2,node1);

    }
    public void listener_checkbox() {
        getCheckBoxValue().addListener(new ChangeListener<FieldData>() {
            @Override
            public void changed(ObservableValue<? extends FieldData> observable, FieldData oldValue, FieldData newValue) {
                if (newValue != null) {
                    selected_fds.add(newValue);

                }
            }
        });
    }




    public TextField getTextField_search() {
        return textField_search;
    }

    public ChoiceBox<String> getChoiceBox() {
        return choiceBox;
    }

    public void listener_choicebox(){
        choiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.isEmpty()){
                    textField_search.setDisable(false);
                }
            }
        });

    }

    public ObservableSet<FieldData> getSelectedRows() {
        return selected_fds;
    }

    /**
     * Listener Choicebox
     *
     */
    public void listener_textfield(){
        textField_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(InputValidation.filled_text(newValue)&& (!choiceBox.getValue().isEmpty()) && (!Objects.equals(choiceBox.getValue(), "Seleziona"))){
                    List<FieldData> list=lottiDao.findBySearch(choiceBox.getValue(), newValue);
                    observableList.setAll(list);
                    getTableView().setItems(observableList);

                }
            }
        });


    }


    public ObservableMap<FieldData, CheckBox> getMapCheckBox() {
        return  col_checkbox.getCheckBoxMap();
    }



}
