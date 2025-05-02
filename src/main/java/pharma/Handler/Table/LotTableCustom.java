package pharma.Handler.Table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pharma.Model.FieldData;
import pharma.config.InputValidation;
import pharma.config.TableUtility;
import pharma.dao.LottiDao;
import pharma.javafxlib.CustomTableView.CheckBoxTableColumn;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.javafxlib.Search.FilterSearch;

import java.util.*;

public class LotTableCustom extends CustomDialog<FieldData> {

    private ChoiceBox<String> choiceBox;
    private TextField textField_search;
    private TableView<FieldData> tableView;
    private  LottiDao lottiDao;
    private ObservableList<FieldData> observableList;
    private ObservableSet<FieldData> selected_fds;
    private  CheckBoxTableColumn<FieldData> col_checkbox;
    public LotTableCustom(String content, LottiDao lottiDao) {
        super(content);
        getDialogPane().setPrefHeight(900);
        getDialogPane().setPrefWidth(1200);
        this.lottiDao=lottiDao;
        choiceBox=add_choiceBox("Seleziona");
        textField_search=add_text_field("");
        getControlList().removeAll(choiceBox,textField_search);
        col_checkbox=new CheckBoxTableColumn<>("Seleziona"){
            @Override
            protected void selectedRow(FieldData data) {
                System.out.println("execute");
            selected_fds.add(data);
            }
        };

        observableList=FXCollections.observableArrayList();
        tableView = add_table();
        selected_fds =FXCollections.observableSet();
        tableView.getColumns().addAll(
                TableUtility.generate_column_string("lotto Code","lotto_id"),
                TableUtility.generate_column_string("Nome","nome"),
                TableUtility.generate_column_string("Categoria","nome_categoria"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),
                TableUtility.generate_column_string("Principio Attivo","nome_principio_attivo"),
                TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                TableUtility.generate_column_int( "Pezzi","quantity"),
                TableUtility.generate_column_string("Data di produzione","production_date"),
                TableUtility.generate_column_string("Data di scadenza","elapsed_date"),
                TableUtility.generate_column_string("Disponibilità","availability"),col_checkbox);

        choiceBox.getItems().setAll(FXCollections.observableArrayList("Nome","Tipologia","Categoria","Principio_Attivo","Data_di_scadenza"));
        textField_search.setDisable(true);
        listener_choicebox();
        listener_textfield();
    }

    public TableView<FieldData> getTableLot() {
        return tableView;
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
                    tableView.setItems(observableList);
                    System.out.println("inserito"+col_checkbox.getCheckBoxMap().size());

                }
            }
        });


    }


    public ObservableMap<FieldData, CheckBox> getMapCheckBox() {
        return  col_checkbox.getCheckBoxMap();
    }

    public void execute_filter() {
        FilterSearch filterSearch = new FilterSearch(tableView, textField_search) {

            @Override
            protected boolean condition(String newValue, FieldData fieldData) {
                return FilterSearch.choice_value(choiceBox.getValue(),
                                new AbstractMap.SimpleEntry<>("Tipologia", fieldData.getNome_tipologia()),
                                new AbstractMap.SimpleEntry<>("Categoria", fieldData.getNome_categoria()),
                                new AbstractMap.SimpleEntry<>("Principio_Attivo", fieldData.getNome_principio_attivo()),
                                new AbstractMap.SimpleEntry<>("Misura", fieldData.getUnit_misure()),
                                new AbstractMap.SimpleEntry<>("Casa_Farmaceutica", fieldData.getNome_casa_farmaceutica())).toLowerCase().
                        contains(newValue.toLowerCase());


            }
        };



    }



}
