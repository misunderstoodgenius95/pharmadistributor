package algoWarehouse;

public  class ShelvesAssigment{
    private int magazzino_id;
    private int id;
    private String shelf_code;
    private int shelves_level;
    private int quantity;
    private int lot_assigment;
    public ShelvesAssigment(int id, String shelf_code, int quantity, int shelves_level, int magazzino_id) {
        this.id = id;
        this.shelf_code = shelf_code;
        this.quantity = quantity;
        this.shelves_level = shelves_level;
        this.magazzino_id=magazzino_id;
    }

    public int getLot_assigment() {
        return lot_assigment;
    }

    public void setLot_assigment(int lot_assigment) {
        this.lot_assigment = lot_assigment;
    }

    public int getMagazzino_id() {
        return magazzino_id;
    }

    public void setMagazzino_id(int magazzino_id) {
        this.magazzino_id = magazzino_id;
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
        return shelves_level;
    }
}