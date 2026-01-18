package pharma.Model;

import java.time.YearMonth;

public class Spesa {
    private YearMonth meseAnno;
    private double spesaTotale;


    public Spesa(YearMonth meseAnno) {
        this.meseAnno = meseAnno;
        this.spesaTotale = 0.0;

    }

    public void aggiungiAcquisto(Ordini ordini) {
        this.spesaTotale += ordini.getTotal_order();

    }



    // Getters e Setters
    public YearMonth getMeseAnno() { return meseAnno; }
    public double getSpesaTotale() { return spesaTotale; }




}