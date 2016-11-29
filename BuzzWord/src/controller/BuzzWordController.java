package controller;

import buzzwordui.CreateProfile;
import buzzwordui.LevelSelection;
import buzzwordui.LoginPage;
import data.*;
import ui.WGGUI;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        switch (mode) {
            case "Famous People":
                solver.setInputFile("words/English Dictionary.txt");
                break;
            case "Places":
                solver.setInputFile("words/English Dictionary.txt");
                break;
            case "Science":
                solver.setInputFile("words/English Dictionary.txt");
                break;
            case "English Dictionary":
                solver.setInputFile("words/English Dictionary.txt");
                break;
        }
        solver.start(buzzBoard);
        System.out.println(solver.getCounter().size() + " words are found, they are: ");
        for (String str : solver.getCounter()) {
            System.out.println(str);
        }
        System.out.println("Total Score: "+calTotalScore());
    }

    public int calTotalScore(){
        return solver.getCounter().size()*10;
    }

    public void checkGrid(){
        while (calTotalScore() < new LevelSelection(this).getTargetScore()){
            initBuzzBoard();
            solver = new BuzzWordSolverFinal();
            solver.start(buzzBoard);
            System.out.println("Total Score: "+calTotalScore()+" Rebuilding...");
            if (calTotalScore() >= new LevelSelection(this).getTargetScore()){
                System.out.println(solver.getCounter().size() + " words are found, they are: ");
                for (String str : solver.getCounter()) {
                    System.out.println(str);
                }
                break;
            }
        }
    }

    public static GameData getGameData() {
        return gameData;
    }

    public static BuzzBoard getBuzzBoard() {
        return buzzBoard;
    }
}
