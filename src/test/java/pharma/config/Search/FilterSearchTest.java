package pharma.config.Search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pharma.Model.FieldData;
import pharma.javafxlib.Search.FilterSearch;

import java.util.AbstractMap;

class FilterSearchTest {


    @Test
     public void choice_value(){
        FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setNome_tipologia("Ciao").build();
        String value= FilterSearch.choice_value("Tipologia",            new AbstractMap.SimpleEntry<>("Tipologia", fieldData.getNome_tipologia()));
        Assertions.assertEquals("Ciao",value);

    }

}