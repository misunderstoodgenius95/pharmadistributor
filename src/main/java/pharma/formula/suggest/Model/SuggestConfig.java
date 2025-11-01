package pharma.formula.suggest.Model;

public class SuggestConfig {

    private int maxium_availability;
    private long maxium_expire_day;
    private int minimum_seller_order;
    public SuggestConfig(long maxium_expire_day, int maxium_availability,int minimum_seller_order) {
        this.maxium_expire_day = maxium_expire_day;
        this.maxium_availability = maxium_availability;
        this.minimum_seller_order=minimum_seller_order;

    }

    public int getMinimum_seller_order() {
        return minimum_seller_order;
    }

    public int getMaxium_availability() {
        return maxium_availability;
    }

    public void setMaxium_availability(int maxium_availability) {
        this.maxium_availability = maxium_availability;
    }

    public long getMaxium_expire_day() {
        return maxium_expire_day;
    }

    public void setMaxium_expire_day(long maxium_expire_day) {
        this.maxium_expire_day = maxium_expire_day;
    }
}
