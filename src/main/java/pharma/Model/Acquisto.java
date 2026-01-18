package pharma.Model;

import java.sql.Date;
import java.time.YearMonth;

public class Acquisto {
    private int farmaco_id;
    private String nome_farmaco;
    private Date data_acquisto;
    private int quantity;
    private double price;

    public Acquisto(int farmaco_id, String nome_farmaco, int quantity, Date data_acquisto, double price) {
        this.farmaco_id = farmaco_id;
        this.nome_farmaco = nome_farmaco;
        this.quantity = quantity;
        this.data_acquisto = data_acquisto;
        this.price = price;
    }
    public Acquisto(int farmaco_id, int quantity, Date data_acquisto, double price) {
        this.farmaco_id = farmaco_id;
        this.quantity = quantity;
        this.data_acquisto = data_acquisto;
        this.price = price;
    }


    public int getFarmaco_id() {
        return farmaco_id;
    }

    public void setFarmaco_id(int farmaco_id) {
        this.farmaco_id = farmaco_id;
    }

    public String getNome_farmaco() {
        return nome_farmaco;
    }

    public void setNome_farmaco(String nome_farmaco) {
        this.nome_farmaco = nome_farmaco;
    }

    public Date getData_acquisto() {
        return data_acquisto;
    }

    public void setData_acquisto(Date data_acquisto) {
        this.data_acquisto = data_acquisto;
    }

    public double getPrice() {
        return price;
    }
    public YearMonth getMeseAnno(){

        return YearMonth.from(data_acquisto.toLocalDate());
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSpesaTotale() {
        return quantity * price;
    }

}
