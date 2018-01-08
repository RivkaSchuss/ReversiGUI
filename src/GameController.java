import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Board board = new Board(8);
        for (int i  = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Cell cell = board.getTable()[i][j];
                //gameBoard.add(cell, i, j);
            }
        }
        GameLogic logic = new DefaultGameLogic();
        Game game = new Game(board, logic);
        game.run();
    }

    @FXML
    protected void hi() {
        System.out.println("hi");
    }

}
