package data;

import buzzwordui.CreateProfile;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import wgcomponents.WGData;
import wgcomponents.WGFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

/**
 * Created by Kun on 11/17/2016.
 */
public class GameDataFile implements WGFile {

    public static final String USER_ID  = "USER_ID";
    public static final String PASSWORD = "PASSWORD";

    @Override
    public void saveData(WGData data, Path to) throws IOException {
        GameData       gamedata    = (GameData) data;
        CreateProfile createProfile = new CreateProfile(gamedata);
        String userID = gamedata.getUserID();
        String passWord  = gamedata.getPassWord();

        JsonFactory jsonFactory = new JsonFactory();

        try {
            JsonGenerator generator = jsonFactory.createGenerator(new File(to+"/"+createProfile.getIdField().getText()+".json"), JsonEncoding.UTF8);

            generator.writeStartObject();

            generator.writeStringField(USER_ID, userID);
            generator.writeStringField(PASSWORD, passWord);
            /*generator.writeFieldName(GOOD_GUESSES);
            generator.writeStartArray(goodguesses.size());
            for (Character c : goodguesses)
                generator.writeString(c.toString());
            generator.writeEndArray();
            generator.writeFieldName(BAD_GUESSES);
            generator.writeStartArray(badguesses.size());
            for (Character c : badguesses)
                generator.writeString(c.toString());
            generator.writeEndArray();*/

            generator.writeEndObject();

            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void loadData(WGData data, Path from) throws IOException {

    }
}
