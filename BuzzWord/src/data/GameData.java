package data;

import buzzwordui.CreateProfile;
import wgcomponents.WGData;
import wgtemplate.WGTemplate;

/**
 * Created by Jude Hokyoon Woo on 11/17/2016.
 */
public class GameData implements WGData {

    private String userID;
    private String passWord;
    private int aModeLevel;
    private int bModeLevel;
    private int cModeLevel;
    private int dModeLevel;
    public WGTemplate wgTemplate;

    public GameData(WGTemplate wgTemplate) {
        this(wgTemplate, false);
    }

    public GameData() {
        reset();
    }

    public GameData(WGTemplate wgTemplate, boolean initgame) {
        if (initgame) {
            this.wgTemplate = wgTemplate;
        } else {
            this.wgTemplate = wgTemplate;
        }
    }

    public void init() {
        this.userID = setUserID();
        this.passWord = setPassWord();
        this.aModeLevel = 1;
        this.bModeLevel = 1;
        this.cModeLevel = 1;
        this.dModeLevel = 1;
    }

    private String setPassWord() {
        return new CreateProfile(GameData.this).getPwField().getText();
    }

    private String setUserID() {
        return new CreateProfile(GameData.this).getIdField().getText();
    }

    public GameData setUserID(String string) {
        this.userID = string;
        return this;
    }

    public GameData setPassWord(String string) {
        this.passWord = string;
        return this;
    }

    public void setaModeLevel(int aModeLevel) {
        this.aModeLevel = aModeLevel;
    }

    public void setbModeLevel(int bModeLevel) {
        this.bModeLevel = bModeLevel;
    }

    public void setcModeLevel(int cModeLevel) {
        this.cModeLevel = cModeLevel;
    }

    public void setdModeLevel(int dModeLevel) {
        this.dModeLevel = dModeLevel;
    }

    @Override
    public void reset() {
        this.userID = null;
        this.passWord = null;
        this.aModeLevel = 1;
        this.bModeLevel = 1;
        this.cModeLevel = 1;
        this.dModeLevel = 1;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassWord() {
        return passWord;
    }

    public int getaModeLevel() {
        return aModeLevel;
    }

    public int getbModeLevel() {
        return bModeLevel;
    }

    public int getcModeLevel() {
        return cModeLevel;
    }

    public int getdModeLevel() {
        return dModeLevel;
    }
}
