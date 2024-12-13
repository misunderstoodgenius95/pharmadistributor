package pharma.Model;

import javafx.scene.control.TextField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;
import pharma.config.CustomDialog;

import static org.junit.jupiter.api.Assertions.*;

class FieldDataTest  {
   CustomDialog<FieldData> customDialog;
    TextField anagrafica;
    TextField  p_iva;
    TextField sigla;
   @BeforeEach
    public void setup() {
        customDialog=new CustomDialog<>("Aggiungi Casa Farmaceutica");
   anagrafica=customDialog.add_text_field("Inserisci Anagrafia Cliente");
        p_iva= customDialog.add_text_field("Inserisci  Partita Iva");
        sigla= customDialog.add_text_field("Inserisci  Sigla");

    }
@Test

    public void testFieldData() {
   FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setAnagrafia_cliente("Agrafia Cliente").setSigla("AF").
            setPartita_iva("IT122222222").build();






}


@Test
    public void get_button(){

int size=customDialog.getDialogPane().getButtonTypes().size();
Assertions.assertTrue(size==2);
}

}