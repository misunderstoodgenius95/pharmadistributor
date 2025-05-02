package pharma.Controller.subpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import pharma.Handler.Table.LotTableCustom;
import pharma.Handler.Table.ProductTableCustom;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.dao.FarmacoDao;
import pharma.dao.LottiDao;
import pharma.javafxlib.CustomTableView.CheckBoxTableColumn;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Expireproduct implements Initializable {
    private LotTableCustom tableCustom;
    private ObservableList<FieldData> observableList;
    private LottiDao lottiDao;
    private CheckBoxTableColumn<FieldData> checkBoxTableColumn;
    private Database database;
    private Properties properties;
    public Expireproduct() {
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            database=Database.getInstance(properties);
           lottiDao=new LottiDao(database,"lotto");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void btn_product_add_action(ActionEvent actionEvent) {
        tableCustom.show();



    };



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableCustom=new LotTableCustom("Scegli Lotti",lottiDao);
        observableList=FXCollections.observableArrayList();






/*
        observableList.setAll(lottiDao.findAll());
        tableCustom.getTableLot().setItems(observableList);
       tableCustom.execute_filter();*/

        tableCustom.getButtonOK().setOnAction(event -> {
         /*   HashSet<FieldData> list_selected= tableCustom.getSelectedRows();
            System.out.println(list_selected.size());*/

        });


    }
}
