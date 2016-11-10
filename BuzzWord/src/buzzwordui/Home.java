package buzzwordui;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ui.WGGUI;
import wgtemplate.WGTemplate;

import java.io.IOException;

/**
 * Created by Jude Hokyoon Woo on 11/9/2016.
 */
public class Home extends WGGUI{
    WGTemplate wgTemplate;

    public Home(Stage primaryStage, String applicationTitle, WGTemplate appTemplate, int appSpecificWindowWidth, int appSpecificWindowHeight) throws IOException, InstantiationException {
        super(primaryStage, applicationTitle, appTemplate, appSpecificWindowWidth, appSpecificWindowHeight);
        layoutGUI();
    }

    public void layoutGUI(){
        login.setVisible(true);
        home.setVisible(false);
        login.setOnMouseClicked(event -> {
            new LoginPage();
        });
    }
}
