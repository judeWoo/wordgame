package buzzwordui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.WGGUI;

/**
 * Created by Jude Hokyoon Woo on 12/10/2016.
 */
public class HelpScreen extends WGGUI{
    StackPane root;
    VBox vBox;
    Label title;
    Label instruction;
    Scene scene;
    ScrollPane scrollPane;
    static Stage stage = new Stage();

    public HelpScreen() {
        layoutGUI();
    }

    public void layoutGUI() {

        title = new Label("Play Guide");

        instruction = new Label("Press the left-button of the mouse on a node in the letter grid.");
        Label guide1 = new Label("Drag it over other nodes one by one, thus forming a path in the grid.");
        Label guide2 = new Label("The path-nodes as well as edges–will be highlighted during dragging.");
        Label guide3 = new Label("Any path that does not form a"+" cycle is not considered as valid.");
        Label guide4 = new Label("By Mouse:");
        Label guide5 = new Label("By Keyboard:");
        Label guide6 = new Label("Simply type a sequence of letters instead of using " +"the mouse.");
        Label guide7 = new Label("This game is created by Jude Hokyoon Woo. December 10, 2016.");
        Label guide8 = new Label("With regard to CSE 219.");
        Label guide9 = new Label("Enjoy the Game.");

        root = new StackPane();
        root.setStyle("-fx-background-color: null;");

        Region region = new Region();
        region.setStyle("-fx-background-radius:20; -fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setEffect(new DropShadow(10, Color.GREY));

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(8));
        vBox.getChildren().addAll(title, guide4, instruction, guide1, guide2, guide3, guide5, guide6, guide7, guide8, guide9);

        scrollPane = new ScrollPane();
        scrollPane.setMaxHeight(250);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(vBox);

        root.getChildren().addAll(region, scrollPane);

        scene = new Scene(root, 450, 250);
        scene.setFill(Color.TRANSPARENT);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                helpButton.setDisable(false);
                stage.close();
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                helpButton.setDisable(false);
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
        HelpScreen.stage = stage;
    }
}
