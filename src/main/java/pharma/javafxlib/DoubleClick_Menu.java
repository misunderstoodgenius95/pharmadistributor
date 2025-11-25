package pharma.javafxlib;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.lang.management.MonitorInfo;

/**
 * Can be create a double click Menu
 * @param <T>
 */
public class DoubleClick_Menu<T> {

    private final ContextMenu contextMenu;

    public DoubleClick_Menu(TableView<T> control) {

        contextMenu = new ContextMenu();
        contextMenu.setMaxHeight(60.0);
        contextMenu.setMaxWidth(100.0);


        control.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (!row.isEmpty()) {
                   row.setContextMenu(contextMenu);


                }
                //event.consume();

            });

            return row;
        });

       //control.setContextMenu(contextMenu);





    }






    public MenuItem create_menu_item(String voice) {
        MenuItem menuItem = new MenuItem(voice);
        contextMenu.getItems().add(menuItem);
        return menuItem;
    }




}
