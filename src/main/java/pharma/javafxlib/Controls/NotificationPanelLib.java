package pharma.javafxlib.Controls;



import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;

public class NotificationPanelLib {

    private final NotificationPane notificationPane;
    private final PauseTransition autoHide;

    public NotificationPanelLib(Node content) {
        this.notificationPane = new NotificationPane(content);
        this.notificationPane.setShowFromTop(true); // Only option available
        this.autoHide = new PauseTransition(Duration.seconds(4)); // Default hide after 3s
        this.autoHide.setOnFinished(e -> notificationPane.hide());
    }

    public Region getPane() {
        return notificationPane;
    }

    public void show(String message) {
        notificationPane.setText(message);
        notificationPane.show();
        autoHide.playFromStart();
    }

    public void setAutoHideDuration(Duration duration) {
        this.autoHide.setDuration(duration);
    }

    public void setGraphic(Node graphic) {
        this.notificationPane.setGraphic(graphic);
    }

    public void hide() {
        notificationPane.hide();
    }


}