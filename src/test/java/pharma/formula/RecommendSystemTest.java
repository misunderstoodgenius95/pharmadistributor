package pharma.formula;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecommendSystemTest {

    @Test
    void Validgain() {

        Assertions.assertEquals(1200,RecommendSystem.gain(1000,20));
    }

    @Test
    void adjust_factor() {
        Assertions.assertEquals(1100, RecommendSystem.adjust_factor(1000));
    }
}