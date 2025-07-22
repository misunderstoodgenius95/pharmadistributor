package algo;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pharma.Controller.subpanel.Pharma;
import pharma.Model.*;
import  org.assertj.core.api.Assertions.*;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;
import static org.junit.jupiter.api.Assertions.*;

class ChoiceWarehouseTest {
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

    @BeforeEach
    public void setUp() {
        List<Warehouse> list_warehouse = List.of(
                new Warehouse(1, "mag1", new PGgeometry(new Point(-23.5501, -46.6330))),
                new Warehouse(2, "mag2", new PGgeometry(new Point(-23.4501, -45.5330))),
                new Warehouse(3, "mag3", new PGgeometry(new Point(-34.5501, -46.6330))),
                new Warehouse(4, "mag4", new PGgeometry(new Point(-22.4501, -45.6330))));

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
        List<ChoiceAssigned> assigneds = Arrays.asList(

                // Using Point constructor
                new ChoiceAssigned(farmacia1, 100),// Near São Paulo
                new ChoiceAssigned(farmacia2, 25), // Near Rio
                new ChoiceAssigned(farmacia1, 150), // Same as farmacia
                new ChoiceAssigned(farmacia3, 30), // Near Curitiba
                new ChoiceAssigned(farmacia2, 20), // Near Rio
                new ChoiceAssigned(farmacia1, 50),
                new ChoiceAssigned(farmacia4, 20),
                new ChoiceAssigned(farmacia4, 100),
                new ChoiceAssigned(farmacia5, 10),
                new ChoiceAssigned(farmacia5, 35),
                new ChoiceAssigned(farmacia6, 50),
                new ChoiceAssigned(farmacia7, 20),
                new ChoiceAssigned(farmacia8, 100),
                new ChoiceAssigned(farmacia9, 10),
                new ChoiceAssigned(farmacia10, 35));


        choiceWarehouse = new ChoiceWarehouse(list_warehouse, assigneds);


    }

/*
    @Test
    public void ValidMaxPharmacyPharmacy1(){
        Map<Integer, Integer> map=choiceWarehouse.max_qty_pharmacy_for_lot();
        map.entrySet().forEach(values->{
            System.out.println("id farmacia "+values.getKey()+" sum of lot "+values.getValue());

        });


        //Assertions.assertEquals(30,map.get(1).getSum());


    }
*/


    @Test
    void pharmacy_by_qty() {

        Map<Farmacia, Integer> map = choiceWarehouse.pharmacy_by_qty();
        Assertions.assertEquals(30, map.get(farmacia1));


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


    public static List<PharmacyDistance> list_distance() {

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


    }


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


    private static Stream<Arguments> provideTestDataDistance() {
        HashMap<Farmacia, List<PharmacyDistance>> expected = new HashMap<>();
        Farmacia farmacia1 = new Farmacia("Farmacia Central", 1,
                new PGgeometry(new Point(38.2547, 15.5049)));
        Farmacia farmacia2 = new Farmacia("Farmacia Norte", 2,
                new PGgeometry(new Point(38.1561, 14.7457)));
        Farmacia farmacia3 = new Farmacia("Farmacia Sul", 3,
                new PGgeometry(new Point(38.0567, 14.8307)));
        Farmacia farmacia4 = new Farmacia("Farmacia Nord", 4,
                new PGgeometry(new Point(38.194237, 13.2113)));


        expected.put(farmacia1, List.of(new PharmacyDistance(farmacia2, 67.23), new PharmacyDistance(farmacia3, 62.92), new PharmacyDistance(farmacia4, 200.46)));
        expected.put(farmacia3, List.of(new PharmacyDistance(farmacia1, 62.92), new PharmacyDistance(farmacia2, 13.32), new PharmacyDistance(farmacia4, 142.47)));
        return Stream.of(
                Arguments.of(
                        List.of(Map.entry(farmacia1, 1000), Map.entry(farmacia2, 200), Map.entry(farmacia3, 500), Map.entry(farmacia4, 200)),
                        expected
                )
        );
    }


    @ParameterizedTest
    @MethodSource("provideTestDataDistance")
    public void ValidTestCalculateDistance(List<Map.Entry<Farmacia, Integer>> input, HashMap<Farmacia, List<PharmacyDistance>> expected) {
        HashMap<Farmacia, List<PharmacyDistance>> actual = ChoiceWarehouse.distance_pharmacist(input);


        System.out.println(actual);
        //assertEquals(expected, actual);


    }


    @Test
    void pharmacy_add() {

        Map<Farmacia, List<PharmacyDistance>> map_ph_distance = new HashMap<>();
        ChoiceWarehouse.pharmacy_distance(60.50, farmacia2, farmacia1, map_ph_distance);
        assertThat(map_ph_distance.get(farmacia1)).contains(new PharmacyDistance(farmacia2,60.50));
    }
}
