package pharma.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SellerCreditNoteDetailDaoTest {
    private SellerCreditNoteDetailDao s_detail;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void InsertIntegration() {
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            s_detail=new SellerCreditNoteDetailDao(Database.getInstance(properties));
             FieldData fd=FieldData.FieldDataBuilder.getbuilder()
                    .setInvoice_id(9).
                    setQuantity(1).
                    setVat_percent(10).
                    setPrice(12.67).setFarmaco_id(347).build();
            boolean value=s_detail.insert(fd);
            Assertions.assertTrue(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}