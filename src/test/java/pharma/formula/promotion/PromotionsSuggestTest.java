package pharma.formula.promotion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.dao.*;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class PromotionsSuggestTest {
    @Mock
    SellerOrderDetails s_detail;
    @Mock
    PurchaseOrderDetailDao p_detail;
    @Mock
    PromotionConfigDao p_conf;
    @Mock
    LottiDao lottiDao;
    @Mock
    LotAssigmentDao assigmentDao;
    @Mock
    private SellerPriceDao s_dao;
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);


    }

    @Test
    void TenGet_promotion_discount() {

        double[] sellerPriceHistory = {110, 130.0};
        double[] currentPurchasePrices = {40.0,50.0, 60.0};
        double[] purchasePriceHistory = {90.0,110};
        double currentPrice = 82.0;




        List<Date> dateList = List.of(
                Date.valueOf(LocalDate.of(2027, 1, 10)),
                Date.valueOf(LocalDate.of(2027, 1, 20)),
                Date.valueOf(LocalDate.of(2027, 1, 30)));

        int[] stock_qty=new int[]{150,200,300,100};
/*       PromotionsSuggest promotionsSuggest=new PromotionsSuggest(sellerPriceHistory,purchasePriceHistory,dateList,stock_qty,configDao);
        int  value=promotionsSuggest.get_promotion_discount(currentPurchasePrices,currentPrice);
        Assertions.assertEquals(10,value);*/



    }
    @Test
    void AlreadyExpire_promotion_discount() {
        when(p_conf.findAll()).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setMedium_stock_item(550).setMin_day_expire(180).build()));
when(p_detail.findByProductPrice(anyInt())).
        thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setPrice(110.0).build(),
                FieldData.FieldDataBuilder.getbuilder().setPrice(90.0).build()));
when(s_detail.findbyProduct(anyInt())).thenReturn(
        List.of(FieldData.FieldDataBuilder.getbuilder().setPrice(130.0).build()),
        List.of(FieldData.FieldDataBuilder.getbuilder().setPrice(80.0).build()));
when(lottiDao.findbyDate(anyInt())).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().
        setElapsed_date(Date.valueOf(LocalDate.of(2025,10,10))).build()));
when(assigmentDao.findQuantitybyFarmacoId(anyInt())).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setQuantity(5).build()));
when(s_dao.findCurrentPricebyFarmaco(anyInt())).thenReturn(82.0);


        PromotionsSuggest suggest=new PromotionsSuggest(s_detail,p_detail,p_conf,lottiDao,assigmentDao,s_dao);
        int discount=suggest.get_promotion_discount(347);
//       PromotionsSuggest promotionsSuggest=new PromotionsSuggest(sellerPriceHistory,purchasePriceHistory,dateList,stock_qty,configDao);
     //   int discount=promotionsSuggest.get_promotion_discount(currentPurchasePrices,currentPrice);*/
        Assertions.assertEquals(50,discount);
    }


    @Test
    void NotAlreadyExpire_promotion_discount() {
        when(p_conf.findAll()).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setMedium_stock_item(550).setMin_day_expire(180).build()));
        when(p_detail.findByProductPrice(anyInt())).
                thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setPrice(110.0).build(),
                        FieldData.FieldDataBuilder.getbuilder().setPrice(90.0).build()));
        when(s_detail.findbyProduct(anyInt())).thenReturn(
                List.of(FieldData.FieldDataBuilder.getbuilder().setPrice(130.0).build()),
                List.of(FieldData.FieldDataBuilder.getbuilder().setPrice(80.0).build()));
        when(lottiDao.findbyDate(anyInt())).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().
                setElapsed_date(Date.valueOf(LocalDate.of(2027,10,10))).build()));
        when(assigmentDao.findQuantitybyFarmacoId(anyInt())).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setQuantity(400).build()));
        when(s_dao.findCurrentPricebyFarmaco(anyInt())).thenReturn(82.0);


        PromotionsSuggest suggest=new PromotionsSuggest(s_detail,p_detail,p_conf,lottiDao,assigmentDao,s_dao);
        int discount=suggest.get_promotion_discount(347);
/*        PromotionsSuggest promotionsSuggest=new PromotionsSuggest(sellerPriceHistory,purchasePriceHistory,dateList,stock_qty,configDao);
        int discount=promotionsSuggest.get_promotion_discount(currentPurchasePrices,currentPrice);*/
        Assertions.assertEquals(10,discount);
    }







    @Test
    void IntegrationAlreadyExpire_promotion_discount() {

        double[] sellerPriceHistory = {110, 130.0};
        double[] currentPurchasePrices = {40.0,50.0, 60.0};
        double[] purchasePriceHistory = {90.0,110};
        double currentPrice = 82.0;



        List<Date> dateList = List.of(
                Date.valueOf(LocalDate.of(2025, 1, 10)),
                Date.valueOf(LocalDate.of(2025, 1, 20)),
                Date.valueOf(LocalDate.of(2025, 1, 30)));
        int[] stock_qty=new int[]{1};
/*        PromotionsSuggest promotionsSuggest=new PromotionsSuggest(sellerPriceHistory,purchasePriceHistory,dateList,stock_qty,configDao);
        int discount=promotionsSuggest.get_promotion_discount(currentPurchasePrices,currentPrice);
        Assertions.assertEquals(50,discount);*/
        //
    }

    @AfterEach
    void tearDown() {

        SellerOrderDetails s_detail=null;

        PurchaseOrderDetailDao p_detail=null;

        PromotionConfigDao p_conf=null;

        LottiDao lottiDao=null;

        LotAssigmentDao assigmentDao=null;
    }

    @Test
    void IntegratioPromotion_discount() {

        Properties properties;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));SellerOrderDetails s_detail=new SellerOrderDetails(Database.getInstance(properties));

            PurchaseOrderDetailDao p_detail=new PurchaseOrderDetailDao(Database.getInstance(properties));
            PromotionConfigDao p_conf=new PromotionConfigDao(Database.getInstance(properties));

            LottiDao lottiDao=new LottiDao(Database.getInstance(properties),"lotto");

            LotAssigmentDao assigmentDao=new LotAssigmentDao(Database.getInstance(properties));
            PromotionsSuggest suggest=new PromotionsSuggest(s_detail,p_detail,p_conf,lottiDao,assigmentDao,s_dao);
            int discount=suggest.get_promotion_discount(347);

            Assertions.assertEquals(25,discount);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }






}