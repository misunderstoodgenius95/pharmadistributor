package pharma.DialogController;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import pharma.Model.FieldData;
import pharma.config.PopulateChoice;
import pharma.config.Utility;
import pharma.javafxlib.Status;
import pharma.config.View.FarmacoLotsConvert;
import pharma.dao.DetailDao;
import pharma.dao.FarmacoDao;
import pharma.dao.GenericJDBCDao;
import pharma.dao.PharmaDao;

import java.rmi.AccessException;
import java.util.List;
import java.util.Optional;

public class FarmacoDialogControllerBase extends DialogControllerBase<FieldData> {
    private TextField nome;
    private TextField descrizione;
    private SearchableComboBox<FieldData>categoria;
    private SearchableComboBox<FieldData>  tipologia;
    private SearchableComboBox<FieldData> misura;
    private SearchableComboBox<FieldData>principio_Attivo;
    private SearchableComboBox<FieldData> casa_farmaceutica;
    private ObservableList<FieldData> obs;
    private FarmacoDao farmacoDao;
    private Spinner<Integer>spinner_qty;
    private Label add_qty;
    private SimpleIntegerProperty property_pos;
    private SimpleIntegerProperty id_property;
    private SimpleObjectProperty<FieldData> fd_property_update;
    public FarmacoDialogControllerBase(String content, ObservableList<FieldData> obs, FarmacoDao farmacoDao, DetailDao detailDao, PharmaDao pharmaDao) {
        super(content,new PopulateChoice(detailDao,pharmaDao));
        this.obs=obs;
        this.farmacoDao=farmacoDao;
        System.out.println(obs.size());



    }


    @Override
    protected void initialize() {

    }

    @Override
    protected  <K>void  initialize(Optional<PopulateChoice<K>> optionalpopulateChoice, Optional<List<GenericJDBCDao>> optionalgenericJDBCDao, Optional<FieldData> optionalfieldData){
        property_pos=new SimpleIntegerProperty(-1);
        if(optionalpopulateChoice.isPresent()) {
             PopulateChoice populateChoice = optionalpopulateChoice.get();

            nome = add_text_field("Nome");
            descrizione = add_text_field("Descrzione");
            categoria =add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli  Categoria").build());
            categoria.setConverter(new FarmacoLotsConvert());
            categoria.getItems().addAll(populateChoice.populate(Utility.Categoria));
            tipologia = add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli  Tipologia").build());
            tipologia.setConverter(new FarmacoLotsConvert());
            tipologia.getItems().addAll(populateChoice.populate(Utility.Tipologia));
            misura = add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli  Misura").build());

            misura.getItems().addAll(populateChoice.populate(Utility.Misura));
            misura.setConverter(new FarmacoLotsConvert());
            principio_Attivo = add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Principio Attvo").build());
            principio_Attivo.setConverter(new FarmacoLotsConvert());
            principio_Attivo.getItems().addAll(populateChoice.populate(Utility.Principio_attivo));
            casa_farmaceutica = add_SearchComboBox(FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Casa Farmaceutica").build());
            casa_farmaceutica.setConverter(new FarmacoLotsConvert());
            casa_farmaceutica.getItems().addAll(populateChoice.populate("pharma"));
            add_qty=add_label("Inserisci Quantità");
            spinner_qty=add_spinner();
            id_property =new SimpleIntegerProperty(-1);
            property_pos=new SimpleIntegerProperty(-1);
            fd_property_update=new SimpleObjectProperty<>(null);
        }


    }

    public void setUpdate(FieldData fieldData_update){
        nome.setText(fieldData_update.getNome());
        descrizione.setText(fieldData_update.getDescription());
        descrizione.setDisable(true);
        categoria.setValue(FieldData.FieldDataBuilder.getbuilder().setNome(fieldData_update.getNome_categoria()).build());

        categoria.getSelectionModel().selectFirst();

        categoria.setMouseTransparent(true);
        tipologia.setValue(FieldData.FieldDataBuilder.getbuilder().setNome(fieldData_update.getNome_tipologia()).build());
        tipologia.setMouseTransparent(true);
        tipologia.getSelectionModel().selectFirst();
        misura.setValue(FieldData.FieldDataBuilder.getbuilder().setNome(fieldData_update.getUnit_misure()).build());
        misura.setMouseTransparent(true);
        misura.getSelectionModel().selectFirst();
        principio_Attivo.setValue(FieldData.FieldDataBuilder.getbuilder().setNome(fieldData_update.getNome_principio_attivo()).build());
        principio_Attivo.setMouseTransparent(true);
        principio_Attivo.getSelectionModel().selectFirst();
        casa_farmaceutica.setValue(FieldData.FieldDataBuilder.getbuilder().setNome(fieldData_update.getNome_casa_farmaceutica()).build());
        casa_farmaceutica.setMouseTransparent(true);
        casa_farmaceutica.getSelectionModel().selectFirst();
        spinner_qty.getValueFactory().setValue(fieldData_update.getQuantity());
        spinner_qty.setMouseTransparent(true);
        add_qty.setVisible(true);
   id_property.set(fieldData_update.getId());
   property_pos.set(obs.lastIndexOf(fieldData_update));
   fd_property_update.set(fieldData_update);

    }





    @Override
    protected FieldData get_return_data() {
        return FieldData.FieldDataBuilder.getbuilder().setNome(nome.getText()).setDescription(descrizione.getText()).
                setNome_categoria(categoria.getValue().getNome()).
                setCategoria(categoria.getValue().getId()).
                setNome_tipologia(tipologia.getValue().getNome()).
                setTipologia(tipologia.getValue().getId())
                .setMisure(misura.getValue().getId())
                .setUnit_misure( misura.getConverter().toString(misura.getValue()))
                .setPrincipio_attivo(principio_Attivo.getValue().getId()).
                setNome_principio_attivo(principio_Attivo.getValue().getNome()).
                setCasa_Farmaceutica(casa_farmaceutica.getValue().getId()).
                setNome_casa_farmaceutica(casa_farmaceutica.getValue().getNome())
                .setQuantity(spinner_qty.getValue())
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
        if(getMode().equals(Mode.Update)){
            FieldData fieldData_update=FieldData.FieldDataBuilder.getbuilder().setId(id_property.get()).setNome(nome.getText()).build();
            success=farmacoDao.update(fieldData_update);
            System.out.println("after"+success);
            if(success){
           fd_property_update.get().setNome(nome.getText());


            }

        }
        return  success;

    }

    @Override
    protected Status condition_event_status(FieldData type) throws Exception {
        return null;
    }

    public void setOperation(Mode mode,FieldData fieldData) throws AccessException {
        if(mode.equals(Mode.Update) && fieldData==null){
            throw  new AccessException("Fieldata is null");
        }
        super.setMode(mode);
        if(mode.equals(Mode.Update)){
            setUpdate(fieldData);

        }

    }




}
