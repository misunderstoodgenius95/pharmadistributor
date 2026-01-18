package pharma.DialogController;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.dao.*;
import pharma.javafxlib.test.SimulateEvents;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class SellerOrderHandlerTest {
    @Mock
    private SellerOrderDao s_dao;
    @Mock
    private SellerOrderDetails s_detail;
    @Mock
    private SellerInvoiceDao s_invoice;
    @Mock
    private SellerCreditNoteDao s_credit;
    @Mock
    private SellerCreditNoteDetailDao s_credit_detail;
    @Start
    public void start(Stage stage){
        Scene scene=new Scene(new VBox());
        stage.setScene(scene);
        stage.show();
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    public void ValidtestWIthAll(FxRobot robot){
        when(s_invoice.findByOrderID(anyInt())).thenReturn(FieldData.FieldDataBuilder.getbuilder().setId(10).build());

        when(s_detail.findbyOrderId(eq(2))).
                thenReturn(
                        List.of(FieldData.FieldDataBuilder.getbuilder().setOrder_id(1).setNome("Amuchina 100 mg 20").setPrice(10.90).setVat_percent(10).setQuantity(10).
                                        build(),
                                FieldData.FieldDataBuilder.getbuilder().setId(1).setNome("Voltaren").
                                        setProduction_date(Date.valueOf(LocalDate.of(2026,1,1))).setPrice(4.99).setQuantity(6).setVat_percent(10).
                                        setNome_casa_farmaceutica("Farmacia Mizzo").setTotal(20.11).
                                        build())

                );

        when(s_dao.findAll()).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setId(1).
                setProduction_date(Date.valueOf(LocalDate.of(2026,1,1))).
                        setNome_casa_farmaceutica("Farmacia Lizzo").setTotal(40.11).
                        build(),

                FieldData.FieldDataBuilder.getbuilder().setId(2).
                setProduction_date(Date.valueOf(LocalDate.of(2026,1,1))).
                setNome_casa_farmaceutica("Farmacia Mizzo").setTotal(20.11).
                build()
        ));
        when(s_dao.findById(anyInt())).thenReturn(FieldData.FieldDataBuilder.getbuilder().setId(1).
                setProduction_date(Date.valueOf(LocalDate.of(2026,1,1))).
                setNome_casa_farmaceutica("Farmacia Lizzo").setTotal(40.11).
                build(),FieldData.FieldDataBuilder.getbuilder().setId(1).
                setProduction_date(Date.valueOf(LocalDate.of(2026,1,1))).
                setNome_casa_farmaceutica("Farmacia Mizzo").setTotal(20.11).
                build()
        );
        when(s_dao.findByRangeBetweenAndRagioneSocialeWhereNotExistCreditNote(any(Date.class),any(Date.class),anyString())).thenReturn(List.of(FieldData.FieldDataBuilder.getbuilder().setId(1).
                setProduction_date(Date.valueOf(LocalDate.of(2026,1,1))).
                setNome_casa_farmaceutica("Farmacia Lizzo").setTotal(40.11).
                build(),FieldData.FieldDataBuilder.getbuilder().setId(1).
                setProduction_date(Date.valueOf(LocalDate.of(2026,1,1))).
                setNome_casa_farmaceutica("Farmacia Mizzo").setTotal(20.11).
                build()
        ));



        Platform.runLater(()->{
            SellerOrderControllerBase sellerOrderHandler=new SellerOrderControllerBase("",s_dao,s_detail,s_invoice,s_credit,s_credit_detail);
            sellerOrderHandler.show();
         sellerOrderHandler.getGroup_choice().getToggles().get(2).setSelected(true);
            SimulateEvents.clickOn(sellerOrderHandler.getSearch_btn());
          WaitForAsyncUtils.waitForFxEvents();
        SimulateEvents.setLastRadioBox(sellerOrderHandler.getRadioButtonTableColumn().getRadioMap());
            WaitForAsyncUtils.waitForFxEvents();
            SimulateEvents.clickOn(sellerOrderHandler.getOrderTableView().getButtonOK());


        });
        robot.sleep(9000000);
    }

    @AfterEach
    void tearDown() {
        s_dao=null;
        s_detail=null;
        s_invoice=null;
        s_credit=null;
        s_credit_detail=null;
    }

    @Test
    public void IntegrationTest(FxRobot robot) throws IOException {

Platform.runLater(()-> {
    Properties properties = null;
    try {
        properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    s_dao = new SellerOrderDao(Database.getInstance(properties));
    s_detail = new SellerOrderDetails(Database.getInstance(properties));
    s_invoice = new SellerInvoiceDao(Database.getInstance(properties));
    s_credit_detail = new SellerCreditNoteDetailDao(Database.getInstance(properties));
    s_credit=new SellerCreditNoteDao(Database.getInstance(properties));
    SellerOrderControllerBase sellerOrderHandler = new SellerOrderControllerBase("", s_dao, s_detail, s_invoice, s_credit, s_credit_detail);
    sellerOrderHandler.execute();

});
    robot.sleep(400000);
    }





}