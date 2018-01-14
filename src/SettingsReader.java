import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class SettingsReader {
    private static final String diskColor1 = "diskColor1";
    private static final String diskColor2 = "diskColor2";
    private static final String size = "size";

    /**
     * static function that reads from the file
     * @return a class GameSettings with its members.
     */
    public static GameSettings fromReader() {
        String thisLine;
        Map<String, String> infoMap = new TreeMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File("src/settings")));
            //checks the key and adds the value to the map based on its key.
            while ((thisLine = br.readLine()) != null) {
                if (thisLine.contains(diskColor1)) {
                    int index = thisLine.indexOf(" ", thisLine.indexOf(diskColor1) +
                            diskColor1.length() + 1);
                    if (index == -1) {
                        index = thisLine.length();
                    }
                    infoMap.put(diskColor1, thisLine.substring(thisLine.indexOf(diskColor1) +
                            diskColor1.length() + 1, index));
                }
                if (thisLine.contains(diskColor2)) {
                    int index = thisLine.indexOf(" ", thisLine.indexOf(diskColor2) +
                            diskColor2.length() + 1);
                    if (index == -1) {
                        index = thisLine.length();
                    }
                    infoMap.put(diskColor2, thisLine.substring(thisLine.indexOf(diskColor2) +
                            diskColor2.length() + 1, index));
                }
                if (thisLine.contains(size)) {
                    int index = thisLine.indexOf(" ", thisLine.indexOf(size) + size.length() + 1);
                    if (index == -1) {
                        index = thisLine.length();
                    }
                    infoMap.put(size, thisLine.substring(thisLine.indexOf(size) + size.length() + 1, index));
                }
            }
            //if there is no file settings, return null.
        } catch(IOException e) {
            return null;
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //returns the class and sends it the map.
        return new GameSettings(infoMap);
    }
}
