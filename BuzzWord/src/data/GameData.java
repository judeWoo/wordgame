package data;

import buzzwordui.LoginPage;
import wgcomponents.WGData;
import wgtemplate.WGTemplate;

import java.util.HashSet;
import java.util.Set;

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
        return new LoginPage().getPwField().getText();
    }

    private String setUserID() {
        return new LoginPage().getIdField().getText();
    }

    @Override
    public void reset() {
        this.userID = null;
        this.passWord = null;
    }


}
