package pharma.DialogController.Report;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.Acquisto;
import pharma.Model.FieldData;
import pharma.dao.FarmacoDao;

import java.sql.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class PiccoDialogTest {
    @Mock
    public FarmacoDao farmacoDao;
    @Start
    public void Start(Stage stage){
        VBox vBox=new VBox();
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void Emptytest(FxRobot robot){
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setId(1).setNome("Tachipirina").setDescription("Febbre").
                setNome_categoria("Antiinfiammatorio").setNome_tipologia("Compresse").setUnit_misure("100mg").setNome_principio_attivo("Paracetamolo").
                setNome_casa_farmaceutica("Angelini").build();
        when(farmacoDao.findByName(Mockito.anyString())).thenReturn(List.of(fieldData));
        Platform.runLater(()->{
         PiccoDialog piccoDialog =new PiccoDialog("",List.of(),farmacoDao);
         piccoDialog.show();
        });
        robot.sleep(40000);




    }

    @Test
    public void ValidPicco(FxRobot robot){

        List<Acquisto> acquistoList=List.of(
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2024-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2021-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2022-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 100, Date.valueOf("2026-01-05"), 8.50),
                new Acquisto(2, "Aspirina", 85, Date.valueOf("2024-01-06"), 6.30),
                new Acquisto(2, "Aspirina", 80, Date.valueOf("2024-01-08"), 10.20),
                new Acquisto(1, "Tachipirina", 50, Date.valueOf("2024-01-10"), 8.50),
                new Acquisto(3, "Brufen", 78, Date.valueOf("2024-01-12"), 9.80),
                new Acquisto(1, "Tachipirina", 300, Date.valueOf("2024-01-15"), 8.50),
                new Acquisto(9, "Bisolvon", 110, Date.valueOf("2024-01-18"), 11.30));

        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setId(1).setNome("Tachipirina").setDescription("Febbre").
                setNome_categoria("Antiinfiammatorio").setNome_tipologia("Compresse").setUnit_misure("100mg").setNome_principio_attivo("Paracetamolo").
                setNome_casa_farmaceutica("Angelini").build();
        when(farmacoDao.findByName(Mockito.anyString())).thenReturn(List.of(fieldData));
        Platform.runLater(()->{
            PiccoDialog piccoDialog =new PiccoDialog("",acquistoList,farmacoDao);
            piccoDialog.show();
        });
        robot.sleep(4000000);




    }
}