package JPath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pharma.JPath.Query;

import  java.util.List;

import java.util.Arrays;
import java.util.stream.Stream;

class QueryTest {

    public  static Stream<Arguments> invalid_values() {
        return Stream.of(Arguments.of(Arrays.asList(null, null)),
                Arguments.of(Arrays.asList("nome", null)),
                Arguments.of(Arrays.asList(null, "Alice"))
        );
    }


        @Test
    void ValidEqual() {


        String actual= Query.equal("name","Alice");
        Assertions.assertEquals("$[?(@.name == 'Alice')]",actual);
    }

    @Test
    void Validlike() {
        String actual=Query.Like("name","a");
        Assertions.assertEquals("$[?(@.name =~ /(?i).*a.*/)]",actual);




    }

    @ParameterizedTest
    @MethodSource("invalid_values")
    public void InvalidLike(List<String> data) {
        Assertions.assertThrows(IllegalArgumentException.class,()->Query.Like(data.getFirst(),data.get(1)));



    }
    @ParameterizedTest
    @MethodSource("invalid_values")
    public void InvalidEquals(List<String> data) {
        Assertions.assertThrows(IllegalArgumentException.class,()->Query.equal(data.getFirst(),data.get(1)));



    }
}