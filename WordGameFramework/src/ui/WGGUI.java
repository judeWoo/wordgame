package ui;

import com.guigarage.flatterfx.FlatterFX;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import wgcomponents.WGStyle;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 10/29/2016.
 */
public class WGGUI implements WGStyle {

    protected Stage     primaryStage;     // the application window
    protected Scene     primaryScene;     // the scene graph
    protected BorderPane appPane;          // the root node in the scene graph, to organize the containers
    protected StackPane basePane;          // base pane that merges the basic paintings.
    protected HBox      baseBox;            //where appPane lies
    protected Label     baseLeftLabel;       //base Label
    protected Label     baseRightLabel;      //base Label
    protected VBox      centerBox;
    protected VBox      leftBox;
    protected VBox      bottomBox;
    protected String    applicationTitle;   //application Title
    protected Label     guiHeadingLabel;   // workspace (GUI) heading label
    protected VBox      topBox;          // conatainer to display the heading
    protected StackPane leftPane;
    protected StackPane createProfilePane;
    protected Pane exitPane;
    protected StackPane bottomPane;
    protected VBox    scoreBox;
    protected HBox    totalBox;
    protected static ScrollPane scroingTablePane;
    protected static Label remainingLabel;
    protected static Label timeLabel;
    protected static Pane  pauseButtonPane;
    protected static Line[] bottomPauseButton;
    protected static Polygon bottomPlayButton;
    protected static Pane gamePane;
    protected static VBox      rightBox;
    protected static HBox      topBottomBox;
    protected static Label     modeLabel;
    protected static Label     levelLabel;
    protected static Button createProfile;
    protected static Button    login;
    protected static Button    start;
    protected static Button    home;
    protected static ComboBox  selectMode;
    protected static Circle[][] gameLetters; // circle labeled letters on the grid
    protected static Line[][] hLettersLines;
    protected static Line[][] vLettersLines;
    protected static Label[][] gameLettersLabel;
    protected static Label   wordLabel;
    protected static TableView<String> scoringTable;
    protected static Label   totalLable;
    protected static Label   totalScoreLable;
    protected static VBox    targetBox;
    protected static Label   targetLable;
    protected static Label   targetPointsLable;


    private int appSpecificWindowWidth;  // optional parameter for window width that can be set by the application
    private int appSpecificWindowHeight; // optional parameter for window height that can be set by the application

    private static final DropShadow highlight =
            new DropShadow(20, Color.GOLDENROD);

    public WGGUI(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        this.appSpecificWindowWidth = appSpecificWindowWidth;
        this.appSpecificWindowHeight = appSpecificWindowHeight;
        this.primaryStage = primaryStage;
        this.applicationTitle = applicationTitle;
        initializeWindow();                     // start the app window (without the application-specific workspace)
        initStyle();
        initGrid();
        makeExitButton();
        initGamePlay();
    }

    public WGGUI(){

    }

