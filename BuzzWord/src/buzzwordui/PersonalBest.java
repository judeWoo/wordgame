package buzzwordui;

import controller.BuzzWordController;
import data.BuzzWordSolverFinal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.WGGUI;

/**
 * Created by Jude Hokyoon Woo on 12/10/2016.
 */
public class PersonalBest extends WGGUI{

    StackPane root;
    VBox vBox;
    Label title;
    Label personalBest;
    Label levelAndMode;
    Label possibleHighScore;
    Label possibleLetters;
    Label emptyLabel;
    Button closeButton;
    Scene scene;
    ScrollPane scrollPane;
    static Stage stage  = new Stage();

    public PersonalBest() {
        layoutGUI();
    }

    public void layoutGUI() {
        BuzzWordController controller = new BuzzWordController();
        title = new Label("Game Just Ended..");
        emptyLabel = new Label();
        emptyLabel.setPrefSize(210, 10);
        personalBest = new Label("Your Personal Best:");
        levelAndMode = new Label(selectMode.getValue().toString()+" Level "+ BuzzWordController.getGameLevel()+": "
                + BuzzWordController.getGameData().getaModeLevelandBest().get(Integer.parseInt(BuzzWordController.getGameLevel())-1));
        possibleHighScore = new Label("Possible High Score: "+controller.calTotalScore());
        possibleLetters = new Label("Possible Letters are:");

        root = new StackPane();
        root.setStyle("-fx-background-color: null;");

        Region region = new Region();
        region.setStyle("-fx-background-radius:20; -fx-background-color: rgba(0, 0, 0, 0.6)");
        region.setEffect(new DropShadow(10, Color.GREY));

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(8));
        vBox.getChildren().addAll(title, personalBest, levelAndMode, possibleHighScore, possibleLetters);
        for (String str : BuzzWordSolverFinal.getCounter()){
            Label word = new Label(str);
            vBox.getChildren().add(word);
        }

        closeButton = new Button("Close");
        closeButton.setOnMouseClicked(event -> {
            stage.close();
        });
        vBox.getChildren().add(closeButton);
        vBox.getChildren().add(emptyLabel);

        scrollPane = new ScrollPane();
        scrollPane.setMaxHeight(400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(0.0);
        scrollPane.setContent(vBox);

        root.getChildren().addAll(region, scrollPane);

        scene = new Scene(root, 215, 400);
        scene.setFill(Color.TRANSPARENT);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                stage.close();
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                stage.close();
            }
        });

        scene.getStylesheets().add("css/popup_style.css");
//        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        PersonalBest.stage = stage;
    }
}
