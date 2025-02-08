package pharma.Model;


import java.sql.Date;


public class FieldData {
    private  int id;
    private String anagrafica_cliente;
    private String sigla;
    private String partita_iva;
    private String nome;
    private String unit_misure;
    private int quantity;
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
    private double price;
    private FieldData(FieldDataBuilder builder) {
        this.anagrafica_cliente = builder.anagrafica_cliente;
        this.partita_iva = builder.partita_iva;
        this.sigla = builder.sigla;
        this.nome = builder.nome;
        this.unit_misure = builder.unit_misure;
        this.quantity = builder.quantity;
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
        this.price= builder.price;
    }


    public double getPrice() {
        return price;
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
           throw new NullPointerException("Anagrafia cliente nulla!");
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
        if ( nome == null ) {
            throw new NullPointerException("Nome nullo!");
        }
        return nome;
    }

    public String getUnit_misure() {
        if ( unit_misure == null ) {
            throw new NullPointerException("Unit miusure nulla!");
        }
        return unit_misure;
    }

    public int  getQuantity() {

        return quantity;
    }

    @Override
    public String toString() {
        if( unit_misure!=null){
            return getQuantity()+" "+getUnit_misure();

        }else{
            return getNome();
        }
    }

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
        int  quantity;
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
        private double price;

        public FieldDataBuilder() {

        }



        //*
        public static   FieldDataBuilder getbuilder() {
            return new FieldDataBuilder();


        }

        public FieldDataBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public FieldDataBuilder setId(int id) {
            this.id = id;
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
            this.quantity = quantity;
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

