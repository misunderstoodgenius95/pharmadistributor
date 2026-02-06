package pharma.config.View;

import javafx.util.StringConverter;
import pharma.Service.Report.OperazioniModel;

public class OperazioniConvert  extends StringConverter<OperazioniModel> {
    @Override
    public String toString(OperazioniModel object) {
     if(object!=null){
         return  ""+object.getNome_operazione();



     }
     return  "";
    }

    @Override
    public OperazioniModel fromString(String string) {
        return null;
    }
}
