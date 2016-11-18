package wgcomponents;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Jude Hokyoon Woo on 10/29/2016.
 */
public interface WGFile {

    void saveData(WGData data, Path filePath) throws IOException;

    void loadData(WGData data, Path filePath) throws IOException;

}
