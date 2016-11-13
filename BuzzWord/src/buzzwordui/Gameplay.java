package buzzwordui;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    Label   timerLabel;
    Label   wordLabel;
    TableView<String>   scoringTable;
    ScrollPane  scroingTablePane;
    VBox    scoreBox;
    HBox    totalBox;
    Label   totalLable;
    Label   totalScoreLable;
    VBox    targetBox;
    Label   targetLable;
    Label   targetPointsLable;


    private static final DropShadow highlight =
            new DropShadow(20, Color.GOLDENROD);

    public Gameplay(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        this.wgTemplate = appTemplate;
        layoutGUI();
    }

    public void layoutGUI(){
        timerLabel = new Label("<u>Time Remaining</u>: 40 seconds");
        //  timerLabel.textProperty().bind(valueProperty);

        topBottomBox.getChildren().add(timerLabel);

        wordLabel = new Label("B U");
        //  timerLabel.textProperty().bind(valueProperty);

        scoringTable = new TableView<>();
        scoringTable.setEditable(true);
        TableColumn words = new TableColumn("Words");
        TableColumn score = new TableColumn("Score");
        scoringTable.getColumns().addAll(words, score);
        scoringTable.setMaxSize(100, 300);
        scoringTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        scroingTablePane = new ScrollPane();
        scroingTablePane.setContent(scoringTable);
        scroingTablePane.setMaxSize(110, 300);
        scroingTablePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroingTablePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        totalLable = new Label("TOTAL");

        totalScoreLable = new Label("40");

        totalBox = new HBox();
        totalBox.getChildren().addAll(totalLable, totalScoreLable);

        scoreBox = new VBox();
        scoreBox.getChildren().addAll(scroingTablePane,totalBox);

        targetLable = new Label("Target");

        targetPointsLable = new Label("75 points");

        targetBox = new VBox();
        targetBox.getChildren().addAll(targetLable, targetPointsLable);


        rightBox.getChildren().addAll(wordLabel,scoreBox,targetBox);

    }

}
