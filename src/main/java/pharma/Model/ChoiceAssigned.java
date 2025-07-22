package pharma.Model;

import net.postgis.jdbc.geometry.Point;

public class ChoiceAssigned {

    private Farmacia farmacia;
    private int quantity;

    public ChoiceAssigned(Farmacia farmacia, int quantity) {
        this.farmacia = farmacia;
        this.quantity = quantity;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
