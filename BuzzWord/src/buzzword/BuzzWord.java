package buzzword;

import buzzwordui.Gameplay;
import buzzwordui.Home;
import buzzwordui.LoginPage;
import javafx.application.Application;
import javafx.stage.Stage;
import wgtemplate.WGTemplate;

/**
 * Created by Jude Hokyoon Woo on 11/2/2016.
 */
public class BuzzWord extends Application{

    WGTemplate app;

    @Override
    public void start(Stage primaryStage) throws Exception {
       // Gameplay ws = new Gameplay(primaryStage, "Godje",app, 800, 550);
       // LoginPage loginPage = new LoginPage();
        Home home = new Home(primaryStage, "Godje",app, 800, 550);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
