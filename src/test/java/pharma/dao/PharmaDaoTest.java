package pharma.dao;


import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PharmaDaoTest {
    private  PharmaDao pharmaDao;
    private  Database database;
    @BeforeEach
    public void setUp() throws SQLException {
         database= Mockito.mock(Database.class);
        pharmaDao=new PharmaDao(database);

    }
    @Test
    public void ValidInsert() throws SQLException {
        PreparedStatement preparedStatement= Mockito.mock(PreparedStatement.class);


        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setPartita_iva("11111").setSigla("MM").setAnagrafica_cliente("Melarini").build();

            Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);


            Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);

            boolean value=pharmaDao.insert(fieldData);
            Assertions.assertTrue(value);

        Mockito.verify(preparedStatement,Mockito.times(1)).executeUpdate();
        Mockito.verify(preparedStatement).setString(1,"Melarini");
        Mockito.verify(preparedStatement).setString(2,"MM");
        Mockito.verify(preparedStatement).setString(3,"11111");
    }

    @Test
    public void findALlName() throws SQLException {
        ResultSet result_pharma=Mockito.mock(ResultSet.class);
        when(result_pharma.next()).thenReturn(true,true,true,false);
        when(result_pharma.getInt(1)).thenReturn(1,2,3);
        when(result_pharma.getString(2)).thenReturn("Angelini","Melarini","Bayer");
        when(database.executeQuery(anyString())).thenReturn(result_pharma);

        List<FieldData> list=pharmaDao.findAllName();
        Assertions.assertEquals(1,list.getFirst().getId());
        Assertions.assertEquals("Angelini",list.getFirst().getNome_casa_farmaceutica());


    }
    @Test
    public void InvalidInsertNull() throws SQLException {
        PreparedStatement preparedStatement= Mockito.mock(PreparedStatement.class);



        Mockito.when(preparedStatement.executeUpdate()).thenReturn(0);


        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);


        Assertions.assertThrows(IllegalArgumentException.class,()->{

           pharmaDao.insert(null);
        });

      Mockito.verify(preparedStatement,Mockito.times(0)).executeUpdate();

    }

    @Test
    public void get_connection() throws IOException, SQLException {
        Properties properties= FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
        database = Database.getInstance(properties);
        Assertions.assertNotNull(database.get_connection().prepareStatement(Mockito.anyString()));
    }



    @Test
    public void ValidFindAll() throws SQLException {
        ResultSet resultSet=Mockito.mock(ResultSet.class);

        Faker faker=new Faker();

      Mockito.when(resultSet.next()).thenReturn(true,true,true,false);
      Mockito.when(resultSet.getString(2)).thenReturn("Bayer","Novartis","Angelini");
      Mockito.when(resultSet.getString(3)).thenReturn("BY","NV","AN");
      Mockito.when(resultSet.getString(4)).thenReturn(faker.number().digits(11),
              faker.number().digits(9),faker.number().digits(9));
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
  List<FieldData> fieldDatas=pharmaDao.findAll();
        System.out.println(fieldDatas.size());

  fieldDatas.forEach(fieldData -> {
      System.out.println(fieldData.getAnagrafica_cliente());
      System.out.println(fieldData.getSigla());
      System.out.println(fieldData.getPartita_iva());

  });

    }

    @Test
    public void ValidUpate() throws SQLException {
        PreparedStatement preparedStatement= Mockito.mock(PreparedStatement.class);
    FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setId(1).setAnagrafica_cliente("Melarini").build();
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
    Assertions.assertTrue(pharmaDao.update(fieldData));
        Mockito.verify(preparedStatement).setString(1,"Melarini");
        Mockito.verify(preparedStatement).setInt(2,1);

    }
    @Test
    public void InvalidUpate() throws SQLException {
        PreparedStatement preparedStatement= Mockito.mock(PreparedStatement.class);
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setId(1).setAnagrafica_cliente("Melarini").build();
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(0);
        Assertions.assertFalse(pharmaDao.update(fieldData));


    }




    @AfterEach
    public  void tearDown(){
    database=null;
    pharmaDao=null;


    }

    /*
    Integration Test
     */
    @Test
 public void findAllIntegrationTest() throws IOException, SQLException {
    // Properties properties= FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
       Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
     PharmaDao pharmaDao=new PharmaDao(Database.getInstance(properties));
     List<FieldData> list=pharmaDao.findAll();
     Assertions.assertEquals(3,list.size());

 }
    @Test
    public void Insert_integration_test()  {
        Properties properties= null;
        PharmaDao pharmaDao=null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));

          pharmaDao=new PharmaDao(Database.getInstance(properties));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setPartita_iva("11111").setSigla("AA").setAnagrafica_cliente("Ciaooo").build();
        boolean res=pharmaDao.insert(fieldData);
        Assertions.assertTrue(res);

        /*try {
         //   Properties properties= FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
         Properties   properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
            database = Database.getInstance(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setPartita_iva("11111").setSigla("AA").setAnagrafia_cliente("Ciaooo").build();
        boolean res=pharmaDao.insert(fieldData);
        Assertions.assertTrue(res);


         */

    }
    @Test
    public void integrationTestUpdate() throws SQLException {
        Properties properties= null;
        PharmaDao pharmaDao=null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));

            pharmaDao=new PharmaDao(Database.getInstance(properties));
        } catch (IOException e) {
            e.printStackTrace();
        }


        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setId(1).setAnagrafica_cliente("Bayer").build();


        Assertions.assertTrue(pharmaDao.update(fieldData));

    }
    @Test
    public void findbyId() throws IOException, SQLException {
        // Properties properties= FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
        Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host","username","password")),new FileReader("database.properties"));
        PharmaDao pharmaDao=new PharmaDao(Database.getInstance(properties));
        FieldData fieldData=pharmaDao.findById(1);
        System.out.println(fieldData.getNome());

    }












}


