package pharma.Service.Report;

public class DatiModel {

    private String nome_tabella;
    private String nome_attributo;

    public DatiModel(String nome_tabella, String nome_attributo) {

        this.nome_tabella = nome_tabella;
        this.nome_attributo = nome_attributo;
    }




    public String getNome_tabella() {
        return nome_tabella;
    }

    public String getNome_attributo() {
        return nome_attributo;
    }
}
