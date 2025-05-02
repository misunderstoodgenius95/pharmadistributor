package pharma.Handler;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.javafxlib.test.SimulateEvents;
import pharma.config.Utility;


import java.util.List;

@ExtendWith(ApplicationExtension.class)
class FarmaciaHandlerTest {

    @Start
    public void  start(Stage stage){
        VBox vBox=new VBox();
        Scene scene=new Scene(vBox);
        stage.setScene(scene);


    }
    @Test
    public  void ValidHandler(FxRobot robot){
        Platform.runLater(()->{
            FarmaciaHandler farmaciaHandler=new FarmaciaHandler("Inserisci Casa Farmaceutica", List.of());

            List<TextField> textFields=Utility.extract_value_from_list(farmaciaHandler.getControlList(), TextField.class);
            System.out.println(textFields.size());
            textFields.getFirst().setText("Farmacia Collica");
            textFields.get(1).setText("IT12345555555");
            textFields.get(2).setText("Via Giud Calvacanti");
            textFields.get(3).setText("98074");
            textFields.get(4).setText("Capo d'Orlando");
            SearchableComboBox<FieldData> f_utility=Utility.extract_value_from_list(farmaciaHandler.getControlList(), SearchableComboBox.class).getFirst();
            f_utility.getSelectionModel().selectFirst();
            farmaciaHandler.show();





        });
        robot.sleep(4000000);


    }
    @Test
    public  void InValidHandler(FxRobot robot){
        Platform.runLater(()->{
            FarmaciaHandler farmaciaHandler=new FarmaciaHandler("Inserisci Casa Farmaceutica", List.of());

            List<TextField> textFields=Utility.extract_value_from_list(farmaciaHandler.getControlList(), TextField.class);
            System.out.println(textFields.size());
            textFields.getFirst().setText("Farmacia Collica");
            textFields.get(1).setText("IT123455055555");
            textFields.get(2).setText("Via Giud Calvacanti");
            textFields.get(3).setText("98074");
            textFields.get(4).setText("Capo d'Orlando");
            SearchableComboBox<FieldData> f_utility=Utility.extract_value_from_list(farmaciaHandler.getControlList(), SearchableComboBox.class).getFirst();
            f_utility.getSelectionModel().selectFirst();
            farmaciaHandler.show();





        });
        robot.sleep(4000000);


    }

    @Test
    public  void ValidHandlerResult(FxRobot robot){
        Platform.runLater(()->{
            FarmaciaHandler farmaciaHandler=new FarmaciaHandler("Inserisci Casa Farmaceutica", List.of());

            List<TextField> textFields=Utility.extract_value_from_list(farmaciaHandler.getControlList(), TextField.class);
            System.out.println(textFields.size());
            textFields.getFirst().setText("Farmacia Collica");
            textFields.get(1).setText("IT12345555555");
            textFields.get(2).setText("Via Giud Calvacanti");
            textFields.get(3).setText("98074");
            textFields.get(4).setText("Capo d'Orlando");
            SearchableComboBox<FieldData> f_utility=Utility.extract_value_from_list(farmaciaHandler.getControlList(), SearchableComboBox.class).getFirst();
            f_utility.getSelectionModel().selectFirst();



           farmaciaHandler.showAndWait().ifPresent(fieldData -> {
                Assertions.assertEquals("Farmacia Collica",fieldData.getAnagrafica_cliente());
                Assertions.assertEquals("IT12345555555",fieldData.getPartita_iva());
                Assertions.assertEquals("Via Giud Calvacanti",fieldData.getStreet());
                Assertions.assertEquals(98074,fieldData.getCap());
                Assertions.assertEquals("Capo d'Orlando",fieldData.getComune());
                Assertions.assertEquals("AG",fieldData.getProvince());

            });
            Platform.runLater(()->{

                SimulateEvents.clickOn(farmaciaHandler.getButtonOK());
            });




        });
        robot.sleep(4000000);


    }
    @Test
    public  void InValidHandlerResult(FxRobot robot){
        Platform.runLater(()->{
            FarmaciaHandler farmaciaHandler=new FarmaciaHandler("Inserisci Casa Farmaceutica", List.of());

            List<TextField> textFields=Utility.extract_value_from_list(farmaciaHandler.getControlList(), TextField.class);
            System.out.println(textFields.size());
            textFields.getFirst().setText("Farmacia Collica");
            textFields.get(1).setText("IT12345555555");
            textFields.get(2).setText("Via Giud Calvacanti");
            textFields.get(3).setText("98074");
            textFields.get(4).setText("Capo d'Orlando");
            SearchableComboBox<FieldData> f_utility=Utility.extract_value_from_list(farmaciaHandler.getControlList(), SearchableComboBox.class).getFirst();
            f_utility.getSelectionModel().selectFirst();



            farmaciaHandler.showAndWait().ifPresent(fieldData -> {
                Assertions.assertEquals("Farmacia Collica",fieldData.getAnagrafica_cliente());
                Assertions.assertEquals("IT1234555555",fieldData.getPartita_iva());
                Assertions.assertEquals("Via Giud Calvacanti",fieldData.getStreet());
                Assertions.assertEquals(9874,fieldData.getCap());
                Assertions.assertEquals("Capo d'Orlando",fieldData.getComune());
                Assertions.assertEquals("AG",fieldData.getProvince());

            });
            Platform.runLater(()->{

                SimulateEvents.clickOn(farmaciaHandler.getButtonOK());
            });




        });
        robot.sleep(4000000);


    }

    @Test
    public  void ValidHandlerResultExecute(FxRobot robot){
        Platform.runLater(()->{
            FarmaciaHandler farmaciaHandler=new FarmaciaHandler("Inserisci Casa Farmaceutica", List.of());

            List<TextField> textFields=Utility.extract_value_from_list(farmaciaHandler.getControlList(), TextField.class);
            System.out.println(textFields.size());
            textFields.getFirst().setText("Farmacia Collica");
            textFields.get(1).setText("IT12345555555");
            textFields.get(2).setText("Via Giud Calvacanti");
            textFields.get(3).setText("98074");
            textFields.get(4).setText("Capo d'Orlando");
            SearchableComboBox<FieldData> f_utility=Utility.extract_value_from_list(farmaciaHandler.getControlList(), SearchableComboBox.class).getFirst();
            f_utility.getSelectionModel().selectFirst();
            farmaciaHandler.execute();




        });
        robot.sleep(4000000);


    }

}