package pharma.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class SellerOrderDetailsTest {

    private Database database;
    private SellerOrderDetails sellerOrderDetails;
    @BeforeEach
    void setUp() throws IOException {
         Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
         database=Database.getInstance(properties);
         sellerOrderDetails=new SellerOrderDetails(database);
    }



    @Test
    void findbyProduct() {
        List<FieldData> list=sellerOrderDetails.findbyProduct(347);
        Assertions.assertFalse(list.isEmpty());

    }

    @Test
    void findbyOrderId() {
        List<FieldData> list=sellerOrderDetails.findbyOrderId(26);
        System.out.println( list.getFirst().getFarmaco_id());
       // Assertions.assertFalse(list.isEmpty());
    }
}