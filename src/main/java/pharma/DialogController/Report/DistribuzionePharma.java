package pharma.DialogController.Report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import pharma.Model.DistribuzioneModel;
import pharma.Service.DistributionPercentuages;
import pharma.config.Utility;
import pharma.dao.PurchaseOrderDao;

import java.time.YearMonth;
import java.util.List;

public class DistribuzionePharma extends ReportHandlerBaseYearMonth {
    private PieChart pieChart;
    private DistributionPercentuages distributionPercentuages;
private List<DistribuzioneModel> distribuzioneModels;
    public DistribuzionePharma(String content, PurchaseOrderDao purchaseOrderDao) {
        super(content);
        getDialogPane().setPrefWidth(800);
        getDialogPane().setPrefHeight(900);

        pieChart=addPieChart();
        getLabel_description().setText("Distribuzione Percentuale Casa Farmaceutica");
        distributionPercentuages=new DistributionPercentuages(purchaseOrderDao);
        listener();
    }
    public void listener(){
       this.getButton().setOnAction(event -> {
           YearMonth starting=getYearMonthPicker().getValue();
           YearMonth finish=getYearMonthPicker2().getValue();
           addPieData(distributionPercentuages.calculatePercentuages(starting,finish));


       });




    }

    public void addPieData(List<DistribuzioneModel> distribuzioneModelList){

        List<PieChart.Data> list=distribuzioneModelList.stream().map(value->new PieChart.Data(value.getPharma_name(),value.getPercentuages())).toList();
        if(list.isEmpty()){
            Utility.create_alert(Alert.AlertType.WARNING,"","Nessun Ordine Acquistato!");
        }
        ObservableList<PieChart.Data> observableList = FXCollections.observableList(list);
        pieChart.setData(observableList);
        observableList.forEach(value->{
            value.setName(String.format("%s (%.1f%%)",value.getName(),value.getPieValue()));
        });



    }





}
