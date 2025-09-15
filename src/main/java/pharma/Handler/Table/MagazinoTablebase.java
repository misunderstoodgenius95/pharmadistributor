package pharma.Handler.Table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import pharma.Handler.MagazzinoHandler;
import pharma.Model.FieldData;
import pharma.Model.Warehouse;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.dao.MagazzinoDao;

import java.util.List;

public class MagazinoTablebase  extends TableBase<Warehouse> {
    private MagazzinoDao magazzinoDao;
    private  Button button_add_warehouse;
    MagazzinoHandler magazzinoHandler;
    private ObservableList<Warehouse> observableList;
    public MagazinoTablebase(String content, MagazzinoDao magazzinoDao) {
        super(content);
        setTitle("Magazzino");
        getDialogPane().setPrefHeight(600);
        getDialogPane().setPrefWidth(800);
        this.magazzinoDao=magazzinoDao;
         button_add_warehouse=addButton("Inserisci Magazzino");
         observableList=FXCollections.observableArrayList(magazzinoDao.findAll());
        getTableView().setItems(observableList);
         magazzinoHandler=new MagazzinoHandler("Inserisci Magazzino", List.of(magazzinoDao),observableList);
         add_warehouse_btn();

    }



    @Override
    protected void setupBaseColumns(TableView<Warehouse> tableView) {
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
