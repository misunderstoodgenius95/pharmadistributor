package algo;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.List;

public class LotAssigment {

    private String lot_code;
    private int farmaco_id;
    private int quantity_request;
    private List<ShelvesAssigment> shelvesAssigmentList;
    private int quantity_occupied;

    public LotAssigment( int farmaco_id, String lot_code, int quantity_request) {
        this.farmaco_id = farmaco_id;
        this.lot_code = lot_code;
        this.quantity_request = quantity_request;
        this.quantity_occupied=0;
        shelvesAssigmentList=new ArrayList<>();
    }
    public void add_assigment(String shelf_code,int level,int quantity,int shelves_id){
        shelvesAssigmentList.add(new ShelvesAssigment(shelves_id,shelf_code,quantity,level));

    }

    public List<ShelvesAssigment> getShelvesAssigmentList() {
        return shelvesAssigmentList;
    }


    public static class ShelvesAssigment{
    private int id;
    private String shelf_code;
    private int shelf_level;
    private int quantity;

        public ShelvesAssigment(int id, String shelf_code, int quantity, int shelf_level) {
            this.id = id;
            this.shelf_code = shelf_code;
            this.quantity = quantity;
            this.shelf_level = shelf_level;
        }


        public int getId() {
            return id;
        }

        public String getShelf_code() {
            return shelf_code;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getShelf_level() {
            return shelf_level;
        }
    }



}
