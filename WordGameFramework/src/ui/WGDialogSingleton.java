package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 * Created by Jude Hokyoon Woo on 11/20/2016.
 */
public class WGDialogSingleton extends Stage{
    static WGDialogSingleton singleton = null;

    StackPane root;
    VBox vBox;
    Label messageLabel;
    Label title;
    Scene scene;
    String selection;

    public static final String YES    = "Yes";
    public static final String NO     = "No";

    private WGDialogSingleton() { }

    /**
     * A static accessor method for getting the singleton object.
     *
     * @return The one singleton dialog of this object type.
     */
    public static WGDialogSingleton getSingleton() {
        if (singleton == null)
            singleton = new WGDialogSingleton();
        return singleton;
    }

    /**
     * This function fully initializes the singleton dialog for use.
     **/
    public void init(Stage owner) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);

        root = new StackPane();
        root.setStyle("-fx-background-color: null;");

        Region region = new Region();
        region.setStyle("-fx-background-radius:20; -fx-background-color: rgba(0, 0, 0, 0.3)");
        region.setEffect(new DropShadow(10, Color.GREY));

        title = new Label("Exit?");
        title.setId("createprofile");

        messageLabel = new Label();

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        vBox.getChildren().addAll(title, messageLabel);

        root.getChildren().addAll(region, vBox);

        scene = new Scene(root, 400, 250);
        scene.setFill(Color.TRANSPARENT);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                this.selection = "Yes";
                this.hide();
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                this.selection = "No";
                this.hide();
            }
        });
        scene.getStylesheets().add("css/popup_style.css");
        this.setScene(scene);
        this.initStyle(StageStyle.TRANSPARENT);
    }

    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     */

    public void setMessageLabel(String messageLabelText) {
        messageLabel.setText(messageLabelText);
    }

    public void setTitleLabel(String titleLabelText) {
        title.setText(titleLabelText);
    }

    public String getSelection() {
        return selection;
    }

    public void show(String title, String message) {
        setTitleLabel(title); // set the dialog title
        setMessageLabel(message); // message displayed to the user
        showAndWait();
    }
}
