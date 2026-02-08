package pharma.Handler;

import javafx.scene.control.Button;
import pharma.DialogController.Report.*;
import pharma.Model.Acquisto;
import pharma.Model.Ordini;
import pharma.dao.CustomFormulaDao;
import pharma.dao.FarmacoDao;
import pharma.dao.PurchaseOrderDao;
import pharma.dao.PurchaseOrderDetailDao;
import pharma.javafxlib.DropDownMenu;

import java.util.List;

public class ReportHandler {
    private DropDownMenu dropDownMenu;
    private Trend trend;
    private SpeseAcquisti acquisti;
    private Variazione variazione;
    private PiccoDialog piccoDialog;
    private DistribuzionePharma distribuzionePharma;
    private ViewFormule viewFormule;
    private CustomFormule customFormule;
    public ReportHandler(PurchaseOrderDao purchaseOrderDao, PurchaseOrderDetailDao p_detail_dao, FarmacoDao farmacoDao, Button report_id, CustomFormulaDao customFormulaDao) {
            dropDownMenu=new DropDownMenu(report_id);

        List<Acquisto> acquistos=p_detail_dao.findAllReportData().stream().map(item->new Acquisto(item.getId(),item.getQuantity(),item.getElapsed_date(),item.getPrice())).toList();
        List<Ordini> ordiniList=purchaseOrderDao.findAll().stream().map(item->new Ordini(item.getOrder_id(),item.getTotal(),item.getProduction_date())).toList();
        variazione=new Variazione("Visualizza variazione",ordiniList);
        piccoDialog =new PiccoDialog("Visualizza Picco",acquistos,farmacoDao);
        distribuzionePharma=new DistribuzionePharma("Visualizza Distribuzione",purchaseOrderDao);
        trend=new Trend("Visualizza Trend",acquistos);
        viewFormule=new ViewFormule("",customFormulaDao,purchaseOrderDao);
        customFormule=new CustomFormule("",purchaseOrderDao,customFormulaDao);

    }

    public void createMenu(){
        dropDownMenu.createItem("Andamento Acquisti Per Mesi ").setOnAction(event -> {
            acquisti.show();
        });
        dropDownMenu.createItem("Variazione Acquisti Tra Mesi").setOnAction(event -> {
            variazione.show();
        });
        dropDownMenu.createItem("Trend").setOnAction(event -> {
            trend.show();
        });
        dropDownMenu.createItem("Picchi").setOnAction(event -> piccoDialog.show());


        dropDownMenu.createItem("Distribuzione").setOnAction(event -> distribuzionePharma.show());
        dropDownMenu.createItem("Crea Formule").setOnAction(event->customFormule.execute());
        dropDownMenu.createItem("Visualizza Formule").setOnAction(event -> viewFormule.show());

    }








}

