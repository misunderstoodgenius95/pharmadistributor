package pharma.DialogController.Table;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.dao.LotAssigmentDao;
import pharma.dao.LotAssigmentShelvesDao;

@ExtendWith(ApplicationExtension.class)
class LotStorageViewTest {
    @Mock
    private LotAssigmentDao assigmentDao;
    @Mock
    private LotAssigmentShelvesDao assigmentShelvesDao;
    @Start
    public void start(Stage stage){
    MockitoAnnotations.openMocks(this);
    VBox vbox=new VBox();
    stage.setScene(new Scene(vbox));
    stage.show();




}
@Test
    public void test(FxRobot robot){
    Platform.runLater(()->{
        LotStorageView storageView=new LotStorageView("Visualizza",assigmentShelvesDao);
        storageView.show();




    });
    robot.sleep(300000);


}




}