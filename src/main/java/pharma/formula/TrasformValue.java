package pharma.formula;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TrasformValue {

    public static double calculus_trend(double input) {

        if ((input < 0) ||  (input >1 )) {
            throw new IllegalArgumentException(" value is not 0<x>=1 ");
        }

       double t =(input-SuggestionFormula.MIDDLE.getValue());
        return Math.round(t*1000.0)/1000.0;

    }

    public static double calculus_percentuages(double input) {

        if ((input < 0) ||  (input >1 )) {
            throw new IllegalArgumentException(" value is not 0<x>=1 ");
        }

        double t =(input-SuggestionFormula.MIDDLE.getValue())*SuggestionFormula.WT.getValue();
        return Math.round(t*1000.0)/1000.0;

    }

    public static double normalizeStock(double stock, double ref) {
        double raw = 0.5 + (stock - ref) / (2.0 * ref);
        double clamped = Math.max(0.0, Math.min(1.0, raw));
        return Math.round(clamped * 10) / 10.0;

    }




    public static  double round(double value){
        BigDecimal bigDecimal=new BigDecimal(value);
        bigDecimal=bigDecimal.setScale(1, RoundingMode.HALF_DOWN);
         return  bigDecimal.doubleValue();
    }




}
