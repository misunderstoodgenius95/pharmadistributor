package pharma.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pharma.Controller.subpanel.Farmaco;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.Database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class FarmacoDaoTest {
    private FarmacoDao pharmacoDao;
    private Database database;

    @BeforeEach
    public void setUp() throws SQLException {
        database = Mockito.mock(Database.class);
        pharmacoDao = new FarmacoDao("farmaco", database);

    }

    @Test
    public void ValidInsert() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setNome("Tachipirina").setDescription("Farmaco per la febbre.").setCategoria(1).
                setTipologia(1).setMisure(1).setCasa_Farmaceutica(1).setPrincipio_attivo(1).build();

        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);


        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        boolean value = pharmacoDao.insert(fieldData);
        Assertions.assertTrue(value);
        Mockito.
                verify(database).execute_prepared_query(" INSERT INTO farmaco (nome,descrizione,categoria,tipologia,misura,principio_attivo,casa_farmaceutica) " +
                        " VALUES( ?,?,?,?,?,?,?) ; ");
        Mockito.verify(preparedStatement).setString(1,"Tachipirina");
        Mockito.verify(preparedStatement).setString(2,"Farmaco per la febbre.");
        Mockito.verify(preparedStatement).setInt(3,1);
        Mockito.verify(preparedStatement).setInt(4,1);
        Mockito.verify(preparedStatement).setInt(5,1);
        Mockito.verify(preparedStatement).setInt(6,1);
        Mockito.verify(preparedStatement).setInt(7,1);


    }

    @Test
    public void InValidInsert() throws SQLException {
        //Missing Principio Attivo
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setNome("Tachpirina").setDescription("Farmaco per la febbre.").setCategoria(1).
                setTipologia(1).setMisure(1).setCasa_Farmaceutica(1).build();

        Mockito.when(preparedStatement.executeUpdate()).thenReturn(0);


        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        boolean value = pharmacoDao.insert(fieldData);
        Assertions.assertFalse(value);
        Mockito.verify(preparedStatement).setString(1,"Tachipirina");
        Mockito.verify(preparedStatement).setString(2,"Farmaco per la febbre.");
        Mockito.verify(preparedStatement).setInt(3,1);
        Mockito.verify(preparedStatement).setInt(4,1);
        Mockito.verify(preparedStatement).setInt(5,1);
        Mockito.verify(preparedStatement).setInt(6,1);
        Mockito.verify(preparedStatement).setInt(7,1);


    }
    @Test
    void findAll() throws SQLException {
        ResultSet resultSet=Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true,false);
        Mockito.when(resultSet.getString(2)).thenReturn("Tachipirina");
        Mockito.when(resultSet.getString(3)).thenReturn("Febbre");
        Mockito.when(resultSet.getString(4)).thenReturn("Antiinfiammatorio");
        Mockito.when(resultSet.getString(5)).thenReturn("Compresse");
        Mockito.when(resultSet.getString(6)).thenReturn("100mg");
        Mockito.when(resultSet.getString(7)).thenReturn("Paracetamolo");
        Mockito.when(resultSet.getString(8)).thenReturn("Angelini");
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        List<FieldData> list=pharmacoDao.findAll();
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setNome("Tachipirina").setDescription("Febbre").
              setNome_categoria("Antiinfiammatorio").setNome_tipologia("Compresse").setUnit_misure("100mg").setNome_principio_attivo("Paracetamolo").
                setNome_casa_farmaceutica("Angelini").build();
        Assertions.assertEquals(fieldData.getUnit_misure(),list.get(0).getUnit_misure());
        Assertions.assertEquals(fieldData.getNome(),list.get(0).getNome());
    }

    //Integration Testing

    @AfterEach
    public void tearDown() {
        database = null;
        pharmacoDao = null;
    }

    @Test
    public void ValidIntegrationTesting() {
        Properties properties = null;
       FarmacoDao farmacoDao = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            farmacoDao=new FarmacoDao("farmaco",Database.getInstance(properties));


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setNome("Tachipirina").setDescription("Farmaco per la febbre.").setCategoria(1).
                setTipologia(1).setMisure(1).setPrincipio_attivo(1).setCasa_Farmaceutica(1).build();


        boolean value=farmacoDao.insert(fieldData);
        Assertions.assertTrue(value);

    }




}