package pharma.config.snippet;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import pharma.Handler.PurchaseCreditNoteHandler;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.dao.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Table {
    private PurchaseCreditNoteDao purchaseCreditNoteDao;
    private PurchaseCreditNoteDetailDao purchaseCreditNoteDetail;
    private PurchaseOrderDetailDao p_detail;
    private Properties properties;
    private PurchaseOrderDao purchaseOrderDao;
    private PurchaseInvoiceDao purchaseInvoiceDao;
    private PharmaDao pharmaDao;
    private TableView<FieldData> table_id;
    public Table() {
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            purchaseInvoiceDao = new PurchaseInvoiceDao(Database.getInstance(properties));
            purchaseOrderDao = new PurchaseOrderDao(Database.getInstance(properties));
            pharmaDao = new PharmaDao(Database.getInstance(properties));
            purchaseCreditNoteDetail=new PurchaseCreditNoteDetailDao(Database.getInstance(properties));
            purchaseCreditNoteDao=new PurchaseCreditNoteDao(Database.getInstance(properties));
            p_detail=new PurchaseOrderDetailDao(Database.getInstance(properties));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public void  row_factory() {


        List<GenericJDBCDao> genericJDBCDaos = new ArrayList<>(Arrays.asList(p_detail, purchaseCreditNoteDetail, purchaseCreditNoteDao));

        genericJDBCDaos.forEach(dao -> System.out.println("Found: " + dao.getClass().getName()));
        PurchaseCreditNoteDetailDao p_credit_dao = (PurchaseCreditNoteDetailDao) genericJDBCDaos.stream().filter(genericJDBCDao -> PurchaseCreditNoteDetailDao.class.isAssignableFrom(genericJDBCDao.getClass())).findFirst().
                orElseThrow(() -> new IllegalArgumentException("Not found PurchaseCreditNoteDetailDao"));
        table_id.setRowFactory(tv-> {
            System.out.println("rowfactory");

                    ContextMenu contextMenu = new ContextMenu();
                    TableRow<FieldData> tableRow = new TableRow<>();
                    MenuItem menuItem = new MenuItem("Aggiungi Nota di Credito.");
               menuItem.setOnAction(event -> {
                FieldData fieldData = tableRow.getItem();
                if(fieldData!=null){
                    assert purchaseCreditNoteDao!=null:"Null";
                    PurchaseCreditNoteHandler purchaseCreditNoteHandler =
                            new PurchaseCreditNoteHandler("Aggiungi Nota di Credito", fieldData, Arrays.asList(p_detail,purchaseCreditNoteDetail, purchaseCreditNoteDao));
                    purchaseCreditNoteHandler.execute();

                }else{
                    System.out.println("fieldata item null");
                }

                    });
                    tableRow.setOnContextMenuRequested(event -> {
                        System.out.println("menu request");
                        FieldData fieldData = tableRow.getItem();

                        if (fieldData != null) {

                            boolean value=purchaseCreditNoteDao.exist_credit_note(fieldData.getId());
                            System.out.println("me");
                            if(!value){
                                System.out.println("execute true");
                              contextMenu.getItems().add(menuItem);

                          }else {

                                System.out.println("nok");
                            }
                            if(!contextMenu.getItems().isEmpty()){
                              contextMenu.show(tableRow, event.getScreenX(), event.getScreenY());


                          }



                        }else{
                            System.out.println("fieldata null");
                        }
                        event.consume();


                    });
                    return tableRow;

                });






  /*          FieldData fieldData=(FieldData) table_id.getSelectionModel().getSelectedItem();
            PurchaseCreditNoteHandler purchaseCreditNoteHandler =
                    new PurchaseCreditNoteHandler("Aggiungi Nota di Credito", fieldData, Arrays.asList(p_detail, purchaseCreditNoteDao, purchaseCreditNoteDetail));
            purchaseCreditNoteHandler.execute();
*/


    }
}
