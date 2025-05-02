package pharma.config;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pharma.javafxlib.test.SimulateEvents;

class ValidationsTest extends ApplicationTest {

    private TextField email;
    private TextField password;
    private Button button;
    VBox vBox;
    @Override
    public void start(Stage stage) throws Exception {

      email=new TextField();
      password=new TextField();
      vBox=new VBox();
      button=new Button();
    vBox.getChildren().addAll(email,password,button);
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.show();

    }
    @Test
    public void ValidCredendials() throws InterruptedException {
        Platform.runLater(()->{
        Validations validations=Validations.ValidationBuilder.getInstance().valid_email(email).valid_password(password).build(button);
        validations.validate();

            SimulateEvents.writeOn(email,"user@exaple.com");
            SimulateEvents.writeOn(password,"@5&17Vhm5QGp");
            SimulateEvents.clickOn(button);

            Assertions.assertThrows(RuntimeException.class,()->lookup("#alert").queryAs(DialogPane.class));

        });

    }



}