package pharma.javafxlib;

import javafx.scene.control.*;

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
        control.setContextMenu(contextMenu);





    }

    public ContextMenu getContextMenu() {
        return contextMenu;
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
