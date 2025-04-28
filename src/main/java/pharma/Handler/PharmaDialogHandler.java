package pharma.Handler;



import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import pharma.Model.FieldData;
import pharma.javafxlib.Dialog.CustomDialog;
import pharma.config.PopulateChoice;
import pharma.dao.GenericJDBCDao;
import pharma.dao.PharmaDao;

import java.rmi.AccessException;
import java.util.List;
import java.util.Optional;


public class PharmaDialogHandler extends DialogHandler<FieldData> {
private TextField anagrafica;
private  TextField vat;
private  TextField sigla;
private int id;
private PharmaDao pharmaDao;
private ObservableList<FieldData> obs;
private SimpleObjectProperty<FieldData> fd_property;
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
         fd_property=new SimpleObjectProperty<>();

    }

    @Override
    protected <K> void initialize(Optional<PopulateChoice<K>> PopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData) {

    }


    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().setSigla(sigla.getText()).setPartita_iva(vat.getText()).
                setNome_casa_farmaceutica(anagrafica.getText()).build();
    }


   public void setOperation(Mode mode,FieldData fieldData) throws AccessException {
        if(mode.equals(Mode.Update) && fieldData==null){
            throw  new AccessException("Fieldata is null");

        }
        super.setMode(mode);
        if(mode.equals(Mode.Update)) {
            setUpdate(fieldData);
        }
   }
    private void setUpdate(FieldData fieldData_update){
        anagrafica.setText(fieldData_update.getNome_casa_farmaceutica());
        sigla.setText(fieldData_update.getSigla());
        vat.setText(fieldData_update.getPartita_iva());
        id=fieldData_update.getId();
        sigla.setDisable(true);
        vat.setDisable(true);
      fd_property.set(fieldData_update);


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
            System.out.println("update");

            FieldData fieldData_update_res =
                    FieldData.FieldDataBuilder.getbuilder().
                            setId(id).
                            setNome_casa_farmaceutica(anagrafica.getText()).build();
            success=pharmaDao.update(fieldData_update_res);
            if(success){

            fd_property.get().setNome_casa_farmaceutica(anagrafica.getText());

            }
        }
        return success;

    }


}
