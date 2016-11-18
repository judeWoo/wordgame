package data;

import wgcomponents.WGData;
import wgtemplate.WGTemplate;

/**
 * Created by Kun on 11/17/2016.
 */
public class GameData implements WGData {

    public GameData(WGTemplate wgTemplate){
        this(wgTemplate, false);
    }

    public GameData(WGTemplate wgTemplate, boolean initgame) {
        if(initgame){
        }
        else {
        }
    }

    @Override
    public void reset() {

    }


}
