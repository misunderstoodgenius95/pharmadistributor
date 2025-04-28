package pharma.Handler.Table;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pharma.Model.FieldData;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.config.TableUtility;

public class ProductTableCustom extends CustomDialog<FieldData> {
        private  TableView<FieldData> tableView;

    public ProductTableCustom(String content) {
        super(content);

        getDialogPane().setPrefWidth(800);
       getDialogPane().setPrefHeight(500);
       add_table_view();



    }

    public void  add_table_view(){
        tableView = add_table();
        tableView.getColumns().addAll(TableUtility.generate_column_string("Nome","nome"),
                TableUtility.generate_column_string("Categoria","nome_categoria"),
                TableUtility.generate_column_string("Tipologia","nome_tipologia"),
                TableUtility.generate_column_string("Misura","unit_misure"),
                TableUtility.generate_column_string("Principio Attivo","nome_principio_attivo"),
                TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
                TableUtility.generate_column_int("Quantità","quantity"));



    }


    public TableView<FieldData> getTableViewProductTable() {
        return tableView;
    }


}
