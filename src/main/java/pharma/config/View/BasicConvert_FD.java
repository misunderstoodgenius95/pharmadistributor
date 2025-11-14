package pharma.config.View;

import javafx.util.StringConverter;
import pharma.Model.FieldData;

public class BasicConvert_FD extends StringConverter<FieldData> {
    @Override
    public String toString(FieldData object) {
        if(object!=null){
            return " "+object.getNome();

        }
        return "";
    }

    @Override
    public FieldData fromString(String string) {
        return null;
    }
}
