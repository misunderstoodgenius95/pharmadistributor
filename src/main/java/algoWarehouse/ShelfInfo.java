package algoWarehouse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


import org.jetbrains.annotations.TestOnly;
import org.slf4j.LoggerFactory;
import pharma.Model.LotDimensionModel;

public class ShelfInfo
{


    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ShelfInfo.class);
    private String  shelf_code;
    private int magazzino_id;
    private double lenght;
    private double height;
    private double deep;
    private int  weight;
    private int num_rip;
    private double shelf_thickness;
    private  List<ShelvesCapacity>   shelvesCapacities;
    private  Logger logger=Logger.getLogger(ShelfInfo.class.getName());
    private String nome_magazzino;
     private  ShelfInfo(){


    }
   private  ShelfInfo(ShelfInfoBuilder builder) {
       this.shelf_code=builder.shelf_code;
       this.magazzino_id=builder.magazzino_id;
       this.height=builder.height;
       this.deep= builder.deep;
       this.num_rip=builder.num_rip;
       this.shelf_thickness=builder.shelf_thickness;
       this.shelvesCapacities=builder.shelvesCapacities;
       this.weight=builder.weight;
       this.lenght=builder.lenght;
       this.nome_magazzino=builder.nome_magazzino;
    }

    public void setNome_magazzino(String nome_magazzino) {
        this.nome_magazzino = nome_magazzino;
    }

    public void setShelvesCapacities(List<ShelvesCapacity> shelvesCapacities) {
        this.shelvesCapacities = shelvesCapacities;
    }

    public String getNome_magazzino() {
        return nome_magazzino;
    }

    public String getShelf_code() {
        return shelf_code;
    }

    public int getMagazzino_id() {
        return magazzino_id;
    }

    public double getLenght() {
        return lenght;
    }

    public double getHeight() {
        return height;
    }

    public double getDeep() {
        return deep;
    }

    public int getWeight() {
        return weight;
    }

    public int getNum_rip() {
        return num_rip;
    }

    public List<ShelvesCapacity> getShelvesCapacities() {
        return shelvesCapacities;
    }

    public double getShelf_thickness() {
        return shelf_thickness;
    }



    @TestOnly
    public boolean canFitProduct(LotDimensionModel lotDimensionModel){
        return lotDimensionModel.getLength()<= lenght &&
                lotDimensionModel.getHeight()<=(height /num_rip)&&
                lotDimensionModel.getDeep()<= deep &&
                lotDimensionModel.getWeight()<= deep;
    }
@TestOnly
 public Optional<List<ShelvesCapacity>> space_shelves_space_exist(LotDimensionModel lotDimensionModel){
              List<ShelvesCapacity> current_space_capacity=new ArrayList<>();
                if(!canFitProduct(lotDimensionModel)){
                    return Optional.empty();
                }
        for(ShelvesCapacity shelvesCapacity:shelvesCapacities){
             double remain_deep=deep-shelvesCapacity.getOccupied_deep();
             double remain_length= lenght-shelvesCapacity.getOccupied_length();
            if(!(remain_deep< lotDimensionModel.getDeep()) ||
            !(remain_length< lotDimensionModel.getLength())){
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
     * @param lotDimensionModel the dimensions of the product lot to be stored, including its
     *                     length, depth, and weight.
     * @return an {@code Optional} containing a list of {@code ShelvesRemain} objects
     *         representing the remaining spaces, or an empty {@code Optional} if the
     *         product cannot fit in any available space.
     */

 public Optional<List<ShelvesRemain>> remaining_levels(LotDimensionModel lotDimensionModel) {
     List<ShelvesRemain> remain = new ArrayList<>();
     if (!canFitProduct(lotDimensionModel)) {
         return Optional.empty();
     }
     for (ShelvesCapacity shelvesCapacity : shelvesCapacities) {
         double remain_deep = deep - shelvesCapacity.getOccupied_deep();
            logger.info("deep: "+remain_deep);
         double remain_length = lenght - shelvesCapacity.getOccupied_length();
         logger.info(" length: "+remain_length);
         if ((remain_deep <= lotDimensionModel.getDeep()) ||
                 (remain_length <= lotDimensionModel.getLength())) {


         continue;
         }
         log.info("continue");


         int fitbylenght = (int) (remain_length / lotDimensionModel.getLength());
        logger.info("Fit Length "+fitbylenght);

         int fitbydeepth = (int) (remain_deep / lotDimensionModel.getDeep());
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
        log.info("Continue");


         // Calculus fitProduct
         int  fitbyWeight =(int)( remaing_weight / lotDimensionModel.getWeight());
         int remaining=Math.min(fit_space,fitbyWeight);
         logger.info("remaining:  "+remain);
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
public static class ShelfInfoBuilder {
    private String  shelf_code;
    private int magazzino_id;
    private double lenght;
    private double height;
    private double deep;
    private int  weight;
    private int num_rip;
    private double shelf_thickness;
    private String nome_magazzino;
    private  List<ShelvesCapacity>   shelvesCapacities;

    private ShelfInfoBuilder() {
    }
    public static ShelfInfoBuilder get_builder(){

        return new ShelfInfoBuilder();
    }

    public ShelfInfoBuilder setNome_magazzino(String nome_magazzino) {
        this.nome_magazzino = nome_magazzino;
        return this;
    }

    public ShelfInfoBuilder setShelf_code(String shelf_code) {
        this.shelf_code = shelf_code;
        return this;
    }

    public ShelfInfoBuilder setLenght(double lenght) {
        this.lenght = lenght;
        return this;
    }

    public ShelfInfoBuilder setMagazzino_id(int magazzino_id) {
        this.magazzino_id = magazzino_id;
        return this;
    }

    public ShelfInfoBuilder setHeight(double height) {
        this.height = height;
        return this;
    }

    public ShelfInfoBuilder setWeight(int  weight) {
        this.weight = weight;
        return this;
    }

    public ShelfInfoBuilder setDeep(double deep) {
        this.deep = deep;
        return this;
    }

    public ShelfInfoBuilder setNum_rip(int num_rip) {
        this.num_rip = num_rip;
        return this;
    }

    public ShelfInfoBuilder setShelvesCapacities(List<ShelvesCapacity> shelvesCapacities) {
        this.shelvesCapacities = shelvesCapacities;
        return this;
    }

    public ShelfInfoBuilder setShelf_thickness(double shelf_thickness) {
        this.shelf_thickness = shelf_thickness;
        return this;
    }

    public ShelfInfo build(){
            return new ShelfInfo(this);

    }
}




 }








