package pharma.Model;

import java.util.ArrayList;
import java.util.List;

public class LotAssigment {

    private String lot_code;
    private int farmaco_id;
    private int quantity_request;
    private List<ShelvesAssigment> shelvesAssigmentList;
    private int quantity_occupied;
    private int id;
    public LotAssigment( int farmaco_id, String lot_code, int quantity_request) {
        this.farmaco_id = farmaco_id;
        this.lot_code = lot_code;
        this.quantity_request = quantity_request;
        this.quantity_occupied=0;
        shelvesAssigmentList=new ArrayList<>();
    }

    public LotAssigment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void add_assigment(String shelf_code, int level, int quantity, int shelves_id, int magazzino_id){
        shelvesAssigmentList.add(new ShelvesAssigment(shelves_id,shelf_code,quantity,level,magazzino_id));

    }

    public String getLot_code() {
        return lot_code;
    }

    public void setLot_code(String lot_code) {
        this.lot_code = lot_code;
    }

    public int getFarmaco_id() {
        return farmaco_id;
    }

    public void setFarmaco_id(int farmaco_id) {
        this.farmaco_id = farmaco_id;
    }

    public int getQuantity_request() {
        return quantity_request;
    }

    public void setQuantity_request(int quantity_request) {
        this.quantity_request = quantity_request;
    }

    public void setShelvesAssigmentList(List<ShelvesAssigment> shelvesAssigmentList) {
        this.shelvesAssigmentList = shelvesAssigmentList;
    }



    public void setQuantity_occupied(int quantity_occupied) {
        this.quantity_occupied = quantity_occupied;
    }

    public List<ShelvesAssigment> getShelvesAssigmentList() {
        return shelvesAssigmentList;
    }






}
