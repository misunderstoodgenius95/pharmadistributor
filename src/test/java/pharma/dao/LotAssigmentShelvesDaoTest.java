package pharma.dao;

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

import static org.junit.jupiter.api.Assertions.*;

class LotAssigmentShelvesDaoTest {

    @Test
    void findByLots() throws IOException {
         Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            LotAssigmentShelvesDao assigmentShelvesDao=new LotAssigmentShelvesDao(Database.getInstance(properties));
            List<FieldData> list=assigmentShelvesDao.findbyLotCode("ax26");
        System.out.println(list.size());

    }
}