package pharma.javafxlib.Controls;

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
