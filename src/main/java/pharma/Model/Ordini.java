package pharma.Model;

import java.sql.Date;

public class Ordini {

    private int ordine_id;
    private Date order_date;
    private double total_order;

    public Ordini(int ordine_id, double total_order, Date order_date) {
        this.ordine_id = ordine_id;
        this.total_order = total_order;
        this.order_date = order_date;
    }

    public int getOrdine_id() {
        return ordine_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public double getTotal_order() {
        return total_order;
    }
}
