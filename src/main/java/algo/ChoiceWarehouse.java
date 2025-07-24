package algo;

import net.postgis.jdbc.geometry.Point;
import org.jetbrains.annotations.TestOnly;
import pharma.Model.*;
import pharma.formula.HarvesinFormula;

import java.util.*;
import java.util.stream.Collectors;


public class ChoiceWarehouse {
private static final int  PHARMACY_LOW_THRESHOLD=500;

private List<Warehouse> warehouses;
private final List<ChoiceAssigned> assigneds;
private final  double DISTANCE_KM=70;
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
     distance_pharmacist(sorted_values);
   /*     ChoiceWarehouse.sorted_coordinate(distance);*/



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
/*    public static HashMap<Farmacia,List<PharmacyDistance>> sorted_coordinate(HashMap<Farmacia,List<PharmacyDistance>> list_distance){
        HashMap<Farmacia,List<PharmacyDistance>> map=new HashMap<>();
            for(Map.Entry<Farmacia,List<PharmacyDistance>> distance:list_distance.entrySet()){
                List<PharmacyDistance> distances=distance.getValue().stream().sorted(Comparator.comparing(PharmacyDistance::getDistance)).toList();
                map.put(distance.getKey(),distances);
            }
            return map;

    }*/
    /**
     *
     * Calculate distance between n pharmacist
     * @param entries
     * @return
     */
@TestOnly
   public  List<PharmacyDistance> distance_pharmacist(List<Map.Entry<Farmacia,Integer>> entries) {
    if(entries.isEmpty()){
        throw new IllegalArgumentException("Map is empty");
    }

       HashMap<Farmacia,List<PharmacyDistance>> pharmacy_distance=new HashMap<>();
    List<Map.Entry<Farmacia, Integer>>  pharmacy_points=ChoiceWarehouse.extract_max_threshold_lot(entries);
    if(pharmacy_points.isEmpty()){
        List<Farmacia> list_divide= ChoiceWarehouse.limit_entries(entries);
       return  List.of(average_entries(list_divide));
    }else{
        List<PharmacyDistance> pharmacyDistanceList=new ArrayList<>();
        for(Map.Entry<Farmacia,Integer> pharmacy_point:pharmacy_points){
            Farmacia farmacia_pont=pharmacy_point.getKey();
            Point point = (Point) farmacia_pont.getCoordianate().getGeometry();
            double lat_point= point.getX();
            double lng_pont = point.getY();
            List<Farmacia> Pharmacylist=new ArrayList<>();
            Pharmacylist.add(farmacia_pont);
            PharmacyDistance pharmacyDistance=new PharmacyDistance(Pharmacylist);
            pharmacyDistanceList.add(pharmacyDistance);
            entries.stream().filter(set->!set.getKey().equals(farmacia_pont)).forEach(set->{
                Farmacia farmacia_neightbour=set.getKey();
                Point point_other=(Point) farmacia_neightbour.getCoordianate().getGeometry();
                double distance=HarvesinFormula.calculate_harvesinDistance(lat_point, lng_pont,point_other.getX(),point_other.getY());
                if(Math.ceil(distance)<=DISTANCE_KM){
                    Pharmacylist.add(farmacia_neightbour);
                }
            });
        }
        if( pharmacyDistanceList.isEmpty()){
            average_entries(limit_entries(pharmacy_points));

        }else{
            pharmacyDistanceList.forEach(list-> list.setAverage( average_point(list.getFarmaciaList())));
            return pharmacyDistanceList;

        }
    }







    return null;


   }



   private static void create_list_neightbour(HashMap<Farmacia,List<Farmacia>> group_pharmacy_map,Farmacia farmacia_point, Farmacia farmacia_neightbour){
    if(group_pharmacy_map.containsKey(farmacia_point)){
        List<Farmacia> distances=group_pharmacy_map.get(farmacia_point);
        distances.add(farmacia_neightbour);
    }else{
      List<Farmacia> list_neightbour=new ArrayList<>();
      list_neightbour.add(farmacia_neightbour);
      group_pharmacy_map.put(farmacia_point,list_neightbour);
    }
   }


    /**
     * If list is <4 return list, otherwise divide by middle
     * Greather than 4, if the value is odd it considerate the default value(an aproximation value).
     * Limit the faram
     * @param list
     * @return
     */
   @TestOnly
   public  static List<Farmacia> limit_entries(List<Map.Entry<Farmacia,Integer>> list){
        return list.stream().limit(20).map(Map.Entry::getKey).toList();

   }

        @TestOnly
   public   static List<Map.Entry<Farmacia, Integer>> extract_max_threshold_lot(List<Map.Entry<Farmacia,Integer>> list){
     return  list.stream().filter(value->value.getValue()>=PHARMACY_LOW_THRESHOLD).toList();

   }

    /**
     * Calculates the average geographic point from a list of pharmacy list and returns a PharmacyDistance object.
     * The average is calculated based on the coordinates of each Farmacia object in the list list.
     *
     * @param list a list of Map.Entry objects where each entry contains a Farmacia object (key) and an Integer value (value),
     *                representing some association or metric regarding the Farmacia.
     * @return a PharmacyDistance object containing the list of Farmacia objects and their computed average coordinates.
     */
   @TestOnly
   public static PharmacyDistance average_entries(List<Farmacia> list){
       if(list.isEmpty()){

           throw new IllegalArgumentException("list is null");
       }

        Point average=average_point(list);


       return new PharmacyDistance(list,average);




   }
    @TestOnly
    public static Point average_point(List<Farmacia> list){
        if(list.isEmpty()){

            throw new IllegalArgumentException("list is null");
        }

        return list.stream().
                map(entry->(Point)entry.getCoordianate().getGeometry()).
                collect(Collectors.teeing(
                        Collectors.averagingDouble(Point::getX),
                        Collectors.averagingDouble(Point::getY),
                        (avgx,avgy)-> new Point(avgx,avgy)));






    }





}









