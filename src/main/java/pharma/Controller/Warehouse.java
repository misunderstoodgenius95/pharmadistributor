package pharma.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import org.jetbrains.annotations.TestOnly;
import pharma.Handler.LottoStorageHandler;
import pharma.Handler.MagazzinoHandler;
import pharma.Handler.ShelfHandler;
import pharma.Handler.Table.MagazinoTablebase;
import pharma.Handler.Table.ShelfTableBased;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.dao.MagazzinoDao;
import pharma.dao.ShelfDao;
import pharma.dao.ShelvesDao;

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
    private MagazinoTablebase magazinoTablebase;
    private ShelfTableBased shelfTableBased;
    private ShelvesDao shelvesDao;
    private LottoStorageHandler storageHandler;
    public Warehouse() {
        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shelfDao=new ShelfDao(Database.getInstance(properties));
        magazzinoDao=new MagazzinoDao(Database.getInstance(properties));
        magazzinoHandler=new MagazzinoHandler("Inserisci Magazzino", List.of(magazzinoDao), FXCollections.observableList(List.of()));
        shelfHandler=new ShelfHandler("Inserisci Scaffali",List.of(magazzinoDao,shelfDao,shelvesDao),FXCollections.observableArrayList());
        magazinoTablebase=new MagazinoTablebase("Visualizza Magazzini",magazzinoDao);
        shelfTableBased=new ShelfTableBased("Visualizza Scaffali",magazzinoDao,shelfDao,shelvesDao);
    }
    @TestOnly
    public Warehouse(MagazzinoDao magazzinoDao, ShelfDao shelfDao,ShelvesDao shelvesDao) {
        this.magazzinoDao = magazzinoDao;
        this.shelfDao = shelfDao;
        this.shelvesDao=shelvesDao;
        magazzinoHandler=new MagazzinoHandler("Inserisci Magazzino", List.of(magazzinoDao),FXCollections.observableList(List.of()));
        shelfHandler=new ShelfHandler("Inserisci Scaffali",List.of(magazzinoDao,shelfDao,shelvesDao),FXCollections.observableArrayList());
        magazinoTablebase=new MagazinoTablebase("Visualizza Magazzini",magazzinoDao);
        shelfTableBased=new ShelfTableBased("Visualizza Scaffali",magazzinoDao,shelfDao,shelvesDao);
    }

    public void warehouse_action(ActionEvent actionEvent) {
        magazinoTablebase.show();

    }

    public void shelf_action(ActionEvent actionEvent) {
        shelfTableBased.show();

    }
     public void lots_action(ActionEvent event){


     }

    public void sposta_lotto_action(ActionEvent actionEvent) {
    }
}
