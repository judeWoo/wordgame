package buzzwordui;

import controller.BuzzWordController;
import data.GameData;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.WGGUI;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class LoginPage extends WGGUI {

    StackPane root;
    VBox vBox;
    HBox idBox;
    Label id;
    HBox pwBox;
    Label pw;
    Label title;
    Scene scene;
    Stage stage;
    static PasswordField pwField;
    static TextField idField;

    public LoginPage() {
        layOutGUI();
    }

    public LoginPage(GameData gameData) {

    }

    public void layOutGUI() {
        BuzzWordController controller = new BuzzWordController();

        title = new Label("Log In");
        title.setId("createprofile");

        root = new StackPane();
        root.setStyle("-fx-background-color: null;");

        Region region = new Region();
        region.setStyle("-fx-background-radius:20; -fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setEffect(new DropShadow(10, Color.GREY));


        idField = new TextField("User") {
//            @Override public void replaceText(int start, int end, String text) {
//                // If the replaced text would end up being invalid, then simply
//                // ignore this call!
//                if (text.matches("[a-z]")) {
//                    super.replaceText(start, end, text);
//                }
//            }
//
//            @Override public void replaceSelection(String text) {
//                if (!text.matches("[a-z]")) {
//                    super.replaceSelection(text);
//                }
//            }
        };
        idField.setBackground(Background.EMPTY);
        idField.setMaxSize(100, 30);
        id = new Label("ID");
        id.setPrefSize(150, 30);

        idBox = new HBox();
        idBox.setSpacing(30);
        idBox.setPadding(new Insets(8));
        idBox.getChildren().addAll(id, idField);

        pwField = new PasswordField();
        pwField.setBackground(Background.EMPTY);
        pwField.setMaxSize(100, 30);
        pw = new Label("Password");
        pw.setPrefSize(150, 30);

        pwBox = new HBox();
        pwBox.setSpacing(30);
        pwBox.setPadding(new Insets(8));
        pwBox.getChildren().addAll(pw, pwField);

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        vBox.getChildren().addAll(title, idBox, pwBox);

        root.getChildren().addAll(region, vBox);

        scene = new Scene(root, 300, 250);
        scene.setFill(Color.TRANSPARENT);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (idField.getText().matches(".*[a-zA-Z]+.*")) {
                    if (controller.logInRequest()) {
                        createProfile.setVisible(false);
                        userButton.setText(idField.getText());
                        userButton.setVisible(true);
                        login.setVisible(false);
                        selectMode.setVisible(true);
                        start.setVisible(true);
                        arrowPane.setVisible(true);
                        stage.close();
                    }
                }
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                stage.close();
            }
        });

        scene.getStylesheets().add("css/popup_style.css");
        stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public static TextField getIdField() {
        return idField;
    }

    public static PasswordField getPwField() {
        return pwField;
    }

}
