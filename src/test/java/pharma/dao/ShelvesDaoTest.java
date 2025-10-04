package pharma.dao;

import algoWarehouse.ShelvesCapacity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

class ShelvesDaoTest {
    private ShelvesDao shelvesDao;
    @BeforeEach
    void setUp() {

        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shelvesDao=new ShelvesDao(Database.getInstance(properties));

    }

    @Test
    public  void ValidInsert(){
        ShelvesCapacity shelvesCapacity=new ShelvesCapacity("A1200",1,0.0,0.0,0.0);
        boolean result=shelvesDao.insert(shelvesCapacity);
        Assertions.assertTrue(result);

    }

}