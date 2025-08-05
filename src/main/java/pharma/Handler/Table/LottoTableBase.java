package pharma.Handler.Table;

import javafx.scene.control.TableView;
import pharma.Model.FieldData;
import pharma.config.TableUtility;

public class LottoTableBase extends TableBase<FieldData> {
    public LottoTableBase(String content) {
        super(content);
    }

    @Override
    protected void setupBaseColumns(TableView<FieldData> tableView) {
        tableView.getColumns().addAll(
                TableUtility.generate_column_string("lotto Code", "code"),
                TableUtility.generate_column_int("Farmaco ID", "farmaco_id"),
                TableUtility.generate_column_string("Data di produzione", "production_date"),
                TableUtility.generate_column_string("Data di scadenza", "elapsed_date"),
                TableUtility.generate_column_string("Disponibilità", "availability"),
                TableUtility.generate_column_int("Pezzi", "quantity"));
    }


}
