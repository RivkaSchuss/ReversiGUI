import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


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

    }

}
