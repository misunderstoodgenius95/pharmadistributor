package pharma.Handler.Table;

import javafx.scene.control.TableView;
import pharma.Model.FieldData;
import pharma.config.TableUtility;

public class LottoStorageSearchTableView  extends  TableBase<FieldData> {
    public LottoStorageSearchTableView(String content) {
        super(content);
    }

    @Override
    protected void setupBaseColumns(TableView<FieldData> tableView) {
        tableView.getColumns().
                addAll(TableUtility.generate_column_double("Farmaco","nome"),
                        TableUtility.generate_column_double("Tipologia","nome_tipologia"),
                        TableUtility.generate_column_string("Nome Magazzino","nome_warehouse"),
                        TableUtility.generate_column_string("Scaffale","shelf_code"),
                        TableUtility.generate_column_int("Ripiano","shelves_code"),
                        TableUtility.generate_column_int("Disponibilità","quantity"));








    }
}
