package pharma.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class PurchaseOrderDetailDaoTest {
    private  PurchaseOrderDetailDao purchaseOrderDetailDao;
    private PreparedStatement preparedStatement;
    private  Database database;
    @BeforeEach
    public void setUp(){
         database= Mockito.mock(Database.class);
        purchaseOrderDetailDao=new PurchaseOrderDetailDao(database);
        preparedStatement=Mockito.mock(PreparedStatement.class);


    }

    @Test
    public void ValidInsert() throws SQLException {
        FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setcode("abb1").setTipologia(100).setId(1).setPrice(10.2).setQuantity(10).
                setVat_percent(1).build();
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        boolean value=purchaseOrderDetailDao.insert(fieldData);
        Assertions.assertTrue(value);
        Mockito.verify(preparedStatement).setString(1,"abb1");
        Mockito.verify(preparedStatement).setInt(2,100);
        Mockito.verify(preparedStatement).setInt(3,1);
        Mockito.verify(preparedStatement).setDouble(4,10.2);
        Mockito.verify(preparedStatement).setInt(5,10);
        Mockito.verify(preparedStatement).setInt(6,1);
    }
    @Test
    public void VaidFindAll() throws SQLException {
        ResultSet resultSet=Mockito.mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true,false);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("aaa");
        when(resultSet.getInt("farmaco")).thenReturn(1);
        when(resultSet.getInt("purchase_order")).thenReturn(100);
        when(resultSet.getDouble("price")).thenReturn(10.1);
        when(resultSet.getInt("quantity")).thenReturn(10);
        when(resultSet.getInt("vat_percent")).thenReturn(4);

        when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        FieldData fd=purchaseOrderDetailDao.findAll().getFirst();
        Assertions.assertEquals(1,fd.getId());
        Assertions.assertEquals("aaa",fd.getCode());
        Assertions.assertEquals(2,fd.getTipologia());
        Assertions.assertEquals(100,fd.getCategoria());
        Assertions.assertEquals(10.1,fd.getPrice());
        Assertions.assertEquals(10,fd.getQuantity());
        Assertions.assertEquals(4,fd.getVat_percent());






    }
    @Test
    public void VaidFindByP_order() throws SQLException {
        PreparedStatement preparedStatement=Mockito.mock(PreparedStatement.class);
        ResultSet resultSet=Mockito.mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true,false);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("aaa");
        when(resultSet.getInt("farmaco")).thenReturn(1);
        when(resultSet.getInt("purchase_order")).thenReturn(100);
        when(resultSet.getDouble("price")).thenReturn(10.1);
        when(resultSet.getInt("quantity")).thenReturn(10);
        when(resultSet.getInt("vat_percent")).thenReturn(4);
        when(resultSet.getString("nome")).thenReturn("Tachipirina");
        when(resultSet.getString("tipologia")).thenReturn("Compresse");
        when(resultSet.getString("misura")).thenReturn("100 mg");
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement);


        FieldData fd=purchaseOrderDetailDao.findDetailbyPurchaseOrderId(15).getFirst();
        Assertions.assertEquals(1,fd.getId());
        Assertions.assertEquals("aaa",fd.getCode());
        Assertions.assertEquals(100,fd.getPurchase_order_id());
        Assertions.assertEquals(10.1,fd.getPrice());
        Assertions.assertEquals(10,fd.getQuantity());
        Assertions.assertEquals(4,fd.getVat_percent());
        Assertions.assertEquals("Tachipirina",fd.getNome_farmaco());
        Assertions.assertEquals("Compresse",fd.getNome_tipologia());
        Assertions.assertEquals("100 mg",fd.getUnit_misure());







    }

    public void tearDown(){


        purchaseOrderDetailDao=null;
        database=null;
    }


    @Test
    public void ValidIntegrationTest() {
        Properties properties;

        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseOrderDetailDao=new PurchaseOrderDetailDao(Database.getInstance(properties));
            boolean value=purchaseOrderDetailDao.insert(FieldData.FieldDataBuilder.getbuilder().setcode("m20733").setPurchase_order_id(15).setFarmaco_id(60).setId(1).setPrice(10.2).setQuantity(10).
                    setVat_percent(1).build());
            Assertions.assertTrue(value);




        }catch (Exception e){

            e.printStackTrace();
        }

    }

    @Test
    public void ValidFindbyPurchaseOrderID(){

        Properties properties;

        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseOrderDetailDao=new PurchaseOrderDetailDao(Database.getInstance(properties));
            List<FieldData>list=purchaseOrderDetailDao.findDetailbyPurchaseOrderId(15);
            System.out.println(list.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




}