package pharma.dao;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.config.Utility;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.AccessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class DetailDaoTest {
    private  Database database;
    private DetailDao detailDao;
    @BeforeEach
    public void setUp(){
     database= Mockito.mock(Database.class);
      detailDao=new DetailDao(database);
    }

    @Test
    public void InsertInvalidTableCategoria() throws SQLException {
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setNome("Antibiotico").build();
        PreparedStatement preparedStatement=Mockito.mock(PreparedStatement.class);
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

        Assertions.assertThrows(AccessException.class,()->{
            detailDao.getInsertQuery();

            detailDao.insert(fieldData);
            detailDao.setInsertParameter(Mockito.any(PreparedStatement.class),Mockito.any(FieldData.class));
        });

    }
    @Test
    public void InsertValidTableCategoria() throws SQLException {
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setNome("Antibiotico").build();
        PreparedStatement preparedStatement=Mockito.mock(PreparedStatement.class);
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        detailDao.setTable_name(Utility.Categoria);


//Act
            Assertions.assertTrue(detailDao.insert(fieldData));
    //Verify
        Mockito.verify(database).execute_prepared_query(" INSERT INTO "+ Utility.Categoria+" (nome) VALUES(?); ");
        Mockito.verify(preparedStatement).setString(1,"Antibiotico");



    }
    @Test
    public void InsertValidTableTipologia() throws SQLException {
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setNome("Compresse").build();
        PreparedStatement preparedStatement=Mockito.mock(PreparedStatement.class);
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        detailDao.setTable_name(Utility.Tipologia);


//Act
        Assertions.assertTrue(detailDao.insert(fieldData));
        //Verify
        Mockito.verify(database).execute_prepared_query(" INSERT INTO "+ Utility.Tipologia+" (nome) VALUES(?); ");
        Mockito.verify(preparedStatement).setString(1,"Compresse");



    }
    @Test
    public void InsertValidMiusure() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);


        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setUnit_misure("ml").setQuantity(120).build();
        detailDao.setTable_name(Utility.Misura);


//Act
        Assertions.assertTrue(detailDao.insert(fieldData));
        //Verify

        Mockito.verify(database).execute_prepared_query(" INSERT INTO " + Utility.Misura + " (quantity,unit) VALUES(?,?); ");
        Mockito.verify(preparedStatement).setInt(1, 120);
        Mockito.verify(preparedStatement).setString(2, "ml");

    }

    @Test
    public void InvalidInsertNameIsInserted() throws SQLException {

        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setNome("Antibiotico").build();
        PreparedStatement preparedStatement=Mockito.mock(PreparedStatement.class);




        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1).thenReturn(0);
        detailDao.setTable_name(Utility.Categoria);


//Act
        Assertions.assertTrue(detailDao.insert(fieldData));
        Assertions.assertTrue(detailDao.insert(fieldData));
        //Verify
        Mockito.verify(database, Mockito.times(2)).execute_prepared_query(" INSERT INTO "+ Utility.Categoria+" (nome) VALUES(?); ");
        Mockito.verify(preparedStatement,Mockito.times(2)).setString(1,"Antibiotico");


    }

    @Test
    void ValidfindAll() throws SQLException {

        ResultSet resultSet=Mockito.mock(ResultSet.class);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
       Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
       Mockito.when(resultSet.getString(2)).thenReturn("Antibiotico");
                detailDao.setTable_name(Utility.Categoria);
            List<FieldData> fieldData = detailDao.findAll();
            Assertions.assertEquals("Antibiotico",fieldData.get(0).getNome());
    }



// Integration
    @AfterEach
    public  void tearDown(){
        database=null;
        detailDao=null;


    }
    @Test
    public void ValidInterationTestNotMisure(){
        Properties properties= null;
       DetailDao detailDao=null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));

            detailDao=new DetailDao(Database.getInstance(properties));
        } catch (IOException e) {
            e.printStackTrace();
        }
        detailDao.setTable_name(Utility.Categoria);
        FieldData fd=FieldData.FieldDataBuilder.getbuilder().setNome("Antibiotico").build();
        Assertions.assertTrue(detailDao.insert(fd));


    }
    @Test
    public void ValidInterationTestMisure(){
        Properties properties= null;
        DetailDao detailDao=null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));

            detailDao=new DetailDao(Database.getInstance(properties));
        } catch (IOException e) {
            e.printStackTrace();
        }
        detailDao.setTable_name(Utility.Misura);
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setUnit_misure("ml").setQuantity(120).build();
        Assertions.assertTrue(detailDao.insert(fieldData));


    }



}