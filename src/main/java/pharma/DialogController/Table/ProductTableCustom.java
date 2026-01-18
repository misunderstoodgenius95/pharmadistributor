package pharma.DialogController.Table;

import javafx.scene.control.TableView;
import pharma.Model.FieldData;
import pharma.config.TableUtility;


public class ProductTableCustom extends TableBase<FieldData> {
        private  TableView<FieldData> tableView;

    public ProductTableCustom(String content) {
        super(content);
        getDialogPane().setPrefWidth(1200);
       getDialogPane().setPrefHeight(900);



    }

    @Override
    protected void setupBaseColumns(TableView<FieldData> tableView) {
        tableView.getColumns().addAll(TableUtility.generate_column_string("Nome","nome"),
                TableUtility.generate_column_string("Categoria","nome_categoria"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),
                TableUtility.generate_column_string("Principio Attivo","nome_principio_attivo"),
                TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                TableUtility.generate_column_int("Quantità","quantity"));
    }







}
