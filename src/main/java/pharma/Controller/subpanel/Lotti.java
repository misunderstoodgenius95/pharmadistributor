package pharma.Controller.subpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import pharma.DialogController.DialogControllerBase;
import pharma.DialogController.LottiDialogControllerBase;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.PathConfig;
import pharma.config.database.Database;
import pharma.config.TableUtility;
import pharma.dao.FarmacoDao;
import pharma.dao.LottiDao;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

public class Lotti implements Initializable {

    public TableView table_id;
    private LottiDao lottiDao;
    private FarmacoDao farmacoDao;
    private ObservableList<FieldData> obs;
    private DialogControllerBase<FieldData> dialogControllerBase;

    public Lotti() {
        Database database;
        Properties properties;
        obs=FXCollections.observableArrayList();
        try {
             properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader(PathConfig.DATABASE_CONF.getValue()));
           database=Database.getInstance(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lottiDao=new LottiDao(database,"lotto");
        farmacoDao=new FarmacoDao(database);

    }
    @FXML
    public void btn_action_add(ActionEvent event) {
       dialogControllerBase = new LottiDialogControllerBase("Aggiungi Lotti", lottiDao, farmacoDao,obs);

        dialogControllerBase.execute();
        table_id.getItems().clear();
        table_id.getItems().setAll(obs);
    }

    public DialogControllerBase<FieldData> getDialogHandler() {
        return dialogControllerBase;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_id.getColumns().addAll(
                TableUtility.generate_column_string("lotto Code","code"),
        TableUtility.generate_column_string("Nome","nome"),
        TableUtility.generate_column_string("Tipologia","nome_tipologia"),
        TableUtility.generate_column_string("Casa Farmaceutica","nome_casa_farmaceutica"),
        TableUtility.generate_column_string("Misura","unit_misure"),
        TableUtility.generate_column_string("Data di produzione","production_date"),
        TableUtility.generate_column_string("Data di scadenza","elapsed_date")
        );
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        obs.addAll(lottiDao.findAll());
        table_id.getItems().addAll(obs);
        table_id.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #636165;");
    }
}
