package pharma.Handler.Table;

import javafx.scene.control.TableView;
import pharma.Model.FieldData;
import pharma.config.TableUtility;

public class LottoViewTableView extends  TableBase<FieldData> {
    public LottoViewTableView(String content) {
        super(content);
    }

    @Override
    protected void setupBaseColumns(TableView<FieldData> tableView) {
        tableView.getColumns().
                addAll(TableUtility.generate_column_double("Codice Lotto","code"),
                        TableUtility.generate_column_string("Nome Magazzino","nome"),
                        TableUtility.generate_column_string("Scaffale","shelf_code"),
                        TableUtility.generate_column_int("Ripiano","shelves_code"),
                        TableUtility.generate_column_int("Disponibilità","quantity"));








    }
}
