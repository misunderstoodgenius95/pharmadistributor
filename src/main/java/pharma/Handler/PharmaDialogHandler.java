package pharma.Handler;

import javafx.scene.control.Alert;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.CustomDialog;
import pharma.config.Database;
import pharma.config.Utility;
import pharma.dao.PharmaDao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;


public class PharmaDialogHandler extends CustomDialog<FieldData> {
private TextField anagrafica;
private  TextField vat;
private  TextField sigla;
private TableView<FieldData> table_id;
    public PharmaDialogHandler(String dialog, TableView<FieldData> table_id) {
        super(dialog);
        initialize();

    }

    public void initialize() {

         anagrafica = add_text_field("Inserisci Anagrafia Utente");
         vat = add_text_field_with_validation("Inserisci Partita Iva", CustomDialog.Validation.Vat);
         sigla = add_text_field("Sigla");


        setResultConverter(dialog -> {
            if (dialog == getButton_click()) {
                return FieldData.FieldDataBuilder.getbuilder().setSigla(sigla.getText()).setPartita_iva(vat.getText()).setAnagrafica_cliente(anagrafica.getText()).build();
            }
            return null;
        });
    }

    public void setTextAnagrafica(String text) {
        anagrafica.setText(text);
    }

    public void setTextVat(String text) {
        vat.setText(text);
    }

    public void setTextSigla(String text) {
      sigla.setText(text);
    }

    public void setDisableVat(){
        vat.setDisable(true);

    }
    public void setDisableSigla(){
        sigla.setDisable(true);
    }
}
