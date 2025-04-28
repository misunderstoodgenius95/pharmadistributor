package pharma.config.View;

import javafx.util.StringConverter;
import pharma.Model.FieldData;

public class FarmacoLotsConvert  extends StringConverter<FieldData> {

    public FarmacoLotsConvert() {


    }

    @Override
    public String toString(FieldData object) {
        if (object != null) {

                if (object.getNome() != null) {
                    return " " + object.getNome();
                } else {

                    return " " + object.getMisure() + " " + object.getUnit_misure();
                }

        }
        return " ";
    }

    @Override
    public FieldData fromString(String string) {
        return null;
    }
}
