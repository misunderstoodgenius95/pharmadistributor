package pharma.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import pharma.Handler.MagazzinoHandler;
import pharma.Handler.ShelfHandler;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.dao.MagazzinoDao;
import pharma.dao.ShelfDao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Warehouse {
    private  MagazzinoHandler magazzinoHandler;
    private MagazzinoDao magazzinoDao;
    private ShelfHandler shelfHandler;
    private ShelfDao shelfDao;
    public Warehouse() {
        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shelfDao=new ShelfDao(Database.getInstance(properties));
        magazzinoDao=new MagazzinoDao(Database.getInstance(properties));
        magazzinoHandler=new MagazzinoHandler("Inserisci Magazzino", List.of(magazzinoDao), FXCollections.observableList());
        shelfHandler=new ShelfHandler("Inserisci Scaffali",List.of(magazzinoDao,shelfDao));
    }

    public Warehouse(MagazzinoDao magazzinoDao, ShelfDao shelfDao) {
        this.magazzinoDao = magazzinoDao;
        this.shelfDao = shelfDao;
        magazzinoHandler=new MagazzinoHandler("Inserisci Magazzino", List.of(magazzinoDao),FXCollections.observableList());
        shelfHandler=new ShelfHandler("Inserisci Scaffali",List.of(magazzinoDao,shelfDao));
    }

    public void warehouse_action(ActionEvent actionEvent) {
        magazzinoHandler.execute();

    }

    public void shelf_action(ActionEvent actionEvent) {
        shelfHandler.execute();
    }

    public void sposta_lotto_action(ActionEvent actionEvent) {
    }
}
