package pharma.chat;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Stages;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class ChatPharmacistGuiTest {
    private  VBox box;
    private Scene scene;

    @Start
    public void start(Stage primaryStage) throws IOException {
         box=new VBox();

     scene=new Scene(box,600,600);
     primaryStage.setScene(scene);

        primaryStage.initStyle(StageStyle.DECORATED);
     primaryStage.show();


    }

    @Test
    void Prova(FxRobot robot){
        Platform.runLater(()->{

        GridPane gridPane=ChatGui.add_chat_container("Ciao","pharmacist", ChatGui.User_type.Pharmacist);
            GridPane gridPane2=ChatGui.add_chat_container("Ciao","Seller", ChatGui.User_type.Seller);
        box.getChildren().addAll(gridPane,gridPane2);






        });
        robot.sleep(3600000);

    }





}