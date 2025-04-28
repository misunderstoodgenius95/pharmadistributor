package pharma.Handler.Table;

import pharma.config.TableUtility;

public class LotTableCustom extends  ProductTableCustom {
    public LotTableCustom(String content) {
        super(content);
        getDialogPane().setPrefHeight(900);
        getDialogPane().setPrefWidth(1200);

        getTableViewProductTable().getColumns().addAll(TableUtility.generate_column_string("lotto Code","lotto_id"),
                TableUtility.generate_column_string("Data di produzione","production_date"),
                TableUtility.generate_column_string("Data di scadenza","elapsed_date"),
        TableUtility.generate_column_string("Disponibilità","availability"));

    }



}
