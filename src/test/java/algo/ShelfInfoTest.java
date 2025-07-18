package algo;

import com.sun.javafx.geom.Dimension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pharma.Model.FieldData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShelfInfoTest {
    private  ShelfInfo shelfInfo;
    FieldData fieldData_shelf;
    @BeforeEach
    public void setUp() {






    }









    @Nested
    class InitializingTest {
        @BeforeEach
        public void setUp() {
            List<ShelvesCapacity> list = List.of(new ShelvesCapacity(1, "a22", 1, 0, 0,0),
                        new ShelvesCapacity(1, "a22", 2, 0, 0,0),
                        new ShelvesCapacity(1, "a22", 3, 0, 0,0),
                        new ShelvesCapacity(1, "a22", 4, 0, 0,0));
                shelfInfo=new ShelfInfo(fieldData_shelf, list);
            }


                @Test
                public void ValidCanFit() {
                    LotDimension lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0);

                    assertTrue(shelfInfo.canFitProduct(lotDimension));


                }

                @Test
                public void spaceExtist() {
                    LotDimension lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0.4, 4.0);
                    assertEquals(4, shelfInfo.space_shelves_space_exist(lotDimension).get().size());
                }

                @Test
                void remaining_levels() {
                    Optional<List<ShelvesRemain>> remains = shelfInfo.remaining_levels(new LotDimension("axx", 1, 12, 4, 0.4, 4.0));
                    assertEquals(4, remains.get().size());

                }
            }

            @Nested
            class RemainTwoSHelves{
            List<ShelvesRemain> remains_list;
            @BeforeEach
            public void  ValidRemaingLevels() {




                List<ShelvesCapacity> shelvesCapacities = new ArrayList<>();
                shelvesCapacities.add(new ShelvesCapacity(5, "a25", 1, 200.0, 40.0, 0.0));
                shelvesCapacities.add(new ShelvesCapacity(5, "a25", 2, 180.0, 30.0, 0.0));
                shelvesCapacities.add(new ShelvesCapacity(5, "a25", 3, 120, 20.0, 0.0));
                shelvesCapacities.add(new ShelvesCapacity(5, "a25", 4, 80, 7.0, 0.0));
                ShelfInfo shelfInfo = new ShelfInfo("a21", 1, 200, 40, 4, 10, shelvesCapacities, 180, 200.0);
                Optional<List<ShelvesRemain>> remains = shelfInfo.remaining_levels(new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0));
                remains.ifPresent(shelvesRemains -> remains_list = shelvesRemains);




            }
            @Test
                public void ValidShelves2(){

                Assertions.assertEquals(2,remains_list.getFirst().getQuantity());
            }

            @Test
            public void ValidShelvesThree(){
                    Assertions.assertEquals(24,remains_list.get(1).getQuantity());
                }
                @Test
                public void ValidDhelvesFor(){
                    Assertions.assertEquals(72,remains_list.get(2).getQuantity());
                }




            }
    @Nested
    class RemaAllEmpty{
        Optional<List<ShelvesRemain>> remains;
        @BeforeEach
        public void  ValidRemaingLevels() {




            List<ShelvesCapacity> shelvesCapacities = new ArrayList<>();
            shelvesCapacities.add(new ShelvesCapacity(5, "a25", 1, 200.0, 40.0, 0.0));
            shelvesCapacities.add(new ShelvesCapacity(5, "a25", 2, 200.0, 40.0, 0.0));
            shelvesCapacities.add(new ShelvesCapacity(5, "a25", 3, 200.0, 40.0, 0.0));
            shelvesCapacities.add(new ShelvesCapacity(5, "a25", 4, 200, 40.0, 0.0));
            ShelfInfo shelfInfo = new ShelfInfo("a21", 1, 200, 40.0, 4, 10, shelvesCapacities, 180, 200.0);
             remains = shelfInfo.remaining_levels(new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0));





        }
        @Test
        public void ValidShelves2(){
          Assertions.assertTrue(remains.isEmpty());


        }





    }

    @Test
    public void multiple_shelf(){

        List<ShelvesCapacity> shelvesCapacities1 = new ArrayList<>();
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 1, 200.0, 40.0, 0.0)); //40
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 2, 200.0, 40.0, 0.0));
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 3, 200.0, 40.0, 0.0));
        shelvesCapacities1.add(new ShelvesCapacity(1, "a21", 4, 200.0, 40.0, 0.0));

        List<ShelvesCapacity> shelvesCapacities5 = new ArrayList<>(); // 98 totali +40 = 138 totali
    /*        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 1, 200.0, 40.0, 0.0)); //0
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 2, 180.0, 30.0, 0.0));// 2
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 3, 120, 20.0, 0.0));//24
            shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 4, 80, 7.0, 0.0));//72*/
        shelvesCapacities1.add(new ShelvesCapacity(1, "a25", 1, 200.0, 40.0, 0.0));
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 2, 200.0, 40.0, 0.0));// 2
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 3, 200.0, 40.0, 0.0));//24
        shelvesCapacities5.add(new ShelvesCapacity(5, "a25", 4, 200.0, 40.0, 0.0));//72

        ShelfInfo shelfInfo1 = new ShelfInfo("a21", 1, 200.0, 40.0, 4, 10, shelvesCapacities1, 180, 200);
        ShelfInfo shelfInfo5 = new ShelfInfo("a25", 5, 200.0, 40.0, 4, 10, shelvesCapacities5, 180, 200);

        List<ShelfInfo> shelfInfoList = Arrays.asList(shelfInfo1, shelfInfo5);


        PlacementShelf placementShelf = new PlacementShelf(shelfInfoList);
         LotDimension lotDimension = new LotDimension("axx", 1, 12.1, 4.1, 0, 4.0);
        LotAssigment lotAssigment = placementShelf.assignmentLots(lotDimension, 30);
        System.out.println(lotAssigment.getShelvesAssigmentList().size());

    }















}