    public void initializeWindow(){
        primaryStage.setTitle(applicationTitle);

        bottomPlayButton = new Polygon();
        bottomPlayButton.setFill(Paint.valueOf("#FFFFFF"));
        bottomPlayButton.getPoints().addAll(new Double[]{
                5.0, 5.0,
                5.0, 45.0,
                45.0, 25.0
        });
        bottomPlayButton.setId("bottom-button");

        bottomPauseButton = new Line[2];

        pauseButtonPane = new Pane();
        pauseButtonPane.setMaxSize(50, 50);

        for (int i = 0; i < 2; i++){
            bottomPauseButton[i] = new Line();
            bottomPauseButton[i].setStrokeWidth(10);
            bottomPauseButton[i].setStroke(Paint.valueOf("#FFFFFF"));
            bottomPauseButton[i].setStartX(15+i*15);
            bottomPauseButton[i].setStartY(0);
            bottomPauseButton[i].setEndX(15+i*15);
            bottomPauseButton[i].setEndY(35);
            pauseButtonPane.getChildren().add(bottomPauseButton[i]);
        }

        bottomPane = new StackPane();
        bottomPane.setMaxSize(50, 50);
        bottomPane.getChildren().addAll(bottomPlayButton, pauseButtonPane);
        pauseButtonPane.setVisible(false);

        topBottomBox = new HBox();

        modeLabel = new Label("Dictionary");
        modeLabel.setVisible(false);
        modeLabel.setId("modelabel");

        levelLabel = new Label("Level 4");
        levelLabel.setVisible(false);

        gamePane = new Pane();

        centerBox = new VBox();
        centerBox.setSpacing(10);
        centerBox.getChildren().addAll(gamePane);
        centerBox.setAlignment(Pos.TOP_CENTER);

        leftBox = new VBox();
        leftBox.setSpacing(10);
        leftBox.setMaxWidth(200);
        leftBox.setAlignment(Pos.TOP_CENTER);

        rightBox = new VBox();
        rightBox.setSpacing(10);
        rightBox.setAlignment(Pos.TOP_RIGHT);

        bottomBox = new VBox();
        bottomBox.setSpacing(10);
        bottomBox.setAlignment(Pos.TOP_CENTER);
        bottomBox.getChildren().addAll(levelLabel, bottomPane);

        appPane = new BorderPane();
        appPane.setLeft(leftBox);
        appPane.setRight(rightBox);
        appPane.setBottom(bottomBox);
        appPane.setCenter(centerBox);

        baseBox = new HBox();
        baseLeftLabel = new Label();
        baseRightLabel = new Label();
        baseBox.getChildren().addAll(baseLeftLabel,baseRightLabel);

        basePane = new StackPane();
        basePane.getChildren().addAll(baseBox, appPane);

        exitPane = new Pane();
        exitPane.setPrefSize(30, 30);

        guiHeadingLabel = new Label("!! BuzzWord !!");
        guiHeadingLabel.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {guiHeadingLabel.setEffect(highlight);});
        guiHeadingLabel.setId("Heading");

        remainingLabel = new Label("Time Remaining");
        remainingLabel.setVisible(false);
        remainingLabel.setId("remaining");

        timeLabel = new Label("40 seconds");
        timeLabel.setVisible(false);
        timeLabel.setId("timer");

        topBottomBox.getChildren().addAll(modeLabel, remainingLabel, timeLabel);
        topBottomBox.setSpacing(10);

        topBox = new VBox();
        topBox.getChildren().addAll(exitPane, guiHeadingLabel, topBottomBox);
        topBox.setSpacing(10);
        topBox.setAlignment(Pos.CENTER);

        appPane.setTop(topBox);

        createProfile = new Button("Create Profile");
        createProfile.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        createProfilePane = new StackPane();
        createProfilePane.setMaxSize(Double.MAX_VALUE, 30);
        createProfilePane.getChildren().add(createProfile);

        login = new Button("Login");
        login.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        selectMode = new ComboBox();
        selectMode.getItems().addAll(
                "English Dictonary",
                "Places",
                "Sciences",
                "Famous People"
        );
        selectMode.setValue("Select Mode");
        selectMode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        selectMode.setVisible(false);

        start = new Button("Start Playing");
        start.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        start.setVisible(false);

        home = new Button("Home");
        home.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        home.setVisible(false);

        leftPane = new StackPane();
        leftPane.setMaxSize(Double.MAX_VALUE, 30);
        leftPane.getChildren().addAll(login, selectMode, home);

        leftBox.getChildren().addAll(createProfilePane, leftPane, start);

        primaryScene = appSpecificWindowWidth < 1 || appSpecificWindowHeight < 1 ? new Scene(basePane)
                : new Scene(basePane,
                appSpecificWindowWidth,
                appSpecificWindowHeight);
       primaryScene.getStylesheets().add("css/buzzword_style.css");

        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    @Override
    public void initStyle() {
        basePane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        baseLeftLabel.setStyle("-fx-background-color: #979CA9;");
        baseLeftLabel.setPrefSize(100,550);
        baseRightLabel.setStyle("-fx-background-color: #A294AC;");
        baseRightLabel.setPrefSize(700,550);
    }

