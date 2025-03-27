package pharma.Handler;



import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pharma.Controller.subpanel.Pharma;
import pharma.Model.FieldData;
import pharma.config.CustomDialog;
import pharma.config.PopulateChoice;
import pharma.dao.GenericJDBCDao;
import pharma.dao.PharmaDao;

import javax.xml.transform.Result;
import java.rmi.AccessException;
import java.util.List;
import java.util.Optional;


public class PharmaDialogHandler extends DialogHandler {
private TextField anagrafica;
private  TextField vat;
private  TextField sigla;
private int id;
private PharmaDao pharmaDao;
private ObservableList<FieldData> obs;

    public PharmaDialogHandler(String dialog, PharmaDao pharmaDao,ObservableList<FieldData> obs) {

        super(dialog);

        this.pharmaDao=pharmaDao;
        this.obs=obs;

    }
@Override
   protected void initialize() {

         anagrafica = add_text_field("Inserisci Anagrafia Utente");
         vat = add_text_field_with_validation("Inserisci Partita Iva", CustomDialog.Validation.Vat);
         sigla = add_text_field("Sigla");

    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

    }


    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().setSigla(sigla.getText()).setPartita_iva(vat.getText()).setAnagrafica_cliente(anagrafica.getText()).build();
    }


   public void setOperation(Mode mode,FieldData fieldData) throws AccessException {
        if(mode.equals(Mode.Update) && fieldData==null){
            throw  new AccessException("Fieldata is null");

        }
        super.setMode(mode);
        if(mode.equals(Mode.Update)) {
            setUpUpdate(fieldData);
        }
   }
    private void setUpUpdate(FieldData fieldData_update){
        anagrafica.setText(fieldData_update.getAnagrafica_cliente());
        sigla.setText(fieldData_update.getSigla());
        vat.setText(fieldData_update.getPartita_iva());
        id=fieldData_update.getId();
        sigla.setDisable(true);
        vat.setDisable(true);

    }

    @Override
    protected boolean condition_event(FieldData fieldData) {
        boolean success=false;
        if(getMode().equals(Mode.Insert)){

       success=pharmaDao.insert(fieldData);
            if(success){
             obs.add(fieldData);

            }

        }
        else if(getMode().equals(Mode.Update)){

            FieldData fieldData_update_res =
                    FieldData.FieldDataBuilder.getbuilder().
                            setId(id).
                            setPartita_iva(fieldData.getPartita_iva()).
                            setSigla(fieldData.getSigla()).
                            setAnagrafica_cliente(fieldData.getAnagrafica_cliente()).build();
            success=pharmaDao.update(fieldData_update_res);
            System.out.println(success);
            if(success){
                obs.remove(fieldData);
                obs.add(fieldData);
            }
        }
        return success;

    }


}
