package pharma.Controller.subpanel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


import pharma.Handler.DialogHandler;
import pharma.Handler.PharmaDialogHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.*;
import pharma.dao.PharmaDao;


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

    private  ObservableList<FieldData> obs_fieldData;
    private final PharmaDao pharmaDao;
    public Pharma()  {

        try {
            Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            pharmaDao = new PharmaDao(Database.getInstance(properties));
        } catch (IOException e ) {
            throw new RuntimeException(e);
        }
       obs_fieldData= FXCollections.observableArrayList();

    }
    @FXML
    public void add_pharma_action() throws AccessException {
        PharmaDialogHandler pharmaDialogHandler=new PharmaDialogHandler("Aggiungi Casa farmaceutica",pharmaDao,obs_fieldData);
        pharmaDialogHandler.setOperation(DialogHandler.Mode.Insert, null);
        pharmaDialogHandler.execute();

      /* pharmaDialogHandler.showAndWait().ifPresent(result-> {
                   try {

                       boolean cond = pharmaDao.insert(result);
                       if (cond) {

                           Utility.create_alert(Alert.AlertType.CONFIRMATION, "", "Aggiunto con sucesso!");
                           obs_fieldData.add(result);
                       } else {
                           Utility.create_alert(Alert.AlertType.ERROR, "", "Errore inserimento!");
                       }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               });
   /* CustomDialog<FieldData> customDialog=new CustomDialog<>("Aggiungi Casa Farmaceutica");
    TextField anagrafica=customDialog.add_text_field("Inserisci Anagrafia Utente");
    TextField vat=customDialog.add_text_field_with_validation("Inserisci Partita Iva", CustomDialog.Validation.Vat);
    TextField sigla=customDialog.add_text_field("Sigla");


    customDialog.setResultConverter(dialog->{
        if(dialog== customDialog.getButton_click()) {
            return FieldData.FieldDataBuilder.getbuilder().setSigla(sigla.getText()).setPartita_iva(vat.getText()).setAnagrafica_cliente(anagrafica.getText()).build();
        }
        return null;
    });
    customDialog.showAndWait().ifPresent(result->{
        try {
            Properties properties= FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
            PharmaDao pharmaDao=new PharmaDao(Database.getInstance(properties));
            boolean cond=pharmaDao.insert(result);
            if(cond){

                Utility.create_alert(Alert.AlertType.CONFIRMATION,"","Aggiunto con sucesso!");
                table_id.getItems().add(result);
            }else{
                Utility.create_alert(Alert.AlertType.ERROR,"","Errore inserimento!");


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    });

    */


    }
    @FXML
    void edit_pharma_event() throws AccessException {

        PharmaDialogHandler pharmaDialogHandler=new PharmaDialogHandler("Aggiungi Casa farmaceutica",pharmaDao,obs_fieldData);
        pharmaDialogHandler.setOperation(DialogHandler.Mode.Update, fieldData_property.get());
        pharmaDialogHandler.execute();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utility.create_btn(button_add_click, "add.png");
        fieldData_property=new SimpleObjectProperty<>();
        RadioButtonTableColumn<FieldData> actionColumn = new RadioButtonTableColumn<>() {
            @Override
            protected void onButtonClick(FieldData rowData) {
                edit_pharma.setVisible(true);
                fieldData_property.set(rowData);
                // Custom behavior for the button click
                //   System.out.println("Custom action for: " + rowData.get;
            }
        };

        table_id.getColumns().addAll( TableUtility.generate_column_string("Anagrafica Utente","nome"),
        TableUtility.generate_column_string("Sigla","sigla"),
        TableUtility.generate_column_string("Partita Iva","partita_iva"),actionColumn);


        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);





       table_id.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #636165;");
        try {
            Properties properties= FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
            PharmaDao pharmaDao=new PharmaDao(Database.getInstance(properties));
            obs_fieldData.setAll(pharmaDao.findAll());
            table_id.setItems(obs_fieldData);
        } catch (IOException e ) {
            throw new RuntimeException(e);
        }
        Utility.search_item(table_id,search_id);

    }
}
