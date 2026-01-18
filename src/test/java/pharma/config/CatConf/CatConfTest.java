package pharma.config.CatConf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.config.PathConfig;

import static org.junit.jupiter.api.Assertions.*;

class CatConfTest {
    private CatConf catConf;

    @BeforeEach
    void setUp() {
        catConf=new CatConf(PathConfig.CAT_SUGGEST.getValue());
    }
    @Test
    public void ValidFindMaximumexpreday(){
        Assertions.assertEquals(180,catConf.get_value_integer("maximumexpreday"));
    }
}