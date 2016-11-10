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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class LoginPage extends Stage{

    StackPane root;
    VBox vBox;
    HBox idBox;
    TextField idField;
    Label id;
    HBox pwBox;
    PasswordField pwField;
    Label pw;
    Scene scene;

    public LoginPage() {
        layOutGUI();
        initStyle(StageStyle.TRANSPARENT);
        setScene(scene);
        show();
    }

    public void layOutGUI() {
        root = new StackPane();
        root.setStyle("-fx-background-color: null;");

        Region region = new Region();
        region.setStyle("-fx-background-radius:20; -fx-background-color: rgba(56, 176, 209, 0.3)");
        region.setEffect(new DropShadow(10, Color.GREY));


        idField = new TextField();
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

    }

}
