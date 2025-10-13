package pharma.dao;

import org.junit.jupiter.api.Test;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SuggestPriceDaoTest {

    @Test
    void findNotExistPrice() throws IOException {
        Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            SuggestPriceDao suggestPriceDao=new SuggestPriceDao(Database.getInstance(properties));
            suggestPriceDao.findNotExistPrice();

    }
}