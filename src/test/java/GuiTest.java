import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pharma.Model.FieldData;
import pharma.config.CustomDialog;

public class GuiTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("purchase.fxml")));
        stage.setScene(scene);
        stage.setTitle("Java Test");
        stage.show();
    }

    @Test
    public void test() {
        Platform.runLater(() -> {
            CustomDialog<FieldData> customDialog = new CustomDialog<>("Aggiungi Casa Farmaceutica");
            TextField anagrafica = customDialog.add_text_field("Inserisci Anagrafia Cliente");
            TextField p_iva = customDialog.add_text_field("Inserisci  Partita Iva");
            TextField sigla = customDialog.add_text_field("Inserisci  Sigla");

            Assertions.assertTrue((customDialog.getDialogPane().getButtonTypes().size()) == 1);
        });
    }
}
