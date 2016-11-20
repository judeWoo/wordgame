package buzzwordui;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class Home extends WGGUI{

    public Home(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        layoutGUI();
    }

    public Home(){
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

    public void layoutGUI(){
        login.setVisible(true);
        home.setVisible(false);
        createProfile.setOnMouseClicked(event -> {
            new CreateProfile();
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

    public void hideLines(){
        for (int i = 0; i < 3; i++) {
            for (int j=0; j <4; j++){
                vLettersLines[i][j].setVisible(false);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j=0; j <3; j++){
                hLettersLines[i][j].setVisible(false);
            }
        }
    }
    public void reinitGrid(){
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
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

}
