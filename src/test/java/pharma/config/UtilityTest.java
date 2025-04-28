package pharma.config;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import pharma.Model.FieldData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class UtilityTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox=new VBox();
        vBox.getChildren().add(new Button("Hi!"));
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void create_alert() {
        Utility.create_alert(Alert.AlertType.WARNING,"aa","bbb");

    }

    @Test
    void Validextract_value_from_list() {
        List<Control> controls=new ArrayList<>(Arrays.asList(new Button(),new TextField("hi")));
        List<TextField> textFields=Utility.extract_value_from_list(controls,TextField.class);
        Assertions.assertEquals("hi",textFields.getFirst().getText());
    }
    @Test
    void InvValidextract_value_from_list() {
        List<Control> controls=new ArrayList<>(Arrays.asList(new Button(),new TextField("hi")));
        List<TextField> textFields=Utility.extract_value_from_list(controls,TextField.class);
        Assertions.assertNotEquals("Ciao",textFields.getFirst().getText());
    }

    @Test
    void ValidIntegrationExtract_province() {
        try {
            String json_string=new String(Files.readAllBytes(Path.of("src/main/resources/json_file/province.json")));
            List<FieldData> list=Utility.extract_province(json_string);
            Assertions.assertFalse(list.isEmpty());
            Assertions.assertEquals("Agrigento",list.getFirst().getProvince());
            Assertions.assertEquals("AG",list.getFirst().getSigla());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}