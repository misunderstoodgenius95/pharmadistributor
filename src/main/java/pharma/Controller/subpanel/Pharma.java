package pharma.Controller.subpanel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import pharma.Handler.DialogHandler;
import pharma.Handler.PharmaDialogHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.*;
import pharma.config.auth.AutorizationService;
import pharma.dao.PharmaDao;
import pharma.config.database.Database;
import pharma.javafxlib.CustomTableView.RadioButtonTableColumn;
import pharma.security.Stytch.StytchClient;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.util.*;

public class Pharma implements Initializable {
    @FXML
    private TextField search_id;
    @FXML
    private Button edit_pharma;
    private SimpleObjectProperty<FieldData> fieldData_property;
    public AnchorPane anchor_id;
    public TableView<FieldData> table_id;
    @FXML
    public Button button_add_click;
    private PharmaDialogHandler pharmaDialogHandler;
    private  ObservableList<FieldData> obs_fieldData;
    private PharmaDao pharmaDao;
    private AutorizationService authorizationService;
    private String jwt;
    public Pharma() throws FileNotFoundException {
        HashMap<String,String> hashMap_json=null;
        hashMap_json = FileStorage.getProperties(List.of("project_id","secret","url"),new FileReader("stytch.properties"));
        authorizationService=
                new AutorizationService( new StytchClient(hashMap_json.get("project_id"),hashMap_json.get("secret"),hashMap_json.get("url")));
         jwt=FileStorage.getProperty("jwt",new FileReader("config.properties"));
    }
    @FXML
    public void add_pharma_action() throws AccessException, FileNotFoundException {

        if(authorizationService.authorization(jwt,"create","pharma")) {
            pharmaDialogHandler.setOperation(DialogHandler.Mode.Insert, null);
            pharmaDialogHandler.execute();
            table_id.setItems(obs_fieldData);
        }else{
            Utility.create_alert(Alert.AlertType.ERROR,"Operation Formitten!","Operazione negata! ");
        }




    }
    @FXML
    void edit_pharma_event() throws AccessException {

        if(authorizationService.authorization(jwt,"update","pharma")) {
            pharmaDialogHandler.setOperation(DialogHandler.Mode.Update, fieldData_property.get());
            pharmaDialogHandler.execute();
            table_id.refresh();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Properties properties;
        try {
             properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e ) {
            throw new RuntimeException(e);
        }

        pharmaDao = new PharmaDao(Database.getInstance(properties));
        obs_fieldData= FXCollections.observableArrayList();
        pharmaDialogHandler=new PharmaDialogHandler("Aggiungi Casa farmaceutica",pharmaDao,obs_fieldData);
        Utility.add_iconButton(button_add_click, FontAwesomeSolid.PLUS);
        fieldData_property=new SimpleObjectProperty<>();
        //For edit row
        RadioButtonTableColumn<FieldData> actionColumn = new RadioButtonTableColumn<>() {
            @Override
            protected void onButtonClick(FieldData rowData) {
                // Show button is visibile
                edit_pharma.setVisible(true);
                //Setting SImplebean to rowdata
                fieldData_property.set(rowData);
                // Custom behavior for the button click
                //   System.out.println("Custom action for: " + rowData.get;
            }
        };

        table_id.getColumns().addAll( TableUtility.generate_column_string("Anagrafica Utente","nome_casa_farmaceutica"),
        TableUtility.generate_column_string("Sigla","sigla"),
        TableUtility.generate_column_string("Partita Iva","partita_iva"),
                actionColumn);
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        table_id.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #636165;");
        obs_fieldData.setAll(pharmaDao.findAll());
        table_id.setItems(obs_fieldData);
        Utility.search_item(table_id,search_id);

    }
}
