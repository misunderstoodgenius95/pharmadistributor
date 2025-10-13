package pharma.Handler.Table;

import algoWarehouse.PlacementShelf;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class LotStorageViewTest {
@Start
    public void start(Stage stage){
    VBox vbox=new VBox();
    stage.setScene(new Scene(vbox));
    stage.show();




}
@Test
    public void test(FxRobot robot){
    Platform.runLater(()->{
        LotStorageView storageView=new LotStorageView("Visualizza");
        storageView.show();




    });
    robot.sleep(300000);


}




}