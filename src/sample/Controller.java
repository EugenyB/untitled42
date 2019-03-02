package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Controller {
    @FXML private Label label;
    private Timeline timeline;


    @FXML
    public void initialize() {
        timeline = new Timeline(new KeyFrame(Duration.millis(2000), e->updateLabel()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void updateLabel() {
        LocalTime time = LocalTime.now();
        label.setText(time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        if (time.getSecond()%10<2) {
            Platform.runLater(()-> {
                timeline.stop();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("alert");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.orElse(ButtonType.CANCEL)==ButtonType.OK) {
                    timeline.getKeyFrames().clear();
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), e->updateLabel()));
                } else {
                    timeline.getKeyFrames().clear();
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000), e->updateLabel()));
                }
                timeline.play();
            });
        }
    }
}
