package buzzwordui;

import controller.BuzzWordController;
import data.GameDataFile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.WGGUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Jude Hokyoon Woo on 12/10/2016.
 */
public class ProfileSetting extends WGGUI{
    StackPane root;
    VBox vBox;
    HBox idBox;
    Label id;
    HBox pwBox;
    Label pw;
    Label title;
    Label instruction;
    Scene scene;
    static Stage stage = new Stage();
    static PasswordField pwField;
    static TextField idField;

    public ProfileSetting() {
        layoutGUI();
    }

    public void layoutGUI() {
        GameDataFile gameDataFile = new GameDataFile();

        title = new Label("Profile Setting");

        instruction = new Label("To change, modify values of ID Field and PW Field and Press Enter.\n"
                +"To exit, Press ESC.");

        root = new StackPane();
        root.setStyle("-fx-background-color: null;");

        Region region = new Region();
        region.setStyle("-fx-background-radius:20; -fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setEffect(new DropShadow(10, Color.GREY));

        idField = new TextField(BuzzWordController.getGameData().getUserID());
        idField.setBackground(Background.EMPTY);
        idField.setMaxSize(100, 30);
        id = new Label("Profile name");
        id.setPrefSize(150, 30);

        idBox = new HBox();
        idBox.setSpacing(30);
        idBox.setPadding(new Insets(8));
        idBox.getChildren().addAll(id, idField);

        pwField = new PasswordField();
        pwField.setBackground(Background.EMPTY);
        pwField.setMaxSize(100, 30);
        pw = new Label("Profile Password");
        pw.setPrefSize(150, 30);

        pwBox = new HBox();
        pwBox.setSpacing(30);
        pwBox.setPadding(new Insets(8));
        pwBox.getChildren().addAll(pw, pwField);

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(title, instruction, idBox, pwBox);

        root.getChildren().addAll(region, vBox);

        scene = new Scene(root, 500, 250);
        scene.setFill(Color.TRANSPARENT);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (idField.getText().matches(".*[a-zA-Z]+.*") && !pwField.getText().equals("")) {
                    BuzzWordController.getGameData().setUserID(idField.getText());
                    BuzzWordController.getGameData().setPassWord(pwField.getText());
                    Path appDirPath = Paths.get("BuzzWord").toAbsolutePath();
                    Path targetPath = appDirPath.resolve("saved");
                    File file = new File(targetPath + "/" + idField.getText() + ".json");
                    if (file.exists() && !BuzzWordController.getGameData().getUserID().equals(idField.getText())) {
                        //Make Label;
                        profileSetting.setDisable(false);
                        return;
                    }
                    try {
                        gameDataFile.saveData(BuzzWordController.getGameData(), targetPath);
                        if (!userButton.getText().equals(idField.getText())) {
                            File oldfile = new File(targetPath + "/" + userButton.getText() + ".json");
                            if (oldfile.exists())
                                oldfile.delete();
                        }
                        userButton.setText(idField.getText());
                        profileSetting.setDisable(false);
                        stage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    //does nothing;
                }
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                profileSetting.setDisable(false);
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
        ProfileSetting.stage = stage;
    }
}
