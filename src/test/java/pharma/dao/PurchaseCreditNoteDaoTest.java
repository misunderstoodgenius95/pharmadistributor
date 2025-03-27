package pharma.dao;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.postgresql.util.ReaderInputStream;
import pharma.Model.FieldData;
import pharma.config.Database;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        p_note_dao=new PurchaseCreditNoteDao(database);




    }
    @Test
    public void ValidInsert() throws SQLException {
        when(resultSet.getInt(1)).thenReturn(100);
        when(resultSet.next()).thenReturn(true);
        when(p_statement.executeQuery()).thenReturn(resultSet);
        when(database.execute_prepared_query(anyString())).thenReturn(p_statement);


        FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setCredit_note_number("p100").
                setInvoice_id(100).
                setProduction_date(Date.valueOf(LocalDate.of(2014,10,1))).
                setMotive("Prezzo Errato").setCasa_Farmaceutica(1).
                build();
        int value=p_note_dao.insertAndReturnID(fieldData);
        Assertions.assertEquals(100,value);

    }




}