package pharma.config.Json;

import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.tomakehurst.wiremock.common.Json;
import net.bytebuddy.asm.Advice;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExtractJsonTest {
    private static String json_string;
    @BeforeEach
     public  void setUp(){


         JSONObject xanax = new JSONObject();

        xanax.put("category", "Ansiolitici");
        xanax.put("typology", "Compresse");
        xanax.put("dosage", "5mg");
        xanax.put("name", "Xanax");
        xanax.put("trend_market_percentage", 8.5);

        // Create second JSON object
        JSONObject sertralina = new JSONObject();
        sertralina.put("name", "Sertralina EG");
        sertralina.put("category", "AntiDepressivo");
        sertralina.put("typology", "Compresse");
        sertralina.put("dosage", "50mg");
        sertralina.put("trend_market_percentage", 12.0);

        // Create a JSON array and add both objects
        JSONArray jsonArray = new JSONArray();
        jsonArray.putAll(List.of(xanax,sertralina));
        json_string=jsonArray.toString();
    }
    public  static Stream<Arguments> invalid_values(){
        return Stream.of(Arguments.of(Arrays.asList(null,null)),
                Arguments.of(Arrays.asList(json_string,null)),
                Arguments.of(Arrays.asList(null,"$[?(@.name == 'Xanax')]"))
        );


    }




    @DisplayName("Find Value")
    @ParameterizedTest
    @ValueSource(strings = {"$[?(@.name == 'Xanax')]","$[?(@.name =~ /(?i).*xan.*/)]"})
    public void ValidTestJson(String path){


        JSONObject jsonObject=ExtractJson.extract_first(json_string,path);
        System.out.println(jsonObject);
        Assertions.assertEquals("Xanax",jsonObject.get("name"));




    }
    @DisplayName("NotFound")
    @ParameterizedTest
    @ValueSource(strings = {"$[?(@.name == 'Mak')]","$[?(@.name =~ /(?i).*mak.*/)]"})
    public void NotFoundTestJson(String path){


        JSONObject jsonObject=ExtractJson.extract_first(json_string,path);
        Assertions.assertTrue(jsonObject.isEmpty());


    }
    @ParameterizedTest
    @MethodSource("invalid_values")
    public void InvalidTestJson(List<String> data) {

        Assertions.assertThrows(IllegalArgumentException.class,()->ExtractJson.extract_first(data.getFirst(),data.get(1)));
    }
    
    @Test
    public  void Test(){

        System.out.println(ExtractJson.extract_first(json_string,"$[?(@.name==Alice )]"));

    }











}