import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pharma.Model.FieldData;
import pharma.javafxlib.Dialog.CustomDialog;

public class GuiTest extends ApplicationTest {
    private  ChoiceBox<String> choice;
    private Button button;
    private String test;
    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox=new VBox();
        Scene scene=new Scene(vBox);
        choice=new ChoiceBox<>();
       button=new Button("Ciao");
   button.setOnAction(s->{

   test="Clicked";
   });
        choice.getItems().addAll("One","Two");
        choice.getStyleClass().add("choice_box");
        vBox.getChildren().addAll(choice,button);
        stage.setScene(scene);
        stage.show();


    }
    @Test
    public void control_choicebox() throws InterruptedException {

       Platform.runLater(()->{
            clickOn(button);
            System.out.println(test);
        });

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
    @Test
    public void test_button(){
        clickOn("#user_field").write("pinco");
        clickOn("#password_field").write("pallino");
        clickOn("#button_click");

    }



}
