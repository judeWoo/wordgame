package controller;

import buzzwordui.*;
import data.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.WGGUI;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Jude Hokyoon Woo on 11/17/2016.
 */
public class BuzzWordController implements FileManager {

    private Path workFile;
    private GameDataFile gameDataFile;
    private BuzzWordSolverFinal solver = new BuzzWordSolverFinal();
    private static BuzzTrie buzzTrie;
    private static GameState gamestate;   // the state of the game being shown in the workspace
    private static LoginState loginState;
    private static Timeline timeline;
    private static GameData gameData;
    private static String gameLevel;
    private static BuzzBoard buzzBoard;
    private static ArrayList<ArrayList<Integer>> record = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> visitedArray = new ArrayList<>();
    private static ArrayList<Character> letters = new ArrayList<>();
    private static ArrayList<String> strings = new ArrayList<>();
    private static ArrayList<Integer> score = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> recorder = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> checker = new ArrayList<>();

    public enum GameState {
        INITIALIZED,
        PAUSED,
        STARTED,
        ENDED
    }

    public enum LoginState {
        LOGGED,
        CREATED
    }
    //empty for uses
    public BuzzWordController() {
    }

    public void savePersonalBest() throws IOException {
        gameDataFile = new GameDataFile();
        String mode = WGGUI.getSelectMode().getValue().toString();
        Path appDirPath = Paths.get("BuzzWord").toAbsolutePath();
        Path targetPath = appDirPath.resolve("saved");
        int index = 0;
        switch (gameLevel) {
            case "1":
                index = 0;
                break;
            case "2":
                index = 1;
                break;
            case "3":
                index = 2;
                break;
            case "4":
                index = 3;
                break;
            case "5":
                index = 4;
                break;
            case "6":
                index = 5;
                break;
            case "7":
                index = 6;
                break;
            case "8":
                index = 7;
                break;
        }
        switch (mode) {
            case "Famous People":
                if (changeTotalScore() > gameData.getdModeLevelandBest().get(index)) {
                    gameData.getdModeLevelandBest().set(index, changeTotalScore());
                    gameDataFile.saveData(gameData, targetPath);
                    return;
                }
                return;
            case "Places":
                if (changeTotalScore() > gameData.getbModeLevelandBest().get(index)) {
                    gameData.getbModeLevelandBest().set(index, changeTotalScore());
                    gameDataFile.saveData(gameData, targetPath);
                    return;
                }
                return;
            case "Science":
                if (changeTotalScore() > gameData.getcModeLevelandBest().get(index)) {
                    gameData.getcModeLevelandBest().set(index, changeTotalScore());
                    gameDataFile.saveData(gameData, targetPath);
                    return;
                }
                return;
            case "English Dictionary":
                if (changeTotalScore() > gameData.getaModeLevelandBest().get(index)) {
                    gameData.getaModeLevelandBest().set(index, changeTotalScore());
                    gameDataFile.saveData(gameData, targetPath);
                    return;
                }
                return;
        }

        return;
    }

