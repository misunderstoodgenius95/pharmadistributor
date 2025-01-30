package pharma.oldest;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pharma.Model.FieldData;
import pharma.config.CustomDialog;
import pharma.config.SimulateEvents;


class CustomDialogTest extends ApplicationTest {
    CustomDialog<FieldData> customDialog;
    private TextField anagrafia;
    private TextField vat;
    private TextField sigla;
    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        customDialog = new CustomDialog<>("Pharma");
        anagrafia=customDialog.add_text_field("Inserisci Anagrafia Utente");
        vat=customDialog.add_text_field_with_validation("Inserisci Partita Iva", CustomDialog.Validation.Vat);
        sigla=customDialog.add_text_field("Sigla");

    }




    @Test
    public void  INValid_Test() throws InterruptedException {

        Platform.runLater(()->{
            ButtonType button_type_ok = customDialog.getDialogPane().getButtonTypes().get(0);
            Button button_ok = (Button) customDialog.getDialogPane().lookupButton(button_type_ok);


            SimulateEvents.writeOn(vat,"IT12555555");
            SimulateEvents.writeOn(sigla,"af");
            SimulateEvents.writeOn(anagrafia,"AF");
            SimulateEvents.clickOn(button_ok);

            Assertions.assertDoesNotThrow(()->lookup("#alert").queryAs(DialogPane.class));
// Non si verifica l'exception perchè l'alert viene trovato, perchè esiste.
        });

    }

    @Test
    public void  Valid_Test() throws InterruptedException {

        Platform.runLater(()->{

            ButtonType button_type_ok = customDialog.getDialogPane().getButtonTypes().get(0);
            Button button_ok = (Button) customDialog.getDialogPane().lookupButton(button_type_ok);


            SimulateEvents.writeOn(vat,"IT12345555555");
            SimulateEvents.writeOn(sigla,"af");
            SimulateEvents.writeOn(anagrafia,"AF");
            SimulateEvents.clickOn(button_ok);
            Assertions.assertThrows(RuntimeException.class,()->lookup("#alert").queryAs(DialogPane.class));
            System.out.println(Stage.getWindows().size());
            sleep(5000);
// Si verifica l'exception se lui non trova l'alert in stato di running.
        });

    }
    @Test void ValidTest_get_Data(){


        Platform.runLater(()->{
            ButtonType button_type_ok = customDialog.getDialogPane().getButtonTypes().get(0);
            Button button_ok = (Button) customDialog.getDialogPane().lookupButton(button_type_ok);
            customDialog.setResultConverter( result->{
                if(result==button_type_ok){
                    return FieldData.FieldDataBuilder.getbuilder().setSigla(sigla.getText()).setPartita_iva(vat.getText()).setAnagrafica_cliente(anagrafia.getText()).build();
                }
                return null;
            });



            SimulateEvents.writeOn(vat,"IT12345555555");
            SimulateEvents.writeOn(sigla,"af");
            SimulateEvents.writeOn(anagrafia,"AF");
            SimulateEvents.clickOn(button_ok);
            Assertions.assertThrows(RuntimeException.class,()->lookup("#alert").queryAs(DialogPane.class));
           customDialog.showAndWait().ifPresent(result->{
               System.out.println(result.getAnagrafica_cliente());
                Assertions.assertEquals("IT12345555555",result.getPartita_iva());
                Assertions.assertEquals("af",result.getSigla());
                Assertions.assertEquals("AF",result.getAnagrafica_cliente());
           });
// Si verifica l'exception se lui non trova l'alert in stato di running.
        });
    }

}