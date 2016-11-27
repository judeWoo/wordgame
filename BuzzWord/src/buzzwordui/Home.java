package buzzwordui;

import controller.BuzzWordController;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class Home extends WGGUI{

    BuzzWordController controller;

    public Home(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        layoutGUI();
    }

    public Home(){
        initGame();
    }

    public void layoutGUI(){
        login.setVisible(true);
        home.setVisible(false);
        pauseLabel.setVisible(false);
        createProfile.setOnMouseClicked(event -> {
            new CreateProfile();
        });
        arrowPane.setOnMouseClicked(event -> {
            controller = new BuzzWordController();
            controller.logOutRequest();
            initGame();
            createProfile.setVisible(true);
            arrowPane.setVisible(false);
            userButton.setVisible(false);
            login.setVisible(true);
            selectMode.setVisible(false);
            start.setVisible(false);
        });
        login.setOnMouseClicked(event -> {
            new LoginPage();
        });
        start.setOnMouseClicked(event -> {
            if (selectMode.getValue().toString() != "Select Mode")
            {
                modeLabel.setText(selectMode.getValue().toString());
                new LevelSelection();
            }
        });
        if (userButton.getText() != ""){
            login.setVisible(false);
            selectMode.setVisible(true);
            start.setVisible(true);
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

    @Override
    public void initLetter() {
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
                gameLettersLabel[i][j].setVisible(true);
                gameLettersLabel[i][j].setText("");
            }
        }
        super.initLetter();
    }

    public void initGame() {
        layoutGUI();
        hideLines();
        reinitGrid();
        initLetter();
        scoreBarPane.setVisible(false);
        levelLabel.setVisible(false);
        modeLabel.setVisible(false);
        timeLabel.setVisible(false);
        remainingLabel.setVisible(false);
        wordLabel.setVisible(false);
        bottomPlayButton.setVisible(false);
        pauseButtonPane.setVisible(false);
        targetPointsLable.setVisible(false);
        targetLable.setVisible(false);
        timeBoxPane.setVisible(false);
        wordBoxPane.setVisible(false);
        targetBoxPane.setVisible(false);
    }
}
