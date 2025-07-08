package algo;

import com.sun.javafx.geom.Dimension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.Model.FieldData;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShelfInfoTest {
    private  ShelfInfo shelfInfo;
    @BeforeEach
    public void setUp(){
        FieldData fieldData_shelf=FieldData.FieldDataBuilder.getbuilder().setId(1).setcode("a22").setLunghezza(102).setAltezza(100).
                setProfondita(40).setCapacity(60).setNum_rip(4).setSpessore(20).setCapacity(30).build();
        List<ShelvesCapacity> list=List.of(new ShelvesCapacity(1,"a22",1,0,0,0.0),
        new ShelvesCapacity(1,"a22",2,0,0,0),
        new ShelvesCapacity(1,"a22",3,0,0,0),
         new ShelvesCapacity(1,"a22",4,0,0,0));
        shelfInfo=new ShelfInfo(fieldData_shelf,list);


    }
    @Test
    public void  ValidCanFit(){
        LotDimension lotDimension=new LotDimension("axx",1,12.1,4.1,0.4,4.0);

        Assertions.assertTrue(shelfInfo.canFitProduct(lotDimension));


    }
    @Test
    public void spaceExtist(){
        LotDimension lotDimension=new LotDimension("axx",1,12.1,4.1,0.4,4.0);
        Assertions.assertEquals(4,shelfInfo.space_shelves_space_exist(lotDimension).get().size());
    }

    @Test
    void remaining_levels() {
        Optional<List<ShelvesRemain>>remains=shelfInfo.remaining_levels(new LotDimension("axx",1,12.1,4.1,0.4,4.0));
        Assertions.assertEquals(4,remains.get().size());
        int total=0;
        for(ShelvesRemain remain:remains.get()){
            total+=remain.getQuantity();

        }
        System.out.println(total);




    }





}