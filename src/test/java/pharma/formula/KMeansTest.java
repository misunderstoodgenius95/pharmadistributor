package pharma.formula;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class KMeansTest {


    @Test
    void get_max_centroid() {
        KMeans kMeans=new KMeans(List.of(new ClusterPoint(
                new double[]{10.10,20.30,60.10}),
                new ClusterPoint(new double[]{80.40,60.30,20.80})
        ),1);
        System.out.println(kMeans.get_max_centroid());

    }
}