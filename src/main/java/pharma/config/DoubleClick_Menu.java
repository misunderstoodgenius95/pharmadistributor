package pharma.config;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.table.TableRowExpanderColumn;
import org.jetbrains.annotations.Nullable;

public class DoubleClick_Menu<T> {

    private final ContextMenu contextMenu;

    public DoubleClick_Menu(TableView<T> control) {

        contextMenu = new ContextMenu();
        contextMenu.setMaxHeight(60.0);
        contextMenu.setMaxWidth(100.0);
        control.setContextMenu(contextMenu);








    }



    public MenuItem create_menu_item(String voice) {
        MenuItem menuItem = new MenuItem(voice);
        contextMenu.getItems().add(menuItem);
        return menuItem;
    }

    public void hidden_all_item(){
        contextMenu.getItems().forEach(menuItem -> menuItem.setDisable(true));


    }
    public void restore_all_item(){
        contextMenu.getItems().forEach(menuItem -> menuItem.setDisable(false));


    }


}
