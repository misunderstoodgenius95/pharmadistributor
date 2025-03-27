package pharma.config;

import com.auth0.json.mgmt.selfserviceprofiles.SelfServiceProfile;
import pharma.Controller.subpanel.Farmaco;
import pharma.Controller.subpanel.Pharma;
import pharma.Model.FieldData;
import pharma.dao.*;

import java.util.List;

public class PopulateChoice<T> {
    private DetailDao detailDao;
    private PharmaDao pharmaDao;

    private GenericJDBCDao<FieldData,T> generic_dao;
    public PopulateChoice(DetailDao detailDao, PharmaDao pharmaDao) {
        this.detailDao = detailDao;
        this.pharmaDao=pharmaDao;
    }

    public   PopulateChoice(GenericJDBCDao<FieldData,T> generic_dao) {
       this.generic_dao=generic_dao;
    }

    public List<FieldData> populate(String table_name) {

        if (detailDao != null & pharmaDao != null) {
            detailDao.setTable_name(table_name);
            return detailDao.findAll();

        }
        else {
            if(generic_dao==null){
                throw new IllegalArgumentException("Generic dao is null");
            }
             return generic_dao.findAll();

    }

    }



}

