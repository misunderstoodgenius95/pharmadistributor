package pharma.Model;

import java.util.Objects;

public class PharmacyDistance {

    private Farmacia farmacia;
    private double distance;


    public PharmacyDistance(Farmacia farmacia, double distance) {
        this.farmacia = farmacia;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    @Override
    public String toString() {
        return "PharmacyDistance{" +
                "farmacia id = " + farmacia.getId() +
                ", distance= " + distance +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PharmacyDistance that)) return false;
        return Double.compare(distance, that.distance) == 0 && Objects.equals(farmacia, that.farmacia);
    }


    @Override
    public int hashCode() {
        return Objects.hash(farmacia, distance);
    }
}
