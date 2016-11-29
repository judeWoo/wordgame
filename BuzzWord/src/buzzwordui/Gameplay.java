package buzzwordui;

import controller.BuzzWordController;
import data.BuzzBoard;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.WGDialogSingleton;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Jude Hokyoon Woo on 11/6/2016.
 */
public class Gameplay extends WGGUI{

    WGTemplate wgTemplate;
    BuzzWordController controller = new BuzzWordController();


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
        setButtonEvent();
        try {
            controller.solveBuzzBoard();
            controller.checkGrid();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
        controller.initBuzzBoard();
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
                gameLettersLabel[i][j].setVisible(true);
                gameLettersLabel[i][j].setText(Character.toString(controller.getBuzzBoard().getLetter(i, j)));
            }
        }
    }

    public void reinitGrid(){
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
                gameLettersLabel[i][j].setOnMousePressed(null);
                gameLetters[i][j].setStyle(null);
                gameLetters[i][j].setFill(Color.valueOf("#979CA9"));
                gameLetters[i][j].setStyle("-fx-effect: dropshadow(gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
                gameLetters[i][j].setVisible(true);
            }
        }
    }

    public void showCircles(){
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
                gameLetters[i][j].setVisible(true);
                gameLettersLabel[i][j].setVisible(true);
            }
        }
    }

    public void setButtonEvent(){
//        BuzzWordController controller = new BuzzWordController();
        bottomPlayButton.setOnMouseClicked(event -> {
            showLines();
            showCircles();
            pauseLabel.setVisible(false);
            pauseButtonPane.setVisible(true);
            bottomPlayButton.setVisible(false);
        });
        pauseButtonPane.setOnMouseClicked(event -> {
            hideCircles();
            hideLines();
            pauseLabel.setVisible(true);
            pauseButtonPane.setVisible(false);
            bottomPlayButton.setVisible(true);
        });
    }

}
