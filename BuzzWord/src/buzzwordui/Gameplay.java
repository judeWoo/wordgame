package buzzwordui;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.WGGUI;
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
        reinitGrid();
        showLines();
        setHighlight();
        initLetter();
    }

    public void layoutGUI(){
        //  timerLabel.textProperty().bind(valueProperty);
        levelLabel.setVisible(true);
        timeLabel.setVisible(true);
        remainingLabel.setVisible(true);
        wordLabel.setVisible(true);
        bottomPlayButton.setVisible(true);
        targetLable.setVisible(true);
        targetPointsLable.setVisible(true);
        scoreBarPane.setVisible(true);
        timeBoxPane.setVisible(true);
        wordBoxPane.setVisible(true);
        targetBoxPane.setVisible(true);
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
        gameLetters[0][1].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.8,1,1);");
        gameLetters[0][2].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.8,1,1);");
        hLettersLines[0][0].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.9,1,1);");
        hLettersLines[0][1].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.9,1,1);");
    }

    @Override
    public void initLetter() {
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
                gameLettersLabel[i][j].setVisible(true);
                gameLettersLabel[i][j].setText("");
            }
        }
        super.initLetter();
        gameLettersLabel[0][2].setText("B");
        gameLettersLabel[3][1].setText("A");
    }

    @Override
    public void drawScoreBox() {
        super.drawScoreBox();
    }

    public void reinitGrid(){
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
                gameLetters[i][j].setFill(Color.valueOf("#979CA9"));
                //gameLetters[i][j].setStyle("-fx-effect: dropshadow(gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
                gameLetters[i][j].setVisible(true);
            }
        }
    }


}
