package pharma.Model;

import java.time.YearMonth;

public class SpesaMensile {
    private YearMonth meseAnno;
    private double spesaTotale;
    private int quantitaTotale;
    private int numeroOrdini;
    private double prezzoMedio;
    private double variazionePercentuale;

    public SpesaMensile(YearMonth meseAnno) {
        this.meseAnno = meseAnno;
        this.spesaTotale = 0.0;
        this.quantitaTotale = 0;
        this.numeroOrdini = 0;
    }

    public void aggiungiAcquisto(Acquisto acquisto) {
        this.spesaTotale += acquisto.getSpesaTotale();
        this.quantitaTotale += acquisto.getQuantity();
        this.numeroOrdini++;
    }

    public void calcolaPrezzoMedio() {
        if (quantitaTotale > 0) {
            this.prezzoMedio = spesaTotale / quantitaTotale;
        }
    }

    // Getters e Setters
    public YearMonth getMeseAnno() { return meseAnno; }
    public double getSpesaTotale() { return spesaTotale; }
    public int getQuantitaTotale() { return quantitaTotale; }
    public int getNumeroOrdini() { return numeroOrdini; }
    public double getPrezzoMedio() { return prezzoMedio; }
    public double getVariazionePercentuale() { return variazionePercentuale; }
    public void setVariazionePercentuale(double variazione) {
        this.variazionePercentuale = variazione;
    }

    @Override
    public String toString() {
        return String.format("%s: €%.2f (Qty: %d, Ordini: %d, Medio: €%.2f)%s",
                meseAnno, spesaTotale, quantitaTotale, numeroOrdini, prezzoMedio);
    }
}