import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GameController {
    @FXML
    private Type startPlayer;

    @FXML
    private int boardSize;

    @FXML
    private Color disk1;

    @FXML
    private Color disk2;

    @FXML
    private GridPane gameBoard;

    @FXML
    public void initialize() {
        GameSettings info = SettingsReader.fromReader();
        System.out.println(info.boardSize());
        System.out.println(info.disk1());
        System.out.println(info.disk2());
        System.out.println(info.startPlayer());
        this.boardSize = info.boardSize();
        this.disk1 = info.disk1();
        this.disk2 = info.disk2();
        this.startPlayer = info.startPlayer();
        Board board = new Board(this.boardSize);
        for (int i  = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                Cell cell = board.getTable()[i][j];
                //gameBoard.add(cell, i, j);
            }
        }
        /*
        GameLogic logic = new DefaultGameLogic(startPlayer);
        Game game = new Game(board, logic);
        game.run();
        */
    }

}
