package pharma.javafxlib.Controls.Notification;

import org.json.JSONObject;
import pharma.Model.FieldData;
import pharma.dao.LottiDao;

import java.util.List;

public class JsonNotifyLottoDao extends JsonNotify {
    private LottiDao lottiDao;
    public JsonNotifyLottoDao(String json, List<String> selected_field, String message, String title, LottiDao lottiDao) {
        super(json, selected_field, message, title);
        this.lottiDao=lottiDao;

    }

    @Override
    protected String create_body(JSONObject input) {

        if(!input.has("product_id") && !input.has("lot_id")) {

            throw new IllegalArgumentException("Argument Not Find");
        }

        FieldData fieldData=lottiDao.findByIds(input.getInt("product_id"),input.getString("lot_id")).get();
        String body=super.create_body(input);
        if(fieldData.getNome()!=null && fieldData.getNome_tipologia()!=null && fieldData.getUnit_misure()!=null){

            return body+" "+fieldData.getNome()+" "+fieldData.getNome_tipologia()+" "+fieldData.getUnit_misure();
        }

        return  body;
    }





}
