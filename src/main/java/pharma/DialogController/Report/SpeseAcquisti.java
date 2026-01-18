package pharma.DialogController.Report;

import com.dlsc.gemsfx.YearMonthPicker;
import javafx.scene.control.Button;
import pharma.Model.FieldData;
import pharma.Model.Ordini;
import pharma.Model.Spesa;
import pharma.Service.AndamentoMensile;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.javafxlib.graphs.Histogram;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


public class SpeseAcquisti extends CustomDialog<FieldData> {
    private AndamentoMensile andamento;
    private Button btnRange;
    private  YearMonthPicker starting_range;
    private   YearMonthPicker end_range;
    private Histogram histogram;
    public SpeseAcquisti( List<Ordini> list) {
        super("Visualizza Andamento");
        getDialogPane().setPrefHeight(600);
        getDialogPane().setPrefWidth(700);
        andamento=new AndamentoMensile(list);
        starting_range=this.add_month_picker();
        end_range=this.add_month_picker();
        btnRange=addButton("Mostra Range Valori");
        histogram=addHistogram("Mese","Spesa","Andamento Spese");
        histogram.addSeries();
        listenerBtn();
    }
    public void listenerBtn(){
        btnRange.setOnAction(event -> {
            Map<YearMonth, Spesa> raggruppa=andamento.raggruppaTRaMesi(starting_range.getValue(),end_range.getValue());

            raggruppa.forEach((key, value1) -> {
                System.out.println(value1.getSpesaTotale()+" " +  value1.getMeseAnno());
                histogram.addData(key.format(DateTimeFormatter.ofPattern("yyyy-MM")), value1.getSpesaTotale());

            });




        });


    }












}
