package algoWarehouse;

public class ShelvesCapacity {
    private int code;
    private String warehouse_shelf;
    private int num_shelf;
    private double occupied_length;
    private  double occupied_deep;
    private  double  current_weight;

    public ShelvesCapacity() {
    }

    public ShelvesCapacity(int code, String warehouse_shelf, int num_shelf, double occupied_length, double occupied_deep, double current_weight) {
        this.code = code;
        this.warehouse_shelf = warehouse_shelf;
        this.num_shelf = num_shelf;
        this.occupied_length = occupied_length;
        this.occupied_deep = occupied_deep;
        this.current_weight=current_weight;

    }   public ShelvesCapacity( String warehouse_shelf, int num_shelf, double occupied_length, double occupied_deep,double current_weight) {

        this.warehouse_shelf = warehouse_shelf;
        this.num_shelf = num_shelf;
        this.occupied_length = occupied_length;
        this.occupied_deep = occupied_deep;
        this.current_weight=current_weight;

    }

    public double getCurrent_weight() {
        return current_weight;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getNum_shelf() {
        return num_shelf;
    }

    public void setNum_shelf(int num_shelf) {
        this.num_shelf = num_shelf;
    }

    public String getWarehouse_shelf() {
        return warehouse_shelf;
    }

    public void setWarehouse_shelf(String warehouse_shelf) {
        this.warehouse_shelf = warehouse_shelf;
    }

    public double getOccupied_deep() {
        return occupied_deep;
    }

    public void setOccupied_deep(double occupied_deep) {
        this.occupied_deep = occupied_deep;
    }

    public double getOccupied_length() {
        return occupied_length;
    }

    public void setOccupied_length(double occupied_length) {
        this.occupied_length = occupied_length;
    }
}



