package pharma.javafxlib.graphs;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Histogram {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart<String,Number> barChart;
    private XYChart.Series<String,Number> series;
    public Histogram(String labelX,String labelY,String titleHistogram) {
        xAxis=new CategoryAxis();
        xAxis.setLabel(labelX);
        yAxis=new NumberAxis();
        yAxis.setLabel(labelY);
        barChart=new BarChart<>(xAxis,yAxis);
        barChart.setTitle(titleHistogram);
        series=new XYChart.Series<>();
    }


    public void addData(String dataX,Number dataY){
        series.getData().add(new XYChart.Data<>(dataX,dataY));




    }
    public void addSeries(){
        barChart.getData().add(series);

    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }
}
