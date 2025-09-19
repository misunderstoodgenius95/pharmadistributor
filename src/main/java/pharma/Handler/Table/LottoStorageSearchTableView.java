package pharma.Handler.Table;

import algo.ShelvesAssigment;
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
                        TableUtility.generate_column_double("Tipologia","Tipologia"),
                        TableUtility.generate_column_string("Nome Magazzino","nome_magazzino")


                );
    }
}
