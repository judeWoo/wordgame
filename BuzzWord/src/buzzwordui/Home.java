package buzzwordui;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class Home extends WGGUI{

    Gameplay gameplay;

    public Home(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        layoutGUI();
    }

    public Home(){
        layoutGUI();
        hideLines();
        reinitGrid();
        initLetter();
        levelLabel.setVisible(false);
        modeLabel.setVisible(false);
        scroingTablePane.setVisible(false);
        totalScoreLable.setVisible(false);
        timeLabel.setVisible(false);
        remainingLabel.setVisible(false);
        totalLable.setVisible(false);
        wordLabel.setVisible(false);
        bottomPlayButton.setVisible(false);
        pauseButtonPane.setVisible(false);
        targetPointsLable.setVisible(false);
        targetLable.setVisible(false);
    }

    public void layoutGUI(){
        login.setVisible(true);
        home.setVisible(false);
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
        if (createProfile.getText() != "Create Profile"){
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
