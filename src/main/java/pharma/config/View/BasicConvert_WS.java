package pharma.config.View;

import javafx.util.StringConverter;
import pharma.Model.WarehouseModel;

public class BasicConvert_WS  extends StringConverter<WarehouseModel> {
    @Override
    public String toString(WarehouseModel object) {
        if(object!=null){
             return " "+ object.getNome();
        }
        return " ";
    }

    @Override
    public WarehouseModel fromString(String string) {
        return null;
    }
}
