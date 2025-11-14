package pharma.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SellerOrderDaoTest {

    @Test
    void findByRangeBetweenAndRagioneSociale() {
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SellerOrderDao sellerOrderDao = new SellerOrderDao(Database.getInstance(properties));
        List<FieldData> list=sellerOrderDao.
                findByRangeBetweenAndRagioneSociale(Date.valueOf("2025-1-1"), Date.valueOf("2027-1-1"), "Farmacia Del Corso");
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void findByOrders() {
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            SellerOrderDao sellerOrderDao = new SellerOrderDao(Database.getInstance(properties));
            List<FieldData> list=sellerOrderDao.findByOrders(347);
            Assertions.assertEquals(2,list.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }



}

