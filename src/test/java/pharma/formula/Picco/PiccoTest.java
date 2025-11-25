package pharma.formula.Picco;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.Model.Acquisto;

import java.sql.Date;
import java.util.List;

class PiccoTest {
    private Picco picco;

    @BeforeEach
    void setUp() {
         List<Acquisto> acquistoList=List.of(
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
picco=new Picco(acquistoList);
    }


   /* @Test
    void generate_array_input() {
    *//*    double[]actual=picco.generate_array_input();*//*
        double[] expected = {
                120.0,  // Tachipirina
                85.0,  // Aspirina
                95.0,  // Fluimucil
                150.0,  // Tachipirina
                78.0,  // Brufen
                200.0,  // Tachipirina
                110.0,  // Bisolvon
                65.0,  // Voltaren
                92.0,  // Aspirina
                180.0,  // Tachipirina
                175.0,  // Tachipirina
                45.0,  // Enterogermina
                85.0,  // Brufen
                55.0,  // Gaviscon
                70.0,  // Aspirina
                88.0,  // Fluimucil
                68.0,  // Voltaren
                38.0,  // Lactoflorene
                92.0,  // Brufen
                42.0,  // Maalox
                95.0,  // Aerius
                88.0,  // Reactine
                150.0,  // Aerius
                105.0,  // Zirtec
                185.0,  // Aerius
                120.0,  // Reactine
                110.0,  // Tachipirina
                95.0,  // Zirtec
                165.0,  // Aerius
                98.0   // Reactine
        };

        Assertions.assertThat(actual).contains(expected);

    }
    @Test
    public void get_standard_derivation(){
        double[] dati = {
                120.0,  // Tachipirina
                85.0,  // Aspirina
                95.0,  // Fluimucil
                150.0,  // Tachipirina
                78.0,  // Brufen
                200.0,  // Tachipirina
                110.0,  // Bisolvon
                65.0,  // Voltaren
                92.0,  // Aspirina
                180.0,  // Tachipirina
                175.0,  // Tachipirina
                45.0,  // Enterogermina
                85.0,  // Brufen
                55.0,  // Gaviscon
                70.0,  // Aspirina
                88.0,  // Fluimucil
                68.0,  // Voltaren
                38.0,  // Lactoflorene
                92.0,  // Brufen
                42.0,  // Maalox
                95.0,  // Aerius
                88.0,  // Reactine
                150.0,  // Aerius
                105.0,  // Zirtec
                185.0,  // Aerius
                120.0,  // Reactine
                110.0,  // Tachipirina
                95.0,  // Zirtec
                165.0,  // Aerius
                98.0   // Reactine
        };


        double actual=picco.generate_standard_devation();
        double media = 0;
        for (double v : dati) {
            media += v;
        }
        media /= dati.length;

        double sommaQuadrati = 0;
        for (double v : dati) {
            double deviazione = v - media;
            sommaQuadrati += deviazione * deviazione;
        }


        double varianza = sommaQuadrati / (dati.length - 1);

        double expected= Math.sqrt(varianza);
       Assertions.assertThat(actual).isEqualTo(expected);

    }


    @Test
    void calculate_soglia_picco() {
        *//*double average=picco.get_average();
        double dev_stnd=picco.generate_standard_devation();
        double actual=picco.calculate_soglia_picco(average,dev_stnd,1.5);
        double expected=average+1.5*dev_stnd;
        Assertions.assertThat(actual).isEqualTo(expected);*//*
    }
*/

    @Test
    void extract_farmaco_byId() {
        List<Acquisto> list=picco.extract_farmaco_byId(1);
        Assertions.assertThat(list).hasSize(6);

    }

    @Test
    void calculate_analisi_picco() {
        /*
          new Acquisto(1, "Tachipirina", 120, Date.valueOf("2024-01-05"), 8.50),
         new Acquisto(1, "Tachipirina", 150, Date.valueOf("2024-01-10"), 8.50)
          new Acquisto(1, "Tachipirina", 250, Date.valueOf("2024-01-15"), 8.50),
            new Acquisto(1, "Tachipirina", 180, Date.valueOf("2024-01-25"), 8.50),
           new Acquisto(1, "Tachipirina", 175, Date.valueOf("2024-02-03"), 8.50),
          new Acquisto(1, "Tachipirina", 110, Date.valueOf("2024-03-18"), 8.50),
         */
        Assertions.assertThat(picco.calculate_analisi_picco(1)).hasSize(1);



    }
}