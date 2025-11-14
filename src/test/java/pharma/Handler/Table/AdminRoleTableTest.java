package pharma.Handler.Table;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.User;
import pharma.config.auth.UserService;
import pharma.test2.ThreadServerManager;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class AdminRoleTableTest {
    private Button button;
    AdminTable adminTable;
    @Start
    public void start(Stage stage) {
        VBox vBox = new VBox();
        button=new Button("Invia");
        vBox.getChildren().addAll(button);
        Scene scene = new Scene(vBox,700,800);
        stage.setScene(scene);
        stage.show();
        adminTable=new AdminTable("Add Table");



    }
    @BeforeEach
    public void setup(){





    }
    @Test
    public void test(FxRobot robot){

        Platform.runLater(()->{
            button.setOnAction(event -> {
                User.TrustedMetadata t1=new User.TrustedMetadata("seller",true);
                User.Results r=new User.Results("user1111111", List.of(new User.Emails("user@example.com")),t1);
                r.setLast_access(Instant.now());
                User  user=new User(List.of(r));
                adminTable.setUser_property(user);
                adminTable.showAndWait();


            });




          /*
            adminTable.setUser_property(user);
            adminTable.close();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            adminTable.show();
            adminTable.setUser_property(user1);*/


        });
        robot.sleep(10000000);
    }






}