package buzzwordui;

import controller.BuzzWordController;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import ui.WGDialogSingleton;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class LevelSelection extends WGGUI {

    BuzzWordController controller = new BuzzWordController();
    public static int targetScore;


    public LevelSelection(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
    }

    public LevelSelection() {
        layoutGUI();
        hideCircles();
    }

    //for controller
    public LevelSelection(BuzzWordController controller) {

    }

    public void layoutGUI() {
        WGDialogSingleton wgDialogSingleton = WGDialogSingleton.getSingleton();
        home.setVisible(true);
        pauseLabel.setVisible(false);
        profileSetting.setVisible(false);
        home.setOnMouseClicked(event -> {
            wgDialogSingleton.show("Back Home?", "Press Enter for Go Home OR Press ESC for cancel.");
            if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                new Home();
            }
            if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {
                //Do NOTHING
            }
        });
        int k = 1;
        int l = 0;
        for (int i = 0; i < selectMode.getItems().size(); i++) {
            if (selectMode.getItems().get(i) == selectMode.getValue()) {
                switch (i) {
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
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                int finalJ = j;
                int finalI = i;
                if (k <= l) {
                    gameLetters[i][j].setFill(Paint.valueOf("#FFFFFF"));
                    gameLettersLabel[i][j].setOnMousePressed(event -> {
                        targetScore = controller.setTargetScore(gameLettersLabel[finalI][finalJ].getText());
                        targetPointsLable.setText(controller.setTargetScore(gameLettersLabel[finalI][finalJ].getText()) + " points");
                        levelLabel.setText("Level " + gameLettersLabel[finalI][finalJ].getText());
                        new Gameplay();
                    });
                }
                gameLettersLabel[i][j].setText("" + k);
                k++;
            }
        }
        exitLine1.setOnMouseClicked(event -> {
            wgDialogSingleton.show("Exit?", "Press Enter for exit OR Press ESC for go back.");
            if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                System.exit(0);
            }
            if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {
            }
        });
        exitLine2.setOnMouseClicked(event -> {
            wgDialogSingleton.show("Exit?", "Press Enter for exit OR Press ESC for go back.");
            if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                System.exit(0);
            }
            if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {
            }
        });

        start.setVisible(false);
        selectMode.setVisible(false);
        modeLabel.setVisible(true);
    }

    @Override
    public void hideCircles() {
        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameLetters[i][j].setVisible(false);
                gameLettersLabel[i][j].setVisible(false);
            }
        }
    }

    public static int getTargetScore() {
        return targetScore;
    }

    public static void setTargetScore(int targetScore) {
        LevelSelection.targetScore = targetScore;
    }
}
