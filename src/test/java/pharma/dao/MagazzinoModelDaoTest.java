package pharma.dao;

import algoWarehouse.ShelfInfo;
import algoWarehouse.ShelvesCapacity;
import net.postgis.jdbc.PGgeometry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pharma.Model.WarehouseModel;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MagazzinoModelDaoTest {
    @Mock
    private MagazzinoDao magazzinoDao;
    @Mock
    private ShelfDao shelfDao;
    @Mock
    private ShelvesDao shelvesDao;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void TestIntegrationShelfFindAll(){

        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShelfDao shelfDao=new ShelfDao(Database.getInstance(properties));
        ShelvesDao shelvesDao=new ShelvesDao(Database.getInstance(properties));
       MagazzinoDao magazzinoDao=new MagazzinoDao(Database.getInstance(properties));
        MagazzinoModelDao magazzinoModelDao =new MagazzinoModelDao(shelfDao,shelvesDao,magazzinoDao);

    }  @Test
    public void TestMagazzinoModelDao() throws SQLException {


        when(shelfDao.findAll()).thenReturn(List.of(ShelfInfo.ShelfInfoBuilder.get_builder().setLenght(100).setHeight(50).setShelf_code("ax12").setNum_rip(2).setMagazzino_id(1).build(),
                        ShelfInfo.ShelfInfoBuilder.get_builder().setLenght(100).setHeight(50).setShelf_code("ax13").setNum_rip(3).setMagazzino_id(1).build()));

        when(shelvesDao.findByShelvesByShelf(any())).thenReturn(List.of(
                new ShelvesCapacity(1,"ax12",1,0,0,0),
                new ShelvesCapacity(2,"ax12",2,0,0,0)));
        when(magazzinoDao.findAll()).thenReturn(List.of(new WarehouseModel(1,"","","","")));







        MagazzinoModelDao magazzinoModelDao =new MagazzinoModelDao(shelfDao,shelvesDao,magazzinoDao);
        List<WarehouseModel> models=magazzinoModelDao.getFullWarehouseModel();
        //Assertions.assertEquals("ax12",models.getFirst().getShelfInfos().getFirst().getShelf_code());
        Assertions.assertEquals(1,models.getFirst().getShelfInfos().getFirst().getShelvesCapacities().getFirst().getOccupied_deep());
    }
    @Test
    public void TestMagazzinoModelDaoIntegration() throws SQLException {

        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shelfDao=new ShelfDao(Database.getInstance(properties));
        shelvesDao=new ShelvesDao(Database.getInstance(properties));
        magazzinoDao=new MagazzinoDao(Database.getInstance(properties));








        MagazzinoModelDao magazzinoModelDao =new MagazzinoModelDao(shelfDao,shelvesDao,magazzinoDao);
        List<WarehouseModel> models=magazzinoModelDao.getFullWarehouseModel();
        System.out.println(models.getFirst().getShelfInfos().getFirst().getShelf_code());

    }



}