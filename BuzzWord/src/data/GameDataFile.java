package data;

import buzzwordui.CreateProfile;
import buzzwordui.LoginPage;
import com.fasterxml.jackson.core.*;
import wgcomponents.WGData;
import wgcomponents.WGFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Jude Hokyoon Woo on 11/17/2016.
 */
public class GameDataFile implements WGFile {

    public static final String USER_ID  = "USER_ID";
    public static final String PASSWORD = "PASSWORD";
    public static final String A_MODE   = "English Dictionary";
    public static final String B_MODE   = "Places";
    public static final String C_MODE   = "Science";
    public static final String D_MODE   = "Famous People";

    @Override
    public void saveData(WGData data, Path to) throws IOException {
        GameData       gamedata    = (GameData) data;
        gamedata.init();
        CreateProfile createProfile = new CreateProfile(gamedata);
        String userID = gamedata.getUserID();
        String passWord  = gamedata.getPassWord();
        Integer aModeLevel = gamedata.getaModeLevel();
        Integer bModeLevel = gamedata.getbModeLevel();
        Integer cModeLevel = gamedata.getcModeLevel();
        Integer dModeLevel = gamedata.getdModeLevel();

        JsonFactory jsonFactory = new JsonFactory();

        try {
            JsonGenerator generator = jsonFactory.createGenerator(new File(to+"/"+createProfile.getIdField().getText()+".json"), JsonEncoding.UTF8);

            generator.writeStartObject();

            generator.writeStringField(USER_ID, userID);
            generator.writeStringField(PASSWORD, passWord);
            generator.writeStringField(A_MODE, aModeLevel.toString());
            generator.writeStringField(B_MODE, bModeLevel.toString());
            generator.writeStringField(C_MODE, cModeLevel.toString());
            generator.writeStringField(D_MODE, dModeLevel.toString());

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
                    case A_MODE:
                        jsonParser.nextToken();
                        gamedata.setaModeLevel(jsonParser.getValueAsInt());
                        break;
                    case B_MODE:
                        jsonParser.nextToken();
                        gamedata.setbModeLevel(jsonParser.getValueAsInt());
                        break;
                    case C_MODE:
                        jsonParser.nextToken();
                        gamedata.setcModeLevel(jsonParser.getValueAsInt());
                        break;
                    case D_MODE:
                        jsonParser.nextToken();
                        gamedata.setdModeLevel(jsonParser.getValueAsInt());
                        break;
                    default:
                        throw new JsonParseException(jsonParser, "Unable to load JSON data");
                }
            }
        }

    }
}
