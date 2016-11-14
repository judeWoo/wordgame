package buzzwordui;

import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class LevelSelection extends WGGUI{
    public LevelSelection(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
    }
    public LevelSelection(){
        layoutGUI();
    }

    public  void  layoutGUI(){
        home.setVisible(true);
        home.setOnMouseClicked(event -> {
            new Home();
        });
        int k = 1;

        for (int i=2; i<4; i++){
            for (int j=0; j<4; j++){
                gameLetters[i][j].setVisible(false);
                gameLettersLabel[i][j].setVisible(false);
            }
        }
        for (int i=0; i<2; i++){
            for (int j=0; j<4; j++){
                gameLetters[i][j].setFill(Paint.valueOf("#FFFFFF"));
                gameLetters[i][j].setOnMouseClicked(event -> {
                    new Gameplay();
                });
                gameLettersLabel[i][j].setText(""+k);
                k++;
            }
        }
        start.setVisible(false);
        selectMode.setVisible(false);
        modeLabel.setVisible(true);
    }
}
