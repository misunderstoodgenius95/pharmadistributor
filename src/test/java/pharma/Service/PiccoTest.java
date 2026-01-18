package pharma.Service;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import pharma.Model.Acquisto;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PiccoTest {

    @Test
    void generate_array_input() {
        List<Acquisto> acquistoList=List.of(
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2024-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2021-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2022-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 100, Date.valueOf("2026-01-05"), 8.50),
                new Acquisto(2, "Aspirina", 85, Date.valueOf("2024-01-06"), 6.30),
                new Acquisto(2, "Aspirina", 80, Date.valueOf("2024-01-08"), 10.20),
                new Acquisto(1, "Tachipirina", 50, Date.valueOf("2024-01-10"), 8.50),
                new Acquisto(3, "Brufen", 78, Date.valueOf("2024-01-12"), 9.80),
                new Acquisto(1, "Tachipirina", 300, Date.valueOf("2024-01-15"), 8.50),
                new Acquisto(9, "Bisolvon", 110, Date.valueOf("2024-01-18"), 11.30));

        double[] actual=Picco.generate_array_input(acquistoList);
        Assertions.assertThat(actual).hasSize(10);
    }


    @Test
    void generate_standard_devation() {
        List<Acquisto> acquistoList=List.of(
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2024-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2021-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2022-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 100, Date.valueOf("2026-01-05"), 8.50),
                new Acquisto(2, "Aspirina", 85, Date.valueOf("2024-01-06"), 6.30),
                new Acquisto(2, "Aspirina", 80, Date.valueOf("2024-01-08"), 10.20),
                new Acquisto(1, "Tachipirina", 50, Date.valueOf("2024-01-10"), 8.50),
                new Acquisto(3, "Brufen", 78, Date.valueOf("2024-01-12"), 9.80),
                new Acquisto(1, "Tachipirina", 300, Date.valueOf("2024-01-15"), 8.50),
                new Acquisto(9, "Bisolvon", 110, Date.valueOf("2024-01-18"), 11.30));

        double value=Picco.generate_standard_devation(acquistoList);
        Assertions.assertThat(value).isCloseTo(611.62, Offset.offset(1.0));



    }

    @Test
    void calculate_analisi_picco() {
        List<Acquisto> acquistoList=List.of(
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2024-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2021-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 80, Date.valueOf("2022-01-05"), 8.50),
                new Acquisto(1, "Tachipirina", 100, Date.valueOf("2026-01-05"), 8.50),
                new Acquisto(2, "Aspirina", 85, Date.valueOf("2024-01-06"), 6.30),
                new Acquisto(2, "Aspirina", 80, Date.valueOf("2024-01-08"), 10.20),
                new Acquisto(1, "Tachipirina", 50, Date.valueOf("2024-01-10"), 8.50),
                new Acquisto(3, "Brufen", 78, Date.valueOf("2024-01-12"), 9.80),
                new Acquisto(1, "Tachipirina", 300, Date.valueOf("2024-01-15"), 8.50),
                new Acquisto(9, "Bisolvon", 110, Date.valueOf("2024-01-18"), 11.30));

        Picco picco=new Picco();
        List<Acquisto> list=picco.calculate_analisi_picco(acquistoList,1).get();
        Assertions.assertThat(list.getFirst().getQuantity()).isEqualTo(300);



    }
}