package pharma.Model;

import java.sql.Date;

public class Lots {
    private String lotto;
    private int farmaco_id;
    private Date elapsed_date;
    private  int availability;

    public Lots(String lotto, int farmaco_id, Date elapsed_date, int availability) {
        this.lotto = lotto;
        this.farmaco_id = farmaco_id;
        this.elapsed_date = elapsed_date;
        this.availability = availability;
    }

    public String getLotto() {
        return lotto;
    }

    public void setLotto(String lotto) {
        this.lotto = lotto;
    }

    public int getFarmaco_id() {
        return farmaco_id;
    }

    public void setFarmaco_id(int farmaco_id) {
        this.farmaco_id = farmaco_id;
    }

    public Date getElapsed_date() {
        return elapsed_date;
    }

    public void setElapsed_date(Date elapsed_date) {
        this.elapsed_date = elapsed_date;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
