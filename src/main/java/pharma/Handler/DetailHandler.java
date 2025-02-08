package pharma.Handler;

import javafx.scene.control.*;
import pharma.Model.FieldData;
import pharma.config.InputValidation;
import pharma.config.Utility;
import pharma.dao.DetailDao;

import java.util.List;


public class DetailHandler extends Handler {

    private final DetailDao detailDao;

    private  ListView<String> listView;
    public DetailHandler(DetailDao detailDao) {
        this.detailDao = detailDao;
    }






    public void setInsertHandler(TextField detail_field_1,TextField detail_field_2,ListView<String> listView,String mode) {
        if (InputValidation.filled_text(detail_field_1.getText()) &&
                (detail_field_2.isVisible() ? InputValidation.filled_text(detail_field_2.getText()) : true)) {

            FieldData fieldData = (detail_field_2.isVisible()) ? miusure_FieldData(detail_field_1,detail_field_2) : other_FieldData(detail_field_1);
                detailDao.setTable_name(mode);
                boolean result=detailDao.insert(fieldData);
                if(result) {

                    listView.getItems().add(operator_mapping(fieldData,mode));
                }
                showAlert(result,"valore già inserito");

        }else{
            showAlert(false,"Campi vuoti o non corretti!");
        }
    }


    public void setViewHandler(TextField detail_field_1,TextField detail_field_2,String mode,ListView<String> listView) {

        if (mode!=null) {
            Utility.set_fieldText(mode, detail_field_1, detail_field_2);
            detailDao.setTable_name(mode);
            List<FieldData> fieldDataList = detailDao.findAll();
            System.out.println(fieldDataList.size());
           listView.getItems().addAll(listMapping(fieldDataList,mode));

        }
    }
    private List<String> listMapping(List<FieldData> fd_list,String mode){

            return fd_list.stream().
                    map(fieldData-> operator_mapping(fieldData,mode)
                    ).toList();


        }
        private String operator_mapping (FieldData fieldData,String mode){

         return (mode.equals(Utility.Misura))? (fieldData.getQuantity()+" "+fieldData.getUnit_misure())  : fieldData.getNome();



        }




    private FieldData miusure_FieldData(TextField detail_field_2,TextField detail_field_1){
        return FieldData.FieldDataBuilder.getbuilder()
                .setUnit_misure(detail_field_2.getText())
                .setQuantity(Integer.parseInt(detail_field_1.getText()))
                .build();

    }
    private FieldData other_FieldData(TextField detail_field_1){
        return FieldData.FieldDataBuilder.getbuilder()
                .setNome(detail_field_1.getText())
                .build();

    }




}
