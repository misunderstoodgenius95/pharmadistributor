import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pharma.Model.FieldData;
import pharma.javafxlib.Dialog.CustomDialog;

public class JavaTest extends ApplicationTest {
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

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("purchase.fxml")));
        stage.setScene(scene);
        stage.setTitle("Java Test");
        stage.show();
    }
    @Test
    public void get_button(){


    }


}
