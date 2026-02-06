package pharma.Service.Report;

public class OperazioniModel {
    private  String nome_operazione;
    private String nome_comando;

    public OperazioniModel(String nome_operazione, String nome_comando) {
        this.nome_operazione = nome_operazione;
        this.nome_comando = nome_comando;
    }


    public OperazioniModel(String nome_operazione) {
        this.nome_operazione = nome_operazione;
    }

    public String getNome_operazione() {
        return nome_operazione;
    }

    public String getNome_comando() {
        return nome_comando;
    }
}
