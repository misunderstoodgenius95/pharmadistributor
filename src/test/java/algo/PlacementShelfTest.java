package algo;

import pharma.Model.FieldData;


import static org.junit.jupiter.api.Assertions.*;

class PlacementShelfTest {


    @Test
    void extract_max_shelf() {
        FieldData fieldData_shelf = FieldData.FieldDataBuilder.getbuilder().setId(1).setcode("a22").setLunghezza(102).setAltezza(100).
                setProfondita(40).setCapacity(60).setNum_rip(4).setSpessore(20).setCapacity(30).build();

        ShelfInfo shelfInfo1 = new ShelfInfo(fieldData_shelf, List.of());


        FieldData fieldData_shelf2 = FieldData.FieldDataBuilder.getbuilder().setId(1).setcode("a23").setLunghezza(102).setAltezza(100).
                setProfondita(40).setCapacity(60).setNum_rip(4).setSpessore(20).setCapacity(30).build();

        ShelfInfo shelfInfo2 = new ShelfInfo(fieldData_shelf2, List.of());


        HashMap<ShelfInfo, Integer> shelfInfoIntegerHashMap = new HashMap<>();
        shelfInfoIntegerHashMap.put(shelfInfo1, 10);
        shelfInfoIntegerHashMap.put(shelfInfo2, 30);

        }
        }