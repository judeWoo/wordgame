package controller;

import javafx.stage.Stage;

/**
 * Created by Jude Hokyoon Woo on 10/29/2016.
 */
public interface FileManager {
    void newGameProfileRequest();

    boolean loginRequest();

    void selectModeRequest();

    void loadGameRequest();

    void saveGameRequest();
}
