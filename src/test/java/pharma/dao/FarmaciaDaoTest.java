package pharma.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FarmaciaDaoTest {
    private FarmaciaDao farmaciaDao;
    @Mock
    private Database database;
    @Mock
    private PreparedStatement preparedStatement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        farmaciaDao = new FarmaciaDao(database);


    }

    @Test
    void ValidInsert() throws SQLException {


        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Farmacia Del Corso").
                setPartita_iva("IT11111111111").setStreet("Via guido Cavalcanti").
                setCap(12345).setComune("Roma").setProvince("RM").build();
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement);
        farmaciaDao.insert(fieldData);
        verify(database).execute_prepared_query("INSERT INTO farmacia (ragione_sociale,p_iva,street,cap,comune,province) VALUES(?,?,?,?,?,?)");
        verify(preparedStatement).setString(1, "Farmacia Del Corso");
        verify(preparedStatement).setString(2, "IT11111111111");
        verify(preparedStatement).setString(3, "Via guido Cavalcanti");
        verify(preparedStatement).setInt(4, 12345);
        verify(preparedStatement).setString(5, "Roma");
        verify(preparedStatement).setString(6, "RM");
        verify(preparedStatement.executeUpdate());

    }

    @Test
    void testInsert_InvalidFieldData_MissingClientName() throws SQLException {
        // anagrafica_cliente is null, which we assume is invalid

        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder()
                .setAnagrafica_cliente(null)
                .setPartita_iva("IT11111111111")
                .setStreet("Via guido Cavalcanti")
                .setCap(12345)
                .setComune("Roma")
                .setProvince("RM")
                .build();


        assertThrows(IllegalArgumentException.class, fieldData::getAnagrafica_cliente);
        farmaciaDao.insert(fieldData);


        // OR
        // 2. If it returns false (e.g., input validation failed before hitting DB)
        // boolean result = farmaciaDao.insert(fieldData);
        // assertFalse(result);

        // Optional: verify DB is NOT called
        verifyNoInteractions(preparedStatement);
        verifyNoMoreInteractions(database);
    }

    @AfterEach
    public void tearDown() {
        database = null;
        farmaciaDao = null;


    }

    @Test
    public void ValidInsertIntegrationTest() {
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            farmaciaDao = new FarmaciaDao(Database.getInstance(properties));
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Farmacia Del Corso").
                    setPartita_iva("IT11111111111").setStreet("Via guido Cavalcanti").
                    setCap(12345).setComune("Roma").setProvince("RM").build();
            boolean result=farmaciaDao.insert(fieldData);
            Assertions.assertTrue(result);




        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    @Test
    void buildQueryasParameter() {
        //Assertions.assertEquals("SELECT * FROM farmacia WHERE comune= ?",farmaciaDao.buildQueryasParameter("comune"));
        //Assertions.assertEquals("SELECT * FROM farmacia WHERE  partita_iva= ?",farmaciaDao.buildQueryasParameter("partita_iva"));
        Assertions.assertEquals("SELECT * FROM farmacia WHERE  ragione_sociale= ?",farmaciaDao.buildQueryasParameter("ragione_sociale"));

    }
    @Test
    void InvalidbuildQueryasParameter() {
      Assertions.assertThrows(IllegalArgumentException.class,()->farmaciaDao.buildQueryasParameter("none"));
    }

    @Test
    void testBuildQueryasParameter() {
    }
}