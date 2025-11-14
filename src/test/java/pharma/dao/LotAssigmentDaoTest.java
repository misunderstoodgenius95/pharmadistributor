package pharma.dao;

import algoWarehouse.LotAssigment;
import org.junit.jupiter.api.Assertions;
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

class LotAssigmentDaoTest {


    @Test
    public void ValidIntegrationTestInsert() {
        Properties properties;

        {
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                LotAssigmentDao lotAssigmentDao = new LotAssigmentDao(Database.getInstance(properties));
                int id = lotAssigmentDao.insertAndReturnID(new LotAssigment(352, "ax26", 13));
                System.out.println(id);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Test
    public void ValidateExists() {
        Properties properties;

        {
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                LotAssigmentDao lotAssigmentDao = new LotAssigmentDao(Database.getInstance(properties));
                boolean value = lotAssigmentDao.findExistsAssigment(352, "ax26");
                Assertions.assertTrue(value);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Test
    public void ValidateExistsAndReturnID() {
        Properties properties;

        {
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                LotAssigmentDao lotAssigmentDao = new LotAssigmentDao(Database.getInstance(properties));
                int id = lotAssigmentDao.findExistAndReturnId(352, "ax26");
                System.out.println(id);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Test
    public void InValidateExistsAndReturnID() {
        Properties properties;

        {
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                LotAssigmentDao lotAssigmentDao = new LotAssigmentDao(Database.getInstance(properties));
                int id = lotAssigmentDao.findExistAndReturnId(351, "ax26");
                System.out.println(id);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Test
    public void NotValidateExists() {
        Properties properties;

        {
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                LotAssigmentDao lotAssigmentDao = new LotAssigmentDao(Database.getInstance(properties));
                boolean value = lotAssigmentDao.findExistsAssigment(351, "ax26");
                System.out.println(value);
                Assertions.assertFalse(value);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Test
    void findQuantitybyFarmacoId() {


        Properties properties;

        {
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                LotAssigmentDao lotAssigmentDao = new LotAssigmentDao(Database.getInstance(properties));
                List<FieldData> list = lotAssigmentDao.findQuantitybyFarmacoId(347);
                Assertions.assertEquals(11, list.getFirst().getQuantity());


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    @Test
    void testFindQuantitybyFarmacoId() {
        Properties properties;

        {
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                LotAssigmentDao lotAssigmentDao = new LotAssigmentDao(Database.getInstance(properties));
                List<FieldData> list = lotAssigmentDao.findQuantitybyFarmacoId(340);
                //System.out.println(list.getFirst().getFarmaco_id());
                Assertions.assertEquals("ax22", list.getFirst().getCode());


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void findByFarmacoAll() {

        Properties properties;

        {
            try {
                properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                LotAssigmentDao lotAssigmentDao = new LotAssigmentDao(Database.getInstance(properties));
                List<FieldData> list = lotAssigmentDao.findByFarmacoAll();
                Assertions.assertFalse(list.isEmpty());

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}