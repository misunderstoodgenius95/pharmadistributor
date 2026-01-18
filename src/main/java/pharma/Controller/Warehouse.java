package pharma.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import org.jetbrains.annotations.TestOnly;
import pharma.DialogController.*;
import pharma.DialogController.ShelfControllerBase;
import pharma.DialogController.SpostaLottoControllerBase;
import pharma.DialogController.Table.LotStorageView;
import pharma.DialogController.Table.LottoStorageTableView;
import pharma.DialogController.Table.MagazinoTablebase;
import pharma.DialogController.Table.ShelfTableBased;
import pharma.Storage.FileStorage;
import pharma.config.PathConfig;
import pharma.config.database.Database;
import pharma.dao.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Warehouse {
    private MagazzinoControllerBase magazzinoHandler;
    private MagazzinoDao magazzinoDao;
    private ShelfControllerBase shelfHandler;
    private ShelfDao shelfDao;
    private MagazinoTablebase magazinoTablebase;
    private ShelfTableBased shelfTableBased;
    private ShelvesDao shelvesDao;
    private LottoStorageControllerBase storageHandler;
    private FarmaciaDao farmaciaDao;
    private SellerOrderDao sellerOrderDao;
    private SellerOrderDetails s_details;
    private LotDimensionDao lotDimensionDao;
    private LottiDao lottiDao;
    private  LotAssigmentDao assigmentDao;
    private  LotAssigmentShelvesDao assigmentShelvesDao;
    private LottoStorageTableView storageTableView;
    private LotStorageView storageView;
    private SpostaLottoControllerBase spostaLottoHandler;
    public Warehouse() {
        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader(PathConfig.DATABASE_CONF.getValue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shelfDao=new ShelfDao(Database.getInstance(properties));
        magazzinoDao=new MagazzinoDao(Database.getInstance(properties));
        shelvesDao=new ShelvesDao(Database.getInstance(properties));
        magazzinoHandler=new MagazzinoControllerBase("Inserisci Magazzino", List.of(magazzinoDao), FXCollections.observableList(List.of()));
        shelfHandler=new ShelfControllerBase("Inserisci Scaffali",List.of(magazzinoDao,shelfDao,shelvesDao),FXCollections.observableArrayList());
        magazinoTablebase=new MagazinoTablebase("Visualizza Magazzini",magazzinoDao);
        shelfTableBased=new ShelfTableBased("Visualizza Scaffali",magazzinoDao,shelfDao,shelvesDao);
        farmaciaDao=new FarmaciaDao(Database.getInstance(properties));
        sellerOrderDao=new SellerOrderDao(Database.getInstance(properties));
        s_details=new SellerOrderDetails(Database.getInstance(properties));
        lottiDao=new LottiDao(Database.getInstance(properties),"lotto");
        lotDimensionDao=new LotDimensionDao(Database.getInstance(properties));

        assigmentDao=new LotAssigmentDao(Database.getInstance(properties));
        assigmentShelvesDao=new LotAssigmentShelvesDao(Database.getInstance(properties));
        storageHandler=new LottoStorageControllerBase("Inserisci Lotti",List.of(farmaciaDao,lottiDao,sellerOrderDao,s_details,lotDimensionDao,magazzinoDao,
                shelfDao,shelvesDao,assigmentDao,assigmentShelvesDao));
        storageTableView=new LottoStorageTableView("Ricerca Lotto",assigmentDao,assigmentShelvesDao);
        storageView=new LotStorageView("Visualizza Risultati",assigmentShelvesDao);
        spostaLottoHandler=new SpostaLottoControllerBase("Sposta Lotto",assigmentDao,assigmentShelvesDao,magazzinoDao,shelfDao);
    }
    @TestOnly
    public Warehouse(MagazzinoDao magazzinoDao, ShelfDao shelfDao,ShelvesDao shelvesDao,LotAssigmentDao assigmentDao,LotAssigmentShelvesDao ass_shelvesDao) {
        this.magazzinoDao = magazzinoDao;
        this.shelfDao = shelfDao;
        this.shelvesDao=shelvesDao;
        magazzinoHandler=new MagazzinoControllerBase("Inserisci Magazzino", List.of(magazzinoDao),FXCollections.observableList(List.of()));
        shelfHandler=new ShelfControllerBase("Inserisci Scaffali",List.of(magazzinoDao,shelfDao,shelvesDao),FXCollections.observableArrayList());
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
