package data;

import buzzwordui.CreateProfile;
import buzzwordui.LoginPage;
import wgcomponents.WGData;
import wgtemplate.WGTemplate;

/**
 * Created by Kun on 11/17/2016.
 */
public class GameData implements WGData {

    private String         userID;
    private String         passWord;
    private int           level;
    private int           score;
    public WGTemplate      wgTemplate;

    public GameData(WGTemplate wgTemplate){
        this(wgTemplate, false);
    }

    public GameData(){
        reset();
    }

    public GameData(WGTemplate wgTemplate, boolean initgame) {
        if(initgame){
            this.wgTemplate = wgTemplate;
        }
        else {
            this.wgTemplate = wgTemplate;
        }
    }

    public void init() {
        this.userID = setUserID();
        this.passWord = setPassWord();
        this.level = 1; //tbd
        this.score = 0; //tbd
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

    public GameData setLevel(int level){
        this.level = level;
        return this;
    }

    public GameData setScore(int score){
        this.score = score;
        return this;
    }

    @Override
    public void reset() {
        this.userID = null;
        this.passWord = null;
        this.level = 1;
        this.score = 0;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassWord() {
        return passWord;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }
}
