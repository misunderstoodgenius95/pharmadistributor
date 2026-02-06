package pharma.Service;

import pharma.Model.DistribuzioneModel;
import pharma.dao.PurchaseOrderDao;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class DistributionPercentuages {
    private PurchaseOrderDao purchaseOrderDao;
    public DistributionPercentuages(PurchaseOrderDao purchaseOrderDao) {
        this.purchaseOrderDao=purchaseOrderDao;
    }



 /*   public List<DistribuzioneModel> filter(YearMonth starting_range, YearMonth end_range,List<DistribuzioneModel> distribuzioneModels) {
        // Validazione input
        if (starting_range == null || end_range == null) {
            throw new IllegalArgumentException("Range dates cannot be null");
        }

        if (starting_range.isAfter(end_range)) {
            throw new IllegalArgumentException(
                    "Starting range must be before or equal to end range: "
                            + starting_range + " > " + end_range
            );
        }

        // Converti YearMonth in LocalDate range
        LocalDate startDate=starting_range.atDay(1);
        LocalDate endDate = end_range.atEndOfMonth();

        // Converti in java.sql.Date per confronto
        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);

        // Filtra gli elementi nel range
        return distribuzioneModels.stream()
                .filter(model -> {
                    Date orderDate = model.getOrder_date();

                    // Gestione null
                    if (orderDate == null) {
                        return false;
                    }

                    // Verifica se è nel range (inclusi estremi)
                    // orderDate >= sqlStartDate AND orderDate <= sqlEndDate
                    return !orderDate.before(sqlStartDate) &&
                            !orderDate.after(sqlEndDate);
                })
                .toList();
    }*/
  public int sum(List<DistribuzioneModel> distribuzioneModels){
         return  distribuzioneModels.stream().mapToInt(DistribuzioneModel::getNum_order).sum();
    }




    public List<DistribuzioneModel> calculatePercentuages(YearMonth starting_range,YearMonth end_range   ){
   Date starting_date=Date.valueOf(starting_range.atDay(1));
   Date end_date=Date.valueOf(end_range.atEndOfMonth());
            List<DistribuzioneModel> distribuzioneModels=purchaseOrderDao.findPharmaOrderNumber(starting_date,end_date);
        int sum=sum(distribuzioneModels);
        return distribuzioneModels.stream().peek(value-> {
            double percentuages=((double) value.getNum_order() /sum) *100;

            value.setPercentuages(percentuages);

        }).toList();




    }


}
