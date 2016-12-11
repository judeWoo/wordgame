package buzzwordui;

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
    static Stage stage;

    public HelpScreen() {
        layoutGUI();
    }

    public void layoutGUI() {

        title = new Label("Profile Setting");

        instruction = new Label("player can press the left-button of the mouse on a node in" +
                "the letter grid, and\n drag it over other nodes one by one, thus forming a path in the grid.\n The path" +
                "– nodes as well as edges – should be highlighted during dragging.\n Any path that does not form a" +
                "cycle should be considered valid for this case,\n even if it does not form a valid word.\n");

        root = new StackPane();
        root.setStyle("-fx-background-color: null;");

        Region region = new Region();
        region.setStyle("-fx-background-radius:20; -fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setEffect(new DropShadow(10, Color.GREY));

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(title, instruction);

        scrollPane = new ScrollPane();
        scrollPane.setMaxHeight(250);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(vBox);

        root.getChildren().addAll(region, scrollPane);

        scene = new Scene(root, 500, 250);
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
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
