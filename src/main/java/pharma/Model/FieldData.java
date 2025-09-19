package pharma.Model;



import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.postgis.jdbc.PGgeometry;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


public class FieldData {

    private  int id;
    private String anagrafica_cliente;
    private String sigla;
    private String partita_iva;
    private String nome;
    private String unit_misure;
    private IntegerProperty quantity;
    private String description;
    private int principio_attivo;
    private  int categoria;
    private  int tipologia;
    private  int misure;
    private  int casa_farmaceutica;
    private  String nome_categoria;
    private  String nome_tipologia;
    private String nome_principio_attivo;
    private  String nome_casa_farmaceutica;
    private Date production_date;
    private  Date elapsed_date;
    private SimpleDoubleProperty price;
    private String code;
    private  SimpleIntegerProperty vat_percent;
    private List<FieldData> fieldDataList;
    private  double subtotal;
    private double vat_amount;
    private double total;
    private  String original_order_id;
    private  String nome_farmaco;
    private  int farmaco_id;
    private  int order_id;
    private  String invoice_number;
    private String payment_mode;
    private  int invoice_id;
    private Timestamp created_at;
    private  String credit_note_number;
    private String motive;
    private  String street;
    private int cap;
    private  String province;
    private  String comune;
    private  UUID uuid;
    private int availability;
    private String email;
    private String role;
    private boolean is_enable;
    private double latitude;
    private double longitude;
    private double lunghezza;
    private  double altezza;
    private double profondita;
    private  double spessore;
    private int  num_rip;
    private int  capacity;
    private PGgeometry location;
    private int foreign_id;
    private String last_name;
    private String warehouse_name;
    private String shelf_code;
    private int shelves_code;

    private FieldData(FieldDataBuilder builder) {
        this.anagrafica_cliente = builder.anagrafica_cliente;
        this.partita_iva = builder.partita_iva;
        this.sigla = builder.sigla;
        this.nome = builder.nome;
        this.unit_misure = builder.unit_misure;
        this.quantity = new SimpleIntegerProperty(builder.quantity);
        this.description = builder.description;
        this.principio_attivo = builder.principio_attivo;
        this.id=builder.id;
        this.categoria=builder.categoria;
        this.tipologia=builder.tipologia;
        this.misure=builder.misure;
        this.casa_farmaceutica = builder.casa_farmaceutica;
        this.nome_categoria=builder.nome_categoria;
        this.nome_tipologia=builder.nome_tipologia;
        this.nome_principio_attivo= builder.nome_principio_attivo;
        this.nome_casa_farmaceutica=builder.nome_casa_farmaceutica;
        this.elapsed_date=builder.elapsed_date;
        this.production_date=builder.production_date;
        this.price= new SimpleDoubleProperty(builder.price);
        this.code=builder.code;
        this.vat_percent=new SimpleIntegerProperty(builder.vat_percent);
        this.fieldDataList= builder.fieldDataList;
        this.subtotal=builder.subtotal;
        this.vat_amount=builder.vat_amount;
        this.total=builder.total;
        this.original_order_id=builder.original_order_id;
        this.nome_farmaco=builder.nome_farmaco;
        this.farmaco_id=builder.farmaco_id;
        this.order_id =builder.order_id;
        this.invoice_number=builder.invoice_number;
        this.payment_mode=builder.payment_mode;
        this.invoice_id=builder.invoice_id;
        this.created_at=builder.created_at;
        this.motive=builder.motive;
        this.credit_note_number=builder.credit_note_number;
        this.street=builder.street;
        this.cap=builder.cap;
        this.province=builder.province;
        this.comune=builder.comune;
        this.uuid=builder.uuid;
        this.availability=builder.availability;
        this.email=builder.email;
        this.role=builder.role;
        this.is_enable= builder.is_enable;
        this.latitude=builder.latitude;
        this.longitude=builder.longitude;
        this.lunghezza=builder.lunghezza;
        this.altezza=builder.altezza;
        this.profondita=builder.profondita;
        this.spessore=builder.spessore;
        this.num_rip=builder.num_rip;
        this.capacity=builder.capacity;
        this.location=builder.location;
        this.foreign_id=builder.foreign_id;
        this.last_name=builder.last_name;

    }

    public PGgeometry getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getLunghezza() {
        return lunghezza;
    }

    public double getAltezza() {
        return altezza;
    }

    public double getProfondita() {
        return profondita;
    }

