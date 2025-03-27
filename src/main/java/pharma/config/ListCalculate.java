package pharma.config;

import javafx.collections.ObservableList;
import pharma.Model.FieldData;

public class ListCalculate {

public static double amount_subtotal(ObservableList<FieldData> observableList){
    return observableList.stream().mapToDouble(fieldata->fieldata.getPrice()*fieldata.getQuantity()).sum();
}
public static  double amount_vat(ObservableList<FieldData> observableList){
    return observableList.stream().mapToDouble(value-> (value.getPrice()  *value.getQuantity())*( (double) value.getVat_percent() /100)).sum();


}
public static  double total(double amount_vat, double subtotal){

   return amount_vat+subtotal;
}
public static  double sumbySubtotals(ObservableList<FieldData> observableList){
    return observableList.stream().mapToDouble(FieldData::getSubtotal).sum();

}
    public static  double sumbyVats(ObservableList<FieldData> observableList){
        return observableList.stream().mapToDouble(FieldData::getVat_amount).sum();

    }
    public static  double sumbyTotals(ObservableList<FieldData> observableList){
        return observableList.stream().mapToDouble(FieldData::getTotal).sum();

    }





}
