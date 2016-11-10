package wgtemplate;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.WGGUI;
import wgcomponents.WGComponentsMaker;

/**
 * Created by Jude Hokyoon Woo on 10/29/2016.
 */
public abstract class WGTemplate extends Application {

    protected WGGUI wggui;

    public WGGUI getWggui() {
        return wggui;
    }

    public abstract WGComponentsMaker makeAppBuilderHook();

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
