package pharma.formula;

import org.junit.jupiter.api.Test;
import pharma.Service.ClusterPoint;
import pharma.Service.KMeans;

import java.util.List;

class KMeansTest {


    @Test
    void get_max_centroid() {
        KMeans kMeans=new KMeans(List.of(new ClusterPoint(
                new double[]{10.10,20.30,60.10}),
                new ClusterPoint(new double[]{80.40,60.30,20.80})
        ),1);
        System.out.println(kMeans.get_max_centroid());

    }
    /*



    purchase_lots price_old     6.60  4.50 5.50  3.90
    purchase lots current    8.50  9.50  11.50 6.50
    purchase seller_old     7.50  8.60   11.50   23.78
     */
    @Test
    void  test(){
        KMeans kMeans=new KMeans(List.of(new ClusterPoint(new double[]{ 6.60, 4.50,5.50,3.90}),
               new ClusterPoint( new double[]{8.50,9.50,11.50,6.50})),1);

        System.out.println(kMeans.get_max_centroid());
    }

    /*
    purchase_lot_price 6.50 5.50
    purchase seller_old  10.50 7.60 -> it is empty
     */
    @Test
    void   TestWithPurchaseValueEMp(){
        KMeans kMeans=new KMeans(List.of(new ClusterPoint(new double[]{ 6.60,5.50})),1);

        System.out.println(kMeans.get_max_centroid());
    }



}