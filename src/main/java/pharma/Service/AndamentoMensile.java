package pharma.Service;

import pharma.Model.Acquisto;
import pharma.Model.Ordini;
import pharma.Model.Spesa;
import pharma.Model.SpesaMensile;

import java.time.YearMonth;
import java.util.*;

public class AndamentoMensile {

   private List<Ordini> ordiniList;
    private Map<YearMonth, Spesa> spesaMensileMap;

    public AndamentoMensile(List<Ordini> ordiniList) {
        this.ordiniList = ordiniList;
        spesaMensileMap=new TreeMap<>();
    }

    public Map<YearMonth, Spesa> raggruppaTRaMesi(YearMonth start, YearMonth end){
        spesaMensileMap.clear();
        for (Ordini ordini: ordiniList) {
           YearMonth meseAnno=YearMonth.from(ordini.getOrder_date().toLocalDate());
           if(!meseAnno.isBefore(start) && !meseAnno.isAfter(end)){
           Spesa spesa = spesaMensileMap.computeIfAbsent(
                    meseAnno,
                    k -> new Spesa(meseAnno));
            spesa.aggiungiAcquisto(ordini);
            }
        }

        return  spesaMensileMap;
    }
    public Map<YearMonth, Spesa> estraiMesi(YearMonth start, YearMonth end){
        spesaMensileMap.clear();
        for (Ordini ordini: ordiniList) {
            YearMonth meseAnno=YearMonth.from(ordini.getOrder_date().toLocalDate());
            if(meseAnno.equals(start) || meseAnno.equals(end)){
                Spesa spesa = spesaMensileMap.computeIfAbsent(
                        meseAnno,
                        k -> new Spesa(meseAnno));
                spesa.aggiungiAcquisto(ordini);
            }
        }

        return  spesaMensileMap;
    }




    public Map<YearMonth,Spesa> raggruppaMesi(){
        for (Ordini ordini : ordiniList ) {
            YearMonth meseAnno = YearMonth.from(ordini.getOrder_date().toLocalDate());
            Spesa spesa = spesaMensileMap.computeIfAbsent(
                    meseAnno,
                    k -> new Spesa(meseAnno));
            spesa.aggiungiAcquisto(ordini);

        }
        return  spesaMensileMap;
    }






    public  Optional<Double> calcolaVariazioneMesi(YearMonth prev,YearMonth next){
        Map<YearMonth,Spesa> spesaMap=raggruppaMesi();
        Spesa spesa_prev=spesaMap.get(prev);
        Spesa spesa_next=spesaMap.get(next);
        if(spesa_next==null || spesa_prev==null){
            return Optional.empty();
        }
        return Optional.of((spesa_next.getSpesaTotale()-spesa_prev.getSpesaTotale()));
    }














}
