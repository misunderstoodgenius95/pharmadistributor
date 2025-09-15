package pharma.Handler;

import algo.PlacementShelf;
import com.github.tomakehurst.wiremock.common.TextFile;
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
import org.mockito.exceptions.base.MockitoAssertionError;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.Utility;
import pharma.dao.InfoSocietyDao;
import pharma.javafxlib.test.SimulateEvents;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pharma.config.Utility.extract_value_from_list;

@ExtendWith(ApplicationExtension.class)
class Info_SocietyHandlerTest {
    @Mock
    private InfoSocietyDao societyDao;
    @Start
    public void start(Stage stage) {
        Scene scene = new Scene(new VBox(), 500, 700);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void ValidFilledTest(FxRobot robot){
        Platform.runLater(()->{

            Info_SocietyHandler infoSocietyHandler=new Info_SocietyHandler("Aggiungi Info Azienda", List.of(societyDao));
            infoSocietyHandler.show();
            List<TextField> textFields=Utility.extract_value_from_list(infoSocietyHandler.getControlList(), TextField.class);
            System.out.println(textFields.size());
            SimulateEvents.writeOn(textFields.getFirst(),"Ultimate Pharma Distro");
            SimulateEvents.writeOn(textFields.get(1),"IT12345555555");
            SimulateEvents.writeOn(textFields.get(2),"Via  Cristoforo Colombo");
            SimulateEvents.writeOn(textFields.get(3),"98074");
            SimulateEvents.writeOn(textFields.get(4),"Naso");
            SearchableComboBox<FieldData> searchableComboBox=Utility.extract_value_from_list(infoSocietyHandler.getControlList(), SearchableComboBox.class).getFirst();
            System.out.println(searchableComboBox.getValue().getNome());
            SimulateEvents.setFirstElementSearchableBox(searchableComboBox);


        });
        robot.sleep(40000);

    }


}