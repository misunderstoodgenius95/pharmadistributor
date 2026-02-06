package pharma.Service;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pharma.Model.DistribuzioneModel;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

class DistributionPercentuagesTest {
    private DistributionPercentuages distributionPercentuages;
    private List<DistribuzioneModel>distribuzioneModels;
    /*@BeforeEach
    void setUp() {
        distributionPercentuages =new DistributionPercentuages();
        distribuzioneModels=List.of(
                new DistribuzioneModel("A",30, Date.valueOf(LocalDate.of(2025,1,1))),
                new DistribuzioneModel("B",40,Date.valueOf(LocalDate.of(2025,3,1))),
                new DistribuzioneModel("C",10,Date.valueOf(LocalDate.of(2025,6,1))),
                new DistribuzioneModel("D",50,Date.valueOf(LocalDate.of(2025,7,1))),
                new DistribuzioneModel("E",500,Date.valueOf(LocalDate.of(2024,7,1))));

    }

    @Test
    void ValidSum() {
        Assertions.assertEquals(630,distributionPercentuages.sum(distribuzioneModels));

    }


    @Test
    void ValidPercentuages(){
        YearMonth yearMonth_start=YearMonth.of(2025,1);
        YearMonth yearMonth_end=YearMonth.of(2026,1);
        List<DistribuzioneModel> list= distributionPercentuages.calculatePercentuages(yearMonth_start,yearMonth_end,distribuzioneModels);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(list).hasSize(4);
            softAssertions.assertThat(list.getFirst().getPercentuages()).isCloseTo(23.0, Offset.offset(0.10));
                });


    }

     */
}