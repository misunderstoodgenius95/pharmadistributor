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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

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
        tableCustom=new LotTableCustom("Scegli Lotti");
        observableList=FXCollections.observableArrayList();
        checkBoxTableColumn=new CheckBoxTableColumn<>();




        tableCustom.getTableViewProductTable().getColumns().add(checkBoxTableColumn);
        observableList.setAll(lottiDao.findAll());
        tableCustom.getTableViewProductTable().setItems(observableList);
        tableCustom.getButtonOK().setOnAction(event -> {
            System.out.println(checkBoxTableColumn.getSelected_row().size());

        });


    }
}
