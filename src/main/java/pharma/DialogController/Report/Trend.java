package pharma.DialogController.Report;

import javafx.scene.control.Alert;
import pharma.Model.Acquisto;
import pharma.config.Utility;
import pharma.Service.Report.Andamento;
import pharma.Service.Report.TrendType;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public class Trend extends ReportHandlerBaseYearMonth {
    private Andamento andamento;
    public Trend(String content, List<Acquisto> list)  {
        super(content);
        andamento=new Andamento(list);
        listener_btn();
        getLabel_description().setText("Scegli Il range di mesi");
        setLabelHeader("Trend:");
    }

    public void listener_btn(){
        getButton().setOnAction(event -> {
            setLabelResult("");
            YearMonth starting=getYearMonthPicker().getValue();
            YearMonth finish=getYearMonthPicker2().getValue();
            Optional<Double> calcolo_spesa=andamento.calcolo_trend(finish,starting);
            if(calcolo_spesa.isEmpty()){
                Utility.create_alert(Alert.AlertType.WARNING,"","Nessun Acquisto nel corrente mese");
            }else{
                 TrendType trendType=andamento.calculate_legenda_trend(calcolo_spesa.get());
                 setLabelResult("Andamento:  "+trendType.name()+" :   "+calcolo_spesa.get());

            }

        });
    }
}
