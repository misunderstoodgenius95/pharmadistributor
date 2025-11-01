package pharma.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

class SellerPriceDaoTest {

    @Test
    void findNotExistPrice() throws IOException {
        Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            SellerPriceDao sellerPriceDao =new SellerPriceDao(Database.getInstance(properties));
            sellerPriceDao.findNotExistPrice();

    }
    @Test
    void findPricebyFarmacoId() throws IOException {
        Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        SellerPriceDao sellerPriceDao =new SellerPriceDao(Database.getInstance(properties));
        Assertions.assertEquals(6,sellerPriceDao.findCurrentPricebyFarmaco(347));

    }
}