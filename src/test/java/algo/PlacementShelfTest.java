package algo;


import algo.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pharma.Model.FieldData;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;


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
        PlacementShelf.extract_max_shelf(shelfInfoIntegerHashMap, 20);


    }

    @Test
    void calculate_fit() {
        FieldData fieldData_shelf = FieldData.FieldDataBuilder.getbuilder().setId(1).setcode("a22").setLunghezza(102).setAltezza(100).
                setProfondita(40).setCapacity(600).setNum_rip(4).setSpessore(20).build();

        List<ShelvesCapacity> list = List.of(new ShelvesCapacity(1, "a22", 1, 0, 0, 0.0),
                new ShelvesCapacity(1, "a22", 2, 0, 0, 0),
                new ShelvesCapacity(1, "a22", 3, 0, 0, 0),
                new ShelvesCapacity(1, "a22", 4, 0, 0, 0));
        ShelfInfo shelfInfo = new ShelfInfo(fieldData_shelf, list);

        LotDimension lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0);
        ShelvesRemain shelvesRemain = new ShelvesRemain(new ShelvesCapacity(1, "a22", 1, 0, 0, 0.0), 80);
        PlacementShelf.calculate_fit(lotDimension, shelvesRemain, 30, shelfInfo);
    }


    @Test
    void sorted_max_shelf_with() {

        ShelfInfo shelfInfo1 = new ShelfInfo("a21", 1, 200, 40, 4, 10, null, 180, 200);
        ShelfInfo shelfInfo2 = new ShelfInfo("a22", 2, 200, 40, 4, 10, null, 180, 200);
        ShelfInfo shelfInfo3 = new ShelfInfo("a23", 3, 200, 40, 4, 10, null, 180, 200);
        ShelfInfo shelfInfo4 = new ShelfInfo("a24", 4, 200, 40, 4, 10, null, 180, 200);
        ShelfInfo shelfInfo5 = new ShelfInfo("a25", 5, 200, 40, 4, 10, null, 180, 200);

        HashMap<ShelfInfo, Integer> hashMap = new HashMap<>();
        hashMap.put(shelfInfo1, 100);
        hashMap.put(shelfInfo2, 300);
        hashMap.put(shelfInfo3, 1000);
        hashMap.put(shelfInfo4, 5000);
        hashMap.put(shelfInfo5, 10);
        Assertions.assertEquals(5000, PlacementShelf.sorted_max_shelf_with(hashMap).getFirst().getValue());


    }

    @Test
    void sorted_max_shelf_with_Filter() {


        ShelfInfo shelfInfo1 = new ShelfInfo("a21", 1, 200, 40, 4, 10, null, 180, 200);
        ShelfInfo shelfInfo2 = new ShelfInfo("a22", 2, 200, 40, 4, 10, null, 180, 200);
        ShelfInfo shelfInfo3 = new ShelfInfo("a23", 3, 200, 40, 4, 10, null, 180, 200);
        ShelfInfo shelfInfo4 = new ShelfInfo("a24", 4, 200, 40, 4, 10, null, 180, 200);
        ShelfInfo shelfInfo5 = new ShelfInfo("a25", 5, 200, 40, 4, 10, null, 180, 200);

        HashMap<ShelfInfo, Integer> hashMap = new HashMap<>();
        hashMap.put(shelfInfo1, 100);
        hashMap.put(shelfInfo2, 300);
        hashMap.put(shelfInfo3, 1000);
        hashMap.put(shelfInfo4, 5000);
        hashMap.put(shelfInfo5, 10);
        List<Map.Entry<ShelfInfo, Integer>> list = new ArrayList<>(hashMap.entrySet());
        Assertions.assertEquals(2, PlacementShelf.filter_value(list, 1000).size());
    }


    @Test
    public void TestWithAllEmpty() {

        List<ShelvesCapacity> shelvesCapacities1 = new ArrayList<>();
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 1, 0.0, 0.0, 0.0));
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 2, 0.0, 0.0, 0.0));
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 3, 0.0, 0.0, 0.0));
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 4, 0.0, 0.0, 0.0));

        // Shelf 2 capacities
        List<ShelvesCapacity> shelvesCapacities2 = new ArrayList<>();
        shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 1, 0.0, 0.0, 0.0));
        shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 2, 0.0, 0.0, 0.0));
        shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 3, 0.0, 0.0, 0.0));
        shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 4, 0.0, 0.0, 0.0));

        // Shelf 3 capacities
        List<ShelvesCapacity> shelvesCapacities3 = new ArrayList<>();
        shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 1, 0.0, 0.0, 0.0));
        shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 2, 0.0, 0.0, 0.0));
        shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 3, 0.0, 0.0, 0.0));
        shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 4, 0.0, 0.0, 0.0));

        // Shelf 4 capacities
        List<ShelvesCapacity> shelvesCapacities4 = new ArrayList<>();
        shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 1, 0.0, 0.0, 0.0));
        shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 2, 0.0, 0.0, 0.0));
        shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 3, 0.0, 0.0, 0.0));
        shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 4, 0.0, 0.0, 0.0));

        // Shelf 5 capacities
        List<ShelvesCapacity> shelvesCapacities5 = new ArrayList<>();
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 1, 0.0, 0.0, 0.0));
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 2, 0.0, 0.0, 0.0));
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 3, 0.0, 0.0, 0.0));
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 4, 0.0, 0.0, 0.0));

        // Create 5 ShelfInfo objects
        ShelfInfo shelfInfo1 = new ShelfInfo("a21", 1, 200, 40, 4, 10, shelvesCapacities1, 180, 200);
        ShelfInfo shelfInfo2 = new ShelfInfo("a22", 2, 200, 40, 4, 10, shelvesCapacities2, 180, 200);
        ShelfInfo shelfInfo3 = new ShelfInfo("a23", 3, 200, 40, 4, 10, shelvesCapacities3, 180, 200);
        ShelfInfo shelfInfo4 = new ShelfInfo("a24", 4, 200, 40, 4, 10, shelvesCapacities4, 180, 200);
        ShelfInfo shelfInfo5 = new ShelfInfo("a25", 5, 200, 40, 4, 10, shelvesCapacities5, 180, 200);

        // Optional: Add them to a list if needed
        List<ShelfInfo> shelfInfoList = new ArrayList<>();
        shelfInfoList.add(shelfInfo1);
        shelfInfoList.add(shelfInfo2);
        shelfInfoList.add(shelfInfo3);
        shelfInfoList.add(shelfInfo4);
        shelfInfoList.add(shelfInfo5);
        PlacementShelf placementShelf = new PlacementShelf(shelfInfoList);

        LotDimension lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0);
        LotAssigment assigment = placementShelf.assignmentLots(lotDimension, 50);
        Assertions.assertEquals(1, assigment.getShelvesAssigmentList().size());


    }


    @Nested
    class TestWithOneEmpty {
        LotAssigment.ShelvesAssigment shelve;

        @BeforeEach
        public void setUp() {


            List<ShelvesCapacity> shelvesCapacities1 = new ArrayList<>();
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 1, 200.0, 40.0, 0.0));
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 2, 200.0, 40.0, 0.0));
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 3, 200.0, 40.0, 0.0));
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 4, 200.0, 40.0, 0.0));

            // Shelf 2 capacities
            List<ShelvesCapacity> shelvesCapacities2 = new ArrayList<>();
            shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 1, 200.0, 40.0, 0.0));
            shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 2, 200.0, 40.0, 0.0));
            shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 3, 200.0, 40.0, 0.0));
            shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 4, 200.0, 40.0, 0.0));

            // Shelf 3 capacities
            List<ShelvesCapacity> shelvesCapacities3 = new ArrayList<>();
            shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 1, 200.0, 40.0, 0.0));
            shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 2, 200.0, 40.0, 0.0));
            shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 3, 200.0, 40.0, 0.0));
            shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 4, 200.0, 40.0, 0.0));

            // Shelf 4 capacities
            List<ShelvesCapacity> shelvesCapacities4 = new ArrayList<>();
            shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 1, 200.0, 40.0, 0.0));
            shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 2, 200.0, 40.0, 0.0));
            shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 3, 200.0, 40.0, 0.0));
            shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 4, 200.0, 40.0, 0.0));

            // Shelf 5 capacities
            List<ShelvesCapacity> shelvesCapacities5 = new ArrayList<>();
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 1, 20.0, 40.0, 0.0));
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 2, 20.0, 40.0, 0.0));
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 3, 20.0, 40.0, 0.0));
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 4, 0.0, 0.0, 0.0));

            // Create 5 ShelfInfo objects
            ShelfInfo shelfInfo1 = new ShelfInfo("a21", 1, 200, 40, 4, 10, shelvesCapacities1, 180, 200);
            ShelfInfo shelfInfo2 = new ShelfInfo("a22", 2, 200, 40, 4, 10, shelvesCapacities2, 180, 200);
            ShelfInfo shelfInfo3 = new ShelfInfo("a23", 3, 200, 40, 4, 10, shelvesCapacities3, 180, 200);
            ShelfInfo shelfInfo4 = new ShelfInfo("a24", 4, 200, 40, 4, 10, shelvesCapacities4, 180, 200);
            ShelfInfo shelfInfo5 = new ShelfInfo("a25", 5, 200, 40, 4, 10, shelvesCapacities5, 180, 200);

            // Optional: Add them to a list if needed
            List<ShelfInfo> shelfInfoList = new ArrayList<>();
            shelfInfoList.add(shelfInfo1);
            shelfInfoList.add(shelfInfo2);
            shelfInfoList.add(shelfInfo3);
            shelfInfoList.add(shelfInfo4);
            shelfInfoList.add(shelfInfo5);
            PlacementShelf placementShelf = new PlacementShelf(shelfInfoList);
            LotDimension lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0);
            LotAssigment assigment = placementShelf.assignmentLots(lotDimension, 50);
            shelve = assigment.getShelvesAssigmentList().getFirst();
        }

        @Test
        public void ValidShelfCode() {
            Assertions.assertEquals("a25", shelve.getShelf_code());
        }

        @Test
        public void ValidShelfLevel() {

            Assertions.assertEquals(4, shelve.getShelf_level());


        }
    }

    @Nested
    class TestOneMax {
        PlacementShelf placementShelf;
        LotDimension lotDimension;
        LotAssigment lotAssigment;

        @BeforeEach
        public void setUp() {


            List<ShelvesCapacity> shelvesCapacities1 = new ArrayList<>();
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 1, 200.0, 40.0, 0.0));
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 2, 200.0, 40.0, 0.0));
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 3, 200.0, 40.0, 0.0));
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 4, 200.0, 40.0, 0.0));

            // Shelf 2 capacities
            List<ShelvesCapacity> shelvesCapacities2 = new ArrayList<>();
            shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 1, 200.0, 40.0, 0.0));
            shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 2, 200.0, 40.0, 0.0));
            shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 3, 200.0, 40.0, 0.0));
            shelvesCapacities2.add(new ShelvesCapacity(2, "a22", 4, 200.0, 40.0, 0.0));

            // Shelf 3 capacities
            List<ShelvesCapacity> shelvesCapacities3 = new ArrayList<>();
            shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 1, 200.0, 40.0, 0.0));
            shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 2, 200.0, 40.0, 0.0));
            shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 3, 200.0, 40.0, 0.0));
            shelvesCapacities3.add(new ShelvesCapacity(3, "a23", 4, 200.0, 40.0, 0.0));

            // Shelf 4 capacities
            List<ShelvesCapacity> shelvesCapacities4 = new ArrayList<>();
            shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 1, 200.0, 40.0, 0.0));
            shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 2, 200.0, 40.0, 0.0));
            shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 3, 200.0, 40.0, 0.0));
            shelvesCapacities4.add(new ShelvesCapacity(4, "a24", 4, 200.0, 40.0, 0.0));

            // Shelf 5 capacities
            List<ShelvesCapacity> shelvesCapacities5 = new ArrayList<>();
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 1, 200.0, 40.0, 0.0)); //0
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 2, 180.0, 30.0, 0.0));// 2
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 3, 120, 20.0, 0.0));//24
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 4, 80, 7.0, 0.0));//72

            // Create 5 ShelfInfo objects
            ShelfInfo shelfInfo1 = new ShelfInfo("a21", 1, 200, 40, 4, 10, shelvesCapacities1, 180, 200);
            ShelfInfo shelfInfo2 = new ShelfInfo("a22", 2, 200, 40, 4, 10, shelvesCapacities2, 180, 200);
            ShelfInfo shelfInfo3 = new ShelfInfo("a23", 3, 200, 40, 4, 10, shelvesCapacities3, 180, 200);
            ShelfInfo shelfInfo4 = new ShelfInfo("a24", 4, 200, 40, 4, 10, shelvesCapacities4, 180, 200);
            ShelfInfo shelfInfo5 = new ShelfInfo("a25", 5, 200, 40, 4, 10, shelvesCapacities5, 180, 200);

            // Optional: Add them to a list if needed
            List<ShelfInfo> shelfInfoList = new ArrayList<>();
            shelfInfoList.add(shelfInfo1);
            shelfInfoList.add(shelfInfo2);
            shelfInfoList.add(shelfInfo3);
            shelfInfoList.add(shelfInfo4);
            shelfInfoList.add(shelfInfo5);
            placementShelf = new PlacementShelf(shelfInfoList);
            lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0);
            lotAssigment = placementShelf.assignmentLots(lotDimension, 26);
        }

        @Test
        public void ValidLevel() {


            Assertions.assertEquals(4, lotAssigment.getShelvesAssigmentList().getFirst().getShelf_level());


        }

        @Test
        public void ValidShelfCode() {
            Assertions.assertEquals("a25", lotAssigment.getShelvesAssigmentList().getFirst().getShelf_code());


        }


    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("Solution 2: TestInstance PER_CLASS")
    class TestTwoSelfClassSolution {

        PlacementShelf placementShelf;
        LotDimension lotDimension;
        LotAssigment lotAssigment;

        @BeforeAll // Changed from @BeforeEach - runs once before all tests
        public void setUp() {
            // Your existing setup code
            List<ShelvesCapacity> shelvesCapacities1 = new ArrayList<>();
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 1, 200.0, 40.0, 0.0));
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 2, 200.0, 40.0, 0.0));
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 3, 200.0, 40.0, 0.0));
            shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 4, 200.0, 40.0, 0.0));

            List<ShelvesCapacity> shelvesCapacities5 = new ArrayList<>();
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 1, 200.0, 40.0, 0.0)); //0
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 2, 180.0, 30.0, 0.0));// 2
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 3, 120, 20.0, 0.0));//24
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 4, 80, 7.0, 0.0));//72


            ShelfInfo shelfInfo1 = new ShelfInfo("a21", 1, 200, 40, 4, 10, shelvesCapacities1, 180, 200);
            ShelfInfo shelfInfo5 = new ShelfInfo("a25", 5, 200, 40, 4, 10, shelvesCapacities5, 180, 200);

            List<ShelfInfo> shelfInfoList = Arrays.asList(shelfInfo1, shelfInfo5);

            placementShelf = new PlacementShelf(shelfInfoList);
            lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0);
            lotAssigment = placementShelf.assignmentLots(lotDimension, 82);
        }


        Stream<Arguments> value_assignment() {  // No 'static' needed
            return Stream.of(
                    Arguments.of(
                            lotAssigment.getShelvesAssigmentList().getFirst().getShelf_code(),
                            "a25"
                    ),
                    Arguments.of(
                            lotAssigment.getShelvesAssigmentList().get(1).getShelf_code(),
                            "a25"

                    ),
                    Arguments.of(
                            lotAssigment.getShelvesAssigmentList().getFirst().getShelf_level(),
                            4
                    ),
                    Arguments.of(
                            lotAssigment.getShelvesAssigmentList().get(1).getShelf_level(),
                            3

                    )
            );
        }

        @ParameterizedTest
        @MethodSource("value_assignment")
        @DisplayName("Method source with PER_CLASS lifecycle")
        void testWithMethodSource(Object actual, Object expected) {
            assertEquals(expected, actual);
        }


    }


    @Test
    public void multiple_shelf() {

        List<ShelvesCapacity> shelvesCapacities1 = new ArrayList<>(); //68 totali
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 1, 100, 20.0, 0.0)); //32
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 2, 80.0, 20.0, 0.0)); //36
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 3, 200.0, 40.0, 0.0));
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 4, 200.0, 40.0, 0.0));

        List<ShelvesCapacity> shelvesCapacities5 = new ArrayList<>(); // 98 totali
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 1, 200.0, 40.0, 0.0)); //0
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 2, 180.0, 30.0, 0.0));// 2
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 3, 120, 20.0, 0.0));//24
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 4, 80, 7.0, 0.0));//72*/


        ShelfInfo shelfInfo1 = new ShelfInfo("a21", 1, 200.0, 40.0, 4, 10, shelvesCapacities1, 180, 200);
        ShelfInfo shelfInfo5 = new ShelfInfo("a25", 5, 200.0, 40.0, 4, 10, shelvesCapacities5, 180, 200);

        List<ShelfInfo> shelfInfoList = Arrays.asList(shelfInfo1, shelfInfo5);


        PlacementShelf placementShelf = new PlacementShelf(shelfInfoList);
        LotDimension lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0);
        LotAssigment lotAssigment = placementShelf.assignmentLots(lotDimension, 109);

        lotAssigment.getShelvesAssigmentList().forEach(value -> {
            System.out.println("code: " + value.getShelf_code() + " level: " + value.getShelf_level() + " quantity: " + value.getQuantity());
        });
        //   System.out.println("size: "+lotAssigment.getShelvesAssigmentList().size());


    }
}




















