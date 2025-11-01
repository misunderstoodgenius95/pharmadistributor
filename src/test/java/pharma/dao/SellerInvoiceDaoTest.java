package pharma.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SellerInvoiceDaoTest {
        @Test
    public void ValidfindbyAll() throws IOException {

        Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        SellerInvoiceDao sellerInvoiceDao=new SellerInvoiceDao(Database.getInstance(properties));
            Assertions.assertFalse(sellerInvoiceDao.findAll().isEmpty());


    }

    @Test
    public void findByRangeBetweenAndRagioneSociale() throws IOException {
        Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        SellerInvoiceDao sellerInvoiceDao=new SellerInvoiceDao(Database.getInstance(properties));
        List<FieldData> list=sellerInvoiceDao.
                findByRangeBetweenAndRagioneSociale(Date.valueOf("2025-1-1"), Date.valueOf("2027-1-1"), "Farmacia Del Corso");;
        Assertions.assertFalse(list.isEmpty());

        }







}