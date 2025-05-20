package pharma.formula;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

class TrasformValueTest {


    @Test
    void round() {
        BigDecimal bigDecimal=new BigDecimal(0.99);
        bigDecimal=bigDecimal.setScale(1, RoundingMode.HALF_UP);
        System.out.println(bigDecimal.doubleValue());

    }
    @ParameterizedTest
    @CsvSource({"0.45,10.0","0.4,20.0","0.55,-10",""})
    void ValidNormalize_percentuages(double expected,double input) {
        Assertions.assertEquals(expected,TrasformValue.normalize_percentuages(input));



    }

    @ParameterizedTest
    @ValueSource(doubles = {-100.01,100.1})
    public  void Invalid_percentages(double value){
        Assertions.assertThrows(IllegalArgumentException.class,()->TrasformValue.normalize_percentuages(value));

    }
    @Test
    public void NormalizePercentuages(){


    }



    @Test
    void Validgain() {

        Assertions.assertEquals(1200,TrasformValue.gain(1000,20));
    }




    @Nested
 class TestNormalize {
     @Nested
     class LevelofStock {
         @Test
         void EqualCalculus_level_of_stock() {
             Assertions.assertEquals(0.5, TrasformValue.normalizeValue(550, 550));
         }

         @Test
         void ExLessCalculus_level_of_stock() {
             Assertions.assertEquals(0.4, TrasformValue.normalizeValue(400, 550));
         }

         @Test
         void ExAboveCalculus_level_of_stock() {
             Assertions.assertEquals(0.6, TrasformValue.normalizeValue(630, 550));

         }
     }

         @Nested
         class DateExpire {
             @Test
             void EqualCalculus_level_of_stock() {
                 Assertions.assertEquals(0.5, TrasformValue.normalizeValue(180, 180));
             }
             @Test
             void ExLessCalculus_level_of_stock() {
                 Assertions.assertEquals(0.4, TrasformValue.normalizeValue(160, 180));
             }
             @Test
             void ExAboveCalculus_level_of_stock() {
                 Assertions.assertEquals(0.6, TrasformValue.normalizeValue(190, 180));
             }
         }






     }

     @Nested
     class CalculusTrend {
         @Nested
         class BoundaryValueAnalysisCalculusTrend {
             //  0<x>1
             @Test
             public void InvalidLimitLeft0CalulusTrend() {
                 Assertions.assertThrows(IllegalArgumentException.class, () -> TrasformValue.calculus_trend(-1));


             }


             @Test
             public void ValidLimitRight0CalulusTrend() {
                 Assertions.assertDoesNotThrow(() -> TrasformValue.calculus_trend(0.1));

             }


             @Test
             public void InValidLimit_Right_1CalulusTrend() {
                 Assertions.assertThrows(IllegalArgumentException.class, () -> TrasformValue.calculus_trend(1.1));

             }

             @Test
             public void ValidLimitLeft1CalulusTrend() {
                 Assertions.assertDoesNotThrow(() -> TrasformValue.calculus_trend(0.9));

             }

             @Test
             public void ValidMin0() {
                 Assertions.assertDoesNotThrow(() -> TrasformValue.calculus_trend(0));

             }

             @Test
             public void ValidMax1() {
                 Assertions.assertDoesNotThrow(() -> TrasformValue.calculus_trend(1));

             }


         }
     }

     @Nested
     class ValidCalculusTrend {
         @Test
         public void ValidTrendAumento() {
             Assertions.assertEquals(0.02, TrasformValue.calculus_trend(0.7));
         }

         @Test
         public void ValidTrendDecremento() {
             Assertions.assertEquals(-0.01, TrasformValue.calculus_trend(0.4));
         }

         public void ValidTrendStabile() {
             Assertions.assertEquals(-0, TrasformValue.calculus_trend(0.5));
         }

     }




       @ParameterizedTest(name = "stock{0},expected{1}")
       @CsvSource({"650"})
       @ValueSource(ints ={650,200,300,100} )
       @ValueSource(doubles = {})
             void ExLessCalculus_level_of_stock(int stock,double expected) {
                 Assertions.assertEquals(expected, TrasformValue.normalizeValue(stock, 550));
             }


        @Nested
        class AjdustValue{


            @ParameterizedTest()
            @ValueSource(doubles = {-0.1,1.1})
            public void InvalidAdjFactor(double normalize_factor) {

                Assertions.assertThrows(IllegalArgumentException.class,()->TrasformValue.adjust_factor(500,normalize_factor));
            }
            @ParameterizedTest
            @CsvSource({"450,500,0.6","550,500,0.4"})
                 public  void ValidAdjustFactor(double expected,double cost,double factor){
                Assertions.assertEquals(expected,TrasformValue.adjust_factor(cost,factor));
            }










        }



}























