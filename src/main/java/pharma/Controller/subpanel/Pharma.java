package pharma.Controller.subpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pharma.Model.FieldData;
import pharma.Stages;
import pharma.config.CustomDialog;

import java.io.IOException;

public class Pharma {


    public AnchorPane anchor_id;
    public TableView table_id;
@FXML
    public void add_pharma_action(ActionEvent actionEvent) throws IOException {
        System.out.println("Add pharma action");
        // Creo Custom Data
   CustomDialog<FieldData> customDialog=new CustomDialog<>("Aggiungi Casa Farmaceutica");
    TextField anagrafica= customDialog.add_text_field("Inserisci Anagrafia Cliente");
    TextField  p_iva= customDialog.add_text_field("Inserisci  Partita Iva");
    TextField sigla= customDialog.add_text_field("Inserisci  Sigla");

customDialog.getDialogPane().getButtonTypes().get(0);

  customDialog.setResultConverter(dialog->{
    if(dialog==ButtonType.OK){
       if(anagrafica.getText().isEmpty()||p_iva.getText().isEmpty()||sigla.getText().isEmpty()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Errore: Tutti i campi non sono stati riempiti!");

       }else {
         return   FieldData.FieldDataBuilder.getbuilder().setAnagrafia_cliente(anagrafica.getText()).setPartita_iva(p_iva.getText()).setSigla(sigla.getText()).build();

       }

    }

return  null;


});
    customDialog.showAndWait().ifPresent(result->{

       System.out.println(result.getAnagrafia_cliente());
       System.out.println(result.getPartita_iva());
       System.out.println(result.getSigla());
   });



    }
}
