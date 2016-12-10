package data;

import buzzwordui.CreateProfile;
import buzzwordui.LoginPage;
import com.fasterxml.jackson.core.*;
import controller.BuzzWordController;
import wgcomponents.WGData;
import wgcomponents.WGFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Jude Hokyoon Woo on 11/17/2016.
 */
public class GameDataFile implements WGFile {

    public static final String USER_ID  = "USER_ID";
    public static final String PASSWORD = "PASSWORD";
    public static final String A_MODE_MAX = "English Dictionary Max Level";
    public static final String B_MODE_MAX = "Places Max Level";
    public static final String C_MODE_MAX = "Science Max Level";
    public static final String D_MODE_MAX = "Famous People Max Level";
    public static final String A_MODE_LEVEL_SCORE = "English Dictionary Level and Score";
    public static final String B_MODE_LEVEL_SCORE = "Places Level and Score";
    public static final String C_MODE_LEVEL_SCORE = "Science Level and Score";
    public static final String D_MODE_LEVEL_SCORE = "Famous People Level and Score";

    @Override
    public void saveData(WGData data, Path to) throws IOException {
        GameData       gamedata    = (GameData) data;
        CreateProfile createProfile = new CreateProfile(gamedata);
        String userID = gamedata.getUserID();
        String passWord  = gamedata.getPassWord();
        Integer aModeLevel = gamedata.getaModeLevel();
        Integer bModeLevel = gamedata.getbModeLevel();
        Integer cModeLevel = gamedata.getcModeLevel();
        Integer dModeLevel = gamedata.getdModeLevel();
        ArrayList<Integer> aModeLevelandBest = gamedata.getaModeLevelandBest();
        ArrayList<Integer> bModeLevelandBest = gamedata.getbModeLevelandBest();
        ArrayList<Integer> cModeLevelandBest = gamedata.getcModeLevelandBest();
        ArrayList<Integer> dModeLevelandBest = gamedata.getdModeLevelandBest();

        JsonFactory jsonFactory = new JsonFactory();

        try {
            JsonGenerator generator = jsonFactory.createGenerator(new File(to+"/"+ userID+".json"), JsonEncoding.UTF8);

            generator.writeStartObject();

            generator.writeStringField(USER_ID, userID);
            generator.writeStringField(PASSWORD, Hash.md5(passWord));
            generator.writeStringField(A_MODE_MAX, aModeLevel.toString());
            generator.writeStringField(B_MODE_MAX, bModeLevel.toString());
            generator.writeStringField(C_MODE_MAX, cModeLevel.toString());
            generator.writeStringField(D_MODE_MAX, dModeLevel.toString());
            generator.writeFieldName(A_MODE_LEVEL_SCORE);
            generator.writeStartArray(aModeLevelandBest.size());
            for (Integer best : aModeLevelandBest){
                generator.writeString(best.toString());
            }
            generator.writeEndArray();
            generator.writeFieldName(B_MODE_LEVEL_SCORE);
            generator.writeStartArray(bModeLevelandBest.size());
            for (Integer best : bModeLevelandBest){
                generator.writeString(best.toString());
            }
            generator.writeEndArray();
            generator.writeFieldName(C_MODE_LEVEL_SCORE);
            generator.writeStartArray(cModeLevelandBest.size());
            for (Integer best : cModeLevelandBest){
                generator.writeString(best.toString());
            }
            generator.writeEndArray();
            generator.writeFieldName(D_MODE_LEVEL_SCORE);
            generator.writeStartArray(dModeLevelandBest.size());
            for (Integer best : dModeLevelandBest){
                generator.writeString(best.toString());
            }
            generator.writeEndArray();
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
        JsonParser jsonParser  = jsonFactory.createParser(new File(from+"/"+ loginPage.getIdField().getText()+".json"));

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
                    case A_MODE_MAX:
                        jsonParser.nextToken();
                        gamedata.setaModeLevel(jsonParser.getValueAsInt());
                        break;
                    case B_MODE_MAX:
                        jsonParser.nextToken();
                        gamedata.setbModeLevel(jsonParser.getValueAsInt());
                        break;
                    case C_MODE_MAX:
                        jsonParser.nextToken();
                        gamedata.setcModeLevel(jsonParser.getValueAsInt());
                        break;
                    case D_MODE_MAX:
                        jsonParser.nextToken();
                        gamedata.setdModeLevel(jsonParser.getValueAsInt());
                        break;
                    case A_MODE_LEVEL_SCORE:
                        jsonParser.nextToken();
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY)
                            gamedata.addAModeLevelandBest(Character.getNumericValue(jsonParser.getText().charAt(0)));
                        break;
                    case B_MODE_LEVEL_SCORE:
                        jsonParser.nextToken();
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY)
                            gamedata.addBModeLevelandBest(Character.getNumericValue(jsonParser.getText().charAt(0)));
                        break;
                    case C_MODE_LEVEL_SCORE:
                        jsonParser.nextToken();
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY)
                            gamedata.addCModeLevelandBest(Character.getNumericValue(jsonParser.getText().charAt(0)));
                        break;
                    case D_MODE_LEVEL_SCORE:
                        jsonParser.nextToken();
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY)
                            gamedata.addDModeLevelandBest(Character.getNumericValue(jsonParser.getText().charAt(0)));
                        break;
                    default:
                        throw new JsonParseException(jsonParser, "Unable to load JSON data");
                }
            }
        }
    }
}
