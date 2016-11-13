package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    protected Pane gamePane;
    protected HBox      baseBox;            //where appPane lies
    protected Label     baseLeftLabel;       //base Label
    protected Label     baseRightLabel;      //base Label
    protected Label     modeLabel;
    protected HBox      topBottomBox;
    protected Label     levelLabel;
    protected VBox      centerBox;
    protected VBox      leftBox;
    protected VBox      rightBox;
    protected VBox      bottomBox;
    protected Button    bottomPlayButton;
    protected String    applicationTitle;   //application Title
    protected Label     guiHeadingLabel;   // workspace (GUI) heading label
    protected VBox      topBox;          // conatainer to display the heading
    protected StackPane leftPane;
    protected Button    createProfile;
    protected Button    login;
    protected Button    start;
    protected Button    home;
    protected ComboBox  selectMode;
    protected Circle[][] gameLetters; // circle labeled letters on the grid
    protected Line[][] hLettersLines;
    protected Line[][] vLettersLines;
    protected Label[][] gameLettersLabel;
    protected Label rightLabel;
    protected Pane exitPane;

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
    }

    private void initializeWindow(){
        primaryStage.setTitle(applicationTitle);

        bottomPlayButton = new Button();
        bottomPlayButton.setId("bottom-button");

        topBottomBox = new HBox();

        modeLabel = new Label("Dictionary");

        rightLabel = new Label("Right");

        levelLabel = new Label("Level 4");

        gamePane = new Pane();


        centerBox = new VBox();
        centerBox.setSpacing(10);
        centerBox.getChildren().addAll(gamePane, levelLabel);
        centerBox.setAlignment(Pos.TOP_CENTER);

        leftBox = new VBox();
        leftBox.setSpacing(10);
        leftBox.setAlignment(Pos.TOP_CENTER);

        rightBox = new VBox();
        rightBox.setSpacing(10);
        rightBox.getChildren().addAll(rightLabel);
        rightBox.setAlignment(Pos.TOP_RIGHT);

        bottomBox = new VBox();
        bottomBox.setSpacing(10);
        bottomBox.setAlignment(Pos.TOP_CENTER);
        bottomBox.getChildren().addAll(bottomPlayButton);

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

        topBottomBox.getChildren().add(modeLabel);
        topBottomBox.setSpacing(100);
        topBottomBox.setAlignment(Pos.CENTER_RIGHT);

        topBox = new VBox();
        topBox.getChildren().addAll(exitPane, guiHeadingLabel, topBottomBox);
        topBox.setSpacing(10);
        topBox.setAlignment(Pos.CENTER);

        appPane.setTop(topBox);

        createProfile = new Button("Create Profile");

        login = new Button("Login");

        selectMode = new ComboBox();
        selectMode.getItems().addAll(
                "English Dictonary",
                "Places",
                "Sciences",
                "Famous People"
        );
        selectMode.setValue("Select Mode");
        selectMode.setVisible(false);

        start = new Button("Start Playing");
        start.setVisible(false);

        home = new Button("Home");
        home.setVisible(false);

        leftPane = new StackPane();
        leftPane.getChildren().addAll(login, selectMode, home);

        leftBox.getChildren().addAll(createProfile, leftPane, start);

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
        baseLeftLabel.setPrefSize(150,550);
        baseRightLabel.setStyle("-fx-background-color: #A294AC;");
        baseRightLabel.setPrefSize(650,550);
    }

    public void initGrid(){
        gameLettersLabel = new Label[4][4];
        gameLetters = new Circle[4][4];
        hLettersLines = new Line[4][3];
        vLettersLines = new Line[3][4];

        int ySpacing = 40;
        int initSpacing = 80;
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
        exitLine1.setStrokeWidth(10);
        exitLine1.setStartX(780);
        exitLine1.setStartY(15);
        exitLine1.setEndX(790);
        exitLine1.setEndY(25);
        Line exitLine2 = new Line();
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
}
