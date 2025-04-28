package pharma.config.View;

import javafx.util.StringConverter;
import pharma.Model.FieldData;

public class LottiConvert extends StringConverter<FieldData> {
    @Override
    public String toString(FieldData object) {
        if (object != null) {
            if (object.getNome().equals("Aggiungi Farmaco")) {

                return " " + object.getNome();
            } else {

                return object.getNome() + ' ' +
                        object.getNome_tipologia() + ' ' +
                        object.getNome_casa_farmaceutica() + object.getUnit_misure() + object.getQuantity();
            }

        }
        return "";

    }

    @Override
    public FieldData fromString(String string) {
        return null;
    }
}
