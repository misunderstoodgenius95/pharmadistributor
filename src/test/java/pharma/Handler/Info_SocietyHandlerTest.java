package pharma.Handler;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.Utility;
import pharma.config.database.Database;
import pharma.dao.InfoSocietyDao;
import pharma.javafxlib.test.SimulateEvents;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@ExtendWith(ApplicationExtension.class)
class Info_SocietyHandlerTest {
    private  Info_SocietyHandler infoSocietyHandler;
    private InfoSocietyDao societyDao;
    @Start
    public void start(Stage stage) {
        Scene scene = new Scene(new VBox(), 500, 700);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() throws IOException {
        Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        societyDao=new InfoSocietyDao(Database.getInstance(properties));
        Platform.runLater(()->{

             infoSocietyHandler=new Info_SocietyHandler("Aggiungi Info Azienda", societyDao);

            List<TextField> textFields=Utility.extract_value_from_list(infoSocietyHandler.getControlList(), TextField.class);
            System.out.println(textFields.size());
            SimulateEvents.writeOn(textFields.getFirst(),"Ultimate Pharma Distro");
            SimulateEvents.writeOn(textFields.get(1),"IT12345555555");
            SimulateEvents.writeOn(textFields.get(2),"Via  Cristoforo Colombo");
            SimulateEvents.writeOn(textFields.get(3),"98074");
            SimulateEvents.writeOn(textFields.get(4),"Naso");
            SimulateEvents.writeOn(textFields.get(5),"https://exmple.com/image/1234");
            SimulateEvents.writeOn(textFields.get(6),"https://example.com");
            SimulateEvents.writeOn(textFields.get(7),"info@example.com");
            SearchableComboBox<FieldData> searchableComboBox=Utility.extract_value_from_list(infoSocietyHandler.getControlList(), SearchableComboBox.class).getFirst();
            System.out.println(searchableComboBox.getValue().getNome());
            SimulateEvents.setFirstElementSearchableBox(searchableComboBox);

        });
    }

    @Test
    public void ValidIntegrationTesting(FxRobot robot){

        Platform.runLater(()->{
            infoSocietyHandler.execute();



        });
        robot.sleep(40000);
    }


}