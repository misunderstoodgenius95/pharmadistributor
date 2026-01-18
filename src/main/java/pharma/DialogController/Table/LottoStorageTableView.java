package pharma.DialogController.Table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import pharma.Model.FieldData;
import pharma.config.TableUtility;
import pharma.dao.LotAssigmentDao;
import pharma.dao.LotAssigmentShelvesDao;
import pharma.javafxlib.Search.FilterSearch;

public class LottoStorageTableView extends LottoTableBase<FieldData>{

    private ObservableList<FieldData> assigments;
    private LotAssigmentDao lotAssigmentDao;
    private LotAssigmentShelvesDao lotAssigmentShelvesDao;
    public LottoStorageTableView(String content, LotAssigmentDao assigmentDao, LotAssigmentShelvesDao assigmentShelvesDao ) {
        super(content);
        getDialogPane().setPrefHeight(600);
        getDialogPane().setPrefWidth(900);
        this.lotAssigmentDao=assigmentDao;
        this.lotAssigmentShelvesDao=assigmentShelvesDao;
        getTableView().getColumns().addAll(
                TableUtility.generate_column_string("Nome","nome"),
                TableUtility.generate_column_string("Categoria","nome_categoria"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"));
assigments= FXCollections.observableArrayList();

        getTableView().setItems(assigments);

        TextField textField_filter=add_text_field("Ricerca");
        FilterSearch filterSearch=new FilterSearch(getTableView(),textField_filter){
            @Override
            protected boolean condition(String newValue, FieldData fieldData) {
                return fieldData.getCode().toLowerCase().contains(newValue.toLowerCase());
            }
        };
    }



}
