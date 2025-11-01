package pharma.Handler.Table;



import javafx.scene.control.TableView;
import pharma.Handler.Table.TableBase;
import pharma.config.TableUtility;

public class OrderTableView extends TableBase {
    public OrderTableView(String content) {
        super(content);
    }

    @Override
    protected void setupBaseColumns(TableView tableView) {
        tableView.getColumns().
                addAll(TableUtility.generate_column_string("Id Ordine","id"),
                        TableUtility.generate_column_date("Data Ordine","elapsed_date"),
                        TableUtility.generate_column_string("Intestazione Farmacia","nome_casa_farmaceutica"),
                        TableUtility.generate_column_double("Totale","total"));

    }
}
