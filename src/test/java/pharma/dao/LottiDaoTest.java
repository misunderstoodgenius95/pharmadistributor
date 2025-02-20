package pharma.dao;

import com.sun.source.tree.ModuleTree;
import net.datafaker.Faker;
import net.jodah.failsafe.internal.util.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pharma.Controller.subpanel.Dettagli;
import pharma.Controller.subpanel.Lotti;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.Database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LottiDaoTest {
    private  Database database;
    private LottiDao lottiDao;
    @BeforeEach
    public void setUp(){
        database= Mockito.mock(pharma.config.Database.class);
        lottiDao=new LottiDao(database,"lotto");

    }
    @Test
    public void ValidInsert() throws SQLException {
        PreparedStatement preparedStatement=Mockito.mock(PreparedStatement.class);


        FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setLotto_id("b9188j").setTipologia(1).
                setProduction_date(Date.valueOf(LocalDate.of(2024,10,10))).
               setElapsed_date(Date.valueOf(LocalDate.of(2025,10,01))).
                setPrice(20.00).setVat_percent(10).setQuantity(100).build();
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Assertions.assertTrue(lottiDao.insert(fieldData));
        Mockito.verify(database).execute_prepared_query("INSERT INTO lotto (id,farmaco,production_date,elapsed_date,price,vat_percent,quantity) VALUES(?,?,?,?,?,?,?)  ");
        Mockito.verify(preparedStatement).setString(1,"b9188j");
        Mockito.verify(preparedStatement).setInt(2,1);
        Mockito.verify(preparedStatement).setDate(3,Date.valueOf(LocalDate.of(2024,10,10)));
        Mockito.verify(preparedStatement).setDate(4,Date.valueOf(LocalDate.of(2025,10,01)));
        Mockito.verify(preparedStatement).setDouble(5,20.00);
        Mockito.verify(preparedStatement).setInt(6,10);
        Mockito.verify(preparedStatement).setInt(7,100);

    }
    @Test
    public void ValidFindAll() throws SQLException {
        ResultSet resultSet=Mockito.mock(ResultSet.class);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true,false);
        Mockito.when(resultSet.getString("id")).thenReturn("aaa");
        Mockito.when(resultSet.getDate("production_date")).thenReturn( Date.valueOf(LocalDate.of(2024,10,01)));
        Mockito.when(resultSet.getDate("elapsed_date")).thenReturn( Date.valueOf(LocalDate.of(2025,10,01)));
        Mockito.when(resultSet.getInt("quantity")).thenReturn(300);
        Mockito.when(resultSet.getString("nome")).thenReturn("Tachipirina");
        Mockito.when(resultSet.getString("tipologia")).thenReturn("Compresse");
        Mockito.when(resultSet.getString("misura")).thenReturn("100mg");
        Mockito.when(resultSet.getString("casa_farmaceutica")).thenReturn("Angelini");
        Mockito.when(resultSet.getDouble("price")).thenReturn(6.50);
        List<FieldData> fieldData = lottiDao.findAll();
        Assertions.assertEquals("Tachipirina",fieldData.get(0).getNome());
       Mockito.verify(resultSet).getString("id");
        Mockito.verify(resultSet).getDate("production_date");
        Assertions.assertEquals(Date.valueOf(LocalDate.of(2024,10,01)),fieldData.getFirst().getProduction_date());
        Assertions.assertEquals(Date.valueOf(LocalDate.of(2025,10,01)),fieldData.getFirst().getElapsed_date());
        Assertions.assertEquals(300,fieldData.getFirst().getQuantity());
        Assertions.assertEquals("Tachipirina",fieldData.getFirst().getNome());
        Assertions.assertEquals("Compresse",fieldData.getFirst().getNome_tipologia());
        Assertions.assertEquals("100mg",fieldData.getFirst().getUnit_misure());
        Assertions.assertEquals(6.50,fieldData.getFirst().getPrice());

    }


    @AfterEach
    public void tearDown() {
        database = null;
        lottiDao=null;
    }

    @Test
    public void ValidIntegrationTestingFind() {
        Properties properties = null;
        LottiDao lottiDao = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            lottiDao = new LottiDao(Database.getInstance(properties), "lotto");
           List<FieldData> lotti=lottiDao.findAll();
            System.out.println(lotti.getFirst().getLotto_id());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void ValidIntegrationInsert() {
        Properties properties = null;
        LottiDao lottiDao = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            lottiDao = new LottiDao(Database.getInstance(properties), "lotto");
             boolean value=lottiDao.insert(FieldData.FieldDataBuilder.getbuilder().setLotto_id("aaa").setTipologia(60).
                    setProduction_date(Date.valueOf(LocalDate.of(2024,10,01)))
                     .setElapsed_date(Date.valueOf(LocalDate.of(2025,01,9))).setQuantity(100).setPrice(10.50).setQuantity(300).build());

        Assertions.assertTrue(value);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}