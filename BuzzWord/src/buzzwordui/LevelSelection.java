package buzzwordui;

import controller.BuzzWordController;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class LevelSelection extends WGGUI{

    BuzzWordController controller = new BuzzWordController();

    public LevelSelection(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
    }
    public LevelSelection(){
        layoutGUI();
        hideCircles();
    }

    public  void  layoutGUI(){
        home.setVisible(true);
        home.setOnMouseClicked(event -> {
            new Home();
        });
        int k = 1;
        int l = 0;
        for (int i = 0; i<selectMode.getItems().size(); i++){
            if (selectMode.getItems().get(i) == selectMode.getValue()) {
                switch (i){
                    case 0:
                        l = controller.getGameData().getaModeLevel();
                        i = selectMode.getItems().size();
                        break;
                    case 1:
                        l = controller.getGameData().getbModeLevel();
                        i = selectMode.getItems().size();
                        break;
                    case 2:
                        l = controller.getGameData().getcModeLevel();
                        i = selectMode.getItems().size();
                        break;
                    case 3:
                        l = controller.getGameData().getdModeLevel();
                        i = selectMode.getItems().size();
                        break;
                }

            }
        }
        for (int i=0; i<2; i++){
            for (int j=0; j<4; j++){
                int finalJ = j;
                int finalI = i;
                if (k <= l){
                    gameLetters[i][j].setFill(Paint.valueOf("#FFFFFF"));
                    gameLettersLabel[i][j].setOnMousePressed(event -> {
                        levelLabel.setText("Level "+gameLettersLabel[finalI][finalJ].getText());
                        new Gameplay();
                    });
                }
                gameLettersLabel[i][j].setText(""+k);
                k++;
            }
        }
        start.setVisible(false);
        selectMode.setVisible(false);
        modeLabel.setVisible(true);
    }
    public void hideCircles(){

        for (int i=2; i<4; i++){
            for (int j=0; j<4; j++){
                gameLetters[i][j].setVisible(false);
                gameLettersLabel[i][j].setVisible(false);
            }
        }
    }
}
