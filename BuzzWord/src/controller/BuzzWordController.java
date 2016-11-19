package controller;

import buzzwordui.CreateProfile;
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
    public void newGameProfileRequest(Stage stage, String string) {
        if (workFile == null) {
            FileChooser fileChooser = new FileChooser();
            Path            appDirPath      = Paths.get("BuzzWord").toAbsolutePath();
            Path            targetPath      = appDirPath.resolve("saved");
//            fileChooser.setInitialDirectory(targetPath.toFile());
//            fileChooser.setInitialFileName(string);
//            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json File(*.json)", "*.json"));
//            File file = fileChooser.showSaveDialog(null);

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
    public void loginRequest() {

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
}
