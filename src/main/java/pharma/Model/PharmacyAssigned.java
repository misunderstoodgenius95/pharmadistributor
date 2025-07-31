package pharma.Model;


public class PharmacyAssigned {
    /**
     * Used for represented the quantity of product that a pharmacy has purchased.
     */
    private Farmacia farmacia;
    private int quantity;

    public PharmacyAssigned(Farmacia farmacia, int quantity) {
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
