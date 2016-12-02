package controller;

import buzzwordui.CreateProfile;
import buzzwordui.LevelSelection;
import buzzwordui.LoginPage;
import data.*;
import javafx.scene.control.Label;
import ui.WGGUI;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Jude Hokyoon Woo on 11/17/2016.
 */
public class BuzzWordController implements FileManager {

    private Path workFile;
    private static GameData gameData;
    private static BuzzBoard buzzBoard;
    private GameDataFile gameDataFile;
    private WGGUI wggui;
    private BuzzWordSolverFinal solver;
    private static ArrayList<ArrayList<Integer>> record = new ArrayList<>();
    private static ArrayList<Character> letters = new ArrayList<>();


    public enum GameState {
        UNINITIALIZED,
        INITIALIZED_UNMODIFIED,
        INITIALIZED_MODIFIED,
        ENDED
    }

    public BuzzWordController() {
    }

    private void initGameData() {
        gameData = new GameData();
    }

    public void initBuzzBoard() {
        buzzBoard = new BuzzBoard();
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
    public void setGameLetterLable() {

    }

    @Override
    public int setTargetScore(String level) {

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
    public void loadGameRequest() {

    }

    @Override
    public void saveGameRequest() {

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
        if (!LoginPage.getPwField().getText().equals(gameData.getPassWord())) {
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
                solver.setInputFile("words/Famous People.txt");
                break;
            case "Places":
                solver.setInputFile("words/Places.txt");
                break;
            case "Science":
                solver.setInputFile("words/Science.txt");
                break;
            case "English Dictionary":
                solver.setInputFile("words/English Dictionary.txt");
                break;
        }
        solver.start(buzzBoard);
        if (calTotalScore() >= new LevelSelection(this).getTargetScore()) {
            System.out.println(solver.getCounter().size() + " words are found, they are: ");
            for (String str : solver.getCounter()) {
                System.out.println(str);
            }
            System.out.println("Total Score: " + calTotalScore());
        }
    }

    public int calTotalScore(){
        return solver.getCounter().size()*10;
    }

    public void checkGrid(){
        while (calTotalScore() < new LevelSelection(this).getTargetScore()){
            initBuzzBoard();
            solver = new BuzzWordSolverFinal();
            solver.start(buzzBoard);
            System.out.println(" Rebuilding...");
            if (calTotalScore() >= new LevelSelection(this).getTargetScore()){
                System.out.println(solver.getCounter().size() + " words are found, they are: ");
                for (String str : solver.getCounter()) {
                    System.out.println(str);
                }
                System.out.println("Total Score: "+calTotalScore());
                break;
            }
        }
    }

    public void makeRightGridIndex(char c){
        letters.add(c);
    }

    String getStringRepresentation(ArrayList<Character> list)
    {
        StringBuilder builder = new StringBuilder(list.size());
        for(Character ch: list)
        {
            builder.append(ch);
        }
        return builder.toString();
    }

    public void checkRightGrid(){
        if (getStringRepresentation(letters).equals(null)){
            initRecord();
            return;
        }

        for (String str : solver.getCounter()) {
            if(str.equals(getStringRepresentation(letters).toLowerCase())){
                Label scorelabel = new Label(getStringRepresentation(letters));
                WGGUI.getScoreLeftBox().getChildren().addAll(scorelabel);
                initLetters();
                initRecord();
                return;
            }
        }
        initLetters();
        initRecord();
    }

    public ArrayList<ArrayList<Integer>> recordGridIndex(int i, int j){
        ArrayList<Integer> recordElement = new ArrayList<>();
        recordElement.add(i);
        recordElement.add(j);
        if (record.isEmpty()) {
            record.add(recordElement);
        }
        else if (!record.isEmpty()){
            ArrayList<Integer> compareElement1 = new ArrayList<>();
            ArrayList<Integer> compareElement2 = new ArrayList<>();
            ArrayList<Integer> compareElement3 = new ArrayList<>();
            ArrayList<Integer> compareElement4 = new ArrayList<>();
            ArrayList<Integer> compareElement5 = new ArrayList<>();
            ArrayList<Integer> compareElement6 = new ArrayList<>();
            ArrayList<Integer> compareElement7 = new ArrayList<>();
            ArrayList<Integer> compareElement8 = new ArrayList<>();
            compareElement1.add(i);compareElement1.add(j-1);
            compareElement2.add(i);compareElement2.add(j+1);
            compareElement3.add(i-1);compareElement3.add(j);
            compareElement4.add(i+1);compareElement4.add(j);
            compareElement5.add(i+1);compareElement5.add(j-1);
            compareElement6.add(i+1);compareElement6.add(j+1);
            compareElement7.add(i-1);compareElement7.add(j-1);
            compareElement8.add(i-1);compareElement8.add(j+1);

            if (record.contains(compareElement1) || record.contains(compareElement2) || record.contains(compareElement3)
                    || record.contains(compareElement4) || record.contains(compareElement5) ||
                    record.contains(compareElement6) || record.contains(compareElement7) || record.contains(compareElement8)){
                record = new ArrayList<>(); //clear first;
                record.add(recordElement);
            }
        }
        return record;
    }

    public boolean checkMouseDrag(int i, int j){
        ArrayList<Integer> recordElement = new ArrayList<>();
        recordElement.add(i);
        recordElement.add(j);
        if (recordGridIndex(i,j).contains(recordElement)){
            return true;
        }
        return false;
    }

    public static void initLetters(){
        letters = new ArrayList<>();
    }

    public static void initRecord(){
        record = new ArrayList<>();
    }

    public static GameData getGameData() {
        return gameData;
    }

    public static BuzzBoard getBuzzBoard() {
        return buzzBoard;
    }
}
