import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.Map;

public class GameSettings {
    private Type startPlayer;
    private Color disk1;
    private Color disk2;
    private int boardSize;
    private Map<String, String> infoMap;

    public GameSettings(Map<String, String> infoMap) {
        this.infoMap = infoMap;
        this.readFromStringMap();
    }

    public Type startPlayer() {
        return this.startPlayer;
    }


    public Color disk1() {
        return this.disk1;
    }


    public Color disk2() {
        return this.disk2;
    }


    public int boardSize() {
        return this.boardSize;
    }

    public void readFromStringMap() {
        for (Map.Entry<String, String> entry : this.infoMap.entrySet()) {
            if (entry.getKey().equals("firstPlayer")) {
                int playerInt = Integer.parseInt(entry.getValue());
                if (playerInt == 1) {
                    this.startPlayer = Type.BLACK;
                } else if(playerInt == 2) {
                    this.startPlayer = Type.WHITE;
                }
            }
            if (entry.getKey().equals("diskColor1")) {
                this.disk1 = Color.web(entry.getValue());
            }
            if (entry.getKey().equals("diskColor2")) {
                this.disk2 = Color.web(entry.getValue());
            }
            if (entry.getKey().equals("size")) {
                this.boardSize = Integer.parseInt(entry.getValue());
            }
        }
    }
}
