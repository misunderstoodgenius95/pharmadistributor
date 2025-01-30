package pharma.Controller.subpanel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import jdk.jshell.execution.Util;
import org.w3c.dom.CDATASection;
import pharma.Handler.PharmaDialogHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.*;
import pharma.dao.PharmaDao;

import javax.print.attribute.standard.PageRanges;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class Pharma implements Initializable {
    @FXML
    private TextField search_id;
    @FXML
    private Button edit_pharma;
    private SimpleObjectProperty<FieldData> fieldData_property;
    public AnchorPane anchor_id;
    public TableView<FieldData> table_id;
    public Button button_click;

    @FXML
    public void add_pharma_action(ActionEvent actionEvent) throws IOException {
        PharmaDialogHandler pharmaDialogHandler=new PharmaDialogHandler("Aggiungi Farmaco",table_id);
       pharmaDialogHandler.showAndWait().ifPresent(result-> {
                   try {
                       Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                       PharmaDao pharmaDao = new PharmaDao(Database.getInstance(properties));
                       boolean cond = pharmaDao.insert(result);
                       if (cond) {

                           Utility.create_alert(Alert.AlertType.CONFIRMATION, "", "Aggiunto con sucesso!");
                           table_id.getItems().add(result);
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
    void edit_pharma_event(ActionEvent event) {

        PharmaDialogHandler pharmaDialogHandler=new PharmaDialogHandler("Personalizza",table_id);
        pharmaDialogHandler.setTextAnagrafica(fieldData_property.get().getAnagrafica_cliente());
        pharmaDialogHandler.setTextSigla(fieldData_property.get().getSigla());
        pharmaDialogHandler.setTextVat(fieldData_property.get().getPartita_iva());
        pharmaDialogHandler.setDisableSigla();
        pharmaDialogHandler.setDisableVat();


        pharmaDialogHandler.showAndWait().ifPresent(result-> {
            try {
                Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                PharmaDao pharmaDao = new PharmaDao(Database.getInstance(properties));
                boolean cond = pharmaDao.update(result);
                if (cond) {
                    Utility.create_alert(Alert.AlertType.CONFIRMATION, "", "Modificato con sucesso!");
                    table_id.getItems().add(result);
                } else {
                    Utility.create_alert(Alert.AlertType.ERROR, "", "Errore inserimento!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fieldData_property=new SimpleObjectProperty<>();
        RadioButtonTableColumn<FieldData> actionColumn = new RadioButtonTableColumn<>() {
            @Override
            protected void onButtonClick(FieldData rowData) {
                System.out.println(rowData.getId());
                edit_pharma.setVisible(true);
                fieldData_property.set(rowData);
                // Custom behavior for the button click
             //   System.out.println("Custom action for: " + rowData.get;
            }
        };

        table_id.getColumns().addAll(  TableUtility.generate_column_string("Anagrafica Utente","anagrafica_cliente"),
        TableUtility.generate_column_string("Sigla","sigla"),
        TableUtility.generate_column_string("Partita Iva","partita_iva"),actionColumn);
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);





       table_id.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #636165;");
        try {
            Properties properties= FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
            PharmaDao pharmaDao=new PharmaDao(Database.getInstance(properties));
            ObservableList<FieldData> fieldData= FXCollections.observableArrayList(pharmaDao.findAll());
            table_id.setItems(fieldData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utility.search_item(table_id,search_id);
    }
}