    public void initGrid(){
        gameLettersLabel = new Label[4][4];
        gameLetters = new Circle[4][4];
        hLettersLines = new Line[4][3];
        vLettersLines = new Line[3][4];

        int ySpacing = 40;
        int initSpacing = 120;
        int k = 0;

        for (int i = 0; i < 4; i++){
            int xRadius = 0;
            for (int j = 0; j < 4; j++) {
                gameLetters[i][j] = new Circle();
                gameLettersLabel[i][j] = new Label("7");
                gameLettersLabel[i][j].setPrefSize(30, 30);
                gameLettersLabel[i][j].setLayoutX(xRadius + initSpacing-4);
                gameLettersLabel[i][j].setLayoutY(ySpacing-15);
                gameLetters[i][j].setRadius(30);
                gameLetters[i][j].setCenterX(xRadius + initSpacing);
                gameLetters[i][j].setCenterY(ySpacing);;
                gameLetters[i][j].setFill(Color.valueOf("#979CA9"));
                gameLetters[i][j].setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 )");
                xRadius = xRadius +100;
            }
            ySpacing = ySpacing +100;
        }
        for (int i = 0; i < 3; i++) {
            for (int j=0; j <4; j++){
                vLettersLines[i][j] = new Line();
                vLettersLines[i][j].startXProperty().bind(gameLetters[i][j].centerXProperty());
                vLettersLines[i][j].startYProperty().bind(gameLetters[i][j].centerYProperty());
                vLettersLines[i][j].endXProperty().bind(gameLetters[i+1][j].centerXProperty());
                vLettersLines[i][j].endYProperty().bind(gameLetters[i+1][j].centerYProperty());
                vLettersLines[i][j].setVisible(false);
                gamePane.getChildren().add(vLettersLines[i][j]);
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j=0; j <3; j++){
                hLettersLines[i][j] = new Line();
                hLettersLines[i][j].startXProperty().bind(gameLetters[i][j].centerXProperty());
                hLettersLines[i][j].startYProperty().bind(gameLetters[i][j].centerYProperty());
                hLettersLines[i][j].endXProperty().bind(gameLetters[i][j+1].centerXProperty());
                hLettersLines[i][j].endYProperty().bind(gameLetters[i][j+1].centerYProperty());
                hLettersLines[i][j].setVisible(false);
                gamePane.getChildren().add(hLettersLines[i][j]);
                gamePane.getChildren().add(gameLetters[i][j]);
                gamePane.getChildren().add(gameLettersLabel[i][j]);
            }
            gamePane.getChildren().add(gameLetters[i][3]);
            gamePane.getChildren().add(gameLettersLabel[i][3]);
        }
    }

    public void makeExitButton(){
        Line exitLine1 = new Line();
        exitLine1.setStroke(Paint.valueOf("FFFFFF"));
        exitLine1.setStrokeWidth(10);
        exitLine1.setStartX(780);
        exitLine1.setStartY(15);
        exitLine1.setEndX(790);
        exitLine1.setEndY(25);
        Line exitLine2 = new Line();
        exitLine2.setStroke(Paint.valueOf("FFFFFF"));
        exitLine2.setStartX(790);
        exitLine2.setStartY(15);
        exitLine2.setEndX(780);
        exitLine2.setEndY(25);
        exitLine2.setStrokeWidth(10);
        exitLine1.setOnMouseClicked(event -> {
            System.exit(0);
        });
        exitLine2.setOnMouseClicked(event -> {
            System.exit(0);
        });
        exitPane.getChildren().addAll(exitLine1, exitLine2);
    }

    public void initGamePlay(){
        wordLabel = new Label("B U");
        //  timerLabel.textProperty().bind(valueProperty);
        wordLabel.setVisible(false);

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
        scroingTablePane.setVisible(false);


        totalLable = new Label("TOTAL");
        totalLable.setVisible(false);

        totalScoreLable = new Label("40");
        totalScoreLable.setVisible(false);

        totalBox = new HBox();
        totalBox.getChildren().addAll(totalLable, totalScoreLable);

        scoreBox = new VBox();
        scoreBox.getChildren().addAll(scroingTablePane,totalBox);

        targetLable = new Label("Target");
        targetLable.setVisible(false);

        targetPointsLable = new Label("75 points");
        targetPointsLable.setVisible(false);

        targetBox = new VBox();
        targetBox.getChildren().addAll(targetLable, targetPointsLable);

        rightBox.getChildren().addAll(wordLabel,scoreBox,targetBox);

    }

}
