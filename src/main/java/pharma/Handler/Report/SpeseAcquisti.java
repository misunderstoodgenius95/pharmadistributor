package pharma.Handler.Report;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import pharma.Model.Acquisto;
import pharma.config.Utility;
import pharma.formula.Report.Andamento;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;



public class SpeseAcquisti extends ReportHandlerBase{
private Andamento andamento;
private  Button button;
    public SpeseAcquisti(String content, List<Acquisto> list) {
        super(content);
        setLabelHeader("Spesa del mese:");
        andamento=new Andamento(list);
        listener_btn();
        setHiddenYerMonthPicker2(true);
    }
    public void listener_btn(){
        getButton().setOnAction(event -> {
            setLabelResult("");
            YearMonth yearMonth=getYearMonthPicker().getValue();
            Optional<Double> calcolo_spesa=andamento.calcolaSpesaMese(yearMonth);
            if(calcolo_spesa.isEmpty()){
                Utility.create_alert(Alert.AlertType.WARNING,"","Nessun Acquisto nel corrente mese");
            }else{
                setLabelResult(String.valueOf(calcolo_spesa.get()));

            }

        });
    }







}
