package algo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pharma.Model.FieldData;

public class ShelfInfo
{   private String  shelf_code;
    private int magazzino_id;
    private double lenght;
    private double height;
    private double deep;
    private double weight;
    private int num_rip;
    private double shelf_thickness;
    private  List<ShelvesCapacity>   shelvesCapacities;

    public ShelfInfo(FieldData fd_shelf,List<ShelvesCapacity> shelvesCapacities ) {
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

    public boolean canFitProduct(LotDimension lotDimension){
        return lotDimension.getLength()<= lenght &&
                lotDimension.getHeight()<=(height /num_rip)&&
                lotDimension.getDeep()<= deep &&
                lotDimension.getWeight()<= deep;

    }

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
 public Optional<List<ShelvesRemain>> remaining_levels(LotDimension lotDimension) {
     List<ShelvesRemain> remain = new ArrayList<>();
     if (!canFitProduct(lotDimension)) {
         return Optional.empty();
     }
     for (ShelvesCapacity shelvesCapacity : shelvesCapacities) {
         double remain_deep = deep - shelvesCapacity.getOccupied_deep();
         double remain_length = lenght - shelvesCapacity.getOccupied_length();
         if ((remain_deep < lotDimension.getDeep()) ||
                 (remain_length < lotDimension.getLength())) {

            continue;
         }
         int fitbylenght = (int) (remain_length / lotDimension.getLength());
         int fitbydeepth = (int) (remain_deep / lotDimension.getDeep());

         int fit_space = fitbydeepth * fitbylenght;
         // Capacity for level
         double weightforlevel = weight / num_rip;
         // Calclus fitProduct
         int  fitbyWeight =(int)( weightforlevel / lotDimension.getWeight());
         int remaing=Math.max(0,Math.max(fit_space,fitbyWeight));
         if(remaing>0){
             remain.add(new ShelvesRemain(shelvesCapacity, remaing));
         }




     }
     return remain.isEmpty() ? Optional.empty() : Optional.of(remain);
 }



 }








