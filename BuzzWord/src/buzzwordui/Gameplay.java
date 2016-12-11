package buzzwordui;

import controller.BuzzWordController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import ui.WGDialogSingleton;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Jude Hokyoon Woo on 11/6/2016.
 */
public class Gameplay extends WGGUI {

    WGTemplate wgTemplate;
    BuzzWordController controller = new BuzzWordController();
    int counter = 0; //for the count of keystroke
    EventHandler filter = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            primaryScene.startFullDrag();
        }
    };
    private BooleanProperty dragging = new ReadOnlyBooleanWrapper(false);
    private BooleanProperty keyInputting = new ReadOnlyBooleanWrapper(false);

    public Gameplay(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        this.wgTemplate = appTemplate;
    }

    public Gameplay() {
        layoutGUI();
        reinitGrid();
        setButtonEvent();
        setExitButtonEvent();
        try {
            controller.solveBuzzBoard();
            controller.checkGrid();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
//        controller.initBuzzBoardTest();
        initLetter();
        setHightLight(gameLettersLabel, gameLetters, vLettersLines, hLettersLines);
        setHelpHomeReplayNextEvent();
    }

    public void layoutGUI() {
        pauseLabel.setVisible(false);
        levelLabel.setVisible(true);
        timeLabel.setVisible(true);
        timerLabel.setVisible(true);
        remainingLabel.setVisible(true);
        bottomPlayButton.setVisible(true);
        targetLable.setVisible(true);
        targetPointsLable.setVisible(true);
        scoreBarPane.setVisible(true);
        timeBoxPane.setVisible(true);
        wordBoxPane.setVisible(true);
        targetBoxPane.setVisible(true);
    }

    public void showLines() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                vLettersLines[i][j].setVisible(true);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                hLettersLines[i][j].setVisible(true);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                dRLettersLines[i][j].setVisible(true);
            }
        }

        for (int i = 0; i < 4 - 1; i++) {
            for (int j = 1; j < 4; j++) {
                dLLettersLines[i][j - 1].setVisible(true);
            }
        }
    }

