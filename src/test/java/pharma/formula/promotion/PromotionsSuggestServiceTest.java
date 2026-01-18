package pharma.formula.promotion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pharma.Model.FieldData;
import pharma.Model.PromotionData;
import pharma.Service.Promotion.PromotionsSuggestService;
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

class PromotionsSuggestServiceTest {
    @Mock
    SellerOrderDetails s_detail;
    @Mock
    PurchaseOrderDetailDao p_detail;

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
    void TwentyFive_promotion_discount() {

        double[] sellerPriceHistory = {110, 130.0};
        double[] currentPurchasePrices = {40.0,50.0, 60.0};
        double currentPrice = 82.0;




        List<Date> dateList = List.of(
                Date.valueOf(LocalDate.of(2027, 1, 10)),
                Date.valueOf(LocalDate.of(2027, 1, 20)),
                Date.valueOf(LocalDate.of(2027, 1, 30)));

        int[] stock_qty=new int[]{150,200,300,100};
       PromotionsSuggestService promotionsSuggest=new PromotionsSuggestService();

        PromotionData promotionData=new PromotionData(currentPurchasePrices,sellerPriceHistory,dateList,stock_qty,currentPrice,550,180);
        int  value=promotionsSuggest.get_promotion_discount(promotionData);
        Assertions.assertEquals(25,value);

    }

    @Test
    void Fifty_promotion_discount() {

        double[] sellerPriceHistory = {110, 130.0};
        double[] currentPurchasePrices = {40.0,50.0, 60.0};
        double currentPrice = 82.0;




        List<Date> dateList = List.of(
                Date.valueOf(LocalDate.of(2026, 2, 20)),
                Date.valueOf(LocalDate.of(2026, 2, 28)),
                Date.valueOf(LocalDate.of(2026, 2, 28)));

        int[] stock_qty=new int[]{10,20,30,10};
        PromotionsSuggestService promotionsSuggest=new PromotionsSuggestService();

        PromotionData promotionData=new PromotionData(currentPurchasePrices,sellerPriceHistory,dateList,stock_qty,currentPrice,550,180);
        int  value=promotionsSuggest.get_promotion_discount(promotionData);
        Assertions.assertEquals(50,value);

    }


    @Test
    void Ten_promotion_discount() {

        double[] sellerPriceHistory = {110, 130.0};
        double[] currentPurchasePrices = {40.0,50.0, 60.0};
        double currentPrice = 82.0;




        List<Date> dateList = List.of(
                Date.valueOf(LocalDate.of(2029, 2, 10)),
                Date.valueOf(LocalDate.of(2029, 2, 10)),
                Date.valueOf(LocalDate.of(2029, 2, 10)));

        int[] stock_qty=new int[]{500,600,300,100};
        PromotionsSuggestService promotionsSuggest=new PromotionsSuggestService();

        PromotionData promotionData=new PromotionData(currentPurchasePrices,sellerPriceHistory,dateList,stock_qty,currentPrice,550,180);
        int  value=promotionsSuggest.get_promotion_discount(promotionData);
        Assertions.assertEquals(10,value);

    }











}