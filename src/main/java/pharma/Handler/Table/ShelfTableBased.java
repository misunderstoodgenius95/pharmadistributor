package pharma.Handler.Table;

import algoWarehouse.ShelfInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import pharma.Handler.ShelfHandler;
import pharma.config.TableUtility;
import pharma.dao.MagazzinoDao;
import pharma.dao.ShelfDao;
import pharma.dao.ShelvesDao;

import java.util.List;

public class ShelfTableBased extends TableBase<ShelfInfo> {
    private ShelfHandler shelfHandler;
    private Button btn_add_shelf;
    private ShelfDao shelfDao;
    private MagazzinoDao magazzinoDao;
    private ObservableList<ShelfInfo> shelfInfos;
    public ShelfTableBased(String content, MagazzinoDao magazzinoDao, ShelfDao shelfDao, ShelvesDao shelvesDao) {
        super(content);
        this.magazzinoDao=magazzinoDao;
        btn_add_shelf=addButton("Inserisci Scaffale");
        this.shelfDao=shelfDao;
        getDialogPane().setPrefWidth(800);
        getDialogPane().setPrefHeight(600);
        table_config();
        shelfHandler=new ShelfHandler("Inserisci Scaffale", List.of(magazzinoDao,shelfDao,shelvesDao),shelfInfos);
        actionBtn();
    }
    public void table_config(){

        shelfInfos=FXCollections.observableArrayList(shelfDao.findAll().stream().
                peek(fieldData->fieldData.setNome_magazzino(magazzinoDao.findById(fieldData.getMagazzino_id()).getNome())).toList());
        getTableView().setItems(shelfInfos);
    }

    @Override
    protected void setupBaseColumns(TableView<ShelfInfo> tableView) {
        tableView.getColumns().setAll(
                TableUtility.generate_column_string("Codice","shelf_code"),
                TableUtility.generate_column_string("Nome Magazzino","nome_magazzino"),
                TableUtility.generate_column_int("id","magazzino_id"),
                TableUtility.generate_column_double("Lunghezza","lenght"),
                TableUtility.generate_column_double("Altezza","height"),
                TableUtility.generate_column_double("Profondità","deep"),
                TableUtility.generate_column_double("Spessore","shelf_thickness"),
                TableUtility.generate_column_int("Numero Livelli","num_rip"),
                TableUtility.generate_column_int("Capacita(kg)","weight")
        );
    }
    public void actionBtn(){
            btn_add_shelf.setOnAction(event -> {

            shelfHandler.execute();

        });



    }




}
