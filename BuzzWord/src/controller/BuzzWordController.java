package controller;

import buzzwordui.CreateProfile;
import buzzwordui.LevelSelection;
import buzzwordui.LoginPage;
import data.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import ui.WGGUI;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by Jude Hokyoon Woo on 11/17/2016.
 */
public class BuzzWordController implements FileManager {

    private Path workFile;
    private GameDataFile gameDataFile;
    private WGGUI wggui;
    private BuzzWordSolverFinal solver;
    private static GameState gamestate;   // the state of the game being shown in the workspace
    private static Timeline timeline;
    private static GameData gameData;
    private static String gameLevel;
    private static BuzzBoard buzzBoard;
    private static ArrayList<ArrayList<Integer>> record = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> recordKeyEvent = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> visited = new ArrayList<>();
    private static ArrayList<Character> letters = new ArrayList<>();
    private static ArrayList<String> strings = new ArrayList<>();
    private static ArrayList<Integer> score = new ArrayList<>();
    private static ArrayList<?> keyInputTree = new ArrayList<>();

    public enum GameState {
        INITIALIZED,
        PAUSED,
        STARTED,
        ENDED
    }

    public BuzzWordController() {
    }

    @Override
    public boolean newGameProfileRequest() {
        initGameData();
        if (workFile == null) {
            Path appDirPath = Paths.get("BuzzWord").toAbsolutePath();
            Path targetPath = appDirPath.resolve("saved");

            try {
                if (save(targetPath)) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                save(workFile);
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
                if (load(targetPath)) {
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
        initGameState();
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
        switch (mode) {
            case "Famous People":
                gameData.setdModeLevel(Integer.parseInt(gameLevel));
                break;
            case "Places":
                gameData.setbModeLevel(Integer.parseInt(gameLevel));
                break;
            case "Science":
                gameData.setcModeLevel(Integer.parseInt(gameLevel));
                break;
            case "English Dictionary":
                gameData.setaModeLevel(Integer.parseInt(gameLevel));
                break;
        }

        if (workFile == null) {
            Path appDirPath = Paths.get("BuzzWord").toAbsolutePath();
            Path targetPath = appDirPath.resolve("saved");

            try {
                if (save(targetPath)) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                save(workFile);
                return;
            } catch (IOException e) {
                e.printStackTrace();
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

        // notify the user that load was successful
//        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
//        PropertyManager           props  = PropertyManager.getManager();
//        dialog.show(props.getPropertyValue(LOAD_COMPLETED_TITLE), props.getPropertyValue(LOAD_COMPLETED_MESSAGE));
//        setGameState(GameState.INITIALIZED_UNMODIFIED);
    }

    public void solveBuzzBoard() throws IOException, URISyntaxException {
        solver = new BuzzWordSolverFinal();
        String mode = WGGUI.getSelectMode().getValue().toString();
        initBuzzBoard();
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
        BuzzWordSolverFinal.start(buzzBoard);
        if (calTotalScore() >= new LevelSelection(this).getTargetScore()) {
            System.out.println(BuzzWordSolverFinal.getCounter().size() + " words are found, they are: ");
            for (String str : BuzzWordSolverFinal.getCounter()) {
                System.out.println(str);
            }
            System.out.println("Total Score: " + calTotalScore());
        }
    }

    public int calTotalScore() {
        return BuzzWordSolverFinal.getCounter().size() * 10;
    }

    public void checkGrid() {
        while (calTotalScore() < new LevelSelection(this).getTargetScore()) {
            initBuzzBoard();
            solver = new BuzzWordSolverFinal();
            BuzzWordSolverFinal.start(buzzBoard);
            System.out.println(" Rebuilding...");
            if (calTotalScore() >= new LevelSelection(this).getTargetScore()) {
                System.out.println(BuzzWordSolverFinal.getCounter().size() + " words are found, they are: ");
                for (String str : BuzzWordSolverFinal.getCounter()) {
                    System.out.println(str);
                }
                System.out.println("Total Score: " + calTotalScore());
                break;
            }
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

    String getStringRepresentation(ArrayList<Character> list) {
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
                } else if (getStringRepresentation(letters).length() >= 5) {
                    scorelabel.setText("30");
                    WGGUI.getScoreRightBox().getChildren().addAll(scorelabel);
                    score.add(30);
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

    public ArrayList<ArrayList<Integer>> recordGridIndex(int i, int j) {
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
                record = new ArrayList<>(); //clear first;
                record.add(recordElement);
            }
        }
        return record;
    }

    public boolean checkMouseDrag(int i, int j) {
        ArrayList<Integer> recordElement = new ArrayList<>();
        recordElement.add(i);
        recordElement.add(j);
        return recordGridIndex(i, j).contains(recordElement);
    }

    public boolean checkVisitied(int i, int j) {
        ArrayList<Integer> recordElement = new ArrayList<>();
        recordElement.add(i);
        recordElement.add(j);
        if (!visited.contains(recordElement)) {
            visited.add(recordElement);
            return true;
        }
        return false;
    }

    public boolean checkKeyInput(KeyEvent event, int i, int j) {
        Item keyInput1 = new Item(i, j, event.getCharacter());
        Item keyInput2 = new Item(i, j-1, event.getCharacter());
        Item keyInput3 = new Item(i, j+1, event.getCharacter());
        Item keyInput4 = new Item(i-1, j, event.getCharacter());
        Item keyInput5 = new Item(i+1, j, event.getCharacter());
        Item keyInput6 = new Item(i-1, j-1, event.getCharacter());
        Item keyInput7 = new Item(i+1, j-1, event.getCharacter());
        Item keyInput8 = new Item(i-1, j+1, event.getCharacter());
        Item keyInput9 = new Item(i+1, j+1, event.getCharacter());
        keyInputTree = new ArrayList<>();
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
        WGGUI.getTimerLabel().textProperty().unbind();
        WGGUI.getPrimaryScene().setOnKeyTyped(null);
        WGGUI.getPrimaryScene().removeEventFilter(MouseEvent.DRAG_DETECTED, filter);
        setGameState(GameState.ENDED);
        System.out.println("Game Ended");
    }

    public void startTimer() {
        setGameState(GameState.STARTED);
        timeline.play();
    }

    public void pauseTimer() {
        setGameState(GameState.PAUSED);
        timeline.pause();
    }

    public static void initTimer(Label timerLabel) {
        Integer starttime = 60;
        IntegerProperty timeSeconds = new SimpleIntegerProperty(starttime);

        // Configure the Label
        timerLabel.setText(timeSeconds.toString());
        timerLabel.textProperty().bind(timeSeconds.asString());

        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(starttime);
        timeline = new Timeline();

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(starttime + 1),
                        new KeyValue(timeSeconds, 0)));

    }

    public void initGameState() { setGameState(GameState.INITIALIZED); }

    private void initGameData() {
        gameData = new GameData();
    }

    public void initBuzzBoard() {
        buzzBoard = new BuzzBoard();
    }

    //for key INPUT
    class Item implements Comparable<Item>{
        public final int x, y;
        public final String prefix;

        public Item(int row, int column, String prefix) {
            this.x = row;
            this.y = column;
            this.prefix = prefix;
        }

        @Override
        public int compareTo(Item o) {
            return prefix.charAt(0) - 'A';
        }
    }

    public void setGameState(GameState gamestate) {
        BuzzWordController.gamestate = gamestate;
    }

    public static void initScore() {
        score = new ArrayList<>();
    }

    public static void initVisited() {
        visited = new ArrayList<>();
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

    public static String getGameLevel() {
        return gameLevel;
    }
}
