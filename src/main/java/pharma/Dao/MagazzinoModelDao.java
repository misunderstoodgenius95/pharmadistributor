package pharma.dao;

import algoWarehouse.ShelfInfo;
import algoWarehouse.ShelvesCapacity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Model.WarehouseModel;

import java.util.ArrayList;
import java.util.List;

public class MagazzinoModelDao {
    private static final Logger log = LoggerFactory.getLogger(MagazzinoModelDao.class);
    private ShelfDao shelfDao;
    private ShelvesDao shelvesDao;
    private  MagazzinoDao magazzinoDao;
    public MagazzinoModelDao(ShelfDao shelfDao, ShelvesDao shelvesDao,MagazzinoDao magazzinoDao) {
        this.shelfDao = shelfDao;
        this.shelvesDao = shelvesDao;
        this.magazzinoDao=magazzinoDao;

    }

    public List<WarehouseModel> getFullWarehouseModel(){

        List<ShelfInfo> list_shelf_info=shelfDao.findAll();
        if(list_shelf_info.isEmpty()){
            throw new IllegalArgumentException("shelfDao it is empty");
        }
            for(ShelfInfo shelfInfo: list_shelf_info){
                 List<ShelvesCapacity> list=shelvesDao.findByShelvesByShelf(shelfInfo.getShelf_code());
                System.out.println(list.size());
                shelfInfo.setShelvesCapacities(list);
            }
         List<WarehouseModel> warehouseModels=magazzinoDao.findAll();

        for(WarehouseModel w_model:warehouseModels){
            List<ShelfInfo> list=new ArrayList<>();
            for(ShelfInfo shelfInfo:list_shelf_info){
                if(shelfInfo.getMagazzino_id()==w_model.getId()){
                    list.add(shelfInfo);
                }
            }
            w_model.setShelfInfos(list);

        }
        return  warehouseModels;







    }
}
