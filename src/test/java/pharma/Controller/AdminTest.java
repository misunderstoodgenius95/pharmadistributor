package pharma.Controller;

import com.auth0.client.mgmt.UsersEntity;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.User;
import pharma.Stages;
import pharma.config.auth.UserService;
import pharma.javafxlib.test.SimulateEvents;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class AdminTest {
    Admin admin;
    @Mock
    UserService userService;


    @Start
    public void start(Stage primaryStage) throws IOException {
MockitoAnnotations.openMocks(this);
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/admin.fxml"));
       admin= new Admin(userService);
        loader.setController(admin);
        Scene scene = new Scene(loader.load());


        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();
    }

    @Test
    public void FoundTestRadioRole(FxRobot robot){
        User.TrustedMetadata t1=new User.TrustedMetadata("seller",true);
        User.Results r=new User.Results("user_id11111111", List.of(new User.Emails("user@example.com")),t1);
        r.setLast_access(Instant.now());
        User  user=new User(List.of(r));
        when(userService.get_user_byRole(eq("seller"))).thenReturn(user);

        Platform.runLater(()->{
            admin.getRadio_role().setSelected(true);
            SimulateEvents.setNElementChoiceBox(admin.getChoice_role_id(),1);
            SimulateEvents.clickOn(admin.getBtn_send_filter_id());


        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertTrue(admin.getAdminTable().isShowing());

       // robot.sleep(4000);
    }
    @Test
    public void FoundTestEmailRadio(FxRobot robot) {
        User.TrustedMetadata t1 = new User.TrustedMetadata("seller", true);
        User.Results r = new User.Results("user_id11111111", List.of(new User.Emails("user@example.com")), t1);
        r.setLast_access(Instant.now());
        User user = new User(List.of(r));
        when(userService.searchUserByEmail(Mockito.anyString())).thenReturn(user);

        Platform.runLater(() -> {


            admin.getRadio_email().setSelected(true);
            SimulateEvents.writeOn(admin.textfield_emai_id, "user@example.com");
           SimulateEvents.clickOn(admin.getBtn_send_filter_id());
        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertTrue(admin.getAdminTable().isShowing());

    }

        @Test
        public void NotFoundValidTestRadioRole(FxRobot robot){

        User  user=new User(List.of());
        when(userService.get_user_byRole(eq("seller"))).thenReturn(user);

        Platform.runLater(()->{

            admin.getRadio_role().setSelected(true);

            SimulateEvents.setNElementChoiceBox(admin.getChoice_role_id(),1);
            SimulateEvents.clickOn(admin.getBtn_send_filter_id());

        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertFalse(admin.property_esitProperty().get());

    }

    @Test
    public void NotFoundTestEmailRadio(FxRobot robot) {
        User user = new User(List.of());
        when(userService.searchUserByEmail(Mockito.anyString())).thenReturn(user);

        Platform.runLater(() -> {


            admin.getRadio_email().setSelected(true);
            SimulateEvents.writeOn(admin.textfield_emai_id, "user@example.com");
            SimulateEvents.clickOn(admin.getBtn_send_filter_id());



        });
        WaitForAsyncUtils.waitForFxEvents();
        Assertions.assertFalse(admin.property_esitProperty().get());
robot.sleep(40000);
    }

    @Test
    public void ValidInsert(FxRobot robot){
        Platform.runLater(()->{
          SimulateEvents.clickOn( admin.getBtn_add_id());



        });
        robot.sleep(4000);




    }

    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{




        });
        robot.sleep(40000000);




    }


}