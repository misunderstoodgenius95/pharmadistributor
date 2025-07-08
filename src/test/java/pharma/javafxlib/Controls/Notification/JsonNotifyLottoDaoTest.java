package pharma.javafxlib.Controls.Notification;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.HttpExpireItemTest;
import pharma.Model.FieldData;
import pharma.Stages;
import pharma.dao.LottiDao;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class JsonNotifyLottoDaoTest {
    private  JsonNotifyLottoDao json_dao;
    @Mock
    private LottiDao lottiDao;
    @Start
    public void start(Stage stage){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();


    }
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        String json=HttpExpireItemTest.get_json_array().toString();

    }


    @Test
    public  void ValidTest(FxRobot robot){
        JSONObject json1 = new JSONObject();
        json1.put("lot_id", "11a");
        json1.put("product_id", 60);
        json1.put("expiration_date", "10/10/2028");

        json_dao=new JsonNotifyLottoDao(json1.toString(), List.of("lot_id"),"Lotti Scaduto","Avviso Scadenza Lotti",lottiDao);
        Platform.runLater(()->{
            FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setcode("11a").setNome("Tachipirina").setUnit_misure("100mg").
                    setNome_tipologia("Capsule").build();

            Mockito.when(lottiDao.findByIds(Mockito.anyInt(),Mockito.anyString())).thenReturn(fieldData);
            json_dao.execute();



        });
        robot.sleep(4000);


    }



}