package pharma.Storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileConfigTest {
    static FileConfig fileConfig;
    @BeforeAll
    static void setUp()  {
         fileConfig=new FileConfig("auth.properties" );


    }


    @Test
    public void setAndGetProperties() {

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("CLIENT_ID","p14pZzboKJeCcIjfmkb3nyTJoD14mf1r");
        hashMap.put("CLIENT_SECRET","p6t-4gyYZAarQo2-H3Jygpk00I0esL0UMO4V869WXGQ_4_HJXMYgP9xlUphK3wsa");
        hashMap.put("GRANT_TYPE","password");
        hashMap.put("audience","https://distroapi.com");
        fileConfig.setProperties(hashMap);
    HashMap<String,String> actual= fileConfig.getProperties(Arrays.asList("CLIENT_ID","CLIENT_SECRET","GRANT_TYPE","audience"));
        Assertions.assertEquals(hashMap,actual);



    }




}