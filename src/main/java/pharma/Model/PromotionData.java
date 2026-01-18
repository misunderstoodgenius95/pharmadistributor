package pharma.Model;

import java.sql.Date;
import java.util.List;

public class PromotionData {

    private  double[] currentPurchasePrices;
    private double[]sellerPriceHistory;
    private List<Date> current_date;
    private int[] current_stock_quantity;
    private double current_price;
    private int medium_stock_item;
    private  int min_day_expire;

    public PromotionData(double[] currentPurchasePrices, double[] sellerPriceHistory, List<Date> current_date, int[] current_stock_quantity, double current_price, int medium_stock_item, int min_day_expire) {
        this.currentPurchasePrices = currentPurchasePrices;
        this.sellerPriceHistory = sellerPriceHistory;
        this.current_date = current_date;
        this.current_stock_quantity = current_stock_quantity;
        this.current_price = current_price;
        this.medium_stock_item = medium_stock_item;
        this.min_day_expire = min_day_expire;
    }

    public double[] getCurrentPurchasePrices() {
        return currentPurchasePrices;
    }

    public double[] getSellerPriceHistory() {
        return sellerPriceHistory;
    }

    public List<Date> getCurrent_date() {
        return current_date;
    }

    public int[] getCurrent_stock_quantity() {
        return current_stock_quantity;
    }

    public double getCurrent_price() {
        return current_price;

    }

    public int getMedium_stock_item() {
        return medium_stock_item;
    }

    public int getMin_day_expire() {
        return min_day_expire;
    }
}
