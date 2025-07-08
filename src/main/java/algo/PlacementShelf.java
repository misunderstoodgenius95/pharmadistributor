package algo;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PlacementShelf {


private List<ShelfInfo> list_shelf;

    public PlacementShelf(List<ShelfInfo> list_shelf) {
        this.list_shelf = list_shelf;
    }

    private int max_fitProduct(List<ShelvesRemain> remains){
    return remains.stream().mapToInt(ShelvesRemain::getQuantity).sum();
}



    public HashMap<ShelfInfo,List<ShelvesRemain>> calculatePlacement(LotDimension dimension){
        HashMap<ShelfInfo,List<ShelvesRemain>> shelf_remaing=new HashMap<>();
        for(ShelfInfo shelfInfo:list_shelf){
              List<ShelvesRemain> remain=shelfInfo.remaining_levels(dimension).get();

              shelf_remaing.put(shelfInfo,remain);
        }
        return shelf_remaing;

    }

    public  HashMap<ShelfInfo, Integer> calculate_max_fit(HashMap<ShelfInfo,List<ShelvesRemain>> capacity){
        HashMap<ShelfInfo,Integer> capacity_max=new HashMap<>();

        capacity.keySet().forEach(key->{
            List<ShelvesRemain> remain=capacity.get(key);
            int value_remain=remain.stream().mapToInt(ShelvesRemain::getQuantity).sum();
            capacity_max.put(key,value_remain);

        });
        return capacity_max;


    }

        //70
    public void AssignmentLots(LotDimension dimension,int quantity){
        HashMap<ShelfInfo,Integer> capacity_max_shelf;
            int remaing=quantity;
        HashMap<ShelfInfo,List<ShelvesRemain>> capacity_single_shelves=calculatePlacement(dimension);
        capacity_max_shelf=calculate_max_fit(capacity_single_shelves);

        ShelfInfo shelfInfo=extract_max_shelf(capacity_max_shelf);
        List<ShelvesRemain> shelves_of_shelf_max=capacity_single_shelves.get(shelfInfo);
        for(ShelvesRemain shelve:shelves_of_shelf_max){
        //
            if(shelve.getQuantity()>=remaing){

              calculate_fit(dimension,shelve.getShelvesCapacity(),quantity);


            }

        }
    }
    private void  calculate_fit( LotDimension lotDimension,ShelvesCapacity shelvesCapacity, int quantity){
        double total_quantity_length=lotDimension.getLength()*quantity;
        double total_quantity_deep=lotDimension.getDeep()*quantity;


        shelvesCapacity.setOccupied_length(shelvesCapacity.getOccupied_deep()+total_quantity_length);
        shelvesCapacity.setOccupied_deep(shelvesCapacity.getOccupied_deep()+total_quantity_deep);



    }
    private double calculate_total_value(double value, double quantity){
        return value*quantity;
    }

    public static  ShelfInfo extract_max_shelf(HashMap<ShelfInfo,Integer> capacity_max){
        AtomicInteger max= new AtomicInteger(0);
        AtomicReference<ShelfInfo> shelfInfo= new AtomicReference<>();
     capacity_max.forEach((key, value) -> {
         if (value > max.get()) {
             shelfInfo.set(key);
             max.set(value);
         }

     });
     return shelfInfo.get();



    }
    private ShelfInfo random_info(List<ShelfInfo> shelfInfos,int quantity){




return  null;

    }



}

