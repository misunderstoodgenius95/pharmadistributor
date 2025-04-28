package pharma.Handler;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Model.FieldData;
import pharma.config.database.Database;
import pharma.javafxlib.Controls.TextFieldComboBox;
import pharma.config.Utility;
import pharma.dao.LottiDao;
import pharma.dao.PharmaDao;
import pharma.dao.PurchaseOrderDao;
import pharma.dao.PurchaseOrderDetailDao;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import  static  org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class PurchaseOrderHandlerTest {

        private PurchaseOrderDao purchaseOrderDao;

        private PurchaseOrderDetailDao purchaseOrderDetailDao;
        @Mock
        private Database database;
        private LottiDao lottiDao;
        private ObservableList<FieldData> obs_fieldData;
        private SimpleBooleanProperty s_boolean;
        @Mock
        private  ResultSet resultSet;
        @Mock
        private PreparedStatement p_statement;
        @Mock
        private  ResultSet res_insert;
        @Mock
        private  Database d_b_p_o;
        @Mock
        private Database d_b_p_d;
        @Mock
        private  PreparedStatement p_insert;
        @Mock
        private  PreparedStatement p_detail;
        @Mock
        private  ResultSet resultSet_name;
    @Mock ResultSet result_pharma;

        private PharmaDao pharmaDao;
        @BeforeEach
        void setup() throws SQLException {
            MockitoAnnotations.openMocks(this);
            result_pharma=mock(ResultSet.class);
          purchaseOrderDetailDao=new PurchaseOrderDetailDao(database);
          lottiDao=new LottiDao(database,"lotto");
          purchaseOrderDao=new PurchaseOrderDao(database);
          pharmaDao=new PharmaDao(database);
          s_boolean = new SimpleBooleanProperty();

            when(result_pharma.next()).thenReturn(true, true, true, false);
            when(result_pharma.getInt(1)).thenReturn(1,2,3);
            when(result_pharma.getString(2)).thenReturn("Angelini", "Melarini", "Bayer");
            when(database.executeQuery(anyString())).thenReturn(result_pharma);

            // finish pharma
            ArgumentCaptor<Integer> paramCaptor = ArgumentCaptor.forClass(Integer.class);
            when(database.execute_prepared_query(anyString())).thenReturn(p_statement,p_insert,p_detail);
            doNothing().when(p_statement).setInt(anyInt(), paramCaptor.capture());

            when(p_statement.executeQuery()).thenAnswer(invocation -> {

                int argument = paramCaptor.getValue();


                if (argument==1) {
                    when(resultSet.next()).thenReturn(true, true, true, true, false);
                    when(resultSet.getString("id")).thenReturn("agk1", "agk2", "agk3", "agk4");
                    when(resultSet.getDate("production_date")).thenReturn(
                            Date.valueOf(LocalDate.of(2026, 10, 01)),
                            Date.valueOf(LocalDate.of(2024, 10, 01)),
                            Date.valueOf(LocalDate.of(2025, 10, 01)),
                            Date.valueOf(LocalDate.of(2023, 10, 01))
                    );
                    when(resultSet.getDate("elapsed_date")).thenReturn(
                            Date.valueOf(LocalDate.of(2027, 10, 01)),
                            Date.valueOf(LocalDate.of(2028, 10, 01)),
                            Date.valueOf(LocalDate.of(2029, 10, 01)),
                            Date.valueOf(LocalDate.of(2030, 10, 01)));
                    when(resultSet.getString("nome")).thenReturn("Amuchina", "Amuchina","Moment", "Moment_Act", "Tachipirina");
                    when(resultSet.getString("tipologia")).thenReturn("Supposte", "Compresse","Compresse", "Compresse", "Compresse");
                    when(resultSet.getString("misura")).thenReturn("100mg", "10gr", "30gr", "40gr");
                    when(resultSet.getString("casa_farmaceutica")).thenReturn("Angelini");
                    when(resultSet.getInt("farmaco")).thenReturn(1, 2, 3, 4);
                    when(resultSet.getInt("pharma_id")).thenReturn(1);


                }
                 else if (argument==2) {
                    when(resultSet.next()).thenReturn(true, true, true, true, false);
                    when(resultSet.getString("id")).thenReturn("m11", "m22", "m33", "m44");
                    when(resultSet.getDate("production_date")).thenReturn(
                            Date.valueOf(LocalDate.of(2026, 10, 01)),
                            Date.valueOf(LocalDate.of(2024, 10, 01)),
                            Date.valueOf(LocalDate.of(2025, 10, 01)),
                            Date.valueOf(LocalDate.of(2023, 10, 01))
                    );
                    when(resultSet.getDate("elapsed_date")).thenReturn(
                            Date.valueOf(LocalDate.of(2027, 10, 01)),
                            Date.valueOf(LocalDate.of(2028, 10, 01)),
                            Date.valueOf(LocalDate.of(2029, 10, 01)),
                            Date.valueOf(LocalDate.of(2030, 10, 01)));
                    when(resultSet.getString("nome")).thenReturn("m1", "m2","m3", "m4", "m5");
                    when(resultSet.getString("tipologia")).thenReturn("Supposte", "Compresse","Compresse", "Compresse", "Compresse");
                    when(resultSet.getString("misura")).thenReturn("100mg", "10gr", "30gr", "40gr");
                    when(resultSet.getString("casa_farmaceutica")).thenReturn("Melarini");
                    when(resultSet.getInt("farmaco")).thenReturn(1, 2, 3, 4);
                    when(resultSet.getInt("pharma_id")).thenReturn(2);



                }
                return resultSet;
            });


        }

        @Start
        public void start(Stage stage) {
            VBox vBox = new VBox();
            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.show();


        }

        @Test
        public void insertHandler(FxRobot robot) throws SQLException {

            Platform.runLater(()->{



                PurchaseOrderHandler p_hadler=new PurchaseOrderHandler(lottiDao,purchaseOrderDao,purchaseOrderDetailDao,s_boolean,pharmaDao);
                DatePicker datePicker=Utility.extract_value_from_list(p_hadler.getControlList(), DatePicker.class).getFirst();
                datePicker.setValue(LocalDate.of(2025,10,1));
                TextField textField=(TextField) Utility.extract_value_from_list(p_hadler.getControlList(), TextField.class).getFirst();
                textField.setText("m2pp");
                TextFieldComboBox<FieldData> fiel_pharma=(TextFieldComboBox)Utility.extract_value_from_list(p_hadler.getControlList(), TextFieldComboBox.class).getFirst();
                TextFieldComboBox<FieldData> fiel_lots=(TextFieldComboBox)Utility.extract_value_from_list(p_hadler.getVbox().getChildren(), TextFieldComboBox.class).getLast();
             TableView<FieldData> tableView=(TableView<FieldData>) Utility.extract_value_from_list(p_hadler.getControlList(),TableView.class).getFirst();
                fiel_pharma.getChoiceBox().setValue(fiel_pharma.getChoiceBox().getItems().getFirst());
               FieldData fieldData_row=(FieldData) fiel_lots.getChoiceBox().getItems().getFirst();
 fiel_lots.getChoiceBox().getSelectionModel().selectFirst();






              /*  FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setLotto_id("agk1").setNome_casa_farmaceutica("Angelini").
                        setProduction_date(Date.valueOf(LocalDate.of(2025, 01, 10))).setFarmaco_id(100).
                        setElapsed_date(Date.valueOf(LocalDate.of(2028, 01, 10))).setNome("Amuchina").setQuantity(10).setPrice(10.8).
                        setNome_tipologia("Supposte").setUnit_misure("100mg").setVat_percent(10).setCasa_Farmaceutica(1).build();*/
             /*   tableView.setItems(FXCollections.observableArrayList(fd_rows));
                p_hadler.calculus_attribute();*/





                s_boolean.addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if(newValue){
                        System.out.println("oki");
                    }
                });

                Platform.runLater(()->{

                try {
                    when(p_detail.executeUpdate()).thenReturn(1,1);
                    when(p_insert.executeQuery()).thenReturn(res_insert);
                    when(res_insert.next()).thenReturn(true,false);
                    when(res_insert.getInt(1)).thenReturn(100);









                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }});
              //  Platform.runLater(()->SimulateEvents.clickOn(p_hadler.getButtonOK()));
                p_hadler.execute();
                try {
                    verify(p_insert).setDate(1,Date.valueOf(LocalDate.of(2025, 10, 01)));
                    verify(p_insert).setDouble(2,108);
                    verify(p_insert).setDouble(3,10.8);
                    verify(p_insert).setDouble(4,118.80);
                    verify(p_insert).setString(5,"m2pp");
                    verify(p_insert).setInt(6,1);
                    verify(p_detail).setString(1,"agk1");
                    verify(p_detail).setInt(2,1);
                    verify(p_detail).setInt(3,100);
                    verify(p_detail).setDouble(4,10.8);
                    verify(p_detail).setInt(5,10);
                    verify(p_detail).setInt(6,10);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }






















            });
            robot.sleep(36000000);



        }



}