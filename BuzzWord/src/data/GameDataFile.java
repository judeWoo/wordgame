package data;

import buzzwordui.CreateProfile;
import buzzwordui.LoginPage;
import com.fasterxml.jackson.core.*;
import wgcomponents.WGData;
import wgcomponents.WGFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * Created by Kun on 11/17/2016.
 */
public class GameDataFile implements WGFile {

    public static final String USER_ID  = "USER_ID";
    public static final String PASSWORD = "PASSWORD";
    public static final String LEVEL    = "LEVEL";
    public static final String SCORE    = "SCORE";

    @Override
    public void saveData(WGData data, Path to) throws IOException {
        GameData       gamedata    = (GameData) data;
        gamedata.init();
        CreateProfile createProfile = new CreateProfile(gamedata);
        String userID = gamedata.getUserID();
        String passWord  = gamedata.getPassWord();
        Integer level = gamedata.getLevel();
        Integer score = gamedata.getScore();

        JsonFactory jsonFactory = new JsonFactory();

        try {
            JsonGenerator generator = jsonFactory.createGenerator(new File(to+"/"+createProfile.getIdField().getText()+".json"), JsonEncoding.UTF8);

            generator.writeStartObject();

            generator.writeStringField(USER_ID, userID);
            generator.writeStringField(PASSWORD, passWord);
            generator.writeStringField(LEVEL, level.toString());
            generator.writeStringField(SCORE, score.toString());
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
        GameData gamedata = (GameData) data;
        LoginPage loginPage = new LoginPage(gamedata);
        gamedata.reset();

        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser  = jsonFactory.createParser(new File(from+"/"+loginPage.getIdField().getText()+".json"));

        while (!jsonParser.isClosed()) {
            JsonToken token = jsonParser.nextToken();
            if (JsonToken.FIELD_NAME.equals(token)) {
                String fieldname = jsonParser.getCurrentName();
                switch (fieldname) {
                    case USER_ID:
                        jsonParser.nextToken();
                        gamedata.setUserID(jsonParser.getValueAsString());
                        break;
                    case PASSWORD:
                        jsonParser.nextToken();
                        gamedata.setPassWord(jsonParser.getValueAsString());
                        break;
                    case LEVEL:
                        jsonParser.nextToken();
                        gamedata.setLevel(jsonParser.getValueAsInt());
                        break;
                    case SCORE:
                        jsonParser.nextToken();
                        gamedata.setScore(jsonParser.getValueAsInt());
                        break;
                    default:
                        throw new JsonParseException(jsonParser, "Unable to load JSON data");
                }
            }
        }

    }
}