    public double getSpessore() {
        return spessore;
    }

    public int  getNum_rip() {
        return num_rip;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean isIs_enable() {
        return is_enable;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getAvailability() {
        return availability;
    }

    public String getProvince() {
        return province;
    }

    public String getComune() {
        return comune;
    }

    public int getCap() {
        return cap;
    }

    public String getStreet() {
        return street;
    }

    public void setNome(String nome) {
        if(nome==null){
            throw  new IllegalArgumentException("Permitting only edit!");
        }
        this.nome = nome;
    }

    public int getForeign_id() {
        return foreign_id;
    }

    public String getMotive() {
        return motive;
    }

    public String getCredit_note_number() {
        return credit_note_number;
    }

    public void setNome_casa_farmaceutica(String nome_casa_farmaceutica) {
        if(nome_casa_farmaceutica==null){
            throw  new IllegalArgumentException("Permitting only edit!");
        }
        this.nome_casa_farmaceutica = nome_casa_farmaceutica;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setVat_amount(double vat_amount) {
        this.vat_amount = vat_amount;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public int getFarmaco_id() {
        return farmaco_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public List<FieldData> getFieldDataList() {
        return this.fieldDataList;
    }

    public void setFieldDataList(List<FieldData> fieldDataList) {
        this.fieldDataList = fieldDataList;
    }


    public String getNome_farmaco() {
        return nome_farmaco;
    }

    public  FieldData getElementFieldDataList(int index) {
        return fieldDataList.get(index);
    }


    public String getOriginal_order_id() {
        return original_order_id;
    }

    public String getCode() {
        return code;
    }

    public double getTotal() {
        return total;
    }

    public double getVat_amount() {
        return vat_amount;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setVat_percent(int vat_percent) {
        this.vat_percent.set(vat_percent);
    }

    public void setPrice(double price) {
        this.price.set(price);

    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getShelf_code() {
        return shelf_code;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public int getShelves_code() {
        return shelves_code;
    }

    public int getVat_percent() {


          if(vat_percent ==null){
            throw new IllegalArgumentException("nessun valore");
          }

        return vat_percent.get();
    }

    public String getLast_name() {
        return last_name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Double getPrice() {
        if(price==null){
            throw new IllegalArgumentException("nessun valore");
        }
        return price.get();
    }

    public Date getProduction_date() {
        return production_date;
    }

    public Date getElapsed_date() {
        return elapsed_date;
    }

    public String getNome_categoria() {
        return nome_categoria;
    }

    public String getNome_tipologia() {
        return nome_tipologia;
    }

    public String getNome_principio_attivo() {
        return nome_principio_attivo;
    }

    public String getNome_casa_farmaceutica() {
        return nome_casa_farmaceutica;
    }

    public int getCasa_farmaceutica() {
        return casa_farmaceutica;
    }

    public int getCategoria() {
        return categoria;
    }

    public int getTipologia() {
        return tipologia;
    }

    public int getMisure() {
        return misure;
    }

    public String getAnagrafica_cliente() {
       if ( anagrafica_cliente == null ) {
           throw new IllegalArgumentException("Anagrafia cliente nulla!");
       }
        return anagrafica_cliente;
    }

    public int getId() {

        return id;
    }

    public String getSigla() {
        if ( sigla == null ) {
            throw new NullPointerException("Sigla nulla!");
        }
        return sigla;
    }

    public String getPartita_iva() {
        if ( partita_iva == null ) {
            throw new NullPointerException("Partita iva nulla!");
        }
        return partita_iva;
    }

    public String getNome() {

        return nome;
    }

    public String getUnit_misure() {
        if ( unit_misure == null ) {
            throw new NullPointerException("Unit miusure nulla!");
        }
        return unit_misure;
    }

    public int  getQuantity() {
        if(quantity==null) {
            throw new IllegalArgumentException("nessun valore");
        }
            return quantity.get();

    }

    /*@Override
    public String toString() {
       if((nome!=null) && (description!=null) &&(nome_categoria!=null) &&( nome_tipologia!=null)
                &&(unit_misure!=null) && (nome_principio_attivo!=null) && (nome_casa_farmaceutica!=null )){
            return nome +' ' +
                    nome_tipologia + ' ' +
                    nome_casa_farmaceutica + ' ' + unit_misure + ' ' ;



        }  else if( unit_misure!=null){
            return getMisure()+" "+getUnit_misure();
        }

        else if(nome!=null){
            return getNome();
        }else{
            return "";
       }
    }*/

    public int  getPrincipio_attivo() {

        return principio_attivo;
    }

    public String getDescription() {
        if ( description == null ) {
            throw new NullPointerException("Description nulla!");
        }
        return description;
    }


    public  static class FieldDataBuilder {
        private String anagrafica_cliente;
        private String sigla;
        private String partita_iva;
        private String nome;
        private String unit_misure;
        private int misure;
        private int  quantity;
        private  String  description;
        private  int categoria;
        private  int tipologia;
        private int principio_attivo;
        private Date production_date;
        private  Date elapsed_date;
        private  int id;
        private  int casa_farmaceutica;
        private  String nome_categoria;
        private  String nome_tipologia;
        private String nome_principio_attivo;
        private  String nome_casa_farmaceutica;
        private  double price;
        private String code;
        private int  vat_percent;
        private List<FieldData> fieldDataList;
        private  double subtotal;
        private double vat_amount;
        private double total;
        private  String original_order_id;
        private  String nome_farmaco;
        private  int farmaco_id;
        private int order_id;
        private  String invoice_number;
        private String payment_mode;
        private  int invoice_id;
        private Timestamp created_at;
        private  String credit_note_number;
        private String motive;
        private  String street;
        private int cap;
        private  String province;
        private  String comune;
        private  UUID uuid;
        private int availability;
        private String email;
        private String role;
        private boolean is_enable;
        private double latitude;
        private double longitude;
        private double lunghezza;
        private  double altezza;
        private double profondita;
        private  double spessore;
        private int  num_rip;
        private int  capacity;
        private PGgeometry location;
        private  int foreign_id;
        private String last_name;
        private String warehouse_name;
        private String shelf_code;
        private int shelves_code;
        private FieldDataBuilder() {

        }

        public FieldDataBuilder setWarehouse_name(String warehouse_name) {
            this.warehouse_name = warehouse_name;
            return this;
        }

        public FieldDataBuilder setShelf_code(String shelf_code) {
            this.shelf_code = shelf_code;
            return this;
        }

        public FieldDataBuilder setShelves_code(int shelves_code) {
            this.shelves_code = shelves_code;
            return this;
        }

        public FieldDataBuilder setLast_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public FieldDataBuilder setLocation(PGgeometry location) {
            this.location = location;
            return this;
        }

        public FieldDataBuilder setCapacity(int capacity) {
            this.capacity = capacity;
            return  this;
        }

        public FieldDataBuilder setNum_rip(int  num_rip) {
            this.num_rip = num_rip;
            return this;
        }

        public FieldDataBuilder setAltezza(double altezza) {
            this.altezza = altezza;
            return  this;
        }

        public FieldDataBuilder setLunghezza(double lunghezza) {
            this.lunghezza = lunghezza;
            return  this;
        }

        public FieldDataBuilder setSpessore(double spessore) {
            this.spessore = spessore;
            return  this;
        }

        public FieldDataBuilder setProfondita(double profondita) {
            this.profondita = profondita;
            return  this;
        }

        //*
        public static   FieldDataBuilder getbuilder() {
            return new FieldDataBuilder();


        }

        public FieldDataBuilder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public FieldDataBuilder setLongitude(double longitude) {
            this.longitude = longitude;
            return  this;
        }

        public FieldDataBuilder setIs_enable(boolean is_enable) {
            this.is_enable = is_enable;
            return this;
        }

        public FieldDataBuilder setRole(String role) {
            this.role = role;
            return this;
        }

        public FieldDataBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public FieldDataBuilder setAvailability(int availability) {
            this.availability = availability;
            return  this;
        }

        public FieldDataBuilder setUUid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public FieldDataBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        public FieldDataBuilder setCap(int cap) {
            this.cap = cap;
            return this;
        }

        public FieldDataBuilder setComune(String comune) {
            this.comune = comune;
            return  this;
        }

        public FieldDataBuilder setProvince(String province) {
            this.province = province;
            return this;
        }

        public FieldDataBuilder setCreated_at(Timestamp created_at) {
            this.created_at = created_at;
            return  this;
        }

        public FieldDataBuilder setInvoice_id(int invoice_id) {
            this.invoice_id = invoice_id;
            return  this;
        }

        public FieldDataBuilder setMotive(String motive) {
            this.motive = motive;
            return  this;
        }

        public FieldDataBuilder setCredit_note_number(String credit_note_number) {
            this.credit_note_number = credit_note_number;
            return  this;
        }

        public FieldDataBuilder setInvoice_number(String invoice_number) {
            this.invoice_number = invoice_number;
            return  this;
        }

        public FieldDataBuilder setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
            return  this;
        }

        public FieldDataBuilder setOrder_id(int order_id) {
            this.order_id = order_id;
            return this;
        }

        public FieldDataBuilder setFarmaco_id(int farmaco_id) {
            this.farmaco_id = farmaco_id;
            return  this;
        }

        public FieldDataBuilder setOriginal_order_id(String original_order_id) {
            this.original_order_id = original_order_id;
            return  this;
        }

        public FieldDataBuilder setNome_farmaco(String nome_farmaco) {
            this.nome_farmaco = nome_farmaco;
            return  this;
        }

        public FieldDataBuilder setVat_amount(double vat_amount) {

            this.vat_amount = vat_amount;
            return  this;
        }

        public FieldDataBuilder setTotal(double total) {
            this.total = total;
            return  this;
        }

        public FieldDataBuilder setSubtotal(double subtotal) {
            this.subtotal = subtotal;
            return  this;
        }

        public FieldDataBuilder setFieldDataList(FieldData fieldData) {
            this.fieldDataList.add(fieldData);
            return this;

        }
        public FieldDataBuilder setFieldDataListAll(List<FieldData> fieldDataList) {
          this.fieldDataList=fieldDataList;
            return this;

        }



        public FieldDataBuilder setPrice(Double price) {
            this.price=price;
            return this;
        }

        public FieldDataBuilder setVat_percent(int vat_percent) {

            this.vat_percent=vat_percent;
            return this;
        }

        public FieldDataBuilder setcode(String code) {
            this.code = code;
            return this;
        }

        public FieldDataBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public FieldDataBuilder setForeign_id(int foreign_id) {
            this.foreign_id = foreign_id;
            return this;
        }

        public FieldDataBuilder setNome_categoria(String nome_categoria) {
            this.nome_categoria = nome_categoria;
            return  this;
        }

        public FieldDataBuilder  setNome_principio_attivo(String nome_principio_attivo) {
            this.nome_principio_attivo = nome_principio_attivo;
            return  this;
        }

        public  FieldDataBuilder setNome_casa_farmaceutica(String nome_casa_farmaceutica) {
            this.nome_casa_farmaceutica = nome_casa_farmaceutica;
            return  this;
        }

        public FieldDataBuilder  setNome_tipologia(String nome_tipologia) {
            this.nome_tipologia = nome_tipologia;
            return  this;
        }


        public FieldDataBuilder setCasa_Farmaceutica(int casa_farmaceutica) {
            this.casa_farmaceutica=casa_farmaceutica;
            return this;
        }
        public FieldDataBuilder setAnagrafica_cliente(String anagrafica_cliente) {
            this.anagrafica_cliente = anagrafica_cliente;
            return this;
        }

        public FieldDataBuilder setTipologia(int tipologia) {
            this.tipologia = tipologia;
            return  this;
        }

        public FieldDataBuilder setMisure(int misure) {
            this.misure = misure;
            return  this;
        }

        public FieldDataBuilder setCategoria(int categoria) {
            this.categoria = categoria;
            return  this;
        }

        public FieldDataBuilder setSigla(String sigla) {
            this.sigla = sigla;
            return this;
        }

        public FieldDataBuilder setPartita_iva(String partita_iva) {
            this.partita_iva = partita_iva;
            return this;
        }

        public  FieldDataBuilder setNome(String nome) {
            this.nome = nome;
                return  this;
        }

        public FieldDataBuilder setUnit_misure(String unit_misure) {
            this.unit_misure = unit_misure;
            return this;
        }

        public FieldDataBuilder setQuantity(int  quantity) {

           this.quantity=quantity;
            return this;
        }

        public FieldDataBuilder setPrincipio_attivo(int  principio_attivo) {
            this.principio_attivo = principio_attivo;
            return this;
        }

        public FieldDataBuilder  setElapsed_date(Date elapsed_date) {
            this.elapsed_date = elapsed_date;
            return  this;
        }

        public FieldDataBuilder setProduction_date(Date production_date) {
            this.production_date = production_date;
            return  this;
        }

        public FieldDataBuilder setDescription(String  description) {
            this.description = description;
            return this;
        }


        public FieldData build() {
            return new FieldData(this);
        }

    }
}

