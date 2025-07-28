package algo;

import org.jetbrains.annotations.TestOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class PlacementShelf {
private static Logger logger=Logger.getLogger(PlacementShelf.class.getName());

private List<ShelfInfo> list_shelf;

    public PlacementShelf(List<ShelfInfo> list_shelf) {
        this.list_shelf = list_shelf;
    }





    /**
     * Calculates and returns the placement capacity for shelves based on the given dimensions.
     * It identifies shelves that can accommodate the specified lot dimensions and returns a mapping
     * of shelf information to relevant levels of remaining capacity.
     *
     * @param dimension the dimensions of the lot to be placed, represented by a LotDimension object.
     *                  Must not be null; otherwise, an IllegalArgumentException is thrown.
     * @return a HashMap where the key is a ShelfInfo object representing a specific shelf,
     *         and the value is a list of ShelvesRemain objects indicating the remaining capacities
     *         for that shelf based on the input dimensions.
     * @throws IllegalArgumentException if the provided LotDimension is null.
     */
    @TestOnly
    public HashMap<ShelfInfo,List<ShelvesRemain>> calculatePlacement(LotDimension dimension){
        if(dimension==null){
            throw  new IllegalArgumentException("Missing LotDimension");
        }
        HashMap<ShelfInfo,List<ShelvesRemain>> shelf_remaing=new HashMap<>();
        for(ShelfInfo shelfInfo:list_shelf){
            shelfInfo.remaining_levels(dimension).ifPresent(shelvesRemain -> {

                shelf_remaing.put(shelfInfo,shelvesRemain);
            });

        }
        return shelf_remaing;

    }


    @TestOnly
    public static List <Map.Entry<ShelfInfo, Integer>> sorted_max_shelf_with(HashMap<ShelfInfo,Integer> hashMap){
        return  hashMap.entrySet().stream().
                sorted(Map.Entry.<ShelfInfo, Integer>comparingByValue().reversed()).toList();

    }
    @TestOnly
    public static List <Map.Entry<ShelfInfo, Integer>> filter_value(List <Map.Entry<ShelfInfo, Integer>> list,int quantity){
        return list.stream().filter(value->value.getValue()>=quantity).toList();

    }



    /**
     * Calculates the maximum fit capacity for each shelf based on the available remaining space.
     * This method iterates through the input shelves and determines the total quantity
     * of items that can be stored, taking into account the remaining space for each shelf.
     *
     * @param capacity a mapping of ShelfInfo objects to a list of ShelvesRemain entries,
     *                 where each ShelvesRemain represents the remaining storage capacity for a shelf.
     * @return a HashMap where the keys are ShelfInfo objects representing specific shelves,
     *         and the values are integers indicating the total remaining capacity
     *         (in terms of number of items that can fit) for each shelf.
     */
    @TestOnly
    public  HashMap<ShelfInfo, Integer> calculate_max_fit(HashMap<ShelfInfo,List<ShelvesRemain>> capacity){
        HashMap<ShelfInfo,Integer> capacity_max=new HashMap<>();
        capacity.keySet().forEach(key->{
            List<ShelvesRemain> remain=capacity.get(key);
            int value_remain=remain.stream().mapToInt(ShelvesRemain::getQuantity).sum();
            capacity_max.put(key,value_remain);

        });
        return capacity_max;


    }



    public LotAssigment  assignmentLots(LotDimension dimension,int quantity){
        LotAssigment lotAssigment=new LotAssigment(dimension.getFarmaco_id(),dimension.getLot_id(),quantity);

        int quantity_remain=quantity;
        HashMap<ShelfInfo,List<ShelvesRemain>> capacity_single_shelves=calculatePlacement(dimension);
        logger.info("number of shelf: "+capacity_single_shelves.size());

        HashMap<ShelfInfo,Integer> capacity_max_shelf=calculate_max_fit(capacity_single_shelves);

        List<Map.Entry<ShelfInfo,Integer>> sorted_shelf_by_max= sorted_max_shelf_with(capacity_max_shelf);

        List<Map.Entry<ShelfInfo,Integer>>  sorted_filter=filter_value(sorted_shelf_by_max,quantity_remain);
        if(sorted_filter.isEmpty()){
            for (Map.Entry<ShelfInfo, Integer> map : sorted_shelf_by_max) {
                if (quantity_remain <= 0) {
                    break;
                }
                List<ShelvesRemain> remain = capacity_single_shelves.get(map.getKey());

                quantity_remain= placingIntoShelf(dimension, map.getKey(), quantity_remain, remain, lotAssigment);


            }



        }else {

            for (Map.Entry<ShelfInfo, Integer> map : sorted_filter) {
                if (quantity_remain <= 0) {
                    break;
                }
                List<ShelvesRemain> remain = capacity_single_shelves.get(map.getKey());

                quantity_remain= placingIntoShelf(dimension, map.getKey(), quantity_remain, remain, lotAssigment);


            }
        }
        return lotAssigment;



    }
    @TestOnly
    public int  placingIntoShelf(LotDimension dimension,ShelfInfo shelfInfo,int quantity,List<ShelvesRemain> remains,LotAssigment assigment){
        int remainingToPlace = quantity;

        for(ShelvesRemain remain:remains) {
           if(remainingToPlace<=0){
               break;
           }
           int level_quantity= Math.min(remain.getQuantity(),remainingToPlace);
           boolean place=calculate_fit(dimension,remain,level_quantity,shelfInfo);
           if(place){
                remainingToPlace-=level_quantity;
                logger.info("Assigment value num shelf: "+remain.getShelvesCapacity().getNum_shelf()+"Code: "+shelfInfo.getShelf_code());
                assigment.add_assigment(shelfInfo.getShelf_code(),remain.getShelvesCapacity().getNum_shelf(),level_quantity,remain.getShelvesCapacity().getCode());
            }

      }

        return remainingToPlace;











    }


    /**
     * Determines if a specified quantity of items can fit onto a shelf based on the given dimensions
     * and the remaining capacities of the shelf. Updates the shelf's occupied dimensions upon success.
     *
     * @param lotDimension an object representing the dimensions of the product to be placed, including its length and depth.
     * @param shelvesRemain an object containing information about the remaining capacity of the shelf and other details.
     * @param quantity the number of items to be placed onto the shelf.
     * @param shelf an object containing the physical dimensions of the shelf, such as length and depth.
     * @return true if the specified quantity of items can fit within the given shelf's remaining capacity, false otherwise.
     */
    @TestOnly
    public static boolean calculate_fit(LotDimension lotDimension, ShelvesRemain shelvesRemain,
                              int quantity, ShelfInfo shelf) {

    double shelfLength = shelf.getLenght();
    double shelfDepth = shelf.getDeep(); // Assume this method exists, e.g., 80 cm
    double productLength = lotDimension.getLength();
    double productDeep = lotDimension.getDeep();
    ShelvesCapacity shelvesCapacity=shelvesRemain.getShelvesCapacity();

    // Calculate products per row
    int productsPerRow = (int) (shelfLength / productLength);
    if (productsPerRow <= 0) return false;

    // Calculate rows needed
    int rowsNeeded = (int) Math.ceil( (double) quantity / productsPerRow);
// Calculus deep necessary
    double actualDeepUsed = rowsNeeded * productDeep;

    // Check if there's enough depth remaining
    double currentOccupiedDeep = shelvesCapacity.getOccupied_deep();
    double remainingDepth = shelfDepth - currentOccupiedDeep;
    if (actualDeepUsed > remainingDepth) {
        return false;
    }

    // Calculate actual length used by new products
    double actualLengthUsed;
    if (rowsNeeded == 1) {
        actualLengthUsed = quantity * productLength;
    } else {
        int productsInLastRow = quantity % productsPerRow;
        if (productsInLastRow == 0) productsInLastRow = productsPerRow;
        actualLengthUsed = Math.max(
            productsPerRow * productLength,
            productsInLastRow * productLength
        );
    }


    // Update occupied length: max of current and new length
    double currentOccupiedLength = shelvesCapacity.getOccupied_length(); // Should be 100 cm
    double newOccupiedLength = Math.max(currentOccupiedLength, actualLengthUsed);
    logger.info("occupated_lenght: "+newOccupiedLength);

    // Update occupied depth: add new depth to current
    double newOccupiedDeep = currentOccupiedDeep + actualDeepUsed;
   logger.info("occupated_deep: "+newOccupiedDeep);

    // Apply updates
    shelvesCapacity.setOccupied_length(newOccupiedLength);
    shelvesCapacity.setOccupied_deep(newOccupiedDeep);

    return true;
}







@TestOnly
    public static Optional<ShelfInfo> extract_max_shelf(HashMap<ShelfInfo,Integer> capacity_max, int quantity){
        AtomicInteger max= new AtomicInteger(0);
        AtomicReference<ShelfInfo> shelfInfo= new AtomicReference<>();
        capacity_max.forEach((key,value)->{
            if(value>max.get() && value>=quantity){
                shelfInfo.set(key);
            }


        });
        return Optional.ofNullable(shelfInfo.get());

    }
    private ShelfInfo random_info(List<ShelfInfo> shelfInfos,int quantity){




return  null;

    }



}




