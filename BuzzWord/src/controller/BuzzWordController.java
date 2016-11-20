package controller;

import buzzwordui.CreateProfile;
import buzzwordui.LoginPage;
import com.sun.org.apache.xerces.internal.impl.PropertyManager;
import data.GameData;
import data.GameDataFile;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Kun on 11/17/2016.
 */
public class BuzzWordController implements FileManager {

    private Path workFile;
    private GameData gameData;
    private GameDataFile gameDataFile;

    public enum GameState {
        UNINITIALIZED,
        INITIALIZED_UNMODIFIED,
        INITIALIZED_MODIFIED,
        ENDED
    }

    public BuzzWordController() {

    }

    @Override
    public void newGameProfileRequest() {
        if (workFile == null) {
            Path            appDirPath      = Paths.get("BuzzWord").toAbsolutePath();
            Path            targetPath      = appDirPath.resolve("saved");

            if (true) {
                try {
                    save(targetPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            try {
                save(workFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }

    @Override
    public boolean loginRequest() {
        Path            appDirPath      = Paths.get("BuzzWord").toAbsolutePath();
        Path            targetPath      = appDirPath.resolve("saved");
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

    private void save(Path target) throws IOException {
        gameData = new GameData();
        gameDataFile = new GameDataFile();
        gameDataFile.saveData(gameData, target);
        workFile = target;
    }

    private boolean load(Path source) throws IOException {
        // load game data
        gameData = new GameData();
        gameDataFile = new GameDataFile();
        LoginPage loginPage = new LoginPage(gameData);
        File file = new File(source+"/"+loginPage.getIdField().getText()+".json");
        if(!file.exists()){
            //singleton
            return false;
        }
        gameDataFile.loadData(gameData, source);
        if (!loginPage.getPwField().getText().equals(gameData.getPassWord())){
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
}
