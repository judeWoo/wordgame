package buzzwordui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ui.WGGUI;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class LoginPage extends WGGUI{

    StackPane root;
    VBox vBox;
    HBox idBox;
    static TextField idField;
    Label id;
    HBox pwBox;
    static PasswordField pwField;
    Label pw;
    Scene scene;
    Stage stage;

    public LoginPage() {
        layOutGUI();
    }

    public void layOutGUI() {
        root = new StackPane();
        root.setStyle("-fx-background-color: null;");

        Region region = new Region();
        region.setStyle("-fx-background-radius:20; -fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setEffect(new DropShadow(10, Color.GREY));


        idField = new TextField("User"){
            @Override public void replaceText(int start, int end, String text) {
                // If the replaced text would end up being invalid, then simply
                // ignore this call!
                if (text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
        idField.setBackground(Background.EMPTY);
        id = new Label("ID");

        idBox = new HBox();
        idBox.setSpacing(10);
        idBox.getChildren().addAll(id, idField);

        pwField = new PasswordField();
        pwField.setBackground(Background.EMPTY);
        pw = new Label("Password");

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
            if (event.getCode().equals(KeyCode.ENTER)){
                if (idField.getText().matches(".*[a-zA-Z]+.*")){
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
            if (event.getCode().equals(KeyCode.ESCAPE)){
                stage.close();
            }
        });
        stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public TextField getIdField() {
        return idField;
    }

    public PasswordField getPwField() {
        return pwField;
    }

}
