import javafx.scene.paint.Color;

import java.util.Map;

public class GameSettings {
    private Type startingPlayer;
    private Color disk1Color;
    private Color disk2Color;
    private int boardSize;
    private Map<String, String> infoMap;

    public GameSettings(Map<String, String> infoMap) {
        this.infoMap = infoMap;
        this.readFromStringMap();
    }

    public Type getStartingPlayer() {
        return this.startingPlayer;
    }


    public Color getDisk1Color() {
        return this.disk1Color;
    }


    public Color getDisk2Color() {
        return this.disk2Color;
    }


    public int getBoardSize() {
        return this.boardSize;
    }

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
