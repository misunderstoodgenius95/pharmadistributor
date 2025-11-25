package pharma.javafxlib;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class DropDownMenu {
    private ContextMenu contextMenu;

    public DropDownMenu(Button button) {
        contextMenu=new ContextMenu();
        button.setCursor(Cursor.HAND);
        button.setOnAction(event -> contextMenu.show(button,javafx.geometry.Side.BOTTOM, 0, 0));
    }
    public MenuItem createItem(String textItem){
        MenuItem menuItem=new MenuItem(textItem);
        contextMenu.getItems().add(menuItem);
        return menuItem;
    }




}
