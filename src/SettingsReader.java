import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class SettingsReader {
    private static final String firstPlayer = "firstPlayer";
    private static final String diskColor1 = "diskColor1";
    private static final String diskColor2 = "diskColor2";
    private static final String size = "size";

    public SettingsReader() {
        //SettingsReader.fromReader();
    }

    public static GameSettings fromReader() {
        String thisLine;
        Map<String, String> infoMap = new TreeMap<>();
        InputStream is = null;
        Reader reader = null;
        BufferedReader br = null;
        try {
            System.out.println("read");
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("settings");
            reader = new InputStreamReader(is);
            br = new BufferedReader(reader);
            while ((thisLine = br.readLine()) != null) {
                if (thisLine.contains(firstPlayer)) {
                    int index = thisLine.indexOf(" ", thisLine.indexOf(firstPlayer) +
                            firstPlayer.length() + 1);
                    if (index == -1) {
                        index = thisLine.length();
                    }
                    infoMap.put(firstPlayer, thisLine.substring(thisLine.indexOf(firstPlayer)
                            + firstPlayer.length() + 1, index));
                }
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
        } catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {
                    br.close();
                }
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new GameSettings(infoMap);
    }
}
