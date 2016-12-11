package ui;

import javafx.geometry.Insets;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import wgcomponents.WGStyle;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 10/29/2016.
 */
public class WGGUI implements WGStyle {

    protected BorderPane appPane;          // the root node in the scene graph, to organize the containers
    protected StackPane basePane;          // base pane that merges the basic paintings.
    protected HBox baseBox;            //where appPane lies
    protected Label baseLeftLabel;       //base Label
    protected Label baseRightLabel;      //base Label
    protected VBox centerBox;
    protected VBox leftBox;
    protected VBox bottomBox;
    protected String applicationTitle;   //application Title
    protected Label guiHeadingLabel;   // workspace (GUI) heading label
    protected VBox topBox;          // conatainer to display the heading
    protected StackPane leftPane;
    protected StackPane createProfilePane;
    protected Pane exitPane;
    protected StackPane startPane;
    protected static Button replayLevel;
    protected static Button helpButton;
    protected static Button startNextLevel;
    protected static Stage primaryStage;     // the application window
    protected static StackPane bottomPane;
    protected static Label timerLabel;
    protected static Label totalScoreLabel;
    protected static Scene primaryScene;     // the scene graph
    protected static Label pauseLabel;
    protected static Button userButton;
    protected static Button profileSetting;
    protected static VBox scoreLeftBox;
    protected static VBox scoreRightBox;
    protected static VBox scoreBarPane;
    protected static Pane wordBoxPane;
    protected static HBox wordBox;
    protected static Pane timeBoxPane;
    protected static HBox timeBox;
    protected static Pane arrowPane;
    protected static Label remainingLabel;
    protected static Label timeLabel;
    protected static Pane pauseButtonPane;
    protected static Line[] bottomPauseButton;
    protected static Polygon bottomPlayButton;
    protected static Pane gamePane;
    protected static VBox rightBox;
    protected static HBox topBottomBox;
    protected static Label modeLabel;
    protected static Label levelLabel;
    protected static Button createProfile;
    protected static Button login;
    protected static Button start;
    protected static Button home;
    protected static ComboBox selectMode;
    protected static Circle[][] gameLetters; // circle labeled letters on the grid
    protected static Line[][] hLettersLines;
    protected static Line[][] vLettersLines;
    protected static Line[][] dRLettersLines;
    protected static Line[][] dLLettersLines;
    protected static Label[][] gameLettersLabel;
    protected static VBox targetBox;
    protected static Pane targetBoxPane;
    protected static Label targetLable;
    protected static Label targetPointsLable;
    protected static Line exitLine1;
    protected static Line exitLine2;

    private int appSpecificWindowWidth;  // optional parameter for window width that can be set by the application
    private int appSpecificWindowHeight; // optional parameter for window height that can be set by the application

    private static final DropShadow highlight =
            new DropShadow(40, Color.LIMEGREEN);

    public WGGUI(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        this.appSpecificWindowWidth = appSpecificWindowWidth;
        this.appSpecificWindowHeight = appSpecificWindowHeight;
        this.primaryStage = primaryStage;
        this.applicationTitle = applicationTitle;
        drawScoreBox();                         // draw score box;
        initializeWindow();                     // start the app window (without the application-specific workspace)
        initStyle();
        initGrid();
        makeExitButton();
        initGamePlay();
        initLetter();
        drawArrow();
    }
    //Default constructer for controllers
    public WGGUI() {

    }

