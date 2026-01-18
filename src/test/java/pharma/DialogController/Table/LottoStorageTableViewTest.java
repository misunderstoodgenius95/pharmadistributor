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
import pharma.Model.FieldData;
import pharma.dao.LotAssigmentDao;
import pharma.dao.LotAssigmentShelvesDao;

import java.sql.Date;

@ExtendWith(ApplicationExtension.class)
class LottoStorageTableViewTest {
    @Mock
    private LotAssigmentDao lotAssigmentDao;
    @Mock
    private LotAssigmentShelvesDao lotAssigmentShelvesDao;
    @Start
    public void start(Stage stage) {
        MockitoAnnotations.openMocks(this);
        Scene scene = new Scene(new VBox(), 500, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void test(FxRobot robot) {
        Platform.runLater(() -> {




            FieldData instance1 = FieldData.FieldDataBuilder.getbuilder()
                    .setcode("MX100")
                    .setProduction_date(Date.valueOf("2025-01-01"))
                    .setElapsed_date(Date.valueOf("2027-01-01"))
                    .setNome("Tachipirina")
                    .setNome_categoria("Antibiotico")
                    .setNome_tipologia("Compresse")
                    .setUnit_misure("120 ml")
                    .setNome_casa_farmaceutica("Bayer")
                    .setFarmaco_id(1)
                    .setNome_principio_attivo("Paracetamolo")
                    .setCasa_Farmaceutica(1)
                    .setQuantity(10)
                    .build();

            FieldData instance2 = FieldData.FieldDataBuilder.getbuilder()
                    .setcode("MX200")
                    .setProduction_date(Date.valueOf("2025-02-01"))
                    .setElapsed_date(Date.valueOf("2027-02-01"))
                    .setNome("Monopril")
                    .setNome_categoria("Antipertensivo")
                    .setNome_tipologia("Compresse")
                    .setUnit_misure("5 mg")
                    .setNome_casa_farmaceutica("Novartis")
                    .setFarmaco_id(2)
                    .setNome_principio_attivo("Fosinopril_sodico")
                    .setCasa_Farmaceutica(2)
                    .setQuantity(15)
                    .build();

            FieldData instance3 = FieldData.FieldDataBuilder.getbuilder()
                    .setcode("LP200")

                    .setProduction_date(Date.valueOf("2025-03-01"))
                    .setElapsed_date(Date.valueOf("2027-03-01"))
                    .setNome("Oki")
                    .setNome_categoria("Antinfiammatorio")
                    .setNome_tipologia("Supposte")
                    .setUnit_misure("50 mg")
                    .setNome_casa_farmaceutica("Melarini")
                    .setFarmaco_id(3)
                    .setNome_principio_attivo("Ketoprofene")
                    .setCasa_Farmaceutica(3)
                    .setQuantity(8)
                    .build();

            FieldData instance4 = FieldData.FieldDataBuilder.getbuilder()
                    .setcode("xl100")
                    .setProduction_date(Date.valueOf("2025-04-01"))
                    .setElapsed_date(Date.valueOf("2027-04-01"))
                    .setNome("Zoloft")
                    .setNome_categoria("Antidepressivo")
                    .setNome_tipologia("Compresse")
                    .setUnit_misure("100 mg")
                    .setNome_casa_farmaceutica("Mylano")
                    .setFarmaco_id(4)
                    .setNome_principio_attivo("Sertralina")
                    .setCasa_Farmaceutica(8)
                    .setQuantity(12)
                    .build();
            LottoStorageTableView storageTable = new LottoStorageTableView("Visualizza Lotti",lotAssigmentDao,lotAssigmentShelvesDao);
            storageTable.show();
        });
        robot.sleep(50000);

    }
}