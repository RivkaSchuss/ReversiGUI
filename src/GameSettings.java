import javafx.scene.paint.Color;

import java.util.Map;

public class GameSettings {
    private Color disk1Color;
    private Color disk2Color;
    private int boardSize;
    private Map<String, String> infoMap;

    /**
     * constructor, calls the function to read.
     * @param infoMap the map of settings.
     */
    public GameSettings(Map<String, String> infoMap) {
        this.infoMap = infoMap;
        this.readFromStringMap();
    }

    /**
     * gets the disk1 color
     * @return the disk1 color
     */
    public Color getDisk1Color() {
        return this.disk1Color;
    }

    /**
     * gets the disk2 color
     * @return the disk2 color
     */
    public Color getDisk2Color() {
        return this.disk2Color;
    }

    /**
     * gets the board size
     * @return the board size
     */
    public int getBoardSize() {
        return this.boardSize;
    }

    /**
     * reads from the map and sets the members.
     */
    public void readFromStringMap() {
        for (Map.Entry<String, String> entry : this.infoMap.entrySet()) {
            if (entry.getKey().equals("diskColor1")) {
                this.disk1Color = Color.web(entry.getValue());
            }
            if (entry.getKey().equals("diskColor2")) {
                this.disk2Color = Color.web(entry.getValue());
            }
            if (entry.getKey().equals("size")) {
                this.boardSize = Integer.parseInt(entry.getValue());
            }
        }
    }
}
