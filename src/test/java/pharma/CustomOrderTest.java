package pharma;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.javafxlib.Dialog.CustomDialog;

@ExtendWith(ApplicationExtension.class)
public class CustomOrderTest {
    private  CustomDialog<FieldData> customDialog;
    @Start
    public void start (Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
        customDialog=new CustomDialog<>("Aggiungi Lotto");


        customDialog.show();
    }
    @Test
    public void test_choice(FxRobot robot){
        Platform.runLater(()-> {


           /* ObservableList<FieldData> fieldData= FXCollections.observableArrayList( FieldData.FieldDataBuilder.getbuilder().setNome("Au0 tachipirina").build(),
                            FieldData.FieldDataBuilder.getbuilder().setNome("Egk2 debridat 10 mg").build(),
                            FieldData.FieldDataBuilder.getbuilder().setNome("Au2 pantoman 10mg").build(),
                            FieldData.FieldDataBuilder.getbuilder().setNome("Au3 lucen 140mg").build());
            customDialog.add_label("Clicca il pulsante per aggiungere nuovi lotti");
          customDialog.add_Button("ComboBox", FieldData.FieldDataBuilder.getbuilder().setNome("Aggiungi Lotto").build(),fieldData);

            //customDialog.add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Aggiungi Lotti").build());


            */
        });
        robot.sleep(400000);
    }

}
