package controller;

import ui.WGGUI;
import wgtemplate.WGTemplate;

/**
 * Created by Kun on 11/17/2016.
 */
public class BuzzWordController implements FileManager {

    public enum GameState {
        UNINITIALIZED,
        INITIALIZED_UNMODIFIED,
        INITIALIZED_MODIFIED,
        ENDED
    }

    private WGTemplate wgTemplate;
    private WGGUI wggui;

    public BuzzWordController(WGTemplate wgTemplate) {
        this.wgTemplate = wgTemplate;

    }

    public void start(){

    }


    @Override
    public void newGameProfileRequest() {

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
}
