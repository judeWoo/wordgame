package data;

import buzzwordui.CreateProfile;
import wgcomponents.WGData;
import wgtemplate.WGTemplate;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Jude Hokyoon Woo on 11/17/2016.
 */
public class GameData implements WGData {

    private String userID;
    private String passWord;
    private String userName;
    private int aModeLevel;
    private int bModeLevel;
    private int cModeLevel;
    private int dModeLevel;
    private ArrayList<Integer> aModeLevelandBest;
    private ArrayList<Integer> bModeLevelandBest;
    private ArrayList<Integer> cModeLevelandBest;
    private ArrayList<Integer> dModeLevelandBest;
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
        this.userID = CreateProfile.getIdField().getText();
        this.passWord = CreateProfile.getPwField().getText();
        this.aModeLevel = 1;
        this.bModeLevel = 1;
        this.cModeLevel = 1;
        this.dModeLevel = 1;
        this.aModeLevelandBest = new ArrayList<>();
        this.bModeLevelandBest = new ArrayList<>();
        this.cModeLevelandBest = new ArrayList<>();
        this.dModeLevelandBest = new ArrayList<>();
        for (int i =0; i < 8; i++) {
            addAModeLevelandBest(0);
            addBModeLevelandBest(0);
            addCModeLevelandBest(0);
            addDModeLevelandBest(0);
        }
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
        this.aModeLevelandBest = new ArrayList<>();
        this.bModeLevelandBest = new ArrayList<>();
        this.cModeLevelandBest = new ArrayList<>();
        this.dModeLevelandBest = new ArrayList<>();
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

    public ArrayList<Integer> getaModeLevelandBest() {
        return aModeLevelandBest;
    }

    public ArrayList<Integer> getbModeLevelandBest() {
        return bModeLevelandBest;
    }

    public ArrayList<Integer> getcModeLevelandBest() {
        return cModeLevelandBest;
    }

    public ArrayList<Integer> getdModeLevelandBest() {
        return dModeLevelandBest;
    }

    public void addAModeLevelandBest(int score){
        aModeLevelandBest.add(score);
    }
    public void addBModeLevelandBest(int score){
        bModeLevelandBest.add(score);
    }
    public void addCModeLevelandBest(int score){
        cModeLevelandBest.add(score);
    }
    public void addDModeLevelandBest(int score){
        dModeLevelandBest.add(score);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
