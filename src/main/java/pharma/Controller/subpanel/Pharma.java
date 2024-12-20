package pharma.Controller.subpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pharma.Model.FieldData;
import pharma.Stages;
import pharma.config.CustomDialog;
import pharma.config.Database;
import pharma.config.InputValidation;
import pharma.config.Utility;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pharma {


    public AnchorPane anchor_id;
    public TableView table_id;
@FXML
    public void add_pharma_action(ActionEvent actionEvent) throws IOException {

        // Creo Custom Data
   CustomDialog<FieldData> customDialog=new CustomDialog<>("Aggiungi Casa Farmaceutica");
    TextField anagrafica= customDialog.add_text_field("Inserisci Anagrafia Cliente");
    TextField  p_iva= customDialog.add_text_field("Inserisci  Partita Iva");
    TextField sigla= customDialog.add_text_field("Inserisci  Sigla");
     ButtonType button_type_ok=customDialog.getDialogPane().getButtonTypes().get(0);
Button button_ok=(Button)customDialog.getDialogPane().lookupButton(button_type_ok);
button_ok.addEventFilter(ActionEvent.ACTION, event -> {
    if(anagrafica.getText().isEmpty()||p_iva.getText().isEmpty()||sigla.getText().isEmpty()) {
        Utility.create_alert(Alert.AlertType.WARNING, " Attenzione!", " Riempire tutti campi!");
        event.consume();
    }
    else if (!InputValidation.validate_p_iva(p_iva)) {
        Utility.create_alert(Alert.AlertType.WARNING, "Alert", "Partita iva non valida!");
        event.consume();
    }
});

customDialog.setResultConverter(dialog->{
if(dialog==button_type_ok) {

return FieldData.FieldDataBuilder.getbuilder().setSigla(sigla.getText()).setPartita_iva(p_iva.getText()).setAnagrafia_cliente(anagrafica.getText()).build();
}
return  null;
});




    customDialog.showAndWait().ifPresent(result->{

       System.out.println(result.getAnagrafia_cliente());
       System.out.println(result.getPartita_iva());
       System.out.println(result.getSigla());

      Database database=Database.getInstance();
      PreparedStatement preparedStatement= database.execute_prepared_query("INSERT INTO add_phrama VALUES(?,?,?)");

        try {
            preparedStatement.setString(1,result.getAnagrafia_cliente());
            preparedStatement.setString(2,result.getSigla());
            preparedStatement.setString(3,result.getPartita_iva());
           boolean val= preparedStatement.execute();
           if(!val){
               throw new SQLException();
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    });



    }
}
