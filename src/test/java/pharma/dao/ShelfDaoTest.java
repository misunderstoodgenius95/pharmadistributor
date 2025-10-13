package pharma.dao;

import org.junit.jupiter.api.Test;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ShelfDaoTest {





    @Test
    public void TestIntegrationShelfFindAll(){

        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShelfDao shelfDao=new ShelfDao(Database.getInstance(properties));

    }






}