package pharma.config.View;

import javafx.util.StringConverter;
import pharma.Model.FieldData;

public class InvoicexConvert  extends StringConverter<FieldData> {
    public enum Type {order_id, combo_id};
    private Type type;

    public InvoicexConvert(Type type) {
        this.type = type;
    }

    @Override
    public String toString(FieldData object) {
        if(object==null){
            return " ";
        }
        if (type.equals(Type.order_id)) {

            if (object.getCode()!= null) {
                return object.getCode();

            } else {
                return "" + object.getId();
            }
        }else if(type.equals(Type.combo_id)){
            if(object.getOriginal_order_id()==null){
                return " "+object.getNome_casa_farmaceutica();

            }
            return object.getOriginal_order_id();
        }
        return "";

}

    @Override
    public FieldData fromString(String string) {
        return null;
    }
}
