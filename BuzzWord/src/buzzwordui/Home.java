package buzzwordui;

import controller.BuzzWordController;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.WGDialogSingleton;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class Home extends WGGUI{

    BuzzWordController controller;

    public Home(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        layoutGUI();
        shortCutsSetting();
    }

    public Home(BuzzWordController buzzWordController){}

    public Home(){
        initGame();
    }

    public void layoutGUI(){
        WGDialogSingleton wgDialogSingleton = WGDialogSingleton.getSingleton();
        login.setVisible(true);
        home.setVisible(false);
        pauseLabel.setVisible(false);
        profileSetting.setOnMouseClicked(event -> {
            clearPopUps();
            ProfileSetting.setStage(new Stage());
            new ProfileSetting();
        });
        createProfile.setOnMouseClicked(event -> {
            clearPopUps();
            CreateProfile.setStage(new Stage());
            new CreateProfile();
        });
        arrowPane.setOnMouseClicked(event -> {
            clearPopUps();
            wgDialogSingleton.show("Log Out?", "Press Enter for LOG OUT OR Press ESC for go back.");
            if (WGDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                controller = new BuzzWordController();
                controller.logOutRequest();
                initGame();
                createProfile.setVisible(true);
                arrowPane.setVisible(false);
                userButton.setVisible(false);
                login.setVisible(true);
                selectMode.setVisible(false);
                selectMode.setValue(new String("Select Mode"));
                start.setVisible(false);
            }
            if (WGDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {
                //Do Nothing
            }
        });
        helpButton.setOnMouseClicked(event -> {
            clearPopUps();
            HelpScreen.setStage(new Stage());
            new HelpScreen();
        });
        login.setOnMouseClicked(event -> {
            clearPopUps();
            LoginPage.setStage(new Stage());
            new LoginPage();
        });
        start.setOnMouseClicked(event -> {
            clearPopUps();
            controller = new BuzzWordController();
            if (selectMode.getValue().toString() != "Select Mode")
            {
                modeLabel.setText(selectMode.getValue().toString());
                controller.buildDictionary(selectMode.getValue().toString()); //make Dictionary
                new LevelSelection();
            }
        });
        exitLine1.setOnMouseClicked(event -> {
            clearPopUps();
            wgDialogSingleton.show("Exit?", "Press Enter for exit OR Press ESC for go back.");
            if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection()))
            { System.exit(0);}
            if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())){
            }
        });
        exitLine2.setOnMouseClicked(event -> {
            clearPopUps();
            wgDialogSingleton.show("Exit?", "Press Enter for exit OR Press ESC for go back.");
            if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection()))
            { System.exit(0);}
            if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())){
            }
        });
        if (userButton.getText() != ""){
            login.setVisible(false);
            selectMode.setVisible(true);
            start.setVisible(true);
            profileSetting.setVisible(true);
        }
    }

    public void reinitGrid(){
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
                gameLettersLabel[i][j].setOnMousePressed(null);
                gameLetters[i][j].setStyle(null);
                gameLetters[i][j].setFill(Color.valueOf("#979CA9"));
                gameLetters[i][j].setStyle("-fx-effect: dropshadow(gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
                gameLetters[i][j].setVisible(true);
            }
        }
    }

    @Override
    public void initLetter() {
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
                gameLettersLabel[i][j].setVisible(true);
                gameLettersLabel[i][j].setText("");
            }
        }
        super.initLetter();
    }

    public void initGame() {
        layoutGUI();
        hideLines();
        reinitGrid();
        initLetter();
        shortCutsSetting();
        scoreBarPane.setVisible(false);
        levelLabel.setVisible(false);
        modeLabel.setVisible(false);
        timeLabel.setVisible(false);
        remainingLabel.setVisible(false);
        bottomPlayButton.setVisible(false);
        pauseButtonPane.setVisible(false);
        targetPointsLable.setVisible(false);
        targetLable.setVisible(false);
        timeBoxPane.setVisible(false);
        wordBoxPane.setVisible(false);
        targetBoxPane.setVisible(false);
        profileSetting.setVisible(false);
    }

    public void clearPopUps() {
        if (ProfileSetting.getStage().isShowing())
            ProfileSetting.getStage().close();
        if (CreateProfile.getStage().isShowing())
            CreateProfile.getStage().close();
        if (HelpScreen.getStage().isShowing())
            HelpScreen.getStage().close();
        if (LoginPage.getStage().isShowing())
            LoginPage.getStage().close();
    }

    public void shortCutsSetting() {
        WGDialogSingleton wgDialogSingleton = WGDialogSingleton.getSingleton();
        primaryScene.setOnKeyPressed(event -> {
            if (CREATE.match(event) && createProfile.isVisible()){
                clearPopUps();
                CreateProfile.setStage(new Stage());
                new CreateProfile();
            }
            if (LOGINOUT.match(event) && arrowPane.isVisible()){
                clearPopUps();
                wgDialogSingleton.show("Log Out?", "Press Enter for LOG OUT OR Press ESC for go back.");
                if (WGDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                    controller = new BuzzWordController();
                    controller.logOutRequest();
                    initGame();
                    createProfile.setVisible(true);
                    arrowPane.setVisible(false);
                    userButton.setVisible(false);
                    login.setVisible(true);
                    selectMode.setVisible(false);
                    selectMode.setValue(new String("Select Mode"));
                    start.setVisible(false);
                }
                if (WGDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {
                    //Do Nothing
                }
            }
            if (LOGINOUT.match(event) && login.isVisible()){
                clearPopUps();
                LoginPage.setStage(new Stage());
                new LoginPage();
            }
            if (START.match(event) && start.isVisible()){
                clearPopUps();
                controller = new BuzzWordController();
                if (selectMode.getValue().toString() != "Select Mode")
                {
                    modeLabel.setText(selectMode.getValue().toString());
                    controller.buildDictionary(selectMode.getValue().toString()); //make Dictionary
                    new LevelSelection();
                }
            }
            if (QUIT.match(event) && (exitLine1.isVisible() || exitLine2.isVisible())){
                clearPopUps();
                wgDialogSingleton.show("Exit?", "Press Enter for exit OR Press ESC for go back.");
                if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection()))
                { System.exit(0);}
                if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())){
                    //Do Nothing
                }
            }
            if (HOME.match(event) && home.isVisible()){
                clearPopUps();
                wgDialogSingleton.show("Back Home?", "Press Enter for Go Home OR Press ESC for cancel.");
                if (wgDialogSingleton.YES.equals(wgDialogSingleton.getSelection())) {
                    new Home();
                }
                if (wgDialogSingleton.NO.equals(wgDialogSingleton.getSelection())) {
                    //Do NOTHING
                }
            }
        });
    }
}
