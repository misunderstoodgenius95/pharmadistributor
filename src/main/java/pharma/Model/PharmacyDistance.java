package pharma.Model;

import net.postgis.jdbc.geometry.Point;

import java.util.List;

public class PharmacyDistance {
    private List<Farmacia> farmaciaList;
    private Point average;
    public PharmacyDistance(List<Farmacia> farmaciaList, Point average) {
        this.farmaciaList = farmaciaList;
        this.average = average;

    }

    public PharmacyDistance(List<Farmacia> farmaciaList) {
        this.farmaciaList = farmaciaList;
    }

    public List<Farmacia> getFarmaciaList() {
        return farmaciaList;
    }

    public void setFarmaciaList(List<Farmacia> farmaciaList) {
        this.farmaciaList = farmaciaList;
    }

    public Point getAverage() {
        return average;
    }

    public void setAverage(Point average) {
        this.average = average;
    }
}
