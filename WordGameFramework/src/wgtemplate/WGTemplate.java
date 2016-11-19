package wgtemplate;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.WGGUI;
import wgcomponents.WGComponentsMaker;
import wgcomponents.WGData;
import wgcomponents.WGFile;

/**
 * Created by Jude Hokyoon Woo on 10/29/2016.
 */
public abstract class WGTemplate extends Application {

    private WGData wgData;
    private WGFile wgFile;
    private WGGUI wggui;

    public WGData getWgData() {
        return wgData;
    }

    public WGFile getWgFile() {
        return wgFile;
    }

    public WGGUI getWggui() {
        return wggui;
    }

    public abstract WGComponentsMaker makeAppBuilderHook();

    @Override
    public void start(Stage primaryStage) throws Exception {

        WGComponentsMaker builder = makeAppBuilderHook();

        wgData = builder.makeDataComponent();
        wgFile = builder.makeFileComponent();

        wggui = new WGGUI(primaryStage, "Godje", this, 800, 550);

    }
}
