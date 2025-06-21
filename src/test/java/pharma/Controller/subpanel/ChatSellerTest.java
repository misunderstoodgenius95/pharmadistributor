package pharma.Controller.subpanel;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Handler.ChatHandler;
import pharma.Stages;
import pharma.test2.ChatServer;

import java.io.IOException;

import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ChatSellerTest {
    ChatHandler chatHandler;
    private  Scene scene;
    @Start
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("seller");
        Stages stage = new Stages();
        String token_seller="eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NTAwODM2NzgsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLTcyZmI3ZGY0LTVkMmItNDNmOC05YWY1LTMyZDU1ZmNmNzNkZiIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA2LTE2VDE0OjE2OjE4WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA2LTE2VDE0OjE2OjE4WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA2LTE2VDE4OjE2OjE4WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNi0xNlQxNDoxNjoxOFoifV19LCJpYXQiOjE3NTAwODMzNzgsImlzcyI6Imh0dHBzOi8vbG9naW4uYXBpbWUub25saW5lIiwibmJmIjoxNzUwMDgzMzc4LCJzdWIiOiJ1c2VyLWxpdmUtNWFjM2U5YmQtZWEwMy00MmEwLWFkMGItZGZlMjRlZTExMjg2In0.0XShP1XWlc8a4HMUSLm1EEmnyGpblUpekI62Qmm9YE4VyjVdASYr6N5grQeXvZxIxOtlpQ25NCbDhb3ehSdjDO-eaSdn2mThg8D59FTROhOEBlySBqEPaB1J_WyDHE2VmvnsVqIiructpUcTeCRBky50VXRjwhyQC_8vD94CcMkaTrqLzPHJAmm_eajYyN8srO_iccccgqDWv_S57EQMaPQZSGG7HoILJsQe9mX2IA9hq6L8Is5p44liKEN76gosRXPN_uouoCp5VtMJLF4c50zaP52IaIWeLs7x8zqiNQCt9zseeozhXUn0f0DIJcJfYes9zlTv80a2gh87z-skcg\n";
        ChatSeller chat_seller =new ChatSeller(token_seller);

       FXMLLoader load = stage.load("/subpanel/chat.fxml");
       load.setController(chat_seller);
        scene = new Scene(load.load(),1000,800);

        primaryStage.initStyle(StageStyle.DECORATED);

        // 2. Enable resizing
        primaryStage.setResizable(true);

        // 3. Set only minimum constraints
      //  primaryStage.setMinWidth(300);
        //primaryStage.setMinHeight(200);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{




        });
        robot.sleep(3600000);

    }
}