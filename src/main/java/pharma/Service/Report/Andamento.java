package pharma.Service.Report;

import pharma.Model.Acquisto;
import pharma.Model.SpesaMensile;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Andamento {

    private List<Acquisto> acquistoList;
    private Map<YearMonth,SpesaMensile> spesaMensileMap;

    public Andamento(List<Acquisto> acquistoList) {
        spesaMensileMap=new TreeMap<>();
        this.acquistoList=acquistoList;
    }

    /**
     * Raggruppo per mese ogni spesa mensile
     * @return
     */
    public Map<YearMonth,SpesaMensile> raggruppaPerMese(){
        for (Acquisto acquisto : acquistoList) {
            YearMonth yearMonth=acquisto.getMeseAnno();
            if(spesaMensileMap.containsKey(yearMonth)){
                SpesaMensile spesaMensile=spesaMensileMap.get(yearMonth);
                spesaMensile.aggiungiAcquisto(acquisto);
            }else{
                spesaMensileMap.put(yearMonth,new SpesaMensile(yearMonth));
            }
        }

        return  spesaMensileMap;
    }






    public Optional<Double> calcolo_trend(YearMonth last, YearMonth first) {
        // Popola la mappa solo se necessario
        if (spesaMensileMap.isEmpty()) {
            raggruppaPerMese();
        }

        if (!spesaMensileMap.containsKey(last) || !spesaMensileMap.containsKey(first)) {
            return Optional.empty();
        }
        SpesaMensile spesa_last = spesaMensileMap.get(last);
        SpesaMensile spesa_first = spesaMensileMap.get(first);
        int numeroMesi = (int) ChronoUnit.MONTHS.between(first, last);
        if (numeroMesi == 0) {
            return Optional.of(0.0);
        }
        double variazione_periodi = spesa_last.getSpesaTotale() - spesa_first.getSpesaTotale();
        return Optional.of(variazione_periodi / numeroMesi);
    }




    public TrendType calculate_legenda_trend(double trend){
        if(trend>10){
            return TrendType.CRESCENTE;
        } else if (trend<-10) {
            return TrendType.DECRESCENTE;
        }else{
            return TrendType.STABILE;
        }


    }






}
