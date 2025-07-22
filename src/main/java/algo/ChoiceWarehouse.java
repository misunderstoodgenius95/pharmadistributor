package algo;

import net.postgis.jdbc.geometry.Point;
import org.jetbrains.annotations.TestOnly;
import pharma.Model.*;
import pharma.formula.HarvesinFormula;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;



public class ChoiceWarehouse {
private static final int  PHARMACY_LOW_THRESHOLD=500;
private List<Warehouse> warehouses;
private final List<ChoiceAssigned> assigneds;
private final  double DISTANCE_KM=50;
    public ChoiceWarehouse(List<Warehouse> warehouses, List<ChoiceAssigned> assigneds) {
        this.warehouses = warehouses;
        this.assigneds = assigneds;
    }

    public Map<Integer, Integer> max_qty_pharmacy_for_lot(){


        return assigneds.stream().collect(Collectors.groupingBy(
           assigneds-> assigneds.getFarmacia().getId(),
                Collectors.summingInt(ChoiceAssigned::getQuantity))


        );

    }
    public void calculate_warehouse(){
        Map<Farmacia,Integer> map_by_qty=pharmacy_by_qty();
        List<Map. Entry<Farmacia, Integer>> sorted_values= sorted_by_max(map_by_qty);
        HashMap<Farmacia, List<PharmacyDistance>> distance=distance_pharmacist(sorted_values);
        ChoiceWarehouse.sorted_coordinate(distance);



    }
/*    public List<PharmacyDistance> choice_pharmacy_distance(HashMap<Farmacia,List<PharmacyDistance>> map){
        map.entrySet().forEach(value->{
            value.getValue();


        });


    }*/

    public Map<Farmacia, Integer> pharmacy_by_qty(){


        return assigneds.stream().collect(
                Collectors.groupingBy(ChoiceAssigned::getFarmacia,
                        Collectors.summingInt(ChoiceAssigned::getQuantity)));


    }

    /**
     * Sorts the entries  pharmacy in base of a given value.
     *
     * @param map a Map where the keys are Farmacia objects and the values are Integer quantities, representing some metric to sort by
     * @return a List of Map.Entry objects sorted in descending order by the Integer values in the map
     */
    public List<Map.Entry<Farmacia,Integer>> sorted_by_max(Map<Farmacia,Integer> map){
        return map.entrySet().stream().sorted(Map.Entry.<Farmacia,Integer>comparingByValue().reversed()).collect(Collectors.toList());

    }
/*  public static List<PharmacyDistance> sorted_coordinate(List<PharmacyDistance> list_distance){
        return list_distance.stream().sorted(Comparator.comparing(PharmacyDistance::getDistance)).toList();

    }*/

    /**
     * Sorts the distances of pharmacies for each Farmacia entry in the provided map in ascending order.
     *
     * @param list_distance a HashMap where the key is a Farmacia object and the value is a list of PharmacyDistance objects representing distances to nearby pharmacies
     * @return a new HashMap with the same keys but where the lists of PharmacyDistance objects are sorted in ascending order of distance
     */
    public static HashMap<Farmacia,List<PharmacyDistance>> sorted_coordinate(HashMap<Farmacia,List<PharmacyDistance>> list_distance){
        HashMap<Farmacia,List<PharmacyDistance>> map=new HashMap<>();
            for(Map.Entry<Farmacia,List<PharmacyDistance>> distance:list_distance.entrySet()){
                List<PharmacyDistance> distances=distance.getValue().stream().sorted(Comparator.comparing(PharmacyDistance::getDistance)).toList();
                map.put(distance.getKey(),distances);
            }
            return map;

    }
    /**
     *
     * Calculate distance between n pharmacist
     * @param map
     * @return
     */
@TestOnly
   public  static HashMap<Farmacia,List<PharmacyDistance>> distance_pharmacist(List<Map.Entry<Farmacia,Integer>> map) {
       HashMap<Farmacia,List<PharmacyDistance>> pharmacy_distance=new HashMap<>();
    List<Map.Entry<Farmacia, Integer>>  pharmacy_points=ChoiceWarehouse.extract_max_threshold_lot(map);
    for(Map.Entry<Farmacia,Integer> pharmacy_point:pharmacy_points){
        Farmacia farmacia_pont=pharmacy_point.getKey();
        Point point = (Point) farmacia_pont.getCoordianate().getGeometry();
        double lat_point= point.getX();
        double lng_pont = point.getY();
        map.stream().filter(set->!set.getKey().equals(farmacia_pont)).forEach(set->{
            Farmacia farmacia_neightbour=set.getKey();
            Point point_other=(Point) farmacia_neightbour.getCoordianate().getGeometry();
            double distance=HarvesinFormula.calculate_harvesinDistance(lat_point, lng_pont,point_other.getX(),point_other.getY());
            ChoiceWarehouse.pharmacy_distance(distance,farmacia_neightbour,farmacia_pont,pharmacy_distance);
        });



    }


        return pharmacy_distance;


   }
        @TestOnly
   public   static List<Map.Entry<Farmacia, Integer>> extract_max_threshold_lot(List<Map.Entry<Farmacia,Integer>> list){
     return  list.stream().filter(value->value.getValue()>=PHARMACY_LOW_THRESHOLD).toList();

   }

        @TestOnly
   public static  void pharmacy_distance(double distance,Farmacia farmacia_neightbour,Farmacia farmacia_f, Map<Farmacia,List<PharmacyDistance>> map_ph_distance){
            BigDecimal bigDecimal=new BigDecimal(distance);
            double  distance_scale=bigDecimal.setScale(2, RoundingMode.DOWN).doubleValue();
        if(map_ph_distance.containsKey(farmacia_f)){
            List<PharmacyDistance> distances=map_ph_distance.get(farmacia_f);
            distances.add(new PharmacyDistance(farmacia_neightbour,distance_scale));
        }else{
            List<PharmacyDistance> list=new ArrayList<>();
            list.add(new PharmacyDistance(farmacia_neightbour,distance_scale));
            map_ph_distance.put(farmacia_f,list);

        }

   }
   





}









