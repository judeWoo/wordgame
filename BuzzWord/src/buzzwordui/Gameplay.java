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
//        try {
//            controller.solveBuzzBoard();
//            controller.checkGrid();
//        } catch (IOException | URISyntaxException e) {
//            e.printStackTrace();
//        }
        controller.initBuzzBoardTest();
        initLetter();
        setHightLight(gameLettersLabel, gameLetters, vLettersLines, hLettersLines);
    }

    public void layoutGUI() {
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

    public void setinitHighlight() {
//        gameLetters[0][0].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.8,1,1);");
//        gameLetters[0][1].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.8,1,1);");
//        gameLetters[0][2].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.8,1,1);");
        hLettersLines[0][0].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
        hLettersLines[0][1].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
    }

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
                            controller.end(filter);
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
                                if (!controller.checkKeyVisitied(i, j, counter)){
                                    String word = BuzzWordController.getBuzzBoard().toString();
//                                    System.out.println(word);
                                    if (word.indexOf(guess.toUpperCase(),
                                            word.indexOf(guess.toUpperCase()) + 1) <= -1){
                                        controller.getLetters().clear();
                                        controller.makeRightKeyGridIndex(BuzzWordController.getBuzzBoard().getLetter(i, j), counter);
                                        controller.removeRightGridIndex();
                                        BuzzWordController.initRecorder();
                                        BuzzWordController.initRecord();
                                        BuzzWordController.initVisited();
                                        clearHighlight();
                                    }
                                }
                                controller.addLetter(BuzzWordController.getBuzzBoard().getLetter(i, j), counter);
                                counter++;
                            }
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            boolean[][] visited = new boolean[4][4];
                            controller.keyEventHighlighter(i, j, visited, "");
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
            for (int j=0; j <4; j++){
                vLettersLines[i][j].setStyle(null);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j=0; j <3; j++){
                hLettersLines[i][j].setStyle(null);
            }
        }
        for (int i =0; i < 3; i++){
            for (int j=0; j < 3; j++){
                dRLettersLines[i][j].setStyle(null);
            }
        }

        for (int i = 0; i < 4-1; i++){
            for (int j=1; j < 4; j++){
                dLLettersLines[i][j-1].setStyle(null);
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
            if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                System.exit(0);
            }
            if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {

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
            if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                System.exit(0);
            }
            if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {

            }
        });
    }

}
