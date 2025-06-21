package pharma.formula;

import org.assertj.core.api.AbstractSoftAssertions;
import org.assertj.core.api.AssertJProxySetup;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pharma.formula.TrasformValue.remaining_day;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrasformValueTest {


    @Test
    void trim_two() {
        BigDecimal bigDecimal=new BigDecimal(0.99);
        bigDecimal=bigDecimal.setScale(1, RoundingMode.HALF_UP);
        System.out.println(bigDecimal.doubleValue());

    }
    @ParameterizedTest
    @CsvSource({"0.45,10.0","0.4,20.0","0.55,-10"})
    void ValidNormalize_percentuages(double expected,double input) {
        assertEquals(expected,TrasformValue.normalize_percentuages(input));



    }

    @ParameterizedTest
    @ValueSource(doubles = {-100.01,100.1})
    public  void Invalid_percentages(double value){
        Assertions.assertThrows(IllegalArgumentException.class,()->TrasformValue.normalize_percentuages(value));

    }
    @Test
    public void NormalizePercentuages(){


    }



    @ParameterizedTest
    @CsvSource({"1200,1000,20","54.31,45.26,20"})
    void ValidGain(double expected,double cost,double gain) {

        assertEquals(expected,TrasformValue.gain(cost,gain));
    }

    @Test
    void testRoundTwo() {
        assertEquals(50.22,TrasformValue.trim_two(50.2222));
    }

    @Test
    void adjust_factor() {
        Assertions.assertEquals(50,TrasformValue.adjust_factor(0.9));
    }

    @Test
    void normalizeValue2() {
        System.out.println(TrasformValue.normalizeValue2(400,180));
    }

    @Test
    void normalizeRemainingDaysRounded() {

        List<Date> list = Arrays.asList(
                Date.valueOf(LocalDate.of(2027, 1, 10)),
                Date.valueOf(LocalDate.of(2027, 1, 20)),
                Date.valueOf(LocalDate.of(2027, 1, 30))
        );
        long remaining=TrasformValue.remaining_day(TrasformValue.calculateAverageEpoch(list));
        System.out.println(remaining);

    }


    @ParameterizedTest
    @CsvSource({"0.0,550,180","0.2,100,180"})
    void calculateExpirationUrgency(double expected,long expireday,double threshold) {
        Assertions.assertEquals(expected,TrasformValue.calculateExpirationUrgency(expireday,threshold));


    }



    @Test
    public  void testLong(){

        long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(),LocalDate.now().plusDays(10));
        System.out.println(daysUntilExpiration);


    }

    @Test
    void calculateStockUrgency() {



    }

    @Nested
 class TestNormalize {
     @Nested
     class LevelofStock {
         @Test
         void EqualCalculus_level_of_stock() {
             assertEquals(0.5, TrasformValue.normalizeValue(550, 550));
         }

         @Test
         void ExLessCalculus_level_of_stock() {
             assertEquals(0.4, TrasformValue.normalizeValue(400, 550));
         }

         @Test
         void ExAboveCalculus_level_of_stock() {
             assertEquals(0.6, TrasformValue.normalizeValue(630, 550));

         }
     }

         @Nested
         class DateExpire {
             @Test
             void EqualCalculus_level_of_stock() {
                 assertEquals(0.5, TrasformValue.normalizeValue(180, 180));
             }
             @Test
             void ExLessCalculus_level_of_stock() {
                 assertEquals(0.6, TrasformValue.normalizeValue(160, 180));
             }
             @Test
             void ExAboveCalculus_level_of_stock() {
                 assertEquals(0.2, TrasformValue.normalizeValue(300, 180));
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


     @Test
     public void test(){
         System.out.println(0.6-0.5);

     }

     @Nested
     class ValidCalculusTrend {
         @Test
         public void ValidTrendAumento() {
             assertEquals(0.02, TrasformValue.calculus_trend(0.7));
         }

         @Test
         public void ValidTrendDecremento() {
             assertEquals(-0.01, TrasformValue.calculus_trend(0.4));
         }

         public void ValidTrendStabile() {
             assertEquals(-0, TrasformValue.calculus_trend(0.5));
         }

     }




       @ParameterizedTest(name = "stock{0},expected{1}")
       @CsvSource({"650"})
       @ValueSource(ints ={650,200,300,100} )
       @ValueSource(doubles = {})
             void ExLessCalculus_level_of_stock(int stock,double expected) {
                 assertEquals(expected, TrasformValue.normalizeValue(stock, 550));
             }


        @Nested
        class AjdustValue{


            @ParameterizedTest()
            @ValueSource(doubles = {-0.1,1.1})
            public void InvalidAdjFactor(double normalize_factor) {

                Assertions.assertThrows(IllegalArgumentException.class,()->TrasformValue.adjust_factor(500,normalize_factor));
            }
            @ParameterizedTest
            @CsvSource({"450,500,0.6","550,500,0.4","540,600,0.62","500,500,0.45"})
                 public  void ValidAdjustFactor(double expected,double cost,double factor){
                assertEquals(expected,TrasformValue.adjust_factor(cost,factor));
            }










        }


    @Test
    void calculateAverageEpoch() {
        List<Date> dateList = List.of(
                Date.valueOf(LocalDate.of(2023, 1, 10)),
                Date.valueOf(LocalDate.of(2023, 1, 20)),
                Date.valueOf(LocalDate.of(2023, 1, 30)));
        System.out.println(TrasformValue.calculateAverageEpoch(dateList));

    }
    Stream<List<Date>> listDateValue(){
        Date date5=null;

        return Stream.of(
            Arrays.asList(
                Date.valueOf(LocalDate.of(2023, 1, 10)),
                Date.valueOf(LocalDate.of(2023, 1, 20)),
                Date.valueOf(LocalDate.of(2023, 1, 30)),
                date5
        ), List.of());


    }


    @Nested
    class AverageEpocheDate {
        @Test
        void InvalidcalculateAverageEpochWithDateisNull() {
            Date date5 = null;

            List<Date> list = Arrays.asList(
                    Date.valueOf(LocalDate.of(2023, 1, 10)),
                    Date.valueOf(LocalDate.of(2023, 1, 20)),
                    Date.valueOf(LocalDate.of(2023, 1, 30)),
                    date5);
            Assertions.assertThrows(NullPointerException.class, () -> {
                TrasformValue.calculateAverageEpoch(list);
            });

        }

        @Test
        void invalidCalucateAveragewithEmptyDateList(){
            List<Date> list=List.of();

            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                TrasformValue.calculateAverageEpoch(list);
            });


        }

        @Test
        void validAverage(){
            Date date_3=null;
            List<Date> list = Arrays.asList(
                    Date.valueOf(LocalDate.of(2023, 1, 10)),
                    Date.valueOf(LocalDate.of(2023, 1, 20)),
                    Date.valueOf(LocalDate.of(2023, 1, 30))
                    );
            long  sum_date=list.stream().mapToLong(java.util.Date::getTime).sum()/list.size();
            assertEquals(sum_date,TrasformValue.calculateAverageEpoch(list));


        }

        @Nested
        class RemaingExpire {
            @Test
            @DisplayName("Should return 1 when epoch is exactly 1 day in the future")
            void ValidRemaining10Day() {
                // Given - 1 day from now
                LocalDate tomorrow = LocalDate.now().plusDays(10);

                long tomorrowEpoch = tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

                long remainingDays = remaining_day(tomorrowEpoch);

                assertEquals(10, remainingDays);
            }



            @DisplayName("Component Testing Remaining day")
            @Test
            public  void ValidRemaing() {
                List<Date> list = Arrays.asList(
                        Date.valueOf(LocalDate.of(2026, 1, 10)),
                        Date.valueOf(LocalDate.of(2026, 1, 20)),
                        Date.valueOf(LocalDate.of(2026, 1, 30))
                );

                System.out.println(TrasformValue.normalizeValue(TrasformValue.remaining_day(TrasformValue.calculateAverageEpoch(list)), 180));
            }
            @Test
            public  void ValidAllTesting(){
                double[] sellerPriceHistory = {110, 130.0};
                double[] currentPurchasePrices = {40.0,50.0, 60.0};
                double[] purchasePriceHistory = {90.0,110};
                double current_price = 82.0;
                List<Date> list = Arrays.asList(
                        Date.valueOf(LocalDate.of(2027, 1, 10)),
                        Date.valueOf(LocalDate.of(2027, 1, 20)),
                        Date.valueOf(LocalDate.of(2027, 1, 30))
                );
                double[] current_stock_quantity={500,600,400,300};



        /*        double value_gain_normalized= TrasformValue.normalize_percentuages(
                                 TrasformValue.calculate_percent_gain(sellerPriceHistory,currentPurchasePrices,purchasePriceHistory,current_price));

                double normalize_date= TrasformValue.percentageFromNormalized(TrasformValueTrasformValue.remaining_day(TrasformValue.calculateAverageEpoch(list)),180));
                System.out.println(normalize_date);

                double normalize_stock= TrasformValue.percentageFromNormalized(TrasformValue.normalizeValue2(TrasformValue.average(current_stock_quantity),550));
                System.out.println(normalize_stock);
*/

            }



















        }






    }






}























