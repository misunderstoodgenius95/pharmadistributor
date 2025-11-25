package pharma.javafxlib;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.javafxlib.test.SimulateEvents;

import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(ApplicationExtension.class)
class DropDownMenuTest {
    Button button;
    @Start
    public void start(Stage stage){
        AnchorPane anchorPane=new AnchorPane();
        button=new Button("Ciao");
        button.setPadding(new Insets(10,20,30,40));
        anchorPane.getChildren().add(button);
        AnchorPane.setTopAnchor(button,60.0);
        AnchorPane.setLeftAnchor(button,200.0);
        Scene scene=new Scene(anchorPane,700,900);
        stage.setScene(scene);
        stage.show();



    }
    @Test
    void createItem(FxRobot robot) {

        Platform.runLater(()->{
            AtomicReference<String> value= new AtomicReference<>("");
      DropDownMenu dropDownMenu=new DropDownMenu(button);
            MenuItem menuitem1=dropDownMenu.createItem("Item1");
            menuitem1.setOnAction(event -> {
                value.set("item1");
            });
            dropDownMenu.createItem("Item2");
            SimulateEvents.clickOn(button);
            WaitForAsyncUtils.waitForFxEvents();
            menuitem1.fire();
            Assertions.assertEquals("item1",value.get());



        });

    }
}