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
    private ShelfInfo shelfInfo1;
   private ShelfInfo shelfInfo2;
    @BeforeEach
    public void setUp(){
     shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setMagazzino_id(1)
                .setShelf_code("a22")
                .setLenght(102)
                .setHeight(100)
                .setDeep(40)
                .setWeight(60)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(List.of())
                .build();

         shelfInfo2 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setMagazzino_id(1)
                .setShelf_code("a22")
                .setLenght(102)
                .setHeight(100)
                .setDeep(40)
                .setWeight(60)
                .setNum_rip(4)
                .setShelf_thickness(20)
                .setShelvesCapacities(List.of())
                .build();
    }

    @Test
    void extract_max_shelf() {




        HashMap<ShelfInfo, Integer> shelfInfoIntegerHashMap = new HashMap<>();
        shelfInfoIntegerHashMap.put(shelfInfo1, 10);
        shelfInfoIntegerHashMap.put(shelfInfo2, 30);
        PlacementShelf.extract_max_shelf(shelfInfoIntegerHashMap, 20);


    }

    @Test
    void calculate_fit() {


        List<ShelvesCapacity> list = List.of(new ShelvesCapacity(1, "a22", 1, 0, 0, 0.0),
                new ShelvesCapacity(1, "a22", 2, 0, 0, 0),
                new ShelvesCapacity(1, "a22", 3, 0, 0, 0),
                new ShelvesCapacity(1, "a22", 4, 0, 0, 0));
            shelfInfo2.setShelvesCapacities(list);

        LotDimension lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0);
        ShelvesRemain shelvesRemain = new ShelvesRemain(new ShelvesCapacity(1, "a22", 1, 0, 0, 0.0), 80);
        PlacementShelf.calculate_fit(lotDimension, shelvesRemain, 30, shelfInfo2);
    }


    @Test
    void sorted_max_shelf_with() {

        ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder().setShelf_code("a21").setMagazzino_id(1).setLenght(200).setDeep(40).setNum_rip( 4).setShelf_thickness(10).setShelvesCapacities(List.of()).setWeight( 180).setLenght(200).build();
        ShelfInfo shelfInfo2 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a22")
                .setMagazzino_id(2)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(null)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo3 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a23")
                .setMagazzino_id(3)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(null)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo4 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a24")
                .setMagazzino_id(4)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(null)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo5 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a25")
                .setMagazzino_id(5)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(null)
                .setWeight(180)
                .setLenght(200)
                .build();
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
ShelfInfo.ShelfInfoBuilder.get_builder().build();

        ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a21")
                .setMagazzino_id(1)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(null)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo2 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a22")
                .setMagazzino_id(2)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(null)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo3 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a23")
                .setMagazzino_id(3)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(null)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo4 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a24")
                .setMagazzino_id(4)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(null)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo5 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a25")
                .setMagazzino_id(5)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(null)
                .setWeight(180)
                .setLenght(200)
                .build();

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

        ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a21")
                .setMagazzino_id(1)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(shelvesCapacities1)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo2 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a22")
                .setMagazzino_id(2)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(shelvesCapacities2)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo3 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a23")
                .setMagazzino_id(3)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(shelvesCapacities3)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo4 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a24")
                .setMagazzino_id(4)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(shelvesCapacities4)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo5 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a25")
                .setMagazzino_id(5)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(shelvesCapacities5)
                .setWeight(180)
                .setLenght(200)
                .build();
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
            ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a21")
                    .setMagazzino_id(1)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities1)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

            ShelfInfo shelfInfo2 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a22")
                    .setMagazzino_id(2)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities2)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

            ShelfInfo shelfInfo3 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a23")
                    .setMagazzino_id(3)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities3)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

            ShelfInfo shelfInfo4 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a24")
                    .setMagazzino_id(4)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities4)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

            ShelfInfo shelfInfo5 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a25")
                    .setMagazzino_id(5)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities5)
                    .setWeight(180)
                    .setLenght(200)
                    .build();
            // Create 5 ShelfInfo objects


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
            ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a21")
                    .setMagazzino_id(1)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities1)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

            ShelfInfo shelfInfo2 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a22")
                    .setMagazzino_id(2)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities2)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

            ShelfInfo shelfInfo3 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a23")
                    .setMagazzino_id(3)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities3)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

            ShelfInfo shelfInfo4 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a24")
                    .setMagazzino_id(4)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities4)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

            ShelfInfo shelfInfo5 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a25")
                    .setMagazzino_id(5)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities5)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

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

            ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a21")
                    .setMagazzino_id(1)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities1)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

            ShelfInfo shelfInfo5 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setShelf_code("a25")
                    .setMagazzino_id(5)
                    .setLenght(200)
                    .setDeep(40)
                    .setNum_rip(4)
                    .setShelf_thickness(10)
                    .setShelvesCapacities(shelvesCapacities5)
                    .setWeight(180)
                    .setLenght(200)
                    .build();

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

        ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a21")
                .setMagazzino_id(1)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(shelvesCapacities1)
                .setWeight(180)
                .setLenght(200)
                .build();

        ShelfInfo shelfInfo5 = ShelfInfo.ShelfInfoBuilder.get_builder()
                .setShelf_code("a25")
                .setMagazzino_id(5)
                .setLenght(200)
                .setDeep(40)
                .setNum_rip(4)
                .setShelf_thickness(10)
                .setShelvesCapacities(shelvesCapacities5)
                .setWeight(180)
                .setLenght(200)
                .build();

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




















