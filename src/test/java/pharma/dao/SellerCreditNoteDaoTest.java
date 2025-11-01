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
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.when;

class SellerCreditNoteDaoTest {
    @Mock
    private ResultSet resultSet;
    @Mock
    private Database database;
    @Mock
    private PreparedStatement preparedStatement;
    private SellerCreditNoteDao sellerCreditNoteDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void INsertValidTestingCreate() throws SQLException {
            sellerCreditNoteDao=new SellerCreditNoteDao(database);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement);
       when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean value=sellerCreditNoteDao.insert(FieldData.FieldDataBuilder.getbuilder().setInvoice_id(12).setVat_amount(1).setTotal(13).build());
        Assertions.assertTrue(value);
    }

    @AfterEach
    void tearDown() {
        sellerCreditNoteDao=null;
        preparedStatement=null;
    }

    @Test
    public void InsertIntegration(){
        Properties properties=null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sellerCreditNoteDao=new SellerCreditNoteDao(Database.getInstance(properties));
       int value=sellerCreditNoteDao.insertAndReturnID(FieldData.FieldDataBuilder.getbuilder().
                setForeign_id(16).setVat_amount(1).setSubtotal(10).setTotal(13).build());
        System.out.println(value);


    }














}