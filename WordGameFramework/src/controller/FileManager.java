package controller;

/**
 * Created by Jude Hokyoon Woo on 10/29/2016.
 */
public interface FileManager {
    boolean newGameProfileRequest();

    boolean logInRequest();

    void logOutRequest();

    void setGameLetterLable();

    int setTargetScore(String level);

    void loadGameRequest();

    void saveGameRequest();
}
