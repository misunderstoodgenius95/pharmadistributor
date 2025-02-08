package pharma.config;

import net.jodah.failsafe.internal.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.dao.DetailDao;
import pharma.dao.FarmacoDao;
import pharma.dao.PharmaDao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PopulateChoiceTest {
    private  Database database;
    private DetailDao detailDao;
    private PopulateChoice populateChoice;
    private PharmaDao pharmaDao;
    @BeforeEach
    public void setUp(){
        database= Mockito.mock(Database.class);
        detailDao=new DetailDao(database);
        pharmaDao=new PharmaDao(database);
        populateChoice=new PopulateChoice(detailDao,pharmaDao);
    }


    @Test
    void populateNOtMiusure() throws SQLException {
        ResultSet resultSet=Mockito.mock(ResultSet.class);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(1)).thenReturn(1);
        Mockito.when(resultSet.getString(2)).thenReturn("Antibiotico");
        List<FieldData> fieldData=populateChoice.populate(Utility.Categoria);
        Assertions.assertEquals("Antibiotico",fieldData.get(0).getNome());



    }
    @Test
    void populateMiusure() throws SQLException {
        ResultSet resultSet=Mockito.mock(ResultSet.class);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(1)).thenReturn(1);
        Mockito.when(resultSet.getInt(2)).thenReturn(100);
        Mockito.when(resultSet.getString(3)).thenReturn("mg");
        List<FieldData> fieldData=populateChoice.populate(Utility.Misura);
        Assertions.assertEquals("100 mg",fieldData.get(0).toString());



    }
    @Test
    void populateNOtMiusureMultple() throws SQLException {
        ResultSet resultSet=Mockito.mock(ResultSet.class);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true,true,false);
        Mockito.when(resultSet.getInt(1)).thenReturn(1,2);
        Mockito.when(resultSet.getString(2)).thenReturn("Antibiotico","Antidepressivo");
        List<FieldData> fieldData=populateChoice.populate(Utility.Categoria);
        Assertions.assertEquals("Antibiotico",fieldData.get(0).getNome());
        Assertions.assertEquals("Antidepressivo",fieldData.get(1).getNome());



    }
    @Test
    void populateCasaF() throws SQLException {
        ResultSet resultSet=Mockito.mock(ResultSet.class);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true,false);
        Mockito.when(resultSet.getInt(1)).thenReturn(1);
        Mockito.when(resultSet.getString(2)).thenReturn("Melarini");
        List<FieldData> fieldData=populateChoice.populate("pharma");
        Assertions.assertEquals("Melarini",fieldData.get(0).toString());




    }



    @BeforeEach
    void TearDown(){

        database=null;
        detailDao=null;
        populateChoice=null;
    }
    @Test
    public void ValidPopulateIntegrationTest() throws IOException {
        Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));

         DetailDao detailDao=new DetailDao(Database.getInstance(properties));
        PopulateChoice populateChoice=new PopulateChoice(detailDao,pharmaDao);
        List<FieldData> list=populateChoice.populate(Utility.Categoria);
        System.out.println(list.get(0).getId());

    }
}