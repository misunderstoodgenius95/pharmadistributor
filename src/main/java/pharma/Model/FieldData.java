package pharma.Model;

import javafx.collections.ObservableArray;

import java.io.FileDescriptor;
import java.util.Date;

public class FieldData {
    private  int id;
    private String anagrafica_cliente;
    private String sigla;
    private String partita_iva;
    private String nome;
    private String unit_miusure;
    private String quantity;
    private String description;
    private String principio_attivo;
    private Date date;

    private FieldData(FieldDataBuilder builder) {
        this.anagrafica_cliente = builder.anagrafica_cliente;
        this.partita_iva = builder.partita_iva;
        this.sigla = builder.sigla;
        this.nome = builder.nome;
        this.unit_miusure = builder.unit_miusure;
        this.quantity = builder.quantity;
        this.description = builder.description;
        this.principio_attivo = builder.principio_attivo;
        this.date = builder.date;
        this.id=builder.id;
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
            throw new NullPointerException("Nome nulla!");
        }
        return nome;
    }

    public String getUnit_miusure() {
        if ( unit_miusure == null ) {
            throw new NullPointerException("Unit miusure nulla!");
        }
        return unit_miusure;
    }

    public String getQuantity() {
        if ( quantity == null ) {
            throw new NullPointerException("Quantity nulla!");
        }
        return quantity;
    }

    public String getPrincipio_attivo() {
        if ( principio_attivo == null ) {
            throw new NullPointerException("Principio attivo nulla!");
        }
        return principio_attivo;
    }

    public String getDescription() {
        if ( description == null ) {
            throw new NullPointerException("Description nulla!");
        }
        return description;
    }

    public Date getDate() {
        if ( date == null ) {
            throw new NullPointerException("Date nulla!");
        }
        return date;
    }

    public  static class FieldDataBuilder {
        private String anagrafica_cliente;
        private String sigla;
        private String partita_iva;
        private String nome;
        private String unit_miusure;
        private String quantity;
        private String description;
        private String principio_attivo;
        private Date date;
        private  int id;
public FieldDataBuilder() {

}



        //*
        public static   FieldDataBuilder getbuilder() {
            return new FieldDataBuilder();


        }

        public FieldDataBuilder setId(int id) {
            this.id = id;
            return this;
        }
        public FieldDataBuilder setAnagrafica_cliente(String anagrafica_cliente) {
            this.anagrafica_cliente = anagrafica_cliente;
            return this;
        }

        public FieldDataBuilder setSigla(String sigla) {
            this.sigla = sigla;
            return this;
        }

        public FieldDataBuilder setPartita_iva(String partita_iva) {
            this.partita_iva = partita_iva;
            return this;
        }

        public FieldDataBuilder setNome(String nome) {
            this.nome = nome;
                return  this;
        }

        public FieldDataBuilder setUnit_miusure(String unit_miusure) {
            this.unit_miusure = unit_miusure;
            return this;
        }

        public FieldDataBuilder setQuantity(String quantity) {
            this.quantity = quantity;
            return this;
        }

        public FieldDataBuilder setPrincipio_attivo(String principio_attivo) {
            this.principio_attivo = principio_attivo;
            return this;
        }

        public FieldDataBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public FieldDataBuilder setDescription(String description) {
            this.description = description;
            return this;
        }


        public FieldData build() {
            return new FieldData(this);
        }

    }
}

