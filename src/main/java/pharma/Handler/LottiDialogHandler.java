package pharma.Handler;

import pharma.Model.FieldData;
import pharma.config.PopulateChoice;

public class LottiDialogHandler  extends DialogHandler{
    public LottiDialogHandler(String content, PopulateChoice populateChoice) {
        super(content, populateChoice);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initialize(PopulateChoice populateChoice) {

    }

    @Override
    protected FieldData get_return_data() {
        return null;
    }

    @Override
    protected boolean condition_event(FieldData fieldData) throws Exception {
        return false;
    }
}
