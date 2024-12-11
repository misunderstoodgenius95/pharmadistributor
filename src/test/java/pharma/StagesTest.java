package pharma;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class StagesTest {

    @Test
    void load_stage() throws IOException {

Stages stages = new Stages();
assertNotNull(stages.load_fxml("/subpanel/pharma.fxml"));
    }

}