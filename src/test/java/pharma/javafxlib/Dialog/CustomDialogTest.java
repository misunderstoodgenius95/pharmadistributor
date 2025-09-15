package pharma.javafxlib.Dialog;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.javafxlib.FileChoseOption;
import pharma.javafxlib.test.SimulateEvents;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class CustomDialogTest {
    CustomDialog<FieldData> fieldDataCustomDialog;

    @Start
    public void start(Stage stage){
        Scene scene=new Scene(new VBox(),500,700);
        stage.setScene(scene);
        stage.show();
    fieldDataCustomDialog=new CustomDialog<>("Test");
}
@Test
    public void testFileOpen(FxRobot robot){
    Platform.runLater(()->{
        FileChoseOption choseOption =fieldDataCustomDialog.add_file_to_target_path("src/logo/", List.of(new FileChooser.ExtensionFilter("Image","*.jpg,*.png")));
        fieldDataCustomDialog.show();

        SimulateEvents.clickOn(choseOption.getButton_insert());


    });


        robot.sleep(600000000);



}






}