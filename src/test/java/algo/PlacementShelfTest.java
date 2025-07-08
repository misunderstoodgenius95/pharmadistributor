package algo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pharma.Model.FieldData;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlacementShelfTest {

    @BeforeEach
    public void setUp(){

    }
    @Test
    void extract_max_shelf() {
        FieldData fieldData_shelf=FieldData.FieldDataBuilder.getbuilder().setId(1).setcode("a22").setLunghezza(102).setAltezza(100).
                setProfondita(40).setCapacity(60).setNum_rip(4).setSpessore(20).setCapacity(30).build();

       ShelfInfo  shelfInfo1=new ShelfInfo(fieldData_shelf,List.of());


        FieldData fieldData_shelf2=FieldData.FieldDataBuilder.getbuilder().setId(1).setcode("a23").setLunghezza(102).setAltezza(100).
                setProfondita(40).setCapacity(60).setNum_rip(4).setSpessore(20).setCapacity(30).build();

        ShelfInfo shelfInfo2=new ShelfInfo(fieldData_shelf2,List.of());


        HashMap<ShelfInfo,Integer> shelfInfoIntegerHashMap=new HashMap<>();
        shelfInfoIntegerHashMap.put(shelfInfo1,10);
        shelfInfoIntegerHashMap.put(shelfInfo2,30);
        ShelfInfo shelfInfo_actual=PlacementShelf.extract_max_shelf(shelfInfoIntegerHashMap);
        Assertions.assertEquals("a23",shelfInfo_actual.getShelf_code());

    }
}