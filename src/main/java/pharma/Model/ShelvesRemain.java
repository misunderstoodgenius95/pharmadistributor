package pharma.Model;

public class ShelvesRemain {
   private ShelvesCapacity shelvesCapacity;
   private int quantity;

    public ShelvesRemain(ShelvesCapacity shelvesCapacity, int quantity) {
        this.shelvesCapacity = shelvesCapacity;
        this.quantity = quantity;
    }

    public ShelvesCapacity getShelvesCapacity() {
        return shelvesCapacity;
    }

    public int getQuantity() {
        return quantity;
    }
}
