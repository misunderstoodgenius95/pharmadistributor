package pharma.Storage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.net.URL;

public class Prova {
    public static void main(String[] args) {
        System.out.println("Ciao");


    }


    public static Parent load_stage(String fxml) {
        try {
            URL fxmlUrl = Prova.class.getResource(fxml);
            if (fxmlUrl == null) {
                throw new RuntimeException("FXML file not found: " + fxml);
            }
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
            return fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace(); // Log exception details
            return null;
        }
    }
}