//    public void setinitHighlight() {
//        hLettersLines[0][0].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
//        hLettersLines[0][1].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
//    }

    public void setHightLight(Label[][] gameLettersLabel, Circle[][] gameLetters, Line[][] hLettersLines, Line[][] vLettersLines) {
        //Do not forget to erase primaryscene event!
        primaryScene.addEventFilter(MouseEvent.DRAG_DETECTED, filter);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int finalI = i;
                int finalJ = j;
                gameLettersLabel[i][j].setOnMouseEntered(event -> {
                    gameLettersLabel[finalI][finalJ].setCursor(Cursor.HAND);
                });
                gameLettersLabel[i][j].setOnMouseDragEntered(event -> {
                    if (!keyInputting.get()) {
                        dragging.set(true);
                        if (controller.checkVisitied(finalI, finalJ)) {
                            if (controller.checkMouseDrag(finalI, finalJ)) {
                                controller.makeRightGridIndex(BuzzWordController.getBuzzBoard().getLetter(finalI, finalJ));
                                gameLetters[finalI][finalJ].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.8,1,1);");
                            }
                        }
                    }
                });
                primaryScene.setOnMouseDragReleased(event -> {
                    if (!keyInputting.get()) {
                        controller.checkRightGrid();
                        totalScoreLabel.setText(controller.changeTotalScore() + "");
                        BuzzWordController.initVisited();
                        clearHighlight();
                        controller.removeRightGridIndex();
                        if (controller.changeTotalScore() >= controller.setTargetScore(BuzzWordController.getGameLevel())) {
                            controller.saveGameRequest();
                            controller.end(filter);
                            startNextLevel.setVisible(true);
                        }
                        dragging.set(false);
                    }
                });
            }
        }
        primaryScene.setOnKeyTyped(event -> {
            if (!dragging.get()) {
                if (event.getCharacter().matches("[a-zA-z]")) {
                    keyInputting.set(true);
                    String guess = event.getCharacter().toLowerCase();
                    clearHighlight();
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (i == 0 && j == 0) {
                                counter = 0;
                            }
                            if (controller.getGamestate().equals(BuzzWordController.GameState.STARTED)
                                    && guess.equals(Character.toString(BuzzWordController.getBuzzBoard().getLetter(i, j)).toLowerCase())) {
                                controller.makeRightKeyGridIndex(BuzzWordController.getBuzzBoard().getLetter(i, j), counter);
                                if (!controller.checkKeyVisitied(i, j, counter)) {
                                    String word = BuzzWordController.getBuzzBoard().toString();
                                    if (word.indexOf(guess.toUpperCase(),
                                            word.indexOf(guess.toUpperCase()) + 1) <= -1) {
                                        BuzzWordController.getLetters().clear();
                                        controller.makeRightKeyGridIndex(BuzzWordController.getBuzzBoard().getLetter(i, j), counter);
                                        controller.removeRightGridIndex();
                                        BuzzWordController.initRecorder();
                                        BuzzWordController.initRecord();
                                        BuzzWordController.initVisited();
                                        clearHighlight();
                                    }
                                }
                                //Where Random Input + Normal Input Comes In
                                controller.addLetter(BuzzWordController.getBuzzBoard().getLetter(i, j), counter);
                                counter++;
                            }
                        }
                    }
                    BuzzWordController.getChecker().clear(); //checker has last letters of continuous letters
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            boolean[][] visited = new boolean[4][4];
                            controller.keyEventHighlighter(i, j, visited, "");
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (controller.nearByChecker(i, j)) {
                                controller.initKeyHighlight();
                            }
                        }
                    }
                }
            }
        });
        primaryScene.setOnKeyPressed(event -> {
            if (!dragging.get()) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    controller.checkRightGrid();
                    totalScoreLabel.setText(controller.changeTotalScore() + "");
                    BuzzWordController.initRecorder();
                    BuzzWordController.initRecord();
                    BuzzWordController.initVisited();
                    clearHighlight();
                    controller.removeRightGridIndex();
                    if (controller.changeTotalScore() >= controller.setTargetScore(BuzzWordController.getGameLevel())) {
                        controller.saveGameRequest();
                        startNextLevel.setVisible(true);
                        controller.end(filter);
                    }
                    keyInputting.set(false);
                }
            }
        });
    }

    @Override
    public void initLetter() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameLettersLabel[i][j].setVisible(false);
                gameLettersLabel[i][j].setText(Character.toString(BuzzWordController.getBuzzBoard().getLetter(i, j)));
            }
        }
    }

    public void reinitGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameLettersLabel[i][j].setOnMousePressed(null);
                gameLetters[i][j].setStyle(null);
                gameLetters[i][j].setFill(Color.valueOf("#979CA9"));
                gameLetters[i][j].setStyle("-fx-effect: dropshadow(gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
                gameLetters[i][j].setVisible(false);
            }
        }
    }

    public void clearHighlight() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameLetters[i][j].setStyle(null);
                gameLetters[i][j].setStyle("-fx-effect: dropshadow(gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                vLettersLines[i][j].setStyle(null);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                hLettersLines[i][j].setStyle(null);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                dRLettersLines[i][j].setStyle(null);
            }
        }

        for (int i = 0; i < 4 - 1; i++) {
            for (int j = 1; j < 4; j++) {
                dLLettersLines[i][j - 1].setStyle(null);
            }
        }
    }

    public void showCircles() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameLetters[i][j].setVisible(true);
                gameLettersLabel[i][j].setVisible(true);
            }
        }
    }

    public void setButtonEvent() {
        getTimerLabel().setText("120");
        controller.initTimer(timerLabel, filter);

        bottomPlayButton.setOnMouseClicked(event -> {
            showLines();
            showCircles();
            controller.startTimer();
            replayLevel.setVisible(true);
            pauseLabel.setVisible(false);
            pauseButtonPane.setVisible(true);
            bottomPlayButton.setVisible(false);
        });
        pauseButtonPane.setOnMouseClicked(event -> {
            hideCircles();
            hideLines();
            controller.pauseTimer();
            pauseLabel.setVisible(true);
            pauseButtonPane.setVisible(false);
            bottomPlayButton.setVisible(true);
        });
    }

    public void setHelpHomeReplayNextEvent() {
        WGDialogSingleton wgDialogSingleton = WGDialogSingleton.getSingleton();
        home.setOnMouseClicked(event -> {
            if (controller.getGamestate().equals(BuzzWordController.GameState.STARTED)) {
                hideCircles();
                hideLines();
                controller.pauseTimer();
                pauseLabel.setVisible(true);
                pauseButtonPane.setVisible(false);
                bottomPlayButton.setVisible(true);
            }
            wgDialogSingleton.show("Back Home?", "Press Enter for Go Home OR Press ESC for cancel.");
            if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                BuzzWordController.initRecorder();
                BuzzWordController.initRecord();
                BuzzWordController.initVisited();
                controller.initBuzzBoard();
                controller.removeRightGridIndex();
                totalScoreLabel.setText("0");
                scoreLeftBox.getChildren().clear();
                scoreRightBox.getChildren().clear();
                new Home();
            }
            if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {
                //Do NOTHING
            }
        });
        arrowPane.setOnMouseClicked(event -> {
            if (controller.getGamestate().equals(BuzzWordController.GameState.STARTED)) {
                hideCircles();
                hideLines();
                controller.pauseTimer();
                pauseLabel.setVisible(true);
                pauseButtonPane.setVisible(false);
                bottomPlayButton.setVisible(true);
            }
            wgDialogSingleton.show("Log Out?", "Press Enter for LOG OUT OR Press ESC for go back.");
            if (WGDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                BuzzWordController.initRecorder();
                BuzzWordController.initRecord();
                BuzzWordController.initVisited();
                controller.initBuzzBoard();
                controller.removeRightGridIndex();
                totalScoreLabel.setText("0");
                scoreLeftBox.getChildren().clear();
                scoreRightBox.getChildren().clear();
                controller = new BuzzWordController();
                controller.logOutRequest();
                createProfile.setVisible(true);
                arrowPane.setVisible(false);
                userButton.setVisible(false);
                login.setVisible(true);
                selectMode.setVisible(false);
                selectMode.setValue(new String("Select Mode"));
                start.setVisible(false);
                new Home();
            }
        });
        replayLevel.setOnMouseClicked(event -> {
            if (controller.getGamestate().equals(BuzzWordController.GameState.STARTED)) {
                hideCircles();
                hideLines();
                controller.pauseTimer();
                pauseLabel.setVisible(true);
                pauseButtonPane.setVisible(false);
                bottomPlayButton.setVisible(true);
            }
            controller.setGameState(BuzzWordController.GameState.INITIALIZED);
            BuzzWordController.initRecorder();
            BuzzWordController.initRecord();
            BuzzWordController.initVisited();
            controller.initBuzzBoard();
            controller.removeRightGridIndex();
            totalScoreLabel.setText("0");
            scoreLeftBox.getChildren().clear();
            scoreRightBox.getChildren().clear();
            layoutGUI();
            setButtonEvent();
            setExitButtonEvent();
            try {
                controller.solveBuzzBoard();
                controller.checkGrid();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            initLetter();
            setHightLight(gameLettersLabel, gameLetters, vLettersLines, hLettersLines);
            bottomPlayButton.setVisible(true);
        });
        startNextLevel.setOnMouseClicked(event -> {
            startNextLevel.setVisible(false);
            int currentlevel = Integer.parseInt(BuzzWordController.getGameLevel());
            int nextLevel = currentlevel+1;
            String nextLevelString = Integer.toString(nextLevel);
            LevelSelection.setTargetScore(controller.setTargetScore(nextLevelString));
            targetPointsLable.setText(controller.setTargetScore(nextLevelString) + " points");
            levelLabel.setText("Level " + nextLevelString);
            BuzzWordController.initRecorder();
            BuzzWordController.initRecord();
            BuzzWordController.initVisited();
            controller.initBuzzBoard();
            controller.removeRightGridIndex();
            controller.setGameState(BuzzWordController.GameState.INITIALIZED);
            totalScoreLabel.setText("0");
            scoreLeftBox.getChildren().clear();
            scoreRightBox.getChildren().clear();
            layoutGUI();
            setButtonEvent();
            setExitButtonEvent();
            try {
                controller.solveBuzzBoard();
                controller.checkGrid();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            initLetter();
            setHightLight(gameLettersLabel, gameLetters, vLettersLines, hLettersLines);
            bottomPlayButton.setVisible(true);
        });
    }

    public void setExitButtonEvent() {
        WGDialogSingleton wgDialogSingleton = WGDialogSingleton.getSingleton();
        exitLine1.setOnMouseClicked(event -> {
            if (controller.getGamestate().equals(BuzzWordController.GameState.STARTED)) {
                hideCircles();
                hideLines();
                controller.pauseTimer();
                pauseLabel.setVisible(true);
                pauseButtonPane.setVisible(false);
                bottomPlayButton.setVisible(true);
            }
            wgDialogSingleton.show("Exit?", "Press Enter for exit OR Press ESC for go back.");
            if (WGDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                System.exit(0);
            }
            if (WGDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {
                //DO NOTHING
            }
        });
        exitLine2.setOnMouseClicked(event -> {
            if (controller.getGamestate().equals(BuzzWordController.GameState.STARTED)) {
                hideCircles();
                hideLines();
                controller.pauseTimer();
                pauseLabel.setVisible(true);
                pauseButtonPane.setVisible(false);
                bottomPlayButton.setVisible(true);
            }
            wgDialogSingleton.show("Exit?", "Press Enter for exit OR Press ESC for go back.");
            if (WGDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                System.exit(0);
            }
            if (WGDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {

            }
        });
    }

}
