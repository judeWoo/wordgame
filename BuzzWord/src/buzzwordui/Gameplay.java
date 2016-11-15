package buzzwordui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ui.WGGUI;
import wgcomponents.WGSpaceComponents;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/6/2016.
 */
public class Gameplay extends WGGUI{

    WGTemplate wgTemplate;

    public Gameplay(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        this.wgTemplate = appTemplate;
    }

    public Gameplay(){
        layoutGUI();
        initGrid();
        showLines();
        setHighlight();
        initLetter();
    }

    public void layoutGUI(){
        //  timerLabel.textProperty().bind(valueProperty);
        levelLabel.setVisible(true);
        scroingTablePane.setVisible(true);
        totalScoreLable.setVisible(true);
        timeLabel.setVisible(true);
        remainingLabel.setVisible(true);
        totalLable.setVisible(true);
        wordLabel.setVisible(true);
        bottomPlayButton.setVisible(true);
        targetLable.setVisible(true);
        targetPointsLable.setVisible(true);
        bottomPlayButton.setOnMouseClicked(event -> {
            pauseButtonPane.setVisible(true);
            bottomPlayButton.setVisible(false);
        });
        pauseButtonPane.setOnMouseClicked(event -> {
            pauseButtonPane.setVisible(false);
            bottomPlayButton.setVisible(true);
        });
    }

    public void showLines(){
        for (int i = 0; i < 3; i++) {
            for (int j=0; j <4; j++){
                vLettersLines[i][j].setVisible(true);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j=0; j <3; j++){
                hLettersLines[i][j].setVisible(true);
            }
        }
    }

    public void setHighlight(){
        gameLetters[0][0].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.8,1,1);");
        vLettersLines[0][0].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.9,1,1);");
    }

    @Override
    public void initLetter() {
        super.initLetter();
        gameLettersLabel[0][2].setText("B");
        gameLettersLabel[3][1].setText("A");
    }
}
