package pharma.DialogController.Table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import pharma.DialogController.MagazzinoControllerBase;
import pharma.Model.WarehouseModel;
import pharma.config.TableUtility;
import pharma.dao.MagazzinoDao;

import java.util.List;

public class MagazinoTablebase  extends TableBase<WarehouseModel> {
    private MagazzinoDao magazzinoDao;
    private  Button button_add_warehouse;
     private MagazzinoControllerBase magazzinoHandler;
    private ObservableList<WarehouseModel> observableList;
    private  Button button_add;
    public MagazinoTablebase(String content, MagazzinoDao magazzinoDao) {
        super(content);
        setTitle("Magazzino");
        getDialogPane().setPrefHeight(600);
        getDialogPane().setPrefWidth(800);
        this.magazzinoDao=magazzinoDao;
         button_add_warehouse=addButton("Inserisci Magazzino");
         observableList=FXCollections.observableArrayList(magazzinoDao.findAll());
        getTableView().setItems(observableList);
         magazzinoHandler=new MagazzinoControllerBase("Inserisci Magazzino", List.of(magazzinoDao),observableList);
        add_warehouse_btn();

    }

    public ObservableList<WarehouseModel> getObservableList() {
        return observableList;
    }

    public Button getButton_add_warehouse() {
        return button_add_warehouse;
    }

    public MagazzinoControllerBase getMagazzinoHandler() {
        return magazzinoHandler;
    }

    @Override
    protected void setupBaseColumns(TableView<WarehouseModel> tableView) {
      tableView.getColumns().addAll(TableUtility.generate_column_int("id","Id"),
              TableUtility.generate_column_string("Nome","nome"),
              TableUtility.generate_column_string("Indirizzo","address"),
              TableUtility.generate_column_string("Provincia","province"),
              TableUtility.generate_column_string("Comune","comune"));
    }
    public void add_warehouse_btn(){

        button_add_warehouse.setOnAction(event -> {

            magazzinoHandler.execute();



        });

    }



}
