package pharma.formula;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

class TrasformValueTest {


    @Test
    void round() {
        BigDecimal bigDecimal=new BigDecimal(0.99);
        bigDecimal=bigDecimal.setScale(1, RoundingMode.HALF_UP);
        System.out.println(bigDecimal.doubleValue());

    }

 @Nested
 class TestNormalize {
     @Nested
     class LevelofStock {
         @Test
         void EqualCalculus_level_of_stock() {
             Assertions.assertEquals(0.5, TrasformValue.normalizeStock(550, 550));
         }

         @Test
         void ExLessCalculus_level_of_stock() {
             Assertions.assertEquals(0.4, TrasformValue.normalizeStock(400, 550));
         }

         @Test
         void ExAboveCalculus_level_of_stock() {
             Assertions.assertEquals(0.5, TrasformValue.normalizeStock(630, 550));
         }

         @Nested
         class DateExpire {
             @Test
             void EqualCalculus_level_of_stock() {
                 Assertions.assertEquals(0.5, TrasformValue.normalizeStock(180, 180));
             }

             @Test
             void ExLessCalculus_level_of_stock() {
                 Assertions.assertEquals(0.4, TrasformValue.normalizeStock(160, 180));
             }

             @Test
             void ExAboveCalculus_level_of_stock() {
                 Assertions.assertEquals(0.6, TrasformValue.normalizeStock(190, 180));
             }
         }


           /*  @Disabled
             @Nested
             class Less {
                 @Test
                 void ExLessCalculus_level_of_stock1() {
                     Assertions.assertEquals(0.9, TrasformValue.normalizeStock(List.of(100, 200, 200), 550));
                 }

                 @Test
                 void ExLessCalculus_level_of_stock2() {
                     Assertions.assertEquals(0.7, TrasformValue.normalizeStock(List.of(100, 100, 200), 550));
                 }

                 @Test
                 void ExLessCalculus_level_of_stock3() {
                     Assertions.assertEquals(0.4, TrasformValue.normalizeStock(List.of(200), 550));
                 }

                 @Test
                 void ExLessCalculus_level_of_stock7() {
                     Assertions.assertEquals(0.4, TrasformValue.normalizeStock(List.of(300), 550));
                 }

                 @Test
                 void ExLessCalculus_level_of_stock4() {
                     Assertions.assertEquals(0.2, TrasformValue.normalizeStock(List.of(100), 550));
                 }


             }

            */


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

         @Test
         public void ValidTrendStabile() {
             Assertions.assertEquals(-0, TrasformValue.calculus_trend(0.5));
         }

     }


   /*  @Nested
     class LevelofStock {
         @Test
         void ExAboveCalculus_level_of_stock() {
             Assertions.assertEquals(1, TrasformValue.normalizeStock(List.of(100, 200, 300), 550));
         }

         @Test
         void ExLessCalculus_level_of_stock() {
             Assertions.assertEquals(0.9, TrasformValue.normalizeStock(List.of(100, 200, 200), 550));
         }

         @Test
         void ExEqualCalculus_level_of_stock() {
             Assertions.assertEquals(0.5, TrasformValue.normalizeStock(List.of(100, 150, 300), 550));
         }

         @Nested
         class Less {
             @Test
             void ExLessCalculus_level_of_stock1() {
                 Assertions.assertEquals(0.9, TrasformValue.normalizeStock(List.of(100, 200, 200), 550));
             }

             @Test
             void ExLessCalculus_level_of_stock2() {
                 Assertions.assertEquals(0.7, TrasformValue.normalizeStock(List.of(100, 100, 200), 550));
             }

             @Test
             void ExLessCalculus_level_of_stock3() {
                 Assertions.assertEquals(0.4, TrasformValue.normalizeStock(List.of(200), 550));
             }

             @Test
             void ExLessCalculus_level_of_stock7() {
                 Assertions.assertEquals(0.4, TrasformValue.normalizeStock(List.of(300), 550));
             }

             @Test
             void ExLessCalculus_level_of_stock4() {
                 Assertions.assertEquals(0.2, TrasformValue.normalizeStock(List.of(100), 550));
             }
        }

         }*/







}




