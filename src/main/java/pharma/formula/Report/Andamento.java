package pharma.formula.Report;

import pharma.Model.Acquisto;
import pharma.Model.SpesaMensile;

import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Andamento {
    private Picco picco;
    private List<Acquisto> acquistoList;
    private Map<YearMonth,SpesaMensile> spesaMensileMap;

    public Andamento(List<Acquisto> acquistoList) {
        spesaMensileMap=new TreeMap<>();
        this.acquistoList=acquistoList;
    }

    public Map<YearMonth,SpesaMensile> raggruppaPerMese(){
        for (Acquisto acquisto : acquistoList) {
            YearMonth meseAnno = acquisto.getMeseAnno();
            SpesaMensile spesa = spesaMensileMap.computeIfAbsent(
                    meseAnno,
                    k -> new SpesaMensile(meseAnno));

            spesa.aggiungiAcquisto(acquisto);
            spesaMensileMap.values().forEach(SpesaMensile::calcolaPrezzoMedio);
        }
        return  spesaMensileMap;
    }
    public Optional<Double> calcolaSpesaMese(YearMonth yearMonth){
        if(yearMonth==null){
            throw new IllegalArgumentException("YearMonth it is null");
        }
        Map<YearMonth, SpesaMensile> spesaMensileMap=raggruppaPerMese();
        if(!raggruppaPerMese().containsKey(yearMonth)){
            return Optional.empty();
        }
         return Optional.of(spesaMensileMap.get(yearMonth).getSpesaTotale());
    }

    public  double calcolaVariazioneMesi(YearMonth prev,YearMonth next){
        raggruppaPerMese();
        SpesaMensile spesa_prev=spesaMensileMap.get(prev);
        SpesaMensile spesa_next=spesaMensileMap.get(next);
        if(spesa_next==null || spesa_prev==null){
            throw new IllegalArgumentException("No found month data");
        }
        return ((spesa_next.getSpesaTotale()-spesa_prev.getSpesaTotale())/spesa_prev.getSpesaTotale()*100);
    }
    public  Optional<Double> calcolo_trend(YearMonth last,YearMonth first){
        raggruppaPerMese();
        if(!spesaMensileMap.containsKey(last) || !spesaMensileMap.containsKey(first)){
             return  Optional.empty();
        }
        SpesaMensile spesa_last=spesaMensileMap.get(last);
        SpesaMensile spesa_first=spesaMensileMap.get(first);


        int numeroMesi = (int) ChronoUnit.MONTHS.between(first, last);

        if (numeroMesi == 0) {
            return Optional.of(0.0); // Stesso mese
        }
     double variazione_periodi= spesa_last.getSpesaTotale()-spesa_first.getSpesaTotale();
        return Optional.of(variazione_periodi/numeroMesi);
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
