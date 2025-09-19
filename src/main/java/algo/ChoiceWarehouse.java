package algo;

import net.postgis.jdbc.geometry.Point;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Model.*;
import pharma.formula.HarvesinFormula;

import java.util.*;
import java.util.stream.Collectors;


public class ChoiceWarehouse {
private static final int  PHARMACY_LOW_THRESHOLD=600;
private static final int  PHARMACY_NEIGHTBOUR_THRESHOLD=300;
private static final double RADIUS_KM_WAREHOUSE_RANGE=40;
private static final double LATITUDE_DEGREE=111.0;
    private static final Logger log = LoggerFactory.getLogger(ChoiceWarehouse.class);
    private List<WarehouseModel> warehouseModels;

private final List<PharmacyAssigned> assigneds;
private final  double DISTANCE_KM=70;
    public ChoiceWarehouse(List<WarehouseModel> warehouseModels, List<PharmacyAssigned> assigneds) {
        this.warehouseModels = warehouseModels;
        this.assigneds = assigneds;

    }
    @TestOnly
    public Map<Integer, Integer> max_qty_pharmacy_for_lot(){


        return assigneds.stream().collect(Collectors.groupingBy(
           assigneds-> assigneds.getFarmacia().getId(),
                Collectors.summingInt(PharmacyAssigned::getQuantity))

        );

    }
    public Set<WarehouseModel> calculate_warehouse(LotDimensionModel dimension, int quantity){
        Map<Farmacia,Integer> map_by_qty=pharmacy_by_qty();
        List<Map. Entry<Farmacia, Integer>> sorted_values= sorted_by_max(map_by_qty);
        List<PharmacyDistance> pharmacyDistances=distance_pharmacist(sorted_values);
         return calculate_availability(pharmacyDistances,dimension,quantity);




    }

    /**
     * Calulate the availability for this loy
     * @param distanceList
     * @param dimension
     * @param quantity
     * @return
     */
        @TestOnly
    public   Set<WarehouseModel> calculate_availability(List<PharmacyDistance> distanceList, LotDimensionModel dimension, int quantity){
        Set<WarehouseModel> availability_warehouseModel =new HashSet<>();
        distanceList.forEach(pharmacy->{

            warehouseModels.forEach(warehouse->{
                Point point_warehouse=(Point) warehouse.getpGgeometry().getGeometry();
                if(in_range(pharmacy.getAverage(),point_warehouse)){

                    warehouse.getShelfInfos().forEach(shelfInfo -> {
                        Optional<List<ShelvesRemain>> remains =shelfInfo.remaining_levels(dimension);

                       if(remains.isPresent()){

                        int availability=remains.get().stream().mapToInt(ShelvesRemain::getQuantity).sum();
                        log.info("availability: "+availability);
                        if(availability>=quantity) {

                            availability_warehouseModel.add(warehouse);
                        }
                       }

                    });
                }
            });
        });
        return availability_warehouseModel;



    }
    @TestOnly
    public static boolean in_range(Point average_point,Point warehouse_point){

        double latitude_offset=RADIUS_KM_WAREHOUSE_RANGE/LATITUDE_DEGREE;
        double min_lat=average_point.getX()-latitude_offset;
        double max_lat=average_point.getX()+latitude_offset;
        double lng_offset=RADIUS_KM_WAREHOUSE_RANGE/ (LATITUDE_DEGREE *Math.cos(Math.toRadians(average_point.getX())));
        double min_lng=average_point.getY()-lng_offset;
        double max_lng=average_point.getY()+lng_offset;

        return (warehouse_point.getX()>min_lat &&   warehouse_point.getX()<max_lat) && ( warehouse_point.getY() >min_lng  &&   warehouse_point.getY()<max_lng);







    }
/*    public List<PharmacyDistance> choice_pharmacy_distance(HashMap<Farmacia,List<PharmacyDistance>> map){
        map.entrySet().forEach(value->{
            value.getValue();


        });


    }*/
    @TestOnly
    public Map<Farmacia, Integer> pharmacy_by_qty(){


        return assigneds.stream().collect(
                Collectors.groupingBy(PharmacyAssigned::getFarmacia,
                        Collectors.summingInt(PharmacyAssigned::getQuantity)));


    }

    /**
     * Sorts the entries  pharmacy in base of a given value.
     *
     * @param map a Map where the keys are Farmacia objects and the values are Integer quantities, representing some metric to sort by
     * @return a List of Map.Entry objects sorted in descending order by the Integer values in the map
     */
    @TestOnly
    public List<Map.Entry<Farmacia,Integer>> sorted_by_max(Map<Farmacia,Integer> map){
        return map.entrySet().stream().sorted(Map.Entry.<Farmacia,Integer>comparingByValue().reversed()).collect(Collectors.toList());

    }



    /**
     * Calculates the clusters of pharmacies based on geographical distance and thresholds.
     * Determines groups of pharmacies that fall within a specified distance range
     * and calculates the average geographical point for each group.
     *We have three case:
     * Pharmacy Point
     * @param entries a list of Map.Entry objects, where each entry contains a Farmacia (pharmacy) object as the key
     *                and an Integer value representing some metric associated with the pharmacy.
     *                The list is used to calculate proximity and categorize pharmacies based on distance thresholds.
     * @return a List of PharmacyDistance objects, where each object represents one group of pharmacies
     *         and their average geographical point. If no eligible clusters are found, a default average
     *         list may be calculated and returned instead. Throws IllegalArgumentException if the input list is empty.
     */


@TestOnly
   public  List<PharmacyDistance> distance_pharmacist(List<Map.Entry<Farmacia,Integer>> entries) {
    if(entries.isEmpty()){
        throw new IllegalArgumentException("Map is empty");
    }


    List<Map.Entry<Farmacia, Integer>>  pharmacy_points=ChoiceWarehouse.extract_max_threshold_lot(entries);
    if(pharmacy_points.isEmpty()){
        List<Farmacia> list_limit= ChoiceWarehouse.limit_entries(entries);
       return  List.of(average_entries(list_limit));
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
                if(Math.ceil(distance)<=DISTANCE_KM  &&  set.getValue()>=PHARMACY_NEIGHTBOUR_THRESHOLD){
                    Pharmacylist.add(farmacia_neightbour);
                }
            });
        }
        if( pharmacyDistanceList.isEmpty()){
              return  List.of(average_entries(limit_entries(pharmacy_points)));

        }else{
            pharmacyDistanceList.forEach(list-> list.setAverage( average_point(list.getFarmaciaList())));
            return pharmacyDistanceList;

        }
    }










   }






    /**
     * Limits the number of entries in a list to a maximum of 20 and retrieves the Farmacia keys.
     *
     * @param list a list of Map.Entry objects where each entry contains a Farmacia object as the key
     *             and an Integer value as the associated metric.
     * @return a list of Farmacia objects limited to the first 20 entries in the input list.
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









