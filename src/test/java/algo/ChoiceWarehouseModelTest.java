package algo;

import algoWarehouse.ChoiceWarehouse;
import algoWarehouse.ShelfInfo;
import algoWarehouse.ShelvesCapacity;
import com.github.curiousoddman.rgxgen.nodes.Choice;
import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import pharma.Controller.Warehouse;
import pharma.Model.*;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ChoiceWarehouseModelTest {
    private ChoiceWarehouse choiceWarehouse;
    private Farmacia farmacia1;
    private Farmacia farmacia2;
    private Farmacia farmacia3;
    private Farmacia farmacia4;
    private Farmacia farmacia5;
    private Farmacia farmacia6;
    private Farmacia farmacia7;
    private Farmacia farmacia8;
    private Farmacia farmacia9;
    private Farmacia farmacia10;
    @Mock
    private List<WarehouseModel> list_warehouseModel;
    private List<PharmacyAssigned> assigneds;
    @BeforeEach
    public void setUp() {
         list_warehouseModel = List.of(
                new WarehouseModel(1, "mag1", new PGgeometry(new Point(-23.5501, -46.6330)),List.of()),
                new WarehouseModel(2, "mag2", new PGgeometry(new Point(-23.4501, -45.5330)),List.of()),
                new WarehouseModel(3, "mag3", new PGgeometry(new Point(34.5501, 45.6330)),List.of()),
                new WarehouseModel(4, "mag4", new PGgeometry(new Point(-22.4501, -45.6330)),List.of()));

        farmacia1 = new Farmacia("Farmacia Central", 1, new PGgeometry(new Point(38.254652, 15.504868)));
        farmacia2 = new Farmacia("Farmacia Norte", 2, new PGgeometry(new Point(38.156059, 14.745716)));
        farmacia3 = new Farmacia("Farmacia Sul", 3, new PGgeometry(new Point(38.056718, 14.830686)));
        farmacia4 = new Farmacia("Farmacia Nord", 4, new PGgeometry(new Point(38.194237, 13.211291)));
        farmacia5 = new Farmacia("Farmacia Est", 5, new PGgeometry(new Point(37.071914, 15.256249)));

        farmacia6 = new Farmacia("Farmacia Central2 ", 1, new PGgeometry(new Point(37.700417, 14.04684)));
        farmacia7 = new Farmacia("Farmacia Norte 2", 2, new PGgeometry(new Point(37.587327, 13.398657)));

        farmacia8 = new Farmacia("Farmacia Sul 2", 3, new PGgeometry(new Point(37.247026, 14.223032)));
        farmacia9 = new Farmacia("Farmacia Nord 3", 4, new PGgeometry(new Point(39.310732, 16.285324)));
        farmacia10 = new Farmacia("Farmacia Est 4 ", 5, new PGgeometry(new Point(38, 843597, 16.520237)));
      assigneds = Arrays.asList(

                // Using Point constructor
                new PharmacyAssigned(farmacia1, 100),// Near São Paulo
                new PharmacyAssigned(farmacia2, 25), // Near Rio
                new PharmacyAssigned(farmacia1, 150), // Same as farmacia
                new PharmacyAssigned(farmacia3, 30), // Near Curitiba
                new PharmacyAssigned(farmacia2, 20), // Near Rio
                new PharmacyAssigned(farmacia1, 50),
                new PharmacyAssigned(farmacia4, 20),
                new PharmacyAssigned(farmacia4, 100),
                new PharmacyAssigned(farmacia5, 10),
                new PharmacyAssigned(farmacia5, 35),
                new PharmacyAssigned(farmacia6, 50),
                new PharmacyAssigned(farmacia7, 20),
                new PharmacyAssigned(farmacia8, 100),
                new PharmacyAssigned(farmacia9, 10),
                new PharmacyAssigned(farmacia10, 35));

        choiceWarehouse = new ChoiceWarehouse(list_warehouseModel, assigneds);


    }


    /*
     * Valid test for Grouping by pharmacist quantity
     */
    @Test
    void pharmacy_by_qty() {
                                        //              SUT
        Map<Farmacia, Integer> map = choiceWarehouse.pharmacy_by_qty();
        Assertions.assertEquals(300, map.get(farmacia1));


    }
    @Test
    void InvalidEmptypharmacy_by_qty() {


        ChoiceWarehouse choiceWarehouse_empty=new ChoiceWarehouse(list_warehouseModel,List.of());
         //SUT

       Assertions.assertThrows(IllegalArgumentException.class, choiceWarehouse_empty::pharmacy_by_qty);

    }

    @Test
    void sorted_by_max() {
        Map<Farmacia, Integer> map = new HashMap<>();
        map.put(farmacia1, 100);
        map.put(farmacia2, 1000);
        map.put(farmacia3, 10);
        map.put(farmacia4, 500);
        map.put(farmacia6, 50);
        map.put(farmacia7, 20);
        map.put(farmacia8, 100);
        map.put(farmacia9, 10);
        map.put(farmacia10, 35);


        List<Map.Entry<Farmacia, Integer>> list = choiceWarehouse.sorted_by_max(map);


        Assertions.assertEquals(1000, list.getFirst().getValue());


    }
    @Test
    void Invalidsorted_by_max() {
        Map<Farmacia, Integer> map = new HashMap<>();



        Assertions.assertThrows(IllegalArgumentException.class,()->choiceWarehouse.sorted_by_max(map));


    }


    /*public static List<PharmacyDistance> list_distance() {

        List<PharmacyDistance> list_distance = new ArrayList<>();

        // Create and add each PharmacyDistance
        list_distance.add(new PharmacyDistance(
                new Farmacia("Farmacia Centro", 2, new PGgeometry(new Point(41.9028, 12.4964))),
                67.2319726790798));

        list_distance.add(new PharmacyDistance(
                new Farmacia("Farmacia Nord", 5, new PGgeometry(new Point(41.9128, 12.5064))),
                133.32265814507414));

        list_distance.add(new PharmacyDistance(
                new Farmacia("Farmacia Sud", 4, new PGgeometry(new Point(41.8928, 12.4864))),
                200.46046826180887));

        list_distance.add(new PharmacyDistance(
                new Farmacia("Farmacia Est", 3, new PGgeometry(new Point(41.9228, 12.5164))),
                62.92264382001019));
        return list_distance;


    }*/


    private static Stream<Arguments> provideTestData() {

        Farmacia farmacia1 = new Farmacia("Farmacia Central", 1,
                new PGgeometry(new Point(38.254652, 15.504868)));
        Farmacia farmacia2 = new Farmacia("Farmacia Norte", 2,
                new PGgeometry(new Point(38.156059, 14.745716)));
        Farmacia farmacia3 = new Farmacia("Farmacia Sul", 3,
                new PGgeometry(new Point(38.056718, 14.830686)));
        Farmacia farmacia4 = new Farmacia("Farmacia Nord", 4,
                new PGgeometry(new Point(38.194237, 13.211291)));
        return Stream.of(
                Arguments.of(
                        List.of(Map.entry(farmacia1, 1000), Map.entry(farmacia2, 200), Map.entry(farmacia3, 500), Map.entry(farmacia4, 200)),
                        Arrays.asList(500, 1000)
                ),
                Arguments.of(
                        List.of(Map.entry(farmacia1, 2000), Map.entry(farmacia2, 1500), Map.entry(farmacia3, 1000), Map.entry(farmacia4, 500)),
                        Arrays.asList(1500, 2000, 1000, 500)
                ),
                Arguments.of(
                        List.of(Map.entry(farmacia1, 100), Map.entry(farmacia2, 200), Map.entry(farmacia3, 300), Map.entry(farmacia4, 400)),
                        List.of()
                )
        );
    }


    @ParameterizedTest
    @MethodSource("provideTestData")
    public void testExtractMaxThresholdLot_ParameterizedTest(
            List<Map.Entry<Farmacia, Integer>> input,
            List<Integer> expectedValues) {

        // Act
        List<Map.Entry<Farmacia, Integer>> result =
                ChoiceWarehouse.extract_max_threshold_lot(input);

        List<Integer> list_actual = result.stream().map(Map.Entry::getValue).toList();

        assertThat(list_actual).containsAnyElementsOf(expectedValues);


    }





    @Test
    void middle_listFor() {

        List<Farmacia> list = ChoiceWarehouse.limit_entries(List.of(
                Map.entry(farmacia1, 1000),
                Map.entry(farmacia2, 200),
                Map.entry(farmacia3, 600),
                Map.entry(farmacia4, 800)
        ));
        assertThat(list).containsAll(List.of(farmacia1, farmacia2));


    }

    @Test
    void middle_listTwo() {

        List<Farmacia> list = ChoiceWarehouse.limit_entries(List.of(
                Map.entry(farmacia1, 1000),
                Map.entry(farmacia2, 200)

        ));
        assertThat(list).containsAll(List.of(farmacia1, farmacia2));


    }

    @Test
    void middle_listOdd() {

        List<Farmacia> list = ChoiceWarehouse.limit_entries(List.of(
                Map.entry(farmacia1, 1000),
                Map.entry(farmacia2, 200),
                Map.entry(farmacia3, 200)

        ));
        assertThat(list).containsAll(List.of(farmacia1, farmacia2, farmacia3));


    }

    @Test
    void middle_listOddGreather4() {

        List<Farmacia> list = ChoiceWarehouse.limit_entries(List.of(
                Map.entry(farmacia1, 1000),
                Map.entry(farmacia2, 200),
                Map.entry(farmacia3, 200),
                Map.entry(farmacia4, 200),
                Map.entry(farmacia5, 200)
        ));
        assertThat(list).containsAll(List.of(farmacia1, farmacia2));
    }


    @Test
    void Validaverage_entries() {

        List<Farmacia> input = List.of(
                farmacia1,
                farmacia2,
                farmacia3,
                farmacia4,
                farmacia5);
        Point point_expected = new Point(37.946716, 14.709762);
        PharmacyDistance distance = ChoiceWarehouse.average_entries(input);
        List<Farmacia> farmacias = List.of(farmacia1, farmacia2, farmacia3, farmacia4, farmacia5);
        assertThat(distance).satisfies(pd -> {
            assertThat(pd.getFarmaciaList()).containsAll(farmacias);
            assertThat(pd.getAverage()).
                    satisfies(point -> {
                        assertThat(point.getX()).isCloseTo(point_expected.getX(), Offset.offset(0.01));
                        assertThat(point.getY()).isEqualTo(point_expected.getY(), Offset.offset(0.01));
                    });
        });


    }

    @Test
    void InValidAverage_entries() {

        List<Farmacia> input = List.of();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ChoiceWarehouse.average_entries(input));
    }

    @Test
    void ValidIn_range() {
        Point point_pharmacy=new Point(40.7589,-73.9851);
        Point point_wharehouse=new Point(40.5017,-74.2291);

        Assertions.assertTrue(ChoiceWarehouse.in_range(point_pharmacy,point_wharehouse));
    }
    @Test
    void ValidIn_range2() {
        Point point_pharmacy=new Point(38.15,15.02);
        Point point_wharehouse=new Point(38.40,15.40);

        Assertions.assertTrue(ChoiceWarehouse.in_range(point_pharmacy,point_wharehouse));
    }


    @Test
    void InValidIn_range() {
        Point point_pharmacy=new Point(40.75,-73.9851);
        Point point_wharehouse=new Point(41.55,-74.98);

        Assertions.assertFalse(ChoiceWarehouse.in_range(point_pharmacy,point_wharehouse));
    }




    @Nested
    class ValidPharmacyDistance {
        @Test
        public void InvalidPhramcyDistanceNoPharmacy() {
            List<Map.Entry<Farmacia, Integer>> entries = new ArrayList<>();
            Assertions.assertThrows(IllegalArgumentException.class, () -> choiceWarehouse.distance_pharmacist(entries));


        }

        @Test
        public void ValidPharamcyDistanceNoPharmacyPoint() {
            List<Map.Entry<Farmacia, Integer>> entries = new ArrayList<>();
            entries.add(Map.entry(farmacia1, 300));
            entries.add(Map.entry(farmacia2, 200));
            entries.add(Map.entry(farmacia3, 400));
            entries.add(Map.entry(farmacia4, 400));
//SUT
            List<PharmacyDistance> list = choiceWarehouse.distance_pharmacist(entries);

            assertThat(list).allSatisfy(p -> {
                assertThat(list).hasSize(1);
                assertThat(p.getAverage()).satisfies(
                        point -> {
                            assertThat(point.getX()).isCloseTo(38.16, Offset.offset(0.01));
                            assertThat(point.getY()).isCloseTo(14.57, Offset.offset(0.01));
                        });
            });
        }

        @Test
        public void ValidPharamcyDistanceNoPharmacyPointNeighbour() {
            List<Map.Entry<Farmacia, Integer>> entries = new ArrayList<>();
            entries.add(Map.entry(farmacia1, 600));
            entries.add(Map.entry(farmacia5, 500));
            entries.add(Map.entry(farmacia3, 400));
            entries.add(Map.entry(farmacia4, 400));
            List<PharmacyDistance> list = choiceWarehouse.distance_pharmacist(entries);
            assertThat(list).allSatisfy(p -> {
                assertThat(list).hasSize(1);
                assertThat(p.getAverage()).satisfies(
                        point -> {
                            assertThat(point.getX()).isCloseTo(38.16, Offset.offset(0.01));
                            assertThat(point.getY()).isCloseTo(14.57, Offset.offset(0.01));
                        });
            });
        }

        @Test
        public void ValidPharamcyDistancePharmacyPointNeighbour() {
            List<Map.Entry<Farmacia, Integer>> entries = new ArrayList<>();
            entries.add(Map.entry(farmacia1, 600));
            entries.add(Map.entry(farmacia2, 500));
            entries.add(Map.entry(farmacia3, 400));
            entries.add(Map.entry(farmacia4, 400));
            //SUT
            List<PharmacyDistance> list = choiceWarehouse.distance_pharmacist(entries);
            assertThat(list).allSatisfy(p -> {
                assertThat(list).hasSize(1);
                assertThat(p.getAverage()).satisfies(
                        point -> {
                            assertThat(point.getX()).isCloseTo(38.15, Offset.offset(0.01));
                            assertThat(point.getY()).isCloseTo(15.02, Offset.offset(0.01));
                        });
            });
        }

        @Test
        public void ValidPharamcyDistancePharmacyMoreOnePointNeighbour() {
            List<Map.Entry<Farmacia, Integer>> entries = new ArrayList<>();
            entries.add(Map.entry(farmacia1, 600));
            entries.add(Map.entry(farmacia2, 500));
            entries.add(Map.entry(farmacia3, 400));
            entries.add(Map.entry(farmacia4, 400));
            entries.add(Map.entry(farmacia6, 600));
            entries.add(Map.entry(farmacia7, 400));
            entries.add(Map.entry(farmacia8, 300));
            //SUT
            List<PharmacyDistance> list = choiceWarehouse.distance_pharmacist(entries);

            Assertions.assertAll(
                    () -> assertThat(list.getFirst()).satisfies(p -> {

                        assertThat(p.getAverage()).satisfies(
                                point -> {
                                    assertThat(point.getX()).isCloseTo(38.15, Offset.offset(0.01));
                                    assertThat(point.getY()).isCloseTo(15.02, Offset.offset(0.01));
                                });
                    }),
                    () -> assertThat(list.get(1)).satisfies(p -> {

                        assertThat(p.getAverage()).satisfies(
                                point -> {
                                    assertThat(point.getX()).isCloseTo(37.51, Offset.offset(0.01));
                                    assertThat(point.getY()).isCloseTo(13.88, Offset.offset(0.01));
                                });
                    })
            );


        }


    }
    @Nested
    class Availability_WarehouseModel {


        @BeforeEach
        public void setUp() {


            ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("A21")
                    .setLenght(102)
                    .setHeight(100)
                    .setDeep(50)
                    .setWeight(200)
                    .setNum_rip(4)
                    .setShelf_thickness(20)
                    .setShelvesCapacities(List.of(
                            new ShelvesCapacity(1, "A21", 1, 102.0, 50.0, 85.5),  // Full - max capacity
                            new ShelvesCapacity(2, "A21", 2, 80.0, 35.0, 58.8),   // 78% occupied
                            new ShelvesCapacity(3, "A21", 3, 45.0, 20.0, 22.5),   // 44% occupied
                            new ShelvesCapacity(4, "A21", 4, 0.0, 0.0, 0.0)       // Empty
                    ))
                    .build();

// Shelf 2 - Length: 150, Depth: 70 (4 levels with different occupancy)
            ShelfInfo shelfInfo2 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("A22")
                    .setLenght(150)
                    .setHeight(100)
                    .setDeep(70)
                    .setWeight(250)
                    .setNum_rip(4)
                    .setShelf_thickness(20)
                    .setShelvesCapacities(List.of(
                            new ShelvesCapacity(5, "A22", 1, 150.0, 70.0, 131.25), // Full - max capacity
                            new ShelvesCapacity(6, "A22", 2, 120.0, 55.0, 82.5),   // 80% occupied
                            new ShelvesCapacity(7, "A22", 3, 75.0, 35.0, 32.8),    // 50% occupied
                            new ShelvesCapacity(8, "A22", 4, 30.0, 15.0, 5.6)      // 20% occupied
                    ))
                    .build();

// Shelf 3 - Length: 80, Depth: 35 (4 levels with different occupancy)
            ShelfInfo shelfInfo3 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("A23")
                    .setLenght(80)
                    .setHeight(100)
                    .setDeep(35)
                    .setWeight(150)
                    .setNum_rip(4)
                    .setShelf_thickness(20)
                    .setShelvesCapacities(List.of(
                            new ShelvesCapacity(9, "A23", 1, 80.0, 35.0, 35.0),    // Full - max capacity
                            new ShelvesCapacity(10, "A23", 2, 65.0, 28.0, 22.8),   // 81% occupied
                            new ShelvesCapacity(11, "A23", 3, 40.0, 18.0, 9.0),    // 51% occupied
                            new ShelvesCapacity(12, "A23", 4, 0.0, 0.0, 0.0)       // Empty
                    ))
                    .build();

// Shelf 4 - Length: 200, Depth: 30 (4 levels with different occupancy)
            ShelfInfo shelfInfo4 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("A24")
                    .setLenght(200)
                    .setHeight(100)
                    .setDeep(30)
                    .setWeight(180)
                    .setNum_rip(4)
                    .setShelf_thickness(20)
                    .setShelvesCapacities(List.of(
                            new ShelvesCapacity(13, "A24", 1, 200.0, 30.0, 75.0),  // Full - max capacity
                            new ShelvesCapacity(14, "A24", 2, 160.0, 24.0, 48.0),  // 80% occupied
                            new ShelvesCapacity(15, "A24", 3, 100.0, 15.0, 18.75), // 50% occupied
                            new ShelvesCapacity(16, "A24", 4, 25.0, 8.0, 2.5)      // 12% occupied
                    ))
                    .build();


            List<WarehouseModel> warehouseModels = List.of(
                    new WarehouseModel(1, "mag1", new PGgeometry(new Point(38.40, 15.40)), List.of(shelfInfo1)),
                    new WarehouseModel(2, "mag2", new PGgeometry(new Point(-23.4501, -45.5330)), List.of(shelfInfo2)),
                    new WarehouseModel(3, "mag3", new PGgeometry(new Point(34.5501, 45.6330)), List.of(shelfInfo3)),
                    new WarehouseModel(4, "mag4", new PGgeometry(new Point(-22.4501, -45.6330)), List.of(shelfInfo4)));

            farmacia1 = new Farmacia("Farmacia Central", 1, new PGgeometry(new Point(38.254652, 15.504868)));
            farmacia2 = new Farmacia("Farmacia Norte", 2, new PGgeometry(new Point(38.156059, 14.745716)));
            farmacia3 = new Farmacia("Farmacia Sul", 3, new PGgeometry(new Point(38.056718, 14.830686)));
            farmacia4 = new Farmacia("Farmacia Nord", 4, new PGgeometry(new Point(38.194237, 13.211291)));
            farmacia5 = new Farmacia("Farmacia Est", 5, new PGgeometry(new Point(37.071914, 15.256249)));

            farmacia6 = new Farmacia("Farmacia Central2 ", 1, new PGgeometry(new Point(37.700417, 14.04684)));
            farmacia7 = new Farmacia("Farmacia Norte 2", 2, new PGgeometry(new Point(37.587327, 13.398657)));

            farmacia8 = new Farmacia("Farmacia Sul 2", 3, new PGgeometry(new Point(37.247026, 14.223032)));
            farmacia9 = new Farmacia("Farmacia Nord 3", 4, new PGgeometry(new Point(39.310732, 16.285324)));
            farmacia10 = new Farmacia("Farmacia Est 4 ", 5, new PGgeometry(new Point(38, 843597, 16.520237)));
            assigneds = Arrays.asList(

                    // Using Point constructor
                    new PharmacyAssigned(farmacia1, 100),// Near São Paulo
                    new PharmacyAssigned(farmacia2, 25), // Near Rio
                    new PharmacyAssigned(farmacia1, 150), // Same as farmacia
                    new PharmacyAssigned(farmacia3, 30), // Near Curitiba
                    new PharmacyAssigned(farmacia2, 20), // Near Rio
                    new PharmacyAssigned(farmacia1, 50),
                    new PharmacyAssigned(farmacia4, 20),
                    new PharmacyAssigned(farmacia4, 100),
                    new PharmacyAssigned(farmacia5, 10),
                    new PharmacyAssigned(farmacia5, 35),
                    new PharmacyAssigned(farmacia6, 50),
                    new PharmacyAssigned(farmacia7, 20),
                    new PharmacyAssigned(farmacia8, 100),
                    new PharmacyAssigned(farmacia9, 10),
                    new PharmacyAssigned(farmacia10, 35));

            choiceWarehouse = new ChoiceWarehouse(warehouseModels, assigneds);
        }

        @Test
        public void ValidTestWithSameLocation() {
                LotDimensionModel dimensionModel= new LotDimensionModel("axx", 1, 12.1, 4.1, 0, 4.0);
            Farmacia farmacia1=new Farmacia("Farmacia1",1, new PGgeometry(new Point(28.40, 15.40)));
                PharmacyDistance pharmacyDistance=new PharmacyDistance(List.of(farmacia1));
            pharmacyDistance.setAverage(new Point(38.40, 15.40));


            System.out.println(choiceWarehouse.calculate_availability(List.of(pharmacyDistance),dimensionModel,10).size());







        }
        @Test
        public void CalculateDistance() {
            LotDimensionModel dimensionModel= new LotDimensionModel("axx", 1, 12.1, 4.1, 0, 4.0);
            PharmacyDistance pharmacyDistance=new PharmacyDistance(List.of(new Farmacia("Farmacia1",1,
                    new PGgeometry(new Point(30.40, 10.40)))));
            pharmacyDistance.setAverage(new Point(20.20,10.10));
         List<WarehouseDistances> list=choiceWarehouse.calculate_availability(List.of(pharmacyDistance),dimensionModel,100);
  List<WarehouseDistances> distances=ChoiceWarehouse.sorted_warehouse(list);
            System.out.println(distances.getFirst().getDistance());




        }
    }

    @Test
    public void sorted_warehouse(){
        List<WarehouseDistances> distances=new ArrayList<>();
        WarehouseDistances distance1=new WarehouseDistances(  new WarehouseModel(1, "mag1", new PGgeometry(new Point(38.40, 15.40))),List.of(),28.10);
        WarehouseDistances distance2=new WarehouseDistances(  new WarehouseModel(1, "mag2", new PGgeometry(new Point(38.40, 15.40))),List.of(),18.10);
        distances.add(distance1);
        distances.add(distance2);
        List<WarehouseDistances> distances_actual=ChoiceWarehouse.sorted_warehouse(distances);
        Assertions.assertEquals(18.10,distances_actual.getFirst().getDistance());


    }





        @Nested
        class CalculateWarehouse {
            ChoiceWarehouse  choiceWarehouse_calculate;
            private List<PharmacyAssigned> farmacias;
        @BeforeEach
        public void setUp(){
            ShelfInfo shelfInfo1 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("A21")
                    .setLenght(102)
                    .setHeight(100)
                    .setDeep(50)
                    .setWeight(200)
                    .setNum_rip(4)
                    .setShelf_thickness(20)
                    .setShelvesCapacities(List.of(
                            new ShelvesCapacity(1, "A21", 1, 102.0, 50.0, 85.5),  // Full - max capacity
                            new ShelvesCapacity(2, "A21", 2, 80.0, 35.0, 58.8),   // 78% occupied
                            new ShelvesCapacity(3, "A21", 3, 45.0, 20.0, 22.5),   // 44% occupied
                            new ShelvesCapacity(4, "A21", 4, 0.0, 0.0, 0.0)       // Empty
                    ))
                    .build();

// Shelf 2 - Length: 150, Depth: 70 (4 levels with different occupancy)
            ShelfInfo shelfInfo2 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("A22")
                    .setLenght(150)
                    .setHeight(100)
                    .setDeep(70)
                    .setWeight(250)
                    .setNum_rip(4)
                    .setShelf_thickness(20)
                    .setShelvesCapacities(List.of(
                            new ShelvesCapacity(5, "A22", 1, 0.0, 0.0, 0.0), // Full - max capacity
                            new ShelvesCapacity(6, "A22", 2, 0.0, 5.0, 0.0),   // 80% occupied
                            new ShelvesCapacity(7, "A22", 3, 0.0, 0.0, 0.0),    // 50% occupied
                            new ShelvesCapacity(8, "A22", 4, 0.0, 0.0, 0.0)      // 20% occupied
                    ))
                    .build();

// Shelf 3 - Length: 80, Depth: 35 (4 levels with different occupancy)
            ShelfInfo shelfInfo3 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("A23")
                    .setLenght(80)
                    .setHeight(100)
                    .setDeep(35)
                    .setWeight(150)
                    .setNum_rip(4)
                    .setShelf_thickness(20)
                    .setShelvesCapacities(List.of(
                            new ShelvesCapacity(9, "A23", 1, 0.0, 0.0, 0.0),
                            new ShelvesCapacity(10, "A23", 2, 0.0, 0.0, 0.0),
                            new ShelvesCapacity(11, "A23", 3, 0.0, 0.0, 0.0),
                            new ShelvesCapacity(12, "A23", 4, 0.0, 0.0, 0.0)
                    ))
                    .build();

// Shelf 4 - Length: 200, Depth: 30 (4 levels with different occupancy)
            ShelfInfo shelfInfo4 = ShelfInfo.ShelfInfoBuilder.get_builder()
                    .setMagazzino_id(1)
                    .setShelf_code("A24")
                    .setLenght(200)
                    .setHeight(100)
                    .setDeep(30)
                    .setWeight(180)
                    .setNum_rip(4)
                    .setShelf_thickness(20)
                    .setShelvesCapacities(List.of(
                            new ShelvesCapacity(13, "A24", 1, 0.0, 0.0, 0.0),
                            new ShelvesCapacity(14, "A24", 2, 0.0, 0.0, 0.0),
                            new ShelvesCapacity(15, "A24", 3, 0.0, 0.0, 0.0),
                            new ShelvesCapacity(16, "A24", 4, 0.0, 0.0, 0.0)
                    ))
                    .build();


            List<WarehouseModel> warehouseModels = List.of(
                    new WarehouseModel(1, "mag1", new PGgeometry(new Point(78.40, 85.40)), List.of(shelfInfo1)),
                    new WarehouseModel(2, "mag2", new PGgeometry(new Point(23.4501, 45.5330)), List.of(shelfInfo2)),
                    new WarehouseModel(3, "mag3", new PGgeometry(new Point(34.5501, 45.6330)), List.of(shelfInfo3)),
                    new WarehouseModel(4, "mag4", new PGgeometry(new Point(22.4501, 45.6330)), List.of(shelfInfo4)));

            farmacia1 = new Farmacia("Farmacia Central", 1, new PGgeometry(new Point(38.254652, 15.504868)));
            farmacia2 = new Farmacia("Farmacia Norte", 2, new PGgeometry(new Point(38.156059, 14.745716)));
            farmacia3 = new Farmacia("Farmacia Sul", 3, new PGgeometry(new Point(38.056718, 14.830686)));
            farmacia4 = new Farmacia("Farmacia Nord", 4, new PGgeometry(new Point(38.194237, 13.211291)));
            farmacia5 = new Farmacia("Farmacia Est", 5, new PGgeometry(new Point(37.071914, 15.256249)));

            farmacia6 = new Farmacia("Farmacia Central2 ", 1, new PGgeometry(new Point(37.700417, 14.04684)));
            farmacia7 = new Farmacia("Farmacia Norte 2", 2, new PGgeometry(new Point(37.587327, 13.398657)));

            farmacia8 = new Farmacia("Farmacia Sul 2", 3, new PGgeometry(new Point(37.247026, 14.223032)));
            farmacia9 = new Farmacia("Farmacia Nord 3", 4, new PGgeometry(new Point(39.310732, 16.285324)));
            farmacia10 = new Farmacia("Farmacia Est 4 ", 5, new PGgeometry(new Point(38.843597, 16.520237)));
            farmacias = Arrays.asList(

                    // Using Point constructor
                    new PharmacyAssigned(farmacia1, 100),// Near São Paulo
                    new PharmacyAssigned(farmacia2, 25), // Near Rio
                    new PharmacyAssigned(farmacia1, 150), // Same as farmacia
                    new PharmacyAssigned(farmacia3, 30), // Near Curitiba
                    new PharmacyAssigned(farmacia2, 20), // Near Rio
                    new PharmacyAssigned(farmacia1, 50),
                    new PharmacyAssigned(farmacia4, 20),
                    new PharmacyAssigned(farmacia4, 100),
                    new PharmacyAssigned(farmacia5, 10),
                    new PharmacyAssigned(farmacia5, 35),
                    new PharmacyAssigned(farmacia6, 50),
                    new PharmacyAssigned(farmacia7, 20),
                    new PharmacyAssigned(farmacia8, 100),
                    new PharmacyAssigned(farmacia9, 10),
                    new PharmacyAssigned(farmacia10, 35));

            choiceWarehouse_calculate = new ChoiceWarehouse(warehouseModels, farmacias);



        }
            @Test
            public void calculate_warehouseWithZeroQTy() {

                List<WarehouseDistances> models = choiceWarehouse_calculate.calculate_warehouse(new LotDimensionModel("axx", 1, 12.1, 4.1, 0, 4.0), 12);
                Assertions.assertEquals("mag3",models.getFirst().getWarehouseModel().getNome());


            }
        }



















}





