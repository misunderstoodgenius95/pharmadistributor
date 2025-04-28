package pharma.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PurchaseOrderDaoTest {
    private PurchaseOrderDao purchaseOrderDao;
    private Database database;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        database = Mockito.mock(Database.class);
        purchaseOrderDao = new PurchaseOrderDao(database);
        preparedStatement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    public void ValidInsert() throws SQLException {


        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
                setProduction_date(Date.valueOf(LocalDate.of(2025, 01, 10))).
                setSubtotal(10.0).setVat_amount(1.0).setTotal(11.0).setOriginal_order_id("001").setNome_casa_farmaceutica("angelini").build();

        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Mockito.when(resultSet.next()).thenReturn(true, false);
        Mockito.when(resultSet.getInt(1)).thenReturn(100);


        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        int value = purchaseOrderDao.insertAndReturnID(fieldData);
        System.out.println(value);
        Assertions.assertEquals(100, value);
        Mockito.verify(database).execute_prepared_query(" INSERT INTO purchase_order (data,subtotale,iva,totale,provider_original_order,pharma_house) VALUES(?,?,?,?,?,?) RETURNING id ; ");
        Mockito.verify(preparedStatement).setDate(1, Date.valueOf(LocalDate.of(2025, 01, 10)));
        Mockito.verify(preparedStatement).setDouble(2, 10.0);
        Mockito.verify(preparedStatement).setDouble(3, 1.0);
        Mockito.verify(preparedStatement).setDouble(4, 11.0);


    }

    @Test
    public void FindAll() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true, false);
        Mockito.when(resultSet.getInt(1)).thenReturn(1);
        Mockito.when(resultSet.getDate(2)).thenReturn(Date.valueOf(LocalDate.of(2024, 6, 10)));
        Mockito.when(resultSet.getDouble(3)).thenReturn(10.1);
        Mockito.when(resultSet.getDouble(4)).thenReturn(1.0);
        Mockito.when(resultSet.getDouble(5)).thenReturn(11.1);
        Mockito.when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);


        FieldData value = purchaseOrderDao.findAll().getFirst();
        Assertions.assertEquals(Date.valueOf(LocalDate.of(2024, 6, 10)), value.getProduction_date());
        Assertions.assertEquals(10.1, value.getSubtotal());
        Assertions.assertEquals(1.0, value.getVat_amount());
        Assertions.assertEquals(11.1, value.getTotal());
        Assertions.assertEquals(1, value.getId());


    }

    @Test
    void ValidAllFindWithInvoiceIdNull() throws SQLException {

ArgumentCaptor<Integer> argumentCaptor=ArgumentCaptor.forClass(Integer.class);
        when(preparedStatement.executeQuery()).thenAnswer(invocation -> {
            if (argumentCaptor.getValue()== 1) {


                Mockito.when(resultSet.next()).thenReturn(true, false, false);
                Mockito.when(resultSet.getInt(1)).thenReturn(100, 200);
                Mockito.when(resultSet.getDate(2)).thenReturn(Date.valueOf(LocalDate.of(2024, 6, 10)),
                        Date.valueOf(LocalDate.of(2021, 6, 10)));
                Mockito.when(resultSet.getDouble(3)).thenReturn(10.1, 11.2);
                Mockito.when(resultSet.getDouble(4)).thenReturn(1.0, 2.1);
                Mockito.when(resultSet.getDouble(5)).thenReturn(11.1, 10.23);
                Mockito.when(resultSet.getString(6)).thenReturn("1600", "1700");
                Mockito.when(resultSet.getInt(7)).thenReturn(1, 2);
                Mockito.when(resultSet.getString(8)).thenReturn("Angelini", "Bayer");

            }
            return  resultSet;
        });
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);

      doNothing().when(preparedStatement).setInt(anyInt(),argumentCaptor.capture());
        List<FieldData> list = purchaseOrderDao.findAllWithInvoiceIdNullByPharmaId(1);
        System.out.println(list.size());
        Assertions.assertEquals(100,list.getFirst().getId());


    }

    @Test
    void ValidUpdate() throws SQLException {
        PreparedStatement preparedStatement_update = Mockito.mock(PreparedStatement.class);
        FieldData fd_update = FieldData.FieldDataBuilder.getbuilder().setInvoice_id(1000).setPurchase_order_id(3000).build();

        Mockito.when(preparedStatement_update.executeUpdate()).thenReturn(1);

        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement_update);
        boolean cond = purchaseOrderDao.update(fd_update);
        Assertions.assertTrue(cond);
        Mockito.verify(preparedStatement_update).setInt(1, 1000);
        Mockito.verify(preparedStatement_update).setInt(2, 3000);


    }

    @Test
    void findByInvoiceid() throws SQLException {

        ArgumentCaptor<Integer> paramCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(preparedStatement.executeQuery()).thenAnswer(invocation -> {
            int value = paramCaptor.getValue();
            if (value == 1) {
                Mockito.when(resultSet.next()).thenReturn(true, false);
                Mockito.when(resultSet.getInt(1)).thenReturn(100);
                Mockito.when(resultSet.getDate(2)).thenReturn(Date.valueOf(LocalDate.of(2024, 6, 10)));
                Mockito.when(resultSet.getDouble(3)).thenReturn(10.1);
                Mockito.when(resultSet.getDouble(4)).thenReturn(1.0);
                Mockito.when(resultSet.getDouble(5)).thenReturn(11.1);
                Mockito.when(resultSet.getString(6)).thenReturn("1600");
                Mockito.when(resultSet.getString(7)).thenReturn("Angelini");
                Mockito.when(resultSet.getInt(8)).thenReturn(1);

            }
            return resultSet;


        });
        doNothing().when(preparedStatement).setInt(eq(1), paramCaptor.capture());
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);

        List<FieldData> fieldDataList = purchaseOrderDao.findByInvoiceid(1);
        Assertions.assertEquals(2, fieldDataList.size());
    }


    @Test
    void findbyPharmaHousebyInvoiceID() throws SQLException {
        ResultSet resultset_pharma=Mockito.mock(ResultSet.class);
        PreparedStatement preparedStatement_pharma = Mockito.mock(PreparedStatement.class);
        when(resultset_pharma.getString(1)).thenReturn("Angelini");
        when(resultset_pharma.next()).thenReturn(true);
        when(preparedStatement_pharma.executeQuery()).thenReturn(resultset_pharma);
        when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement_pharma);
        String value=purchaseOrderDao.findbyPharmaHousebyInvoiceID(11);
        Assertions.assertEquals("Angelini",value);
    }


    @AfterEach
    public void tearDown() {

        resultSet = null;
        purchaseOrderDao = null;
        database = null;
    }

    @Test
    public void ValidIntegrationTestInsertAndreturnId() {
        Properties properties;

        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseOrderDao = new PurchaseOrderDao(Database.getInstance(properties));
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().
                    setInvoice_number("P1000").
                    setProduction_date(Date.valueOf(LocalDate.of(2014, 10, 1))).
                    setPayment_mode("BONIFICO_BANCARIO").
                    setSubtotal(10.1).
                    setVat_amount(12.1).
                    setTotal(6.5)
                    .build();

            int value = purchaseOrderDao.insertAndReturnID(fieldData);
            System.out.println(value);
            //Assertions.assertNotEquals(-1,value);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void ValidFindIntegreation() {
        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseOrderDao = new PurchaseOrderDao(Database.getInstance(properties));
            List<FieldData> list = purchaseOrderDao.findAll();
            System.out.println(list.getFirst().getId());

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    @Test
    public void ValidIntegrationAllFindWithInvoiceIdNull() {
        Properties properties;


        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseOrderDao = new PurchaseOrderDao(Database.getInstance(properties));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<FieldData> fieldData = purchaseOrderDao.findAllWithInvoiceIdNullByPharmaId(1);
        Assertions.assertEquals(7, fieldData.size());

    }

    @Test
    public void ValidIntegrationAllByInvoiceID() {
        Properties properties;


        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseOrderDao = new PurchaseOrderDao(Database.getInstance(properties));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<FieldData> fieldData = purchaseOrderDao.findByInvoiceid(6);
        Assertions.assertEquals(2, fieldData.size());

    }


}

