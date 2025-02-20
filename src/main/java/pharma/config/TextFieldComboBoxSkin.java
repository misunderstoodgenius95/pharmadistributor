package pharma.config;

import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;

public class TextFieldComboBoxSkin extends SkinBase<TextFieldComboBox> {


    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    protected TextFieldComboBoxSkin(TextFieldComboBox control) {
        super(control);
        getChildren().add(control.getvBox());
    }
}
