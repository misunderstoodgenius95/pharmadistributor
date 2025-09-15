package pharma.Handler;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.SearchableComboBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.config.Utility;
import pharma.config.auth.UserService;
import pharma.config.auth.UserServiceResponse;
import pharma.dao.FarmaciaDao;
import pharma.dao.PharmaDao;
import pharma.javafxlib.test.SimulateEvents;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class PharmacistHandlerCreateTest {
    @Mock
    private FarmaciaDao farmaciaDao;
    @Mock
    UserService userService;

    @Start
    public void start(Stage stage){
        VBox vBox=new VBox();
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.show();



        // 2. Enable resizing
        stage.setResizable(true);




    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test(FxRobot robot){



        Platform.runLater(()->{
           FieldData fieldData= FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Farmacia Del Corso").
                    setPartita_iva("IT11111111111").setStreet("Via guido Cavalcanti").
                    setCap(12345).setComune("Roma").setProvince("RM").build();
            FieldData fieldData2 = FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Farmacia San Marco").
                    setPartita_iva("IT22222222222").setStreet("Corso Venezia").
                    setCap(20121).setComune("Milano").setProvince("MI").build();

            FieldData fieldData3 = FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Farmacia Centrale").
                    setPartita_iva("IT33333333333").setStreet("Via Dante Alighieri").
                    setCap(50123).setComune("Firenze").setProvince("FI").build();

            FieldData fieldData4 = FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Farmacia Nuova").
                    setPartita_iva("IT44444444444").setStreet("Via Giuseppe Garibaldi").
                    setCap(80138).setComune("Napoli").setProvince("NA").build();

            FieldData fieldData5 = FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Farmacia Europa").
                    setPartita_iva("IT55555555555").setStreet("Via Alessandro Manzoni").
                    setCap(10121).setComune("Torino").setProvince("TO").build();



           when(farmaciaDao.findAll()).thenReturn(List.of(fieldData,fieldData2,fieldData3,fieldData4,fieldData5));
           when(userService.register(anyString(),anyString(),anyString(),anyString(),anyString())).thenReturn(new UserServiceResponse("",200));
            PharmacistHandlerCreate pharmacistHandlerCreate=new PharmacistHandlerCreate( farmaciaDao, userService);

            List<TextField> list_textField= Utility.extract_value_from_list(pharmacistHandlerCreate.getControlList(), TextField.class);
            list_textField.get(0).setText("luigi.neri_azienda@proton.me");
            list_textField.get(1).setText("Luigi");
            list_textField.get(2).setText("Neri");
            SearchableComboBox<FieldData> choiceBox=Utility.extract_value_from_list(pharmacistHandlerCreate.getControlList(), SearchableComboBox.class).getFirst();
            SimulateEvents.setFirstElementSearchableBox(choiceBox);
            SimulateEvents.clickOn(pharmacistHandlerCreate.getButtonOK());
            pharmacistHandlerCreate.executeStatus();






        });
        robot.sleep(40000000);

    }

}