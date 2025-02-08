package pharma.dao;

import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pharma.Controller.subpanel.Dettagli;
import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

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
        long value_ms=System.currentTimeMillis();
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(value_ms);
        calendar.add(Calendar.YEAR,4);

        FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setTipologia(1).
                setProduction_date(new Date(value_ms)).
               setElapsed_date(new Date(calendar.getTimeInMillis())).
                setPrice(20.00).setQuantity(100).build();
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Assertions.assertTrue(lottiDao.insert(fieldData));
        Mockito.verify(database).execute_prepared_query("INSERT INTO lotto (farmaco,production_date,elapsed_date,price,quantity) VALUES(?,?,?,?,?)  ");
        Mockito.verify(preparedStatement).setInt(1,1);
        Mockito.verify(preparedStatement).setDate(2,new Date(value_ms));
        Mockito.verify(preparedStatement).setDate(3,new Date(calendar.getTimeInMillis()));
        Mockito.verify(preparedStatement).setDouble(4,20.00);
        Mockito.verify(preparedStatement).setInt(5,100);

    }



}