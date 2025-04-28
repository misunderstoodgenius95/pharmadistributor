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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class PurchaseCreditNoteDaoTest {
    private PurchaseCreditNoteDao p_note_dao;
    @Mock
    private Database database;
    @Mock
    private PreparedStatement p_statement;
    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        p_note_dao = new PurchaseCreditNoteDao(database);


    }

    @Test
    public void ValidInsert() throws SQLException {
        when(resultSet.getInt(1)).thenReturn(100);
        when(resultSet.next()).thenReturn(true);
        when(p_statement.executeQuery()).thenReturn(resultSet);
        when(database.execute_prepared_query(anyString())).thenReturn(p_statement);


        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setCredit_note_number("p100").
                setInvoice_id(100).
                setProduction_date(Date.valueOf(LocalDate.of(2014, 10, 1))).
                setMotive("Prezzo Errato").setCasa_Farmaceutica(1).
                build();
        int value = p_note_dao.insertAndReturnID(fieldData);
        Assertions.assertEquals(100, value);

    }


    @Test
    void exist_credit_note() throws SQLException {
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.next()).thenReturn(true);
        when(p_statement.executeQuery()).thenReturn(resultSet);

        when(database.execute_prepared_query(anyString())).thenReturn(p_statement);
        Assertions.assertTrue(p_note_dao.exist_credit_note(1));

    }

    @Test
    void findDetailbyInvoiceId() throws SQLException {
        when(resultSet.next()).thenReturn(true,false);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("aaa");
        when(resultSet.getInt("farmaco")).thenReturn(1);
        when(resultSet.getInt("purchase_order")).thenReturn(100);
        when(resultSet.getDouble("price")).thenReturn(10.1);
        when(resultSet.getInt("quantity")).thenReturn(10);
        when(resultSet.getInt("vat_percent")).thenReturn(4);

        when(p_statement.executeQuery()).thenReturn(resultSet);
        when(database.execute_prepared_query(anyString())).thenReturn(p_statement);
        List<FieldData> list=p_note_dao.findDetailbyInvoiceId(8);
        Assertions.assertEquals(1,list.size());


    }

@AfterEach
void TearDown(){

        p_note_dao=null;
        database=null;
}

    @Test
    void ExistIntegration() {

        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            p_note_dao=new PurchaseCreditNoteDao(Database.getInstance(properties));
         Assertions.assertTrue(p_note_dao.exist_credit_note(8));

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Test
    void IntegrationFindDetailsbyInvoiceId() {

        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            PurchaseCreditNoteDao purchaseCreditNotedao= new PurchaseCreditNoteDao(Database.getInstance(properties));
            List<FieldData> list=purchaseCreditNotedao.findDetailbyInvoiceId(8);
            Assertions.assertEquals(3,list.size());

        } catch (Exception e) {
            e.printStackTrace();

        }


    }


    @Test
    void findbyInvoiceid() {
        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            PurchaseCreditNoteDao purchaseCreditNotedao= new PurchaseCreditNoteDao(Database.getInstance(properties));
         FieldData fieldData=purchaseCreditNotedao.findbyInvoiceid(8);
            Assertions.assertNotNull(fieldData);

        } catch (Exception e) {
            e.printStackTrace();

        }



    }
}