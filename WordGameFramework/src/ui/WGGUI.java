package ui;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
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
    protected StackPane centerPane;
    protected Canvas    centerCanvas;
    protected GridPane  gamePane;
    protected HBox      baseBox;            //where appPane lies
    protected Label     baseLeftLabel;       //base Label
    protected Label     baseRightLabel;      //base Label
    protected Label     modeLabel;
    protected Label     levelLabel;
    protected VBox      centerBox;
    protected VBox      leftBox;
    protected VBox      rightBox;
    protected VBox      bottomBox;
    protected Button    bottomPlayButton;
    protected String    applicationTitle;   //application Title
    protected Label     guiHeadingLabel;   // workspace (GUI) heading label
    protected HBox      headPane;          // conatainer to display the heading
    protected StackPane leftPane;
    protected Button    createProfile;
    protected Button    login;
    protected Button    start;
    protected Button    home;
    protected ComboBox  selectMode;
    protected Circle[] gameLetters; // circle labeled letters on the grid

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
    }

    private void initializeWindow(){
        primaryStage.setTitle(applicationTitle);

        bottomPlayButton = new Button();
        bottomPlayButton.setId("bottom-button");

        gamePane = new GridPane();

        centerCanvas = new Canvas();

        centerPane = new StackPane();
        centerPane.getChildren().addAll(centerCanvas, gamePane);

        modeLabel = new Label();

        levelLabel = new Label("Level 4");

        centerBox = new VBox();
        centerBox.setSpacing(10);
        centerBox.getChildren().addAll(modeLabel,centerPane);

        leftBox = new VBox();
        leftBox.setSpacing(10);
        leftBox.setAlignment(Pos.CENTER);

        rightBox = new VBox();
        rightBox.setSpacing(10);
        leftBox.setAlignment(Pos.CENTER);

        bottomBox = new VBox();
        bottomBox.setSpacing(10);
        bottomBox.setAlignment(Pos.TOP_CENTER);
        bottomBox.getChildren().addAll(levelLabel, bottomPlayButton);

        appPane = new BorderPane();
        appPane.setPrefSize(800, 550);
        appPane.setCenter(centerBox);
        appPane.setLeft(leftBox);
        appPane.setRight(rightBox);
        appPane.setBottom(bottomBox);

        baseBox = new HBox();
        baseLeftLabel = new Label();
        baseRightLabel = new Label();
        baseBox.getChildren().addAll(baseLeftLabel,baseRightLabel);

        basePane = new StackPane();
        basePane.getChildren().addAll(baseBox, appPane);

        guiHeadingLabel = new Label("!! BuzzWord !!");
        guiHeadingLabel.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {guiHeadingLabel.setEffect(highlight);});
        guiHeadingLabel.setId("Heading");

        headPane = new HBox();
        headPane.getChildren().add(guiHeadingLabel);
        headPane.setAlignment(Pos.CENTER);

        appPane.setTop(headPane);

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
}