    public void initializeWindow() {
        primaryStage.setTitle(applicationTitle);

        bottomPlayButton = new Polygon();
        bottomPlayButton.setFill(Paint.valueOf("#FFFFFF"));
        bottomPlayButton.getPoints().addAll(5.0, 5.0,
                5.0, 35.0,
                45.0, 20.0);
        bottomPlayButton.setId("bottom-button");

        bottomPauseButton = new Line[2];

        pauseButtonPane = new Pane();
        pauseButtonPane.setPrefSize(40, 40);

        for (int i = 0; i < 2; i++) {
            bottomPauseButton[i] = new Line();
            bottomPauseButton[i].setStrokeWidth(10);
            bottomPauseButton[i].setStroke(Paint.valueOf("#FFFFFF"));
            bottomPauseButton[i].setStartX(10 + i * 15);
            bottomPauseButton[i].setStartY(5);
            bottomPauseButton[i].setEndX(10 + i * 15);
            bottomPauseButton[i].setEndY(30);
            pauseButtonPane.getChildren().add(bottomPauseButton[i]);
        }

        bottomPane = new StackPane();
        bottomPane.setMaxSize(40, 40);
        bottomPane.getChildren().addAll(bottomPlayButton, pauseButtonPane);
        bottomPlayButton.setVisible(false);
        pauseButtonPane.setVisible(false);

        topBottomBox = new HBox();

        modeLabel = new Label("Dictionary");
        modeLabel.setUnderline(true);
        modeLabel.setVisible(false);
        modeLabel.setId("modelabel");

        levelLabel = new Label("Level 4");
        levelLabel.setUnderline(true);
        levelLabel.setId("level");
        levelLabel.setVisible(false);

        gamePane = new Pane();

        centerBox = new VBox();
        centerBox.setSpacing(10);
        centerBox.getChildren().addAll(gamePane);
        centerBox.setAlignment(Pos.TOP_CENTER);

        leftBox = new VBox();
        leftBox.setSpacing(10);
        leftBox.setPrefWidth(200);
        leftBox.setAlignment(Pos.TOP_CENTER);

        rightBox = new VBox();
        rightBox.setSpacing(10);
        rightBox.setPrefWidth(200);
        rightBox.setAlignment(Pos.TOP_LEFT);

        bottomBox = new VBox();
        bottomBox.setSpacing(5);
        bottomBox.setAlignment(Pos.TOP_CENTER);
        bottomBox.getChildren().addAll(levelLabel, bottomPane);

        appPane = new BorderPane();
        appPane.setPrefSize(800, 550);
        appPane.setLeft(leftBox);
        appPane.setRight(rightBox);
        appPane.setBottom(bottomBox);
        appPane.setCenter(centerBox);

        baseBox = new HBox();
        baseLeftLabel = new Label();
        baseRightLabel = new Label();
        baseBox.getChildren().addAll(baseLeftLabel, baseRightLabel);

        pauseLabel = new Label("Pause");
        pauseLabel.setId("pauselabel");
        pauseLabel.setVisible(false);

        basePane = new StackPane();
        basePane.getChildren().addAll(baseBox, appPane, pauseLabel);

        exitPane = new Pane();
        exitPane.setPrefSize(30, 30);

        guiHeadingLabel = new Label("!! BuzzWord !!");
        guiHeadingLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            guiHeadingLabel.setEffect(highlight);
        });
        guiHeadingLabel.setId("Heading");

        remainingLabel = new Label("Time Remaining:");
        remainingLabel.setUnderline(true);
        remainingLabel.setVisible(false);
        remainingLabel.setId("remaining");

        timerLabel = new Label("60");
        timerLabel.setVisible(false);
        timerLabel.setId("time");

        timeLabel = new Label("seconds");
        timeLabel.setVisible(false);
        timeLabel.setId("timer");

        timeBox = new HBox();
        timeBox.setSpacing(5);
        timeBox.getChildren().addAll(remainingLabel, timerLabel, timeLabel);
        timeBox.setPadding(new Insets(8));
        timeBox.setAlignment(Pos.CENTER_LEFT);

        timeBoxPane = new Pane();
        timeBoxPane.getChildren().add(timeBox);
        timeBoxPane.setPrefSize(180, 30);
        timeBoxPane.setStyle("-fx-background-color: #F3EFEF;");
        timeBoxPane.getStyleClass().add("pane");
        timeBoxPane.setVisible(false);

        Pane modePane = new StackPane();
        modePane.setMinWidth(400);
        //modePane.setStyle("-fx-background-color: #000000;");
        modePane.getChildren().add(modeLabel);

        HBox emptypane = new HBox();
        emptypane.setPrefSize(21, 30);

        topBottomBox.getChildren().addAll(modePane, timeBoxPane, emptypane);
        topBottomBox.setSpacing(0);
        topBottomBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        topBottomBox.setAlignment(Pos.TOP_RIGHT);

        topBox = new VBox();
        topBox.getChildren().addAll(exitPane, guiHeadingLabel, topBottomBox);
        topBox.setSpacing(10);
        topBox.setAlignment(Pos.CENTER);

        appPane.setTop(topBox);

        createProfile = new Button("Create Profile");
        createProfile.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        userButton = new Button("");
        userButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        userButton.setVisible(false);

        profileSetting = new Button("View/Edit Profile");
        profileSetting.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        profileSetting.setVisible(false);

        helpButton = new Button("Help");
        helpButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        helpButton.setVisible(true);

        arrowPane = new Pane();
        arrowPane.setMaxSize(Double.MAX_VALUE, 30);
        arrowPane.setVisible(false);

        createProfilePane = new StackPane();
        createProfilePane.setMaxSize(Double.MAX_VALUE, 30);
        createProfilePane.getChildren().addAll(createProfile, userButton, arrowPane);

        login = new Button("Login");
        login.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        selectMode = new ComboBox();
        selectMode.getItems().addAll(
                "English Dictionary",
                "Places",
                "Science",
                "Famous People"
        );
        selectMode.setValue("Select Mode");
        selectMode.setMaxSize(Double.MAX_VALUE, 35);
        selectMode.setPadding(new Insets(8));
        selectMode.setVisible(false);

        start = new Button("Start Playing");
        start.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        start.setVisible(false);

        startNextLevel = new Button("Start Next Level");
        startNextLevel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        startNextLevel.setVisible(false);

        replayLevel = new Button("Replay");
        replayLevel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        replayLevel.setVisible(false);

        home = new Button("Home");
        home.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        home.setVisible(false);

        leftPane = new StackPane();
        leftPane.setMaxSize(Double.MAX_VALUE, 30);
        leftPane.getChildren().addAll(login, selectMode, home);

        startPane = new StackPane();
        startPane.setMaxSize(Double.MAX_VALUE, 30);
        startPane.getChildren().addAll(start, startNextLevel);

        Pane emptyPane = new Pane();
        emptyPane.setMinWidth(200);

        leftBox.getChildren().addAll(createProfilePane, leftPane, startPane, profileSetting, replayLevel, helpButton, emptyPane);

        primaryScene = appSpecificWindowWidth < 1 || appSpecificWindowHeight < 1 ? new Scene(basePane)
                : new Scene(basePane,
                appSpecificWindowWidth,
                appSpecificWindowHeight);
        primaryScene.getStylesheets().add("css/buzzword_style.css");

        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    @Override
    public void initStyle() {
        basePane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        baseLeftLabel.setStyle("-fx-background-color: #979CA9;");
        baseLeftLabel.setPrefSize(100, 600);
        baseRightLabel.setStyle("-fx-background-color: #A294AC;");
        baseRightLabel.setPrefSize(750, 600);
    }

    public void initGrid() {
        gameLettersLabel = new Label[4][4];
        gameLetters = new Circle[4][4];
        hLettersLines = new Line[4][3];
        vLettersLines = new Line[3][4];
        dRLettersLines = new Line[3][3];
        dLLettersLines = new Line[3][3];

        int ySpacing = 40;
        int initSpacing = 50;
        int k = 0;

        for (int i = 0; i < 4; i++) {
            int xRadius = 0;
            for (int j = 0; j < 4; j++) {
                gameLetters[i][j] = new Circle();
                //bind gameletters and gameletterslabel when drag and drop
                gameLettersLabel[i][j] = new Label();
                gameLettersLabel[i][j].setPrefSize(60, 60);
                gameLettersLabel[i][j].setLayoutX(xRadius + initSpacing - 30);
                gameLettersLabel[i][j].setLayoutY(ySpacing - 30);
                gameLettersLabel[i][j].setAlignment(Pos.CENTER);
                gameLetters[i][j].setRadius(30);
                gameLetters[i][j].setCenterX(xRadius + initSpacing);
                gameLetters[i][j].setCenterY(ySpacing);
                gameLetters[i][j].setFill(Color.valueOf("#979CA9"));
                gameLetters[i][j].setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
                xRadius = xRadius + 100;
            }
            ySpacing = ySpacing + 100;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                vLettersLines[i][j] = new Line();
                vLettersLines[i][j].startXProperty().bind(gameLetters[i][j].centerXProperty());
                vLettersLines[i][j].startYProperty().bind(gameLetters[i][j].centerYProperty());
                vLettersLines[i][j].endXProperty().bind(gameLetters[i + 1][j].centerXProperty());
                vLettersLines[i][j].endYProperty().bind(gameLetters[i + 1][j].centerYProperty());
                vLettersLines[i][j].setStroke(Paint.valueOf("#FFFFFF"));
                vLettersLines[i][j].setStrokeWidth(5);
                vLettersLines[i][j].setVisible(false);
                gamePane.getChildren().add(vLettersLines[i][j]);
            }
        }

        for (int i =0; i < 3; i++){
            for (int j=0; j < 3; j++){
                dRLettersLines[i][j] = new Line();
                dRLettersLines[i][j].startXProperty().bind(gameLetters[i][j].centerXProperty());
                dRLettersLines[i][j].startYProperty().bind(gameLetters[i][j].centerYProperty());
                dRLettersLines[i][j].endXProperty().bind(gameLetters[i + 1][j+1].centerXProperty());
                dRLettersLines[i][j].endYProperty().bind(gameLetters[i + 1][j+1].centerYProperty());
                dRLettersLines[i][j].setStroke(Paint.valueOf("#FFFFFF"));
                dRLettersLines[i][j].setStrokeWidth(5);
                dRLettersLines[i][j].setVisible(false);
                gamePane.getChildren().add(dRLettersLines[i][j]);
            }
        }

        for (int i = 0; i < 4-1; i++){
            for (int j=1; j < 4; j++){
                dLLettersLines[i][j-1] = new Line();
                dLLettersLines[i][j-1].startXProperty().bind(gameLetters[i][j].centerXProperty());
                dLLettersLines[i][j-1].startYProperty().bind(gameLetters[i][j].centerYProperty());
                dLLettersLines[i][j-1].endXProperty().bind(gameLetters[i + 1][j-1].centerXProperty());
                dLLettersLines[i][j-1].endYProperty().bind(gameLetters[i + 1][j-1].centerYProperty());
                dLLettersLines[i][j-1].setStroke(Paint.valueOf("#FFFFFF"));
                dLLettersLines[i][j-1].setStrokeWidth(5);
                dLLettersLines[i][j-1].setVisible(false);
                gamePane.getChildren().add(dLLettersLines[i][j-1]);
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                hLettersLines[i][j] = new Line();
                hLettersLines[i][j].startXProperty().bind(gameLetters[i][j].centerXProperty());
                hLettersLines[i][j].startYProperty().bind(gameLetters[i][j].centerYProperty());
                hLettersLines[i][j].endXProperty().bind(gameLetters[i][j + 1].centerXProperty());
                hLettersLines[i][j].endYProperty().bind(gameLetters[i][j + 1].centerYProperty());
                hLettersLines[i][j].setStroke(Paint.valueOf("#FFFFFF"));
                hLettersLines[i][j].setStrokeWidth(5);
                hLettersLines[i][j].setVisible(false);
                gamePane.getChildren().add(hLettersLines[i][j]);
                gamePane.getChildren().add(gameLetters[i][j]);
                gamePane.getChildren().add(gameLettersLabel[i][j]);
            }
            gamePane.getChildren().add(gameLetters[i][3]);
            gamePane.getChildren().add(gameLettersLabel[i][3]);
        }
    }

    //change exit event for each pages
    public void makeExitButton() {
        WGDialogSingleton wgDialogSingleton = WGDialogSingleton.getSingleton();
        wgDialogSingleton.init(primaryStage);
        exitLine1 = new Line();
        exitLine1.setStroke(Paint.valueOf("FFFFFF"));
        exitLine1.setStrokeWidth(10);
        exitLine1.setStartX(780);
        exitLine1.setStartY(15);
        exitLine1.setEndX(790);
        exitLine1.setEndY(25);
        exitLine2 = new Line();
        exitLine2.setStroke(Paint.valueOf("FFFFFF"));
        exitLine2.setStartX(790);
        exitLine2.setStartY(15);
        exitLine2.setEndX(780);
        exitLine2.setEndY(25);
        exitLine2.setStrokeWidth(10);
        exitPane.getChildren().addAll(exitLine1, exitLine2);
    }

    public void initGamePlay() {
        wordBox = new HBox();
        wordBox.setAlignment(Pos.CENTER_LEFT);
        wordBox.setPadding(new Insets(8));
        wordBox.setSpacing(3);

        wordBoxPane = new Pane();
        wordBoxPane.setPrefSize(150, 30);
        wordBoxPane.setStyle("-fx-background-color: #979CA9;");
        wordBoxPane.getStyleClass().add("pane");
        wordBoxPane.getChildren().add(wordBox);
        wordBoxPane.setVisible(false);

        Pane emptypane2 = new Pane();
        emptypane2.setPrefSize(30, 30);

        Pane wordBoxPanePane = new HBox();
        wordBoxPanePane.setPrefWidth(180);
        wordBoxPanePane.getChildren().addAll(emptypane2, wordBoxPane);

        targetLable = new Label("Target");
        targetLable.setUnderline(true);
        targetLable.setVisible(false);
        targetLable.getStyleClass().addAll("word");

        targetPointsLable = new Label("75 points");
        targetPointsLable.setVisible(false);
        targetPointsLable.getStyleClass().add("word");

        targetBox = new VBox();
        targetBox.setSpacing(10);
        targetBox.setPadding(new Insets(8));
        targetBox.getChildren().addAll(targetLable, targetPointsLable);

        targetBoxPane = new Pane();
        targetBoxPane.getChildren().addAll(targetBox);
        targetBoxPane.setPrefSize(150, 60);
        targetBoxPane.setStyle("-fx-background-color: #979CA9;");
        targetBoxPane.getStyleClass().add("pane");
        targetBoxPane.setVisible(false);

        Pane emptypane3 = new Pane();
        emptypane3.setPrefSize(30, 30);

        Pane targetBoxPanePane = new HBox();
        targetBoxPanePane.setPrefWidth(180);
        targetBoxPanePane.getChildren().addAll(emptypane3, targetBoxPane);

        Pane emptypane = new Pane();
        emptypane.setMinSize(200, 30);
        rightBox.getChildren().addAll(emptypane, wordBoxPanePane, scoreBarPane, targetBoxPanePane);
    }

    public void drawArrow() {
        Polygon polygon = new Polygon();
        polygon.setFill(Paint.valueOf("#FFFFFF"));
        polygon.getPoints().addAll(170.0, 5.0,
                170.0, 25.0,
                160.0, 15.0);
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Paint.valueOf("#FFFFFF"));
        rectangle.setX(170);
        rectangle.setY(10);
        rectangle.setHeight(10);
        rectangle.setWidth(23);
        arrowPane.getChildren().addAll(polygon, rectangle);
    }

    public void initLetter() {
        gameLettersLabel[0][0].setText("B");
        gameLettersLabel[0][1].setText("U");
        gameLettersLabel[1][0].setText("Z");
        gameLettersLabel[1][1].setText("Z");
        gameLettersLabel[2][2].setText("W");
        gameLettersLabel[2][3].setText("O");
        gameLettersLabel[3][2].setText("R");
        gameLettersLabel[3][3].setText("D");
    }
    public void drawScoreBox(){
        HBox scoreBoardBox = new HBox();
        HBox totalBoardBox = new HBox();
        Pane scoreLeftBoxPane = new StackPane();
        Pane scoreLeftBoxColorPane = new Pane();
        Pane scoreRightBoxPane = new StackPane();
        Pane scoreRIghtBoxColorPane = new Pane();
        Pane totalLeftBoxPane = new StackPane();
        Pane totalLeftBoxColorPane = new Pane();
        Pane totalRightBoxPane = new StackPane();
        Pane totalRightBoxColorPane = new Pane();
        totalLeftBoxColorPane.setPrefSize(105, 30);
        totalLeftBoxColorPane.setStyle("-fx-background-color: #A294AC;");
        totalRightBoxColorPane.setPrefSize(30, 30);
        totalRightBoxColorPane.setStyle("-fx-background-color: #A294AC;");
        scoreLeftBoxColorPane.setPrefSize(105, 200);
        scoreLeftBoxColorPane.setStyle("-fx-background-color: #979CA9;");
        scoreRIghtBoxColorPane.setPrefSize(30, 200);
        scoreRIghtBoxColorPane.setStyle("-fx-background-color: #979CA9;");
        Label total = new Label("TOTAL");
        total.getStyleClass().add("total");
        totalScoreLabel = new Label("0");
        totalScoreLabel.setId("totalScoreLabel");
        totalScoreLabel.getStyleClass().add("total");
        scoreLeftBox = new VBox();
        scoreLeftBox.setPadding(new Insets(8));
        scoreLeftBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#A294AC"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        scoreRightBox = new VBox();
        scoreRightBox.setPadding(new Insets(8));
        scoreRightBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#A294AC"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        VBox totalLeftBox = new VBox();
        totalLeftBox.setPadding(new Insets(8));
        totalLeftBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#A294AC"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        totalLeftBox.getChildren().add(total);
        VBox totalRightBox = new VBox();
        totalRightBox.setPadding(new Insets(8));
        totalRightBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#A294AC"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        totalRightBox.getChildren().add(totalScoreLabel);
        scoreLeftBoxPane.getChildren().addAll(scoreLeftBoxColorPane, scoreLeftBox);
        scoreRightBoxPane.getChildren().addAll(scoreRIghtBoxColorPane, scoreRightBox);
        totalLeftBoxPane.getChildren().addAll(totalLeftBoxColorPane, totalLeftBox);
        totalRightBoxPane.getChildren().addAll(totalRightBoxColorPane, totalRightBox);
        scoreBoardBox.getChildren().addAll(scoreLeftBoxPane, scoreRightBoxPane);
        Pane emptyBox2 = new Pane();
        emptyBox2.setPrefSize(31, 30);
        totalBoardBox.getChildren().addAll(emptyBox2, totalLeftBoxPane, totalRightBoxPane);
        ScrollPane scoreBar = new ScrollPane();
        scoreBar.setMaxHeight(200);
        scoreBar.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scoreBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scoreBar.setContent(scoreBoardBox);
        Pane emptyBox = new Pane();
        emptyBox.setPrefSize(30, 30);
        HBox scoreBarPaneHBox = new HBox();
        scoreBarPaneHBox.getChildren().addAll(emptyBox, scoreBar);
        scoreBarPane = new VBox();
        scoreBarPane.getChildren().addAll(scoreBarPaneHBox, totalBoardBox);
        scoreBarPane.setVisible(false);
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
        for (int i =0; i < 3; i++){
            for (int j=0; j < 3; j++){
                dRLettersLines[i][j].setVisible(false);
            }
        }

        for (int i = 0; i < 4-1; i++){
            for (int j=1; j < 4; j++){
                dLLettersLines[i][j-1].setVisible(false);
            }
        }
    }

    public void hideCircles(){
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                gameLetters[i][j].setVisible(false);
                gameLettersLabel[i][j].setVisible(false);
            }
        }
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

    public void showCircles(){
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                gameLetters[i][j].setVisible(true);
                gameLettersLabel[i][j].setVisible(true);
            }
        }
    }

    public static Label[][] getGameLettersLabel() {
        return gameLettersLabel;
    }

    public static Line[][] gethLettersLines() {
        return hLettersLines;
    }

    public static Line[][] getvLettersLines() {
        return vLettersLines;
    }

    public static Line[][] getdRLettersLines() {
        return dRLettersLines;
    }

    public static Line[][] getdLLettersLines() {
        return dLLettersLines;
    }

    public static Label getTimerLabel() {
        return timerLabel;
    }

    public static Scene getPrimaryScene() {
        return primaryScene;
    }

    public static Circle[][] getGameLetters() {
        return gameLetters;
    }

    public static ComboBox getSelectMode() {
        return selectMode;
    }

    public static HBox getWordBox() {
        return wordBox;
    }

    public static VBox getScoreLeftBox() {
        return scoreLeftBox;
    }

    public static VBox getScoreRightBox() {
        return scoreRightBox;
    }

    public static StackPane getBottomPane() {
        return bottomPane;
    }
}
