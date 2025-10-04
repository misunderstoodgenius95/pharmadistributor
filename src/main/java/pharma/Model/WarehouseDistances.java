package pharma.Model;

import java.util.List;

public class WarehouseDistances {

    private WarehouseModel warehouseModel;
    private List<Farmacia> farmacia;
    private double distance;

    public WarehouseDistances(WarehouseModel warehouseModel, List<Farmacia> farmacia, double distance) {
        this.warehouseModel = warehouseModel;
        this.farmacia = farmacia;
        this.distance = distance;
    }

    public WarehouseModel getWarehouseModel() {
        return warehouseModel;
    }

    public void setWarehouseModel(WarehouseModel warehouseModel) {
        this.warehouseModel = warehouseModel;
    }

    public List<Farmacia> getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(List<Farmacia> farmacia) {
        this.farmacia = farmacia;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


}
