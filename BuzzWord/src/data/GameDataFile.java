package data;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import wgcomponents.WGData;
import wgcomponents.WGFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
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
        String userID = gamedata.getUserID();
        String passWord  = gamedata.getPassWord();

        JsonFactory jsonFactory = new JsonFactory();

        try (OutputStream out = Files.newOutputStream(to)) {

            JsonGenerator generator = jsonFactory.createGenerator(out, JsonEncoding.UTF8);

            generator.writeStartObject();

            generator.writeStringField(USER_ID, gamedata.getUserID());
            generator.writeStringField(PASSWORD, gamedata.getPassWord());
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
