package pharma.formula.Report;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jetbrains.annotations.TestOnly;
import pharma.Model.Acquisto;

import java.util.Arrays;
import java.util.List;

public class Picco {

    private  List<Acquisto> acquistoList;

    public Picco(List<Acquisto> acquistoList) {
        this.acquistoList = acquistoList;
    }

    public static double generate_standard_devation(List<Acquisto> list){
        double[] input=generate_array_input(list);
        DescriptiveStatistics statistics=new DescriptiveStatistics(input);
         return statistics.getStandardDeviation();
    }

    @TestOnly
    public  static double[] generate_array_input(List<Acquisto> list){
        return list.stream().mapToDouble(Acquisto::getQuantity).toArray();

    }
   public static double get_average(List<Acquisto> list){
        return Arrays.stream(generate_array_input(list)).summaryStatistics().getAverage();
   }

    public static     double  calculate_soglia_picco( double avg,double std_devation,double k){
        return  avg+k*std_devation;
    }


    public List<Acquisto> extract_farmaco_byId(int id){

        return  acquistoList.stream().filter(item->item.getFarmaco_id()==id).toList();
    }

    public List<Acquisto> calculate_analisi_picco(int id){
        List<Acquisto> acquistoList=extract_farmaco_byId(id);
     double standard_std=generate_standard_devation(acquistoList);
     double avg=get_average(acquistoList);
     double soglia=calculate_soglia_picco(avg,standard_std,1.5);
         return acquistoList.stream().filter(item->item.getQuantity()>soglia).toList();


    }






}
