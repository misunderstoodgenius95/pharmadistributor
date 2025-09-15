package pharma.Handler;

import algo.PlacementShelf;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.Status;

import java.util.Optional;

@ExtendWith(ApplicationExtension.class)
class DialogHandleTest {
    DialogHandler dialogHandler;
    @Start
    public void start(Stage stage) {
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
         dialogHandler = new DialogHandler<FieldData>() {
            @Override
            protected void initialize() {

            }

            @Override
            protected void initialize(Optional PopulateChoice, Optional optionalgenericJDBCDao, Optional optionalfieldData) {

            }

            @Override
            protected FieldData get_return_data() {
                return null;
            }

             @Override
             protected boolean condition_event(FieldData type) throws Exception {
                 return false;
             }

             @Override
             protected Status condition_event_status(FieldData type) throws Exception {
                 return null;
             }


         };


    }
    @Test
    public void TestFile(FxRobot robot){
        Platform.runLater(()->{
            dialogHandler.addButton("a");
            //dialogHandler.add_file_to_target_path("mio");

        });
     robot.sleep(40000);

    }


}