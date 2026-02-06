package pharma.javafxlib.CustomTableView;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TableColumnButtonBase<S> extends TableColumn<S, Void> {

    private String buttonText;
    private Callback<S, Void> action;


    public TableColumnButtonBase(String columnTitle, String buttonText, Callback<S, Void> action) {
        super(columnTitle);
        this.buttonText = buttonText;
        this.action = action;
        setCellFactory(createCellFactory());
    }

    private Callback<TableColumn<S, Void>, TableCell<S, Void>> createCellFactory() {
        return param -> new TableCell<>() {
            private final Button button = new Button(buttonText);

            {
                button.setOnAction(event -> {
                    S item = getTableView().getItems().get(getIndex());

                    action.call(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        };
    }


}