    @Override
    public boolean newGameProfileRequest() {
        initGameData();
        if (workFile == null) {
            Path appDirPath = Paths.get("BuzzWord").toAbsolutePath();
            Path targetPath = appDirPath.resolve("saved");
            try {
                gameData.init(); //when create id
                setLoginState(LoginState.CREATED);
                if (save(targetPath)) {
                    initGameState(); //start game state;
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                setLoginState(LoginState.CREATED);
                gameData.init(); //when create id
                save(workFile);
                initGameState(); //start game state;
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean logInRequest() {
        Path appDirPath = Paths.get("BuzzWord").toAbsolutePath();
        Path targetPath = appDirPath.resolve("saved");
        if (targetPath != null) {
            try {
                setLoginState(LoginState.LOGGED);
                if (load(targetPath)) {
                    initGameState(); //start game state;
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public void logOutRequest() {
        gameData.reset();
    }

    @Override
    public int setTargetScore(String level) {
        gameLevel = new String(level);
        switch (level) {
            case "1":
                return 30;
            case "2":
                return 45;
            case "3":
                return 60;
            case "4":
                return 75;
            case "5":
                return 90;
            case "6":
                return 105;
            case "7":
                return 120;
            case "8":
                return 135;
        }
        return 1;
    }

    @Override
    public void saveGameRequest() {
        String mode = WGGUI.getSelectMode().getValue().toString();
        if (gameData.getaModeLevel() < 8 || gameData.getbModeLevel() < 8 || gameData.getcModeLevel() < 8 || gameData.getdModeLevel() < 8) {
            switch (mode) {
                case "Famous People":
                    if (gameData.getdModeLevel() < 8)
                        gameData.setdModeLevel(Integer.parseInt(gameLevel) + 1);
                    break;
                case "Places":
                    if (gameData.getbModeLevel() < 8)
                        gameData.setbModeLevel(Integer.parseInt(gameLevel) + 1);
                    break;
                case "Science":
                    if (gameData.getcModeLevel() < 8)
                        gameData.setcModeLevel(Integer.parseInt(gameLevel) + 1);
                    break;
                case "English Dictionary":
                    if (gameData.getaModeLevel() <8)
                        gameData.setaModeLevel(Integer.parseInt(gameLevel) + 1);
                    break;
            }
        }
    }

    private boolean save(Path target) throws IOException {
        //save game data
        gameDataFile = new GameDataFile();
        File file = new File(target + "/" + CreateProfile.getIdField().getText() + ".json");
        if (file.exists()) {
            //singleton
            return false;
        }
        gameDataFile.saveData(gameData, target);
        workFile = target;
        return true;
    }

    private boolean load(Path source) throws IOException {
        // load game data
        gameData = new GameData();
        gameDataFile = new GameDataFile();
        File file = new File(source + "/" + LoginPage.getIdField().getText() + ".json");
        if (!file.exists()) {
            return false;
        }
        gameDataFile.loadData(gameData, source);
        if (!Hash.md5(LoginPage.getPwField().getText()).equals(gameData.getPassWord())) {
            //singleton
            return false;
        }
        // set the work file as the file from which the game was loaded
        workFile = source;
        return true;
    }

    public void buildDictionary(String mode) {
        switch (mode) {
            case "Famous People":
                BuzzWordSolverFinal.setInputFile("words/Famous People.txt");
                break;
            case "Places":
                BuzzWordSolverFinal.setInputFile("words/Places.txt");
                break;
            case "Science":
                BuzzWordSolverFinal.setInputFile("words/Science.txt");
                break;
            case "English Dictionary":
                BuzzWordSolverFinal.setInputFile("words/English Dictionary.txt");
                break;
        }
        buzzTrie = solver.buildTrie();
    }

    public void solveBuzzBoard() throws IOException, URISyntaxException {
//        String mode = WGGUI.getSelectMode().getValue().toString();
        initBuzzBoard();
        solver.start(buzzBoard, buzzTrie);
        //Active Follow Code to Reaveal Answers
//        if (calTotalScore() >= new LevelSelection(this).getTargetScore()) {
//            System.out.println(BuzzWordSolverFinal.getCounter().size() + " words are found, they are: ");
//            for (String str : BuzzWordSolverFinal.getCounter()) {
//                System.out.println(str);
//            }
//            System.out.println("Total Score: " + calTotalScore());
//        }
    }

    public int calTotalScore() {
        int total = 0;
        for (String str : BuzzWordSolverFinal.getCounter()) {
            if (str.length() == 3)
                total = total+10;
            if (str.length() == 4)
                total = total+20;
            if (str.length() == 5)
                total = total+25;
            if (str.length() == 6)
                total = total+30;
            if (str.length() >= 7)
                total = total+35;
        }
        return total;
    }

    public void checkGrid() {
        while (calTotalScore() < new LevelSelection(this).getTargetScore()) {
            System.out.println(" Rebuilding The Grid...");
            initBuzzBoard();
            solver.start(buzzBoard, buzzTrie);
            //Active follow codes for Answers
//            if (calTotalScore() >= new LevelSelection(this).getTargetScore()) {
//                System.out.println(BuzzWordSolverFinal.getCounter().size() + " words are found, they are: ");
//                for (String str : BuzzWordSolverFinal.getCounter()) {
//                    System.out.println(str);
//                }
//                System.out.println("Total Score: " + calTotalScore());
//                break;
//            }
        }
    }

    public void addLetter(char c, int counter) {
        if (counter <= 0) {
            Label word = new Label();
            word.getStyleClass().add("word");
            word.setText(String.valueOf(c));
            WGGUI.getWordBox().getChildren().add(word);
        }
    }

    //if key, use visited
    public void makeRightKeyGridIndex(char c, int counter) {
        if (counter <= 0) {
            letters.add(c);
        }
    }

    public void makeRightGridIndex(char c) {
        letters.add(c);
        Label word = new Label();
        word.getStyleClass().add("word");
        word.setText(String.valueOf(c));
        WGGUI.getWordBox().getChildren().add(word);
    }

    public void removeRightGridIndex() {
        WGGUI.getWordBox().getChildren().clear();
    }

    private String getStringRepresentation(ArrayList<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }
        return builder.toString();
    }

    public void checkRightGrid() {
        if (getStringRepresentation(letters).equals(null)) {
            initRecord();
            return;
        }
        for (String str : BuzzWordSolverFinal.getCounter()) {
            if (str.equals(getStringRepresentation(letters).toLowerCase()) && !strings.contains(getStringRepresentation(letters).toLowerCase())) {
                strings.add(getStringRepresentation(letters).toLowerCase());
                Label wordlabel = new Label(getStringRepresentation(letters));
                Label scorelabel = new Label("10");
                WGGUI.getScoreLeftBox().getChildren().addAll(wordlabel);
                if (getStringRepresentation(letters).length() == 3) {
                    WGGUI.getScoreRightBox().getChildren().addAll(scorelabel);
                    score.add(10);
                    initLetters();
                    initRecord();
                    return;
                } else if (getStringRepresentation(letters).length() == 4) {
                    scorelabel.setText("20");
                    WGGUI.getScoreRightBox().getChildren().addAll(scorelabel);
                    score.add(20);
                    initLetters();
                    initRecord();
                    return;
                } else if (getStringRepresentation(letters).length() == 5) {
                    scorelabel.setText("25");
                    WGGUI.getScoreRightBox().getChildren().addAll(scorelabel);
                    score.add(25);
                    initLetters();
                    initRecord();
                    return;
                } else if (getStringRepresentation(letters).length() == 6) {
                    scorelabel.setText("30");
                    WGGUI.getScoreRightBox().getChildren().addAll(scorelabel);
                    score.add(30);
                    initLetters();
                    initRecord();
                    return;
                } else if (getStringRepresentation(letters).length() >= 7) {
                    scorelabel.setText("35");
                    WGGUI.getScoreRightBox().getChildren().addAll(scorelabel);
                    score.add(35);
                    initLetters();
                    initRecord();
                    return;
                } else {
                    //for safety
                    System.out.println(-1);
                    return;
                }
            }
        }
        initLetters();
        initRecord();
    }

    public void recordGridIndex(int i, int j) {
        ArrayList<Integer> recordElement = new ArrayList<>();
        recordElement.add(i);
        recordElement.add(j);
        if (record.isEmpty()) {
            record.add(recordElement);
        } else if (!record.isEmpty()) {
            ArrayList<Integer> compareElement1 = new ArrayList<>();
            ArrayList<Integer> compareElement2 = new ArrayList<>();
            ArrayList<Integer> compareElement3 = new ArrayList<>();
            ArrayList<Integer> compareElement4 = new ArrayList<>();
            ArrayList<Integer> compareElement5 = new ArrayList<>();
            ArrayList<Integer> compareElement6 = new ArrayList<>();
            ArrayList<Integer> compareElement7 = new ArrayList<>();
            ArrayList<Integer> compareElement8 = new ArrayList<>();
            compareElement1.add(i);
            compareElement1.add(j - 1);
            compareElement2.add(i);
            compareElement2.add(j + 1);
            compareElement3.add(i - 1);
            compareElement3.add(j);
            compareElement4.add(i + 1);
            compareElement4.add(j);
            compareElement5.add(i + 1);
            compareElement5.add(j - 1);
            compareElement6.add(i + 1);
            compareElement6.add(j + 1);
            compareElement7.add(i - 1);
            compareElement7.add(j - 1);
            compareElement8.add(i - 1);
            compareElement8.add(j + 1);

            if (record.contains(compareElement1) || record.contains(compareElement2) || record.contains(compareElement3)
                    || record.contains(compareElement4) || record.contains(compareElement5) ||
                    record.contains(compareElement6) || record.contains(compareElement7) || record.contains(compareElement8)) {
                if (record.contains(compareElement1))
                    WGGUI.gethLettersLines()[i][j - 1].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
                if (record.contains(compareElement2))
                    WGGUI.gethLettersLines()[i][j].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
                if (record.contains(compareElement3))
                    WGGUI.getvLettersLines()[i - 1][j].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
                if (record.contains(compareElement4))
                    WGGUI.getvLettersLines()[i][j].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
                if (record.contains(compareElement5))
                    WGGUI.getdLLettersLines()[i][j - 1].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
                if (record.contains(compareElement6))
                    WGGUI.getdRLettersLines()[i][j].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
                if (record.contains(compareElement7))
                    WGGUI.getdRLettersLines()[i - 1][j - 1].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
                if (record.contains(compareElement8))
                    WGGUI.getdLLettersLines()[i - 1][j].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.7,1,1);");
                record.clear();
                record.add(recordElement);
            } else {
                //allows discontinued drag
                clearHighlight();
                record.clear();
                record.add(recordElement);
                removeRightGridIndex();
                letters.clear();
            }
        }
        return;
    }

    public void clearHighlight() {
        for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
                WGGUI.getGameLetters()[k][l].setStyle(null);
                WGGUI.getGameLetters()[k][l].setStyle("-fx-effect: dropshadow(gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                WGGUI.getvLettersLines()[i][j].setStyle(null);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                WGGUI.gethLettersLines()[i][j].setStyle(null);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                WGGUI.getdRLettersLines()[i][j].setStyle(null);
            }
        }

        for (int i = 0; i < 4 - 1; i++) {
            for (int j = 1; j < 4; j++) {
                WGGUI.getdLLettersLines()[i][j - 1].setStyle(null);
            }
        }

    }

    public boolean checkMouseDrag(int i, int j) {
        ArrayList<Integer> recordElement = new ArrayList<>();
        recordElement.add(i);
        recordElement.add(j);
        recordGridIndex(i, j);
        if (record.contains(recordElement)) {
            return true;
        }
        return false;
    }

    public boolean checkVisitied(int i, int j) {
        ArrayList<Integer> recordElement = new ArrayList<>();
        recordElement.add(i);
        recordElement.add(j);
        if (!visitedArray.contains(recordElement)) {
            visitedArray.add(recordElement);
            return true;
        }
        return false;
    }

    public boolean checkKeyVisitied(int i, int j, int count) {
        ArrayList<Integer> recordElement = new ArrayList<>();
        recordElement.add(i);
        recordElement.add(j);
        if (!visitedArray.contains(recordElement)) {
            if (count <= 0) {
                visitedArray.add(recordElement);
                return true;
            }
            return true;
        }
        return false;
    }

    public int changeTotalScore() {
        int total = 0;
        for (int i = 0; i < score.size(); i++) {
            total = total + score.get(i);
        }
        return total;
    }

    public void end(EventHandler filter) {
        timeline.stop();
        clearHighlight();
        initVisited();
        removeEventHandler();
        removeRightGridIndex();
        WGGUI.getBottomPane().setVisible(false);
        WGGUI.getPrimaryScene().setOnKeyTyped(null);
        WGGUI.getPrimaryScene().setOnKeyPressed(null);
        WGGUI.getPrimaryScene().setOnKeyPressed(event -> {
                    new Gameplay(this).setKeyShortCuts(event);
            });
        WGGUI.getPrimaryScene().removeEventFilter(MouseEvent.DRAG_DETECTED, filter);
        setGameState(GameState.ENDED);
        try {
            savePersonalBest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PersonalBest.setStage(new Stage());
        new PersonalBest();
    }

    public void startTimer() {
        setGameState(GameState.STARTED);
        timeline.play();
    }

    public void pauseTimer() {
        setGameState(GameState.PAUSED);
        timeline.pause();
    }

    public void initTimer(Label timerLabel, EventHandler filter) {
        Integer STARTTIME = 120; // can modify later;
        final Integer[] timeSeconds = {STARTTIME};
        // Configure the Label
        timerLabel.setText(timeSeconds[0].toString());

        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                timeSeconds[0]--;
                                // update timerLabel
                                timerLabel.setText(
                                        timeSeconds[0].toString());
                                if (timeSeconds[0] <= 0) {
                                    end(filter);
                                }
                            }
                        }));

    }

    public void keyEventHighlighter(int i, int j, boolean[][] visited, String s) {
        //grid is 4x4
        if (i < 0 || j < 0 || i > 3 || j > 3) {
            return;
        } else if (visited[i][j] == true) {
            return;
        }
        ArrayList<Integer> recordElement = new ArrayList<>();
        recordElement.add(i);
        recordElement.add(j);
        String word = getStringRepresentation(letters); //user input
        s = s + buzzBoard.getLetter(i, j); //add a letter on a grid which locates in (i,j)
        if (word.contains(s)) {
            recorder.add(recordElement);
            if (word.equals(s)) {
                for (ArrayList<Integer> list : recorder) {
                    for (int xy = 0; xy < list.size(); xy += 2) {
                        WGGUI.getGameLetters()[list.get(xy)][list.get(xy + 1)].setStyle(null);
                        WGGUI.getGameLetters()[list.get(xy)][list.get(xy + 1)].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.8,1,1);");
                        recordGridIndex(list.get(xy), list.get(xy + 1));
                    }
                }
                checker.add(recordElement);
                recorder.remove(recordElement);
                record.remove(recordElement);
                return;
            }
            visited[i][j] = true;
            keyEventHighlighter(i, j - 1, visited, s);
            keyEventHighlighter(i, j + 1, visited, s);
            keyEventHighlighter(i - 1, j, visited, s);
            keyEventHighlighter(i + 1, j, visited, s);
            keyEventHighlighter(i + 1, j - 1, visited, s);
            keyEventHighlighter(i + 1, j + 1, visited, s);
            keyEventHighlighter(i - 1, j - 1, visited, s);
            keyEventHighlighter(i - 1, j + 1, visited, s);
            visited[i][j] = false;
            recorder.remove(recordElement);
            record.remove(recordElement);
        }
    }

    public void initKeyHighlight() {
        //Clear all
        char startLetter= 'a'; //initialize
        int size = letters.size();
        if (letters.size() <= 0)
            startLetter = letters.get(0);
        else if (letters.size() > 0)
            startLetter = letters.get(size - 1); //get the last character of letters.
        letters.clear();
        removeRightGridIndex();
        recorder.clear();
        record.clear();
        visitedArray.clear();
        //Start new
        letters.add(startLetter);
        addLetter(startLetter, 0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (startLetter == buzzBoard.getLetter(i, j)) {
                    WGGUI.getGameLetters()[i][j].setStyle(null);
                    WGGUI.getGameLetters()[i][j].setStyle("-fx-effect: dropshadow(gaussian, rgba(34,252,2,0.75), 20,0.8,1,1);");
                }
            }
        }
    }

