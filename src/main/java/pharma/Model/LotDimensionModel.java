package pharma.Model;

public class LotDimensionModel {
    private String lot_id;
    private  int  farmaco_id;
    private double length;
    private  double height;
    private   double weight;
    private  double deep;

    public LotDimensionModel() {

    }

    public LotDimensionModel(String lot_id, int farmaco_id, double length, double deep, double weight, double height) {
        this.lot_id = lot_id;
        this.farmaco_id = farmaco_id;
        this.length = length;
        this.deep = deep;
        this.weight = weight;
        this.height = height;
    }

    public double getDeep() {
        return deep;
    }

    public String getLot_id() {
        return lot_id;
    }

    public void setLot_id(String lot_id) {
        this.lot_id = lot_id;
    }

    public int getFarmaco_id() {
        return farmaco_id;
    }

    public void setFarmaco_id(int farmaco_id) {
        this.farmaco_id = farmaco_id;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setDeep(double deep) {
        this.deep = deep;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
