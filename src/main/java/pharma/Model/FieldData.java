package pharma.Model;

import java.util.Date;

public class FieldData {
private String anagrafia_cliente;
private String sigla;
private String partita_iva;
private String nome;
private String unit_miusure;
private  String quantity;
private  String description;
private String principio_attivo;
private Date date;

    public FieldData(String anagrafia_cliente, String partita_iva, String sigla, String nome, String unit_miusure, String quantity, String description, String principio_attivo, Date date) {
        this.anagrafia_cliente = anagrafia_cliente;
        this.partita_iva = partita_iva;
        this.sigla = sigla;
        this.nome = nome;
        this.unit_miusure = unit_miusure;
        this.quantity = quantity;
        this.description = description;
        this.principio_attivo = principio_attivo;
        this.date = date;
    }

    public  static class FieldDataBuilder {

        private String anagrafia_cliente;
        private String sigla;
        private String partita_iva;
        private String nome;
        private String unit_miusure;
        private  String quantity;
        private  String description;
        private String principio_attivo;
        private Date date;

        public void setAnagrafia_cliente(String anagrafia_cliente) {
            this.anagrafia_cliente = anagrafia_cliente;
        }

        public void setSigla(String sigla) {
            this.sigla = sigla;
        }

        public void setPartita_iva(String partita_iva) {
            this.partita_iva = partita_iva;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setUnit_miusure(String unit_miusure) {
            this.unit_miusure = unit_miusure;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public void setPrincipio_attivo(String principio_attivo) {
            this.principio_attivo = principio_attivo;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        public FieldData build() {
         return new FieldData(anagrafia_cliente,partita_iva,sigla,nome,unit_miusure,quantity,description,principio_attivo,date);
        }
    }
}
