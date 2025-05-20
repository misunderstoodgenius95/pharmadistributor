package pharma.formula;

public class TrasformValue {

    public static double calculus_trend(double input) {

        if ((input < 0) ||  (input >1 )) {
            throw new IllegalArgumentException(" value is not 0<x>=1 ");
        }

       double t =(input-SuggestionFormula.MIDDLE.getValue());
        return Math.round(t*1000.0)/1000.0;

    }

    protected static double normalize_percentuages(double input) {

      if(input<-100.0 || input>100.0){
          throw  new IllegalArgumentException("value is not between -100.0 to 100.0");
      }
      return (100-input)/200.0;


    }
    public  static  double  gain(double cost, double gain){
        return  cost*(1+gain/100.0);

    }

    /**
     *Aumento o decremento il costo a secondo del fattore normalizzato [0-1]
     *
     * @param cost
     * @param normalize_factor
     * @param
     * @return
     */

    public static double adjust_factor(double cost,double normalize_factor){
        if(normalize_factor<0 || normalize_factor>1){
            throw new IllegalArgumentException("Factor cannot in range between 0 to  1");

        }


       if(normalize_factor>0.5){
           double percent_factor=(normalize_factor-0.5)*100;

            return cost*(1-(percent_factor/100));


        }else if(normalize_factor<0.5){
           double percent_factor=(0.5-normalize_factor)*100;
            return cost*(1+(percent_factor/100));


        }
        return cost;
    }



    protected static double normalizeValue(double stock, double ref) {
        double raw = 0.5 + (stock - ref) / (2.0 * ref);
        double clamped = Math.max(0.0, Math.min(1.0, raw));
        return Math.round(clamped * 10) / 10.0;

    }

    public  static double cost(double cost,double percent_factor){

        return cost*(1-(percent_factor/100));
    }







}
