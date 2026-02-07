package pharma.Service.Report;

public class UserFormula {

    private String nome;
    private  String formula;

    public UserFormula(String nome, String formula) {
        this.nome = nome;
        this.formula = formula;
    }

    public String getNome() {
        return nome;
    }

    public String getFormula() {
        return formula;
    }

    @Override
    public String toString() {
        return  formula;
    }
}
