package controller;

import buzzwordui.CreateProfile;
import buzzwordui.LoginPage;
import data.GameData;
import data.GameDataFile;
import ui.WGGUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Kun on 11/17/2016.
 */
public class BuzzWordController implements FileManager {

    private Path workFile;
    private static GameData gameData;
    private GameDataFile gameDataFile;
    private WGGUI   wggui;

    public enum GameState {
        UNINITIALIZED,
        INITIALIZED_UNMODIFIED,
        INITIALIZED_MODIFIED,
        ENDED
    }

    public BuzzWordController() {
    }

    public void initGameData() {
        gameData = new GameData();
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
    public boolean loginRequest() {
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
    public void selectModeRequest() {

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
        CreateProfile createProfile = new CreateProfile(gameData);
        File file = new File(target + "/" + createProfile.getIdField().getText() + ".json");
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
        LoginPage loginPage = new LoginPage(gameData);
        File file = new File(source + "/" + loginPage.getIdField().getText() + ".json");
        if (!file.exists()) {
            //singleton
            return false;
        }
        gameDataFile.loadData(gameData, source);
        if (!loginPage.getPwField().getText().equals(gameData.getPassWord())) {
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
    public static GameData getGameData() {
        return gameData;
    }
}
