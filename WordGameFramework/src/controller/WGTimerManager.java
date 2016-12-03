package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Created by Jude Hokyoon Woo on 10/29/2016.
 */
public class WGTimerManager {
    private Timeline timeline;

    public void start(Label timerLabel, Integer STARTTIME) {

        IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

        // Configure the Label
        timerLabel.setText(timeSeconds.toString());
        timerLabel.textProperty().bind(timeSeconds.asString());

        // Create and configure the Button
        Button button = new Button();
        button.setText("Start Timer");
        button.setOnAction(event -> {
            if (timeline != null) {
                timeline.stop();
            }
            timeSeconds.set(STARTTIME);
            timeline = new Timeline();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(STARTTIME+1),
                            new KeyValue(timeSeconds, 0)));
            timeline.playFromStart();
        });
    }

}
