package pharma.javafxlib.test;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

public class CheckEvent {
    public CheckEvent() {
    }

    private   MouseType last_type;

    public MouseType getLast_type() {
        return last_type;
    }

    public   void  check_mouse_event(Button button) {

        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                last_type=MouseType.PRIMARY;
                
            } else if (event.getButton() == MouseButton.SECONDARY) {
               last_type=MouseType.SECONDARY;

            }

        });

    }



}
