package pharma.javafxlib;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.stage.FileChooser;

public class FileChoseOption {
    private Button button_insert;
    private Button button_upload;
    private FileChooser chooser;

    public FileChoseOption(Button button_insert, Button button_upload, FileChooser fileChooser) {
        this.button_insert = button_insert;
        this.button_upload = button_upload;
        this.chooser=fileChooser;
    }

    public Button getButton_insert() {
        return button_insert;
    }

    public Button getButton_upload() {
        return button_upload;
    }

    public FileChooser getChooser() {
        return chooser;
    }

    public void setChooser(FileChooser chooser) {
        this.chooser = chooser;
    }
}
