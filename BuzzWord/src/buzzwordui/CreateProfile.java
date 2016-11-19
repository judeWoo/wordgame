package buzzwordui;

import controller.BuzzWordController;
import data.GameData;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.WGGUI;
import wgcomponents.WGComponentsMaker;
import wgtemplate.WGTemplate;

/**
 * Created by Kun on 11/18/2016.
 */
public class CreateProfile extends WGGUI {

    StackPane root;
    VBox vBox;
    HBox idBox;
    Label id;
    HBox pwBox;
    Label pw;
    Scene scene;
    Stage stage;
    static TextField idField;
    static PasswordField pwField;

    public CreateProfile(){
        layOutGUI();
    }

    public CreateProfile(GameData gameData){
    }

    public static TextField getIdField() {
        return idField;
    }

    public static PasswordField getPwField() {
        return pwField;
    }

    public void layOutGUI() {
        BuzzWordController controller = new BuzzWordController();

        root = new StackPane();
        root.setStyle("-fx-background-color: null;");

        Region region = new Region();
        region.setStyle("-fx-background-radius:20; -fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setEffect(new DropShadow(10, Color.GREY));


        idField = new TextField("User") {
            @Override
            public void replaceText(int start, int end, String text) {
                // If the replaced text would end up being invalid, then simply
                // ignore this call!
                if (text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
        idField.setBackground(Background.EMPTY);
        id = new Label("Profile Name");

        idBox = new HBox();
        idBox.setSpacing(10);
        idBox.getChildren().addAll(id, idField);

        pwField = new PasswordField();
        pwField.setBackground(Background.EMPTY);
        pw = new Label("Profile Password");

        pwBox = new HBox();
        idBox.setSpacing(20);
        pwBox.getChildren().addAll(pw, pwField);

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        vBox.getChildren().addAll(idBox, pwBox);

        root.getChildren().addAll(region, vBox);

        scene = new Scene(root, 300, 250);
        scene.setFill(Color.TRANSPARENT);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (idField.getText().matches(".*[a-zA-Z]+.*")) {
                    controller.newGameProfileRequest(stage, idField.getText());
                    userButton.setVisible(true);
                    userButton.setText(idField.getText());
                    createProfile.setVisible(false);
                    login.setVisible(false);
                    selectMode.setVisible(true);
                    start.setVisible(true);
                    arrowPane.setVisible(true);
                    stage.close();
                }
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                stage.close();
            }
        });
        stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }
}