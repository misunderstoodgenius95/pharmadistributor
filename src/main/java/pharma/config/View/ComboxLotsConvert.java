package pharma.config.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.util.StringConverter;
import pharma.Model.FieldData;

import java.util.ArrayList;
import java.util.List;

public class ComboxLotsConvert  extends StringConverter<FieldData> {
private SimpleStringProperty simpleStringProperty;
    private final List<FieldData> fieldDataList;
    public ComboxLotsConvert(String value) {
        this.simpleStringProperty = new SimpleStringProperty(value);
        this.fieldDataList = new ArrayList<>();
    }

    @Override
    public String toString(FieldData fieldData) {
      if(fieldData!=null) {
        if(simpleStringProperty.get().equals("Lotto")) {
            return fieldData.getLotto_id();
        }else if(simpleStringProperty.get().equals("Farmaco")){
            return  fieldData.getNome();
        }

      }

        return "";
    }

    @Override
    public FieldData fromString(String string) {
        return  null;
     /*    return fieldDataList.stream().
        filter(fd->"Lotto".equals(simpleStringProperty.get()) &&fd.getLotto_id().equals(string)||
        "Farmaco".equals(simpleStringProperty.get()) && fd.getNome().equals(string)
        ).toList().getFirst();*/



    }

    public void set_property(String simpleStringProperty) {
        this.simpleStringProperty.set(simpleStringProperty);
    }
}
