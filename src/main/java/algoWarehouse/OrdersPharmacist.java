package algoWarehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pharma.Model.Farmacia;
import pharma.Model.FieldData;
import pharma.Model.LotDimensionModel;
import pharma.Model.PharmacyAssigned;
import pharma.dao.FarmaciaDao;
import pharma.dao.SellerOrderDao;
import pharma.dao.SellerOrderDetails;
import pharma.formula.suggest.Model.SellerOrders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersPharmacist {
    private static final Logger log = LoggerFactory.getLogger(OrdersPharmacist.class);
    private SellerOrderDetails sellerOrderDetails;
    private FarmaciaDao farmaciaDao;
    private SellerOrderDao sellerOrderDao;
    public OrdersPharmacist(SellerOrderDetails sellerOrderDetails,FarmaciaDao farmaciaDao,SellerOrderDao sellerOrderDao) {
        this.sellerOrderDetails = sellerOrderDetails;
        this.sellerOrderDao=sellerOrderDao;
        this.farmaciaDao=farmaciaDao;
    }

    /**
     * Calculate the quantity of the given product purchased by the pharmacists.
     * @param product_id
     * @return a list contain Pharmacist and the correlate quantity
     */
    public List<PharmacyAssigned> calculatePharmacyByOrder(int product_id) {

        List<PharmacyAssigned> pharmacyAssigneds = new ArrayList<>();

        List<FieldData> order_details = sellerOrderDetails.findbyProduct(product_id);
            log.info("number  order_details: "+order_details.size());
        for (FieldData orderDetail : order_details) {
            //obtein  the order id
            int order_id = orderDetail.getOrder_id();
        log.info("order_id: "+order_id);
            //obtain the  order
            FieldData fd_order = sellerOrderDao.findById(order_id);
            // obtain the farmacia
            int farmacia_id = fd_order.getForeign_id();
            log.info("farmacia_Id "+farmacia_id);
            FieldData fd_farmacia = farmaciaDao.findById(farmacia_id);
            Farmacia farmacia = new Farmacia(fd_farmacia.getNome(), fd_farmacia.getId(), fd_farmacia.getLocation());
            pharmacyAssigneds.add(new PharmacyAssigned(farmacia, orderDetail.getQuantity()));

        }
        if (pharmacyAssigneds.isEmpty()) {
            pharmacyAssigneds.addAll(farmaciaDao.findAll().stream().map
                    (farmacia_fd -> new PharmacyAssigned(new Farmacia(farmacia_fd.getAnagrafica_cliente(), farmacia_fd.getId(), farmacia_fd.getLocation()), 0)).toList());
        }
        return pharmacyAssigneds;



    }




}
