package pharma.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import org.jetbrains.annotations.TestOnly;
import pharma.Handler.LottoStorageHandler;
import pharma.Handler.MagazzinoHandler;
import pharma.Handler.ShelfHandler;
import pharma.Handler.SpostaLottoHandler;
import pharma.Handler.Table.LotStorageView;
import pharma.Handler.Table.LottoStorageTableView;
import pharma.Handler.Table.MagazinoTablebase;
import pharma.Handler.Table.ShelfTableBased;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.dao.*;

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
    private FarmaciaDao farmaciaDao;
    private SellerOrderDao sellerOrderDao;
    private SellerOrderDetails s_details;
    private LotDimensionDao lotDimensionDao;
    private LottiDao lottiDao;
    private  LotAssigmentDao assigmentDao;
    private  LotAssigmentShelvesDao assigmentShelvesDao;
    private LottoStorageTableView storageTableView;
    private LotStorageView storageView;
    private SpostaLottoHandler spostaLottoHandler;
    public Warehouse() {
        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shelfDao=new ShelfDao(Database.getInstance(properties));
        magazzinoDao=new MagazzinoDao(Database.getInstance(properties));
        shelvesDao=new ShelvesDao(Database.getInstance(properties));
        magazzinoHandler=new MagazzinoHandler("Inserisci Magazzino", List.of(magazzinoDao), FXCollections.observableList(List.of()));
        shelfHandler=new ShelfHandler("Inserisci Scaffali",List.of(magazzinoDao,shelfDao,shelvesDao),FXCollections.observableArrayList());
        magazinoTablebase=new MagazinoTablebase("Visualizza Magazzini",magazzinoDao);
        shelfTableBased=new ShelfTableBased("Visualizza Scaffali",magazzinoDao,shelfDao,shelvesDao);
        farmaciaDao=new FarmaciaDao(Database.getInstance(properties));
        sellerOrderDao=new SellerOrderDao(Database.getInstance(properties));
        s_details=new SellerOrderDetails(Database.getInstance(properties));
        lottiDao=new LottiDao(Database.getInstance(properties),"lotto");
        lotDimensionDao=new LotDimensionDao(Database.getInstance(properties));

        assigmentDao=new LotAssigmentDao(Database.getInstance(properties));
        assigmentShelvesDao=new LotAssigmentShelvesDao(Database.getInstance(properties));
        storageHandler=new LottoStorageHandler("Inserisci Lotti",List.of(farmaciaDao,lottiDao,sellerOrderDao,s_details,lotDimensionDao,magazzinoDao,
                shelfDao,shelvesDao,assigmentDao,assigmentShelvesDao));
        storageTableView=new LottoStorageTableView("Ricerca Lotto",assigmentDao,assigmentShelvesDao);
        storageView=new LotStorageView("Visualizza Risultati",assigmentShelvesDao);
        spostaLottoHandler=new SpostaLottoHandler("Sposta Lotto",assigmentDao,assigmentShelvesDao,magazzinoDao,shelfDao);
    }
    @TestOnly
    public Warehouse(MagazzinoDao magazzinoDao, ShelfDao shelfDao,ShelvesDao shelvesDao,LotAssigmentDao assigmentDao,LotAssigmentShelvesDao ass_shelvesDao) {
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
        storageHandler.execute();
     }

    public void sposta_lotto_action(ActionEvent actionEvent) {
        spostaLottoHandler.execute();
    }

    public void lot_view(ActionEvent event) {
            storageView.show();
    }
}
