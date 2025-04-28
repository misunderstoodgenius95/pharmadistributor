package pharma.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.Model.FieldData;
import pharma.config.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.when;

class PurchaseCreditNoteDetailDaoTest {

    @Mock
    private Database database;
    PurchaseCreditNoteDetailDao purchaseCreditNoteDetailDao;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @BeforeEach
    public  void setUp() {
        MockitoAnnotations.openMocks(this);
        purchaseCreditNoteDetailDao = new PurchaseCreditNoteDetailDao(database);

    }
    @Test
    void findDetailbyCreditNoteId() throws SQLException {
        Mockito.when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true,false);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("aaa");
        when(resultSet.getInt("farmaco")).thenReturn(1);
        when(resultSet.getInt("purchase_order")).thenReturn(100);
        when(resultSet.getDouble("price")).thenReturn(10.1);
        when(resultSet.getInt("quantity")).thenReturn(10);
        when(resultSet.getInt("vat_percent")).thenReturn(4);

        List<FieldData> list=purchaseCreditNoteDetailDao.findDetailbyCreditNoteId(8);
        Assertions.assertEquals(1,list.size());


    }
}