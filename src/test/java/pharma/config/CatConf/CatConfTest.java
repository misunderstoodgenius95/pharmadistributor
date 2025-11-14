package pharma.config.CatConf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatConfTest {
    private CatConf catConf;

    @BeforeEach
    void setUp() {
        catConf=new CatConf("configcat-sdk-1/LxreCILqCUKPiPgevSQGoQ/w1WIJVMWoUOKocMj7FderA");
    }
    @Test
    public void ValidFindMaximumexpreday(){
        Assertions.assertEquals(180,catConf.get_value_integer("maximumexpreday"));
    }
}