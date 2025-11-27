package pharma.Handler.Report;

import com.dlsc.gemsfx.YearMonthPicker;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.jetbrains.annotations.TestOnly;
import pharma.Model.FieldData;
import pharma.dao.SellerOrderDetails;
import pharma.javafxlib.Dialog.CustomDialog;

public class ReportHandlerBase extends CustomDialog<FieldData> {
    private YearMonthPicker yearMonthPicker;
    private Button button;
    private Label labelHeader;
    private SellerOrderDetails sellerOrderDetails;
    private Label label_result;
    private YearMonthPicker yearMonthPicker2;
    private Label label_description;
    public ReportHandlerBase(String content) {
        super(content);
        label_description=add_label("");
        yearMonthPicker=add_month_picker();
        yearMonthPicker2=add_month_picker();
        button=addButton("Invia");
        labelHeader =add_label("");
        label_result=add_label("");
    }
    public void setHiddenYerMonthPicker2(boolean value){
        yearMonthPicker2.setVisible(!value);
    }

    protected void setLabelHeader(String text) {
      labelHeader.setText(text);
    }

    protected void setLabelResult(String text) {
        label_result.setText(text);
    }


   protected Label getLabel_description() {
        return label_description;
    }

    public YearMonthPicker getYearMonthPicker() {
        return yearMonthPicker;
    }

   public Button getButton() {
        return button;
    }
@TestOnly
    public Label getLabel_result() {
        return label_result;
    }

    public YearMonthPicker getYearMonthPicker2() {
        return yearMonthPicker2;
    }
}
