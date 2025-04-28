package pharma.Controller.subpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import pharma.Handler.DetailHandler;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.config.Utility;
import pharma.dao.DetailDao;


import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class Dettagli implements Initializable {

     
    @FXML
    private ChoiceBox<String> select_detail;

    @FXML
    private TextField detail_field_1;

    @FXML
    private TextField detail_field_2;
    @FXML
    private ListView<String> list_view;
    private DetailDao detailDao;
    private  DetailHandler detailHandler;

    public Dettagli() {

        try {
            Properties properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            detailDao=new DetailDao(Database.getInstance(properties));


        } catch (IOException e ) {
            throw new RuntimeException(e);
        }
    }
@FXML
    public void change_value(ActionEvent event) {

        detail_field_1.setText("");
        detail_field_2.setText("");
        System.out.println("change action");

   /*     detailHandler.setViewHandler(detail_field_1,detail_field_2, detail_field_3,getChoiceMode(),list_view);*/

        /*if(!select_detail.getSelectionModel().isEmpty()) {

            String mode = select_detail.getSelectionModel().getSelectedItem();
         Utility.set_fieldText(mode,detail_field_1,detail_field_2);
            detailDao.setTable_name(mode);
            List<FieldData> fieldDataList = detailDao.findAll();
            List<String> fd=fieldDataList.stream().
                    map(fieldData-> (mode.equals(Utility.Misura))? (fieldData.getUnit_miusure()+" "+ fieldData.getQuantity()) : fieldData.getNome()
                    ).toList();
            list_view.getItems().addAll(fd);

        }

         */

    }
/*    private String getChoiceMode(){
        return select_detail.getSelectionModel().getSelectedItem();


    }*/
@FXML
    public void btn_send (ActionEvent event) {
        detailHandler.setInsertHandler();
        System.out.println("clicked");
    /*    DetailHandler detailHandler=new DetailHandler(detailDao);
        detailHandler.setInsertHandler(detail_field_1,detail_field_2,detail_field_3,list_view,select_detail.getValue(/));

     */


    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        detailHandler=new DetailHandler(detail_field_1,detail_field_2,detailDao,list_view,select_detail);
        select_detail.setStyle("-fx-font-size: 18px;");
        select_detail.setValue("Seleziona valore");
        select_detail.getItems().addAll(Utility.Categoria,Utility.Principio_attivo,Utility.Tipologia,Utility.Misura);
        list_view.setStyle("-fx-font-size: 18px;");



    }
}
