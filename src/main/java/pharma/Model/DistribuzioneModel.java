package pharma.Model;

import java.sql.Date;

public class DistribuzioneModel {

    private String  pharma_name;
    private int num_order;
    private double percentuages;

    public DistribuzioneModel(String pharma_name, int num_order) {
        this.pharma_name = pharma_name;
        this.num_order = num_order;
    }

    public void setPercentuages(double percentuages) {
        this.percentuages = percentuages;
    }


    public String getPharma_name() {
        return pharma_name;
    }

    public int getNum_order() {
        return num_order;
    }

    public double getPercentuages() {
        return percentuages;
    }
}
