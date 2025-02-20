package pharma.Handler;

import com.sun.source.tree.ModuleTree;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.config.Database;
import pharma.dao.LottiDao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class OrdiniHandlerTest {
    private OrdiniHandler ordiniHandler;
    private Database database;
    private LottiDao lottiDao;
    @Start
    public void start(Stage stage){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();



    }

    @BeforeEach
    public void setUp() throws SQLException {
        database=Mockito.mock(Database.class);
        lottiDao=new LottiDao(database,"lotto");

        ResultSet resultSet=Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true,true,true, false);
        Mockito.when(resultSet.getString("id")).thenReturn("agk1","agk2","agk3");
        Mockito.when(resultSet.getDate("production_date")).thenReturn( Date.valueOf(LocalDate.of(2026,10,01)),
                Date.valueOf(LocalDate.of(2024,10,01)),Date.valueOf(LocalDate.of(2025,10,01) )) ;
        Mockito.when(resultSet.getDate("elapsed_date")).thenReturn( Date.valueOf(LocalDate.of(2027,10,01)),
                Date.valueOf(LocalDate.of(2028,10,01)),Date.valueOf(LocalDate.of(2029,10,01)));
        Mockito.when(resultSet.getInt("quantity")).thenReturn(300,100,200);
        Mockito.when(resultSet.getString("nome")).thenReturn("Tachipirina","DebridAt","Pantopan");
        Mockito.when(resultSet.getString("tipologia")).thenReturn("Compresse","Compresse","Compresse");
        Mockito.when(resultSet.getString("misura")).thenReturn("100mg","10gr","30gr");
        Mockito.when(resultSet.getString("casa_farmaceutica")).thenReturn("Angelini","Afpt","Akg");
        Mockito.when(resultSet.getDouble("price")).thenReturn(6.50,11.50,3.40);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);


    }
    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{



            ordiniHandler=new OrdiniHandler(lottiDao);
            ordiniHandler.show();
        });
        robot.sleep(400000);
    }





}