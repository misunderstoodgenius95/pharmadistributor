package pharma.config;

import javafx.util.StringConverter;
import pharma.Model.FieldData;

import java.lang.reflect.Field;

public class ComboxLotsConvert  extends StringConverter<FieldData> {

    @Override
    public String toString(FieldData fieldData) {
      if(fieldData!=null){
          return  fieldData.getLotto_id()+ " "+fieldData.getNome() + " "+fieldData.getNome_tipologia()+" "+fieldData.getUnit_misure()+" "+fieldData.getPrice()+
                  fieldData.getNome_casa_farmaceutica()+" "+ fieldData.getQuantity();


      }
        return "";
    }

    @Override
    public FieldData fromString(String string) {
        return null;
       /// return FieldData.FieldDataBuilder.getbuilder().setLotto_id("aaaa").build();
       /* String[] splitted=string.split(" ");
        System.out.println(splitted.length);

        return null;

        */
    }
}
