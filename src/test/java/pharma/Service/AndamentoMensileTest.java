package pharma.Service;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.Model.Ordini;
import pharma.Model.Spesa;
import pharma.Service.Report.Andamento;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

class AndamentoMensileTest {

private List<Ordini> list;
    @BeforeEach
    void setUp() {
        list=List.of(new Ordini(1,100.00, Date.valueOf(LocalDate.of(2026,2,10))),
        new Ordini(2,200.00, Date.valueOf(LocalDate.of(2026,2,20))),
                new Ordini(3,300.00, Date.valueOf(LocalDate.of(2026,2,20))),
        new Ordini(3,300.00, Date.valueOf(LocalDate.of(2027,2,28))));


    }


    @Test
    void ValidRaggruppaPerMese(){
        AndamentoMensile andamentoMensile=new AndamentoMensile(list);
        Map<YearMonth, Spesa> spesaMap=andamentoMensile.raggruppaTRaMesi(YearMonth.of(2026,2),YearMonth.of(2026,2));
        org.assertj.core.api.Assertions.assertThat(spesaMap.get(YearMonth.of(2026,2)).getSpesaTotale()).isEqualTo(600);

    }


    @Test
    void calcolaVariazioneMesi() {
        AndamentoMensile andamento=new AndamentoMensile(list);
        YearMonth spesa_prev=YearMonth.from(LocalDate.of(2026,2,10));
        YearMonth spesa_next=YearMonth.from(LocalDate.of(2027,2,10));

        double value=andamento.calcolaVariazioneMesi(spesa_prev,spesa_next).get();

        Assertions.assertThat(value).isCloseTo(-300, Offset.offset(0.01));


    }
}