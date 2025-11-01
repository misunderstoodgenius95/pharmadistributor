package pharma.dao;

import org.junit.jupiter.api.Test;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import javax.xml.crypto.Data;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PromotionDaoTest {


    @Test
    public void insert(){
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            PromotionDao dao=new PromotionDao(Database.getInstance(properties));
            dao.insert(FieldData.FieldDataBuilder.getbuilder().setDiscount_value(10).setProduction_date(Date.valueOf(LocalDate.now())).
                    setElapsed_date(Date.valueOf(LocalDate.of(2027,1,1))).setFarmaco_id(347).build());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}