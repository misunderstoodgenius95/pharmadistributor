package JPath;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    @Test
    void equal() {


        String actual=Query.equal("name","Alice");
        Assertions.assertEquals("$[?(@.name == 'Alice')]",actual);
    }
}