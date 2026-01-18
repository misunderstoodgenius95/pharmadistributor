package pharma.Model;

import java.sql.Date;

public class SellerOrders {
    private  int farmaco_id;
    private int quantity;
    private Date date_order;

    public SellerOrders(int farmaco_id, int quantity, Date date_order) {
        this.farmaco_id = farmaco_id;
        this.quantity = quantity;
        this.date_order = date_order;
    }

    public int getFarmaco_id() {
        return farmaco_id;
    }

    public void setFarmaco_id(int farmaco_id) {
        this.farmaco_id = farmaco_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate_order() {
        return date_order;
    }

    public void setDate_order(Date date_order) {
        this.date_order = date_order;
    }
}
