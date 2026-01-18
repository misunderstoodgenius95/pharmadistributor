package pharma.Model;

public class PriceSuggestConf {
    double gain;
    int medium_stock_item;
    int min_day_expire;

    public PriceSuggestConf(double gain, int medium_stock_item, int min_day_expire) {
        this.gain = gain;
        this.medium_stock_item = medium_stock_item;
        this.min_day_expire = min_day_expire;
    }

    public int getMedium_stock_item() {
        return medium_stock_item;
    }

    public int getMin_day_expire() {
        return min_day_expire;
    }

    public double getGain() {
        return gain;
    }
}
