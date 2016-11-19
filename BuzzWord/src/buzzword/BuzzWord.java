package buzzword;

import buzzwordui.Home;
import data.GameData;
import data.GameDataFile;
import javafx.application.Application;
import javafx.stage.Stage;
import wgcomponents.WGComponentsMaker;
import wgcomponents.WGData;
import wgcomponents.WGFile;
import wgtemplate.WGTemplate;

/**
 * Created by Jude Hokyoon Woo on 11/2/2016.
 */
public class BuzzWord extends WGTemplate{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Home home = new Home(primaryStage, "Godje",BuzzWord.this, 800, 550);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
