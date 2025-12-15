package observer;

import javafx.scene.control.Alert;

public class NotificationObserver implements Observer {

    private final String receiver;

    public NotificationObserver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public void update(String message) {
        System.out.println(message);
            javafx.application.Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bildirim");
                alert.setContentText(message);
                alert.show();
            });
    }
}
