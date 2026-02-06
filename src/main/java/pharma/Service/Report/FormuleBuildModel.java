package pharma.Service.Report;

import java.util.List;

public class FormuleBuildModel {
    private List<DatiModel> datiModel;
    private String aggregate;

    public FormuleBuildModel() {
    }

    public FormuleBuildModel(List<DatiModel> datiModel, String aggregate) {
        this.datiModel = datiModel;
        this.aggregate = aggregate;
    }

    public String getAggregate() {
        return aggregate;
    }

    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }

    public List<DatiModel> getDatiModel() {
        return datiModel;
    }

    public void setDatiModel(List<DatiModel> datiModel) {
        this.datiModel = datiModel;
    }
}




