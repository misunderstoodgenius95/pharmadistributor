package pharma.dao;

import pharma.dao.*;
import org.junit.jupiter.api.BeforeEach;
import pharma.config.Database;

import static org.junit.jupiter.api.Assertions.*;

public class PharmaDaoTest {


    @BeforeEach
    public void setUp() {
        Database database = Database.getInstance();
     PharmaDao pharmaDao=new PharmaDao(database);


    }













}


