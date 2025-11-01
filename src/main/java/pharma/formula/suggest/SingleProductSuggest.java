package pharma.formula.suggest;

import org.jetbrains.annotations.TestOnly;
import pharma.formula.suggest.Model.Lots;
import pharma.formula.suggest.Model.SellerOrders;
import pharma.formula.suggest.Model.SuggestConfig;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SingleProductSuggest {
    private  List<Lots> lotsList;
    private SuggestConfig suggestConfig;
    private List<SellerOrders> sellerOrders;
    public SingleProductSuggest(List<Lots> lots,SuggestConfig suggestConfig,List<SellerOrders> sellerOrders) {
        this.lotsList=lots;
        this.suggestConfig=suggestConfig;
        this.sellerOrders=sellerOrders;
    }

    /**
     * Calculate the number of day remaining to the current day
     * @return
     */
    @TestOnly
    public  long calculateDateofSum() {
        if (lotsList.isEmpty()) return 0;
LocalDate today=LocalDate.now();
        return lotsList.stream().mapToLong(lot-> {
            LocalDate expirationDate=lot.getElapsed_date().toLocalDate();
            return ChronoUnit.DAYS.between(today,expirationDate);
        }).sum();








    }

    @TestOnly
    public long calculate_average_availability(){
        if(lotsList.isEmpty()){
            return  0;
        }
        return  lotsList.stream().mapToInt(Lots::getAvailability).sum();
    }
    @TestOnly
    public int calculate_average_seller_order_quantity(){
        if(sellerOrders.isEmpty()){
            return 0;
        }
        return sellerOrders.stream().mapToInt(SellerOrders::getQuantity).sum();
    }

    /**
     *
     * @return true if the all value it is empty,or seller_order> threshold and both other threshold
     */
    public boolean calculate_suggest() {
        boolean availability = false;
        boolean date = false;
        boolean sellers = false;
        if(calculate_average_availability()==0 || calculate_average_seller_order_quantity()==0){
            return true;
        }
        if (calculateDateofSum() <= suggestConfig.getMaxium_expire_day()) {
            date = true;
        }
        if (calculate_average_availability() <= suggestConfig.getMaxium_availability()) {
                availability = true;
            }
        if (calculate_average_seller_order_quantity() >= suggestConfig.getMinimum_seller_order()) {
                sellers = true;
            }
//       both true for return true
        boolean value=date && availability;
        if(!value && sellers){
            return  false;
        }else return value && sellers;


    }













}
