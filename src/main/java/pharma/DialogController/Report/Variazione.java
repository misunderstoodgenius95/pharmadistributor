package pharma.DialogController.Report;

import javafx.scene.control.Alert;
import pharma.Model.Ordini;
import pharma.Model.Spesa;
import pharma.Service.AndamentoMensile;
import pharma.config.Utility;
import pharma.javafxlib.graphs.Histogram;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Variazione extends ReportHandlerBaseYearMonth {

    private AndamentoMensile andamento;
    private Histogram histogram;
    public Variazione(String content, List<Ordini> list) {
        super(content);
        andamento=new AndamentoMensile(list);
        histogram=addHistogram("Mese","Spese","Variazione");
        getLabel_description().setText("Variazione Tra Mesi");
        listener_btn();
    }
    public void listener_btn(){
        getButton().setOnAction(event -> {
            setLabelResult("");
            YearMonth starting=getYearMonthPicker().getValue();
            YearMonth finish=getYearMonthPicker2().getValue();
            andamento.estraiMesi(starting,finish).entrySet().forEach( item->
                    histogram.addData(item.getKey().format(DateTimeFormatter.ofPattern("yyyy-MM")), item.getValue().getSpesaTotale()));
            histogram.addSeries();
            Optional<Double> calcolo_variazione=andamento.calcolaVariazioneMesi(finish,starting);
            if(calcolo_variazione.isEmpty()){
                Utility.create_alert(Alert.AlertType.WARNING,"","Nessun Acquisto nel corrente mese");
            }else{
                setLabelResult("Variazione:  "+String.format("%.2f",calcolo_variazione.get()));
            }

        });
    }

}
