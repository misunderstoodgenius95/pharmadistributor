package algo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


import org.jetbrains.annotations.TestOnly;
import org.slf4j.LoggerFactory;
import pharma.Model.FieldData;

public class ShelfInfo
{


    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ShelfInfo.class);
    private String  shelf_code;
    private int magazzino_id;
    private double lenght;
    private double height;
    private double deep;
    private double weight;
    private int num_rip;
    private double shelf_thickness;
    private  List<ShelvesCapacity>   shelvesCapacities;
    private  Logger logger=Logger.getLogger(ShelfInfo.class.getName());

    public ShelfInfo(String shelf_code, int magazzino_id, double height, double deep, int num_rip, double shelf_thickness, List<ShelvesCapacity> shelvesCapacities, double weight, double lenght) {
        this.shelf_code = shelf_code;
        this.magazzino_id = magazzino_id;
        this.height = height;
        this.deep = deep;
        this.num_rip = num_rip;
        this.shelf_thickness = shelf_thickness;
        this.shelvesCapacities = shelvesCapacities;
        this.weight = weight;
        this.lenght = lenght;
    }

    public ShelfInfo(FieldData fd_shelf, List<ShelvesCapacity> shelvesCapacities ) {
        this.lenght =fd_shelf.getLunghezza();
        this.height =fd_shelf.getAltezza();
        this.deep =fd_shelf.getProfondita();
        this.weight =fd_shelf.getCapacity();
        this.magazzino_id=fd_shelf.getId();
        this.shelf_code=fd_shelf.getCode();
        this.num_rip=fd_shelf.getNum_rip();
        this.shelf_thickness=fd_shelf.getSpessore();
        this.shelvesCapacities=shelvesCapacities;

    }

    public String getShelf_code() {
        return shelf_code;
    }

    public void setShelf_code(String shelf_code) {
        this.shelf_code = shelf_code;
    }

    public int getMagazzino_id() {
        return magazzino_id;
    }

    public void setMagazzino_id(int magazzino_id) {
        this.magazzino_id = magazzino_id;
    }

    public int getNum_rip() {
        return num_rip;
    }

    public void setNum_rip(int num_rip) {
        this.num_rip = num_rip;
    }

    public double getLenght() {
        return lenght;
    }

    public void setLenght(double lenght) {
        this.lenght = lenght;
    }

    public double getDeep() {
        return deep;
    }

    public void setDeep(double deep) {
        this.deep = deep;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getShelf_thickness() {
        return shelf_thickness;
    }

    public void setShelf_thickness(double shelf_thickness) {
        this.shelf_thickness = shelf_thickness;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<ShelvesCapacity> getShelvesCapacities() {
        return shelvesCapacities;
    }

    public void setShelvesCapacities(List<ShelvesCapacity> shelvesCapacities) {
        this.shelvesCapacities = shelvesCapacities;
    }
    @TestOnly
    public boolean canFitProduct(LotDimension lotDimension){
        return lotDimension.getLength()<= lenght &&
                lotDimension.getHeight()<=(height /num_rip)&&
                lotDimension.getDeep()<= deep &&
                lotDimension.getWeight()<= deep;

    }
@TestOnly
 public Optional<List<ShelvesCapacity>> space_shelves_space_exist(LotDimension lotDimension){
              List<ShelvesCapacity> current_space_capacity=new ArrayList<>();
                if(!canFitProduct(lotDimension)){
                    return Optional.empty();
                }
        for(ShelvesCapacity shelvesCapacity:shelvesCapacities){
             double remain_deep=deep-shelvesCapacity.getOccupied_deep();
             double remain_length= lenght-shelvesCapacity.getOccupied_length();
            if(!(remain_deep<lotDimension.getDeep()) ||
            !(remain_length<lotDimension.getLength())){
                current_space_capacity.add(shelvesCapacity);
            }
        }
        return Optional.of(current_space_capacity);
 }
 /*
  * It calculate max fit product with length, deep and weight for each shelves.
  * It return all shelves and the number that can be max fit almost 1 product.
  */

    /**
     * Calculates and retrieves the remaining storage levels in the shelf system
     * that can accommodate the given lot dimensions. Considers available space
     * based on the shelf's dimensions, weight capacity, and current occupancies.
     *
     * @param lotDimension the dimensions of the product lot to be stored, including its
     *                     length, depth, and weight.
     * @return an {@code Optional} containing a list of {@code ShelvesRemain} objects
     *         representing the remaining spaces, or an empty {@code Optional} if the
     *         product cannot fit in any available space.
     */

 public Optional<List<ShelvesRemain>> remaining_levels(LotDimension lotDimension) {
     List<ShelvesRemain> remain = new ArrayList<>();
     if (!canFitProduct(lotDimension)) {
         return Optional.empty();
     }
     for (ShelvesCapacity shelvesCapacity : shelvesCapacities) {
         double remain_deep = deep - shelvesCapacity.getOccupied_deep();
            logger.info("deep: "+remain_deep);
         double remain_length = lenght - shelvesCapacity.getOccupied_length();
         logger.info(" length: "+remain_length);
         if ((remain_deep <= lotDimension.getDeep()) ||
                 (remain_length <= lotDimension.getLength())) {


            continue;
         }
         log.info("continue");


         int fitbylenght = (int) (remain_length / lotDimension.getLength());
        logger.info("Fit Length "+fitbylenght);

         int fitbydeepth = (int) (remain_deep / lotDimension.getDeep());
        logger.info("fit deep  "+fitbydeepth);

         int fit_space = fitbydeepth * fitbylenght;
       logger.info("space "+fit_space);


         // Capacity for level
         double weightforlevel = weight / num_rip;
         // Calculus the weight remaing
        double remaing_weight= weightforlevel- shelvesCapacity.getCurrent_weight();

        if(remaing_weight<0 || fitbylenght<0){
            continue;
        }


         // Calculus fitProduct
         int  fitbyWeight =(int)( remaing_weight / lotDimension.getWeight());
         int remaining=Math.min(fit_space,fitbyWeight);
     //    logger.info("remaining: "+remaining);
         if(remaining>0){
             remain.add(new ShelvesRemain(shelvesCapacity, remaining));
         }




     }
     if(remain.isEmpty()){
         return Optional.empty();
     }else{
        return Optional.of(order_by_increase(remain));

     }



 }
 private static List<ShelvesRemain> order_by_increase(List<ShelvesRemain> remains){

    return remains.stream().sorted(Comparator.comparing(ShelvesRemain::getQuantity).reversed()).toList();
 }





 }








