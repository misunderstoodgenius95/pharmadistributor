package pharma.Handler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import pharma.Model.FieldData;
import pharma.config.CustomDialog;
import pharma.config.PopulateChoice;
import pharma.config.Utility;
import pharma.dao.DetailDao;
import pharma.dao.FarmacoDao;
import pharma.dao.PharmaDao;

import java.rmi.AccessException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FarmacoDialogHandler  extends DialogHandler {
    private TextField nome;
    private TextField descrizione;
   private ChoiceBox<FieldData> categoria;
    private ChoiceBox<FieldData>  tipologia;
    private ChoiceBox<FieldData> misura;
    private ChoiceBox<FieldData>principio_Attivo;
    private ChoiceBox<FieldData> casa_farmaceutica;
    private ObservableList<FieldData> obs;
    private FarmacoDao farmacoDao;

    public FarmacoDialogHandler(String content, ObservableList<FieldData> obs, FarmacoDao farmacoDao, DetailDao detailDao, PharmaDao pharmaDao) {
        super(content,new PopulateChoice(detailDao,pharmaDao));
        this.obs=obs;
        this.farmacoDao=farmacoDao;
        System.out.println(obs.size());


    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initialize(PopulateChoice populateChoice){

        nome=add_text_field("Nome");
        descrizione=add_text_field("Descrzione");
        categoria=add_choiceBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Valore").build());
        categoria.getItems().addAll(populateChoice.populate(Utility.Categoria));
        tipologia=add_choiceBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Valore").build());
        tipologia.getItems().addAll(populateChoice.populate(Utility.Tipologia));
        misura=add_choiceBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Valore").build());
        misura.getItems().addAll(populateChoice.populate(Utility.Misura));
        principio_Attivo=add_choiceBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Valore").build());
        principio_Attivo.getItems().addAll(populateChoice.populate(Utility.Principio_attivo));
        casa_farmaceutica=add_choiceBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Valore").build());
        casa_farmaceutica.getItems().addAll(populateChoice.populate("pharma"));

    }
    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().setNome(nome.getText()).setDescription(descrizione.getText()).
                setNome_categoria(categoria.getValue().getNome()).
                setCategoria(categoria.getValue().getId()).
                setNome_tipologia(tipologia.getValue().getNome()).
                setTipologia(tipologia.getValue().getId())
                .setMisure(misura.getValue().getId())
                .setUnit_misure( misura.getValue().getQuantity()+" "+misura.getValue().getUnit_misure())
                .setPrincipio_attivo(principio_Attivo.getValue().getId()).
                setNome_principio_attivo(principio_Attivo.getValue().getNome()).
                setCasa_Farmaceutica(casa_farmaceutica.getValue().getId()).
                setNome_casa_farmaceutica(casa_farmaceutica.getValue().getNome())
                .build();
    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        boolean success = false;
        if(getMode().equals(Mode.Insert)){

            success=farmacoDao.insert(fieldData);
            if(success){

                obs.add(fieldData);
            }
        }
        return  success;

    }
    public void setOperation(Mode mode,FieldData fieldData) throws AccessException {
        if(mode.equals(Mode.Update) && fieldData==null){
            throw  new AccessException("Fieldata is null");

        }
        super.setMode(mode);

    }




}
