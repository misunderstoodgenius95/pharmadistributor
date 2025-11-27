package pharma.formula.Report;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.Model.Acquisto;
import pharma.Model.SpesaMensile;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;


class AndamentoTest {
    private List<Acquisto> acquistoList;
    @BeforeEach
    void setUp() {
         acquistoList=List.of(
                new Acquisto(1, "Tachipirina", 120, Date.valueOf("2024-01-05"), 8.50),
                new Acquisto(2, "Aspirina", 85, Date.valueOf("2024-01-06"), 6.30),
                new Acquisto(8, "Fluimucil", 95, Date.valueOf("2024-01-08"), 10.20),
                new Acquisto(1, "Tachipirina", 150, Date.valueOf("2024-01-10"), 8.50),
                new Acquisto(3, "Brufen", 78, Date.valueOf("2024-01-12"), 9.80),
                new Acquisto(1, "Tachipirina", 250, Date.valueOf("2024-01-15"), 8.50),
                new Acquisto(9, "Bisolvon", 110, Date.valueOf("2024-01-18"), 11.30),
                new Acquisto(4, "Voltaren", 65, Date.valueOf("2024-01-20"), 12.50),
                new Acquisto(2, "Aspirina", 92, Date.valueOf("2024-01-22"), 6.30),
                new Acquisto(1, "Tachipirina", 180, Date.valueOf("2024-01-25"), 8.50),
                new Acquisto(1, "Tachipirina", 175, Date.valueOf("2024-02-03"), 8.50),
                new Acquisto(10, "Enterogermina", 45, Date.valueOf("2024-02-05"), 12.90),
                new Acquisto(3, "Brufen", 85, Date.valueOf("2024-02-08"), 9.80),
                new Acquisto(12, "Gaviscon", 55, Date.valueOf("2024-02-10"), 9.70),
                new Acquisto(2, "Aspirina", 70, Date.valueOf("2024-02-12"), 6.30),
                new Acquisto(8, "Fluimucil", 88, Date.valueOf("2024-02-14"), 10.20),
                new Acquisto(4, "Voltaren", 68, Date.valueOf("2024-02-18"), 12.50),
                new Acquisto(11, "Lactoflorene", 38, Date.valueOf("2024-02-20"), 18.50),
                new Acquisto(3, "Brufen", 92, Date.valueOf("2024-02-25"), 9.80),
                new Acquisto(13, "Maalox", 42, Date.valueOf("2024-02-28"), 8.90),
                new Acquisto(5, "Aerius", 95, Date.valueOf("2024-03-02"), 15.20),
                new Acquisto(6, "Reactine", 88, Date.valueOf("2024-03-05"), 13.80),
                new Acquisto(5, "Aerius", 150, Date.valueOf("2024-03-08"), 15.20),
                new Acquisto(7, "Zirtec", 105, Date.valueOf("2024-03-10"), 14.50),
                new Acquisto(5, "Aerius", 185, Date.valueOf("2024-03-12"), 15.20),
                new Acquisto(6, "Reactine", 120, Date.valueOf("2024-03-15"), 13.80),
                new Acquisto(1, "Tachipirina", 110, Date.valueOf("2024-03-18"), 8.50),
                new Acquisto(7, "Zirtec", 95, Date.valueOf("2024-03-20"), 14.50),
                new Acquisto(5, "Aerius", 165, Date.valueOf("2024-03-25"), 15.20),
                new Acquisto(6, "Reactine", 98, Date.valueOf("2024-03-28"), 13.80));
    }

    @Test
    void raggruppaPerMese() {
        Andamento andamento=new Andamento(acquistoList);
        Map<YearMonth, SpesaMensile> actual= andamento.raggruppaPerMese();
        Assertions.assertThat(actual.values()).extracting(SpesaMensile::getSpesaTotale).contains( 10854.0,7601.500000000001,17101.8);


    }

    @Test
    void calcolaVariazioneMesi() {
        Andamento andamento=new Andamento(acquistoList);

         YearMonth spesa_prev=YearMonth.from(LocalDate.of(2024,1,10));
         YearMonth spesa_next=YearMonth.from(LocalDate.of(2024,2,10));
      //  Gennaio: 10,854.00€, Febbraio: 7,601.50€
        // Variazione = ((7601.50 - 10854.00) / 10854.00) * 100 = -29.97%
        double value=andamento.calcolaVariazioneMesi(spesa_prev,spesa_next);
        Assertions.assertThat(value).isCloseTo(-29.97, Offset.offset(0.01));
    }


    @Test
    void calcolo_trend() {
        Andamento andamento=new Andamento(acquistoList);
        YearMonth spesa_first=YearMonth.from(LocalDate.of(2024,1,10));
        YearMonth spesa_last=YearMonth.from(LocalDate.of(2024,3,10));
        double actual=andamento.calcolo_trend(spesa_last,spesa_first).get();
        double expected=(17101.80-10854.00)/2;
        Assertions.assertThat(actual).isEqualTo(expected);
    }


    @Test
    void calculate_legenda_trend() {
        Andamento andamento=new Andamento(acquistoList);
        YearMonth spesa_first=YearMonth.from(LocalDate.of(2024,1,10));
        YearMonth spesa_last=YearMonth.from(LocalDate.of(2024,3,10));
        double trend=andamento.calcolo_trend(spesa_last,spesa_first).get();
        TrendType legenda=andamento.calculate_legenda_trend(trend);
        Assertions.assertThat(legenda).isEqualTo(TrendType.CRESCENTE);
    }

    @Test
    void calcolaSpesaMese() {
        Andamento andamento=new Andamento(acquistoList);
        YearMonth spesa_mese=YearMonth.from(LocalDate.of(2024,1,10));
        double actual=andamento.calcolaSpesaMese(spesa_mese).get();
        double expected=10854.0;
        Assertions.assertThat(actual).isEqualTo(expected);

    }
}
















