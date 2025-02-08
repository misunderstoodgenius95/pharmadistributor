package pharma.config;

import pharma.Controller.subpanel.Pharma;
import pharma.Model.FieldData;
import pharma.dao.DetailDao;
import pharma.dao.PharmaDao;

import java.util.List;

public class PopulateChoice {
    private DetailDao detailDao;
    private PharmaDao pharmaDao;
    public PopulateChoice(DetailDao detailDao, PharmaDao pharmaDao) {
        this.detailDao = detailDao;
        this.pharmaDao=pharmaDao;
    }
    public List<FieldData> populate(String table_name) {
        if (table_name.equals("pharma")) {

           List<FieldData> list_pharma=pharmaDao.findAll();
           return list_pharma;
        } else {
            detailDao.setTable_name(table_name);
            List<FieldData> list = detailDao.findAll();
            return list;
        }
    }



}

