package pharma.Controller.subpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import pharma.Handler.DialogHandler;
import pharma.Handler.FarmacoDialogHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.Database;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.dao.DetailDao;
import pharma.dao.FarmacoDao;
import pharma.dao.PharmaDao;


import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

public class Farmaco implements Initializable {
    @FXML
    public TableView table_id;
    @FXML

    public  AnchorPane anchor;
    @FXML
    private Button btn_id_add;
    private ObservableList<FieldData> obs_fieldData= FXCollections.observableArrayList();
    FarmacoDao farmacoDao;
    DetailDao detailDao;
    PharmaDao pharmaDao;
    public Farmaco(){
        try {
            Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            farmacoDao=new FarmacoDao("farmaco", Database.getInstance(properties));
            detailDao=new DetailDao(Database.getInstance(properties));
            pharmaDao=new PharmaDao(Database.getInstance(properties));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void btn_action_add(ActionEvent event) throws AccessException {
        FarmacoDialogHandler farmacoDialogHandler=new FarmacoDialogHandler("Aggiungi Farmaco",obs_fieldData, farmacoDao,detailDao,pharmaDao);
        farmacoDialogHandler.setOperation(DialogHandler.Mode.Insert,null);
        farmacoDialogHandler.execute();
        table_id.getItems().clear();
        table_id.getItems().setAll(obs_fieldData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utility.create_btn(btn_id_add, "add.png");

        table_id.getColumns().addAll(TableUtility.generate_column_string("Nome","nome"),
        TableUtility.generate_column_string("Descrizione","description"),
        TableUtility.generate_column_string("Categoria","nome_categoria"),
        TableUtility.generate_column_string("Tipologia","nome_tipologia"),
        TableUtility.generate_column_string("Misura","unit_misure"),
        TableUtility.generate_column_string("Principio Attivo","nome_principio_attivo"),
        TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"));
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
       obs_fieldData.setAll(farmacoDao.findAll());

        table_id.getItems().addAll(obs_fieldData);
        table_id.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #636165;");
       anchor.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;");
    }
}
