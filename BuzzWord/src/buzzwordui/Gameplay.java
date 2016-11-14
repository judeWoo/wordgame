package buzzwordui;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ui.WGGUI;
import wgcomponents.WGSpaceComponents;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/6/2016.
 */
public class Gameplay extends WGGUI{

    WGTemplate wgTemplate;


    private static final DropShadow highlight =
            new DropShadow(20, Color.GOLDENROD);

    public Gameplay(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        this.wgTemplate = appTemplate;
    }

    public Gameplay(){
        layoutGUI();
        initGrid();
        showLines();
    }

    public void layoutGUI(){
        //  timerLabel.textProperty().bind(valueProperty);
        levelLabel.setVisible(true);
        scroingTablePane.setVisible(true);
        totalScoreLable.setVisible(true);
        timeLabel.setVisible(true);
        remainingLabel.setVisible(true);
        totalLable.setVisible(true);
        wordLabel.setVisible(true);
    }

    public void showLines(){
        for (int i = 0; i < 3; i++) {
            for (int j=0; j <4; j++){
                vLettersLines[i][j].setVisible(true);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j=0; j <3; j++){
                hLettersLines[i][j].setVisible(true);
            }
        }
    }


}
