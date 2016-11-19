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
    public WGTemplate      wgTemplate;

    public GameData(WGTemplate wgTemplate){
        this(wgTemplate, false);
    }

    public GameData(){
        reset();
        init();
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
    }

    private String setPassWord() {
        return new CreateProfile(GameData.this).getPwField().getText();
    }

    private String setUserID() {
        return new CreateProfile(GameData.this).getIdField().getText();
    }

    @Override
    public void reset() {
        this.userID = null;
        this.passWord = null;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassWord() {
        return passWord;
    }
}