    public void removeEventHandler() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                WGGUI.getGameLettersLabel()[i][j].setOnMouseDragEntered(null);
                WGGUI.getGameLettersLabel()[i][j].setOnMouseEntered(null);
            }
        }
    }

    public boolean nearByChecker(int i, int j) {
        ArrayList<Integer> recordElement = new ArrayList<>();
        ArrayList<Integer> compareElement1 = new ArrayList<>();
        ArrayList<Integer> compareElement2 = new ArrayList<>();
        ArrayList<Integer> compareElement3 = new ArrayList<>();
        ArrayList<Integer> compareElement4 = new ArrayList<>();
        ArrayList<Integer> compareElement5 = new ArrayList<>();
        ArrayList<Integer> compareElement6 = new ArrayList<>();
        ArrayList<Integer> compareElement7 = new ArrayList<>();
        ArrayList<Integer> compareElement8 = new ArrayList<>();
        compareElement1.add(i);
        compareElement1.add(j - 1);
        compareElement2.add(i);
        compareElement2.add(j + 1);
        compareElement3.add(i - 1);
        compareElement3.add(j);
        compareElement4.add(i + 1);
        compareElement4.add(j);
        compareElement5.add(i + 1);
        compareElement5.add(j - 1);
        compareElement6.add(i + 1);
        compareElement6.add(j + 1);
        compareElement7.add(i - 1);
        compareElement7.add(j - 1);
        compareElement8.add(i - 1);
        compareElement8.add(j + 1);
        if (checker.contains(recordElement) || checker.isEmpty()) {
            if (record.contains(compareElement1) || record.contains(compareElement2) || record.contains(compareElement3)
                    || record.contains(compareElement4) || record.contains(compareElement5) ||
                    record.contains(compareElement6) || record.contains(compareElement7) || record.contains(compareElement8)
                    || record.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static LoginState getLoginState() {
        return loginState;
    }

    public static void setLoginState(LoginState loginState) {
        BuzzWordController.loginState = loginState;
    }

    public void initGameState() {
        setGameState(GameState.INITIALIZED);
    }

    private void initGameData() {
        gameData = new GameData();
    }

    public void initBuzzBoard() {
        buzzBoard = new BuzzBoard();
    }

    public void initBuzzBoardTest() {
        buzzBoard = new BuzzBoard("Test");
    } //for the test of the algorithm

    public void setGameState(GameState gamestate) {
        BuzzWordController.gamestate = gamestate;
    }

    public static void initScore() {
        score = new ArrayList<>();
    }

    public static void initRecorder() {
        recorder = new ArrayList<>();
    }

    public static void initVisited() {
        visitedArray = new ArrayList<>();
    }

    public static void initLetters() {
        letters = new ArrayList<>();
    }

    public static void initRecord() {
        record = new ArrayList<>();
    }

    public static ArrayList<String> getStrings() {
        return strings;
    }

    public static GameData getGameData() {
        return gameData;
    }

    public static BuzzBoard getBuzzBoard() {
        return buzzBoard;
    }

    public GameState getGamestate() {
        return gamestate;
    }

    public static ArrayList<Character> getLetters() {
        return letters;
    }

    public static String getGameLevel() {
        return gameLevel;
    }

    public static ArrayList<ArrayList<Integer>> getRecorder() {
        return recorder;
    }

    public static ArrayList<ArrayList<Integer>> getChecker() {
        return checker;
    }
}
