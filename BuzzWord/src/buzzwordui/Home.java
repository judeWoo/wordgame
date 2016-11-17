package buzzwordui;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class Home extends WGGUI{

    Gameplay gameplay;

    public Home(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        layoutGUI();
        drawScoreBox();
    }

    public Home(){
        layoutGUI();
        hideLines();
        reinitGrid();
        initLetter();
        levelLabel.setVisible(false);
        modeLabel.setVisible(false);
        timeLabel.setVisible(false);
        remainingLabel.setVisible(false);
        wordLabel.setVisible(false);
        bottomPlayButton.setVisible(false);
        pauseButtonPane.setVisible(false);
        targetPointsLable.setVisible(false);
        targetLable.setVisible(false);
    }

    public void layoutGUI(){
        login.setVisible(true);
        home.setVisible(false);
        login.setOnMouseClicked(event -> {
            new LoginPage();
        });
        start.setOnMouseClicked(event -> {
            if (selectMode.getValue().toString() != "Select Mode")
            {
                modeLabel.setText(selectMode.getValue().toString());
                new LevelSelection();
            }
        });
        if (createProfile.getText() != "Create Profile"){
            login.setVisible(false);
            selectMode.setVisible(true);
            start.setVisible(true);
        }
    }

    public void hideLines(){
        for (int i = 0; i < 3; i++) {
            for (int j=0; j <4; j++){
                vLettersLines[i][j].setVisible(false);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j=0; j <3; j++){
                hLettersLines[i][j].setVisible(false);
            }
        }
    }
    public void reinitGrid(){
        for(int i =0; i < 4; i++){
            for (int j=0; j <4; j++) {
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

    public void drawScoreBox(){
        HBox scoreBoardBox = new HBox();
        VBox scoreLeftBox = new VBox();
        scoreLeftBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#A294AC"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        VBox scoreRightBox = new VBox();
        scoreRightBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#A294AC"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Label label = new Label();
        label.setPrefSize(105, 150);
        label.setStyle("-fx-background-color: #979CA9;");
        Label label1 = new Label();
        label1.setPrefSize(30, 150);
        label1.setStyle("-fx-background-color: #979CA9;");
        Label label2 = new Label("Total");
        label2.setPrefSize(105, 30);
        label2.setStyle("-fx-background-color: #8F94A1;");
        scoreLeftBox.getChildren().addAll(label, label2);
        scoreRightBox.getChildren().add(label1);
        scoreBoardBox.getChildren().addAll(scoreLeftBox, scoreRightBox);
        ScrollPane scoreBar = new ScrollPane();
        scoreBar.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scoreBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scoreBar.setContent(scoreBoardBox);
        Pane emptyBox = new Pane();
        emptyBox.setPrefSize(30, 30);
        Pane scoreBarPane = new HBox();
        scoreBarPane.getChildren().addAll(emptyBox, scoreBar);
        rightBox.getChildren().addAll(scoreBarPane);
    }
}
