package wgcomponents;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Created by Jude Hokyoon Woo on 10/29/2016.
 */
public abstract class WGSpaceComponents implements WGStyle {

    protected Pane gamespace;          // The workspace that can be customized depending on what the app needs
    protected boolean gamespaceInitiated; // Denotes whether or not the workspace is activated

    public void activateWorkspace(BorderPane appPane) {
        if (!gamespaceInitiated) {
            appPane.setCenter(gamespace);
            gamespaceInitiated = true;
        }
    }

    public void setGamespace(Pane initGamespace) {
        gamespace = initGamespace;
    }

    public Pane getGamespace() {
        return gamespace;
    }

}
