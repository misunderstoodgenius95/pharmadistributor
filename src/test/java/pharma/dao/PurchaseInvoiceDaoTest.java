package pharma.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.postgresql.util.ReaderInputStream;
import pharma.Model.FieldData;
import pharma.config.Database;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PurchaseInvoiceDaoTest {
@Mock
private Database database;
@Mock
private PreparedStatement p_statement;
@Mock
private ResultSet resultSet;
private  PurchaseInvoiceDao p_inv_dao;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        p_inv_dao=new PurchaseInvoiceDao(database);
    }


    @Test
    public  void ValidInsert() throws SQLException {

        FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().
                setInvoice_number("p1000").
                setProduction_date(Date.valueOf(LocalDate.of(2025,10,1))).
                setPayment_mode("BONIFICO_BANCARIO").
                setSubtotal(10.1).
                setVat_amount(4.1).
                setTotal(14.2)
                .build();
        when(resultSet.getInt(1)).thenReturn(100);
        when(resultSet.next()).thenReturn(true);
      when(p_statement.executeQuery()).thenReturn(resultSet);
        when(database.execute_prepared_query(Mockito.anyString())).thenReturn(p_statement);
        int value=p_inv_dao.insertAndReturnID(fieldData);
        Assertions.assertEquals(100,value);
    }

    @Test
    public void ValidFindAll() throws SQLException {
        when(resultSet.getInt(1)).thenReturn(1,2);
        when(resultSet.getString(2)).thenReturn("p100","p220");
        Mockito.when(resultSet.getDate(3)).thenReturn(Date.valueOf(LocalDate.of(2024,6,10)),
                Date.valueOf(LocalDate.of(2021,6,10)));
       when(resultSet.getString(4)).thenReturn("BONIFICO_BANCARIO","BONIFICO_BANCARIO");
        Mockito.when(resultSet.getDouble(5)).thenReturn(10.1,11.2);
        Mockito.when(resultSet.getDouble(6)).thenReturn(1.0,2.1);
        Mockito.when(resultSet.getDouble(7)).thenReturn(11.1,10.23);
        Mockito.when(resultSet.getTimestamp(8)).thenReturn(new Timestamp(1742226308),new Timestamp(1742186708));
        when(resultSet.next()).thenReturn(true,true,false);
        when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        p_inv_dao.findAll();


    }

}