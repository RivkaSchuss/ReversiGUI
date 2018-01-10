import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable{
    @FXML
    private int boardSize;
    @FXML
    private Color disk1;
    @FXML
    private Color disk2;
    @FXML
    private Type startPlayer;
    @FXML
    private Board board;
    @FXML
    private GameLogic logic;
    @FXML
    private HBox root;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void startGame(ActionEvent event){
        try {
            GameSettings info = SettingsReader.fromReader();
            if (info == null) {
                boardSize = 8;
                disk1 = Color.BLACK;
                disk2 = Color.WHITE;
                startPlayer = Type.FIRST;
            } else {
                boardSize = info.getBoardSize();
                disk1 = info.getDisk1Color();
                disk2 = info.getDisk2Color();
                startPlayer = info.getStartingPlayer();
            }
            board = new Board(boardSize, disk1, disk2);
            board.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                Location move = converter(e.getX(), e.getY());
                logic.playOneTurn(move);
            });
            logic = new GameLogic(startPlayer, board);
            root = new HBox();
            root.setPadding(new Insets(5));
            //board.setPadding(new Insets(10,10,10,10));
            VBox gameStatus = new VBox();
            //board.setAlignment(Pos.CENTER);
            root.setAlignment(Pos.CENTER);
            root.setSpacing(30);
            Label current = new Label("Current player: ");
            Label blackPlayer = new Label("Black player's score: ");
            Label whitePlayer = new Label("White player's score: ");
            Button quit = new Button("Quit");
            quit.setOnAction(ev-> { loadFXML("menu.fxml", 400, 350, ev); });
            gameStatus.getChildren().add(current);
            gameStatus.getChildren().add(blackPlayer);
            gameStatus.getChildren().add(whitePlayer);
            gameStatus.getChildren().add(quit);
            root.getChildren().addAll(board, gameStatus);
            Scene scene = new Scene(root, 800, 700);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            board.draw();
            stage.show();
            run();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Location converter(double x, double y) {
        int col = (int) x / (600 / boardSize) + 1;
        int row = (int) y / (600 / boardSize) + 1;
        return new Location(row, col);
    }

    public void run() {
        if (logic.isRunning()) {
            board.draw();
            //logic.playOneTurn(board);
            if (logic.getTurn() == Type.FIRST) {
                logic.setTurn(Type.SECOND);
            } else {
                logic.setTurn(Type.FIRST);
            }
        } else {
            int blackScore = logic.checkScore(board.getTable(), board.getSize(), 1);
            int whiteScore = logic.checkScore(board.getTable(), board.getSize(), 2);
            if (blackScore > whiteScore) {
                System.out.println("X: Congrats, you win!");
            } else if (whiteScore > blackScore) {
                System.out.println("O: Congrats, you win!");
            } else {
                System.out.println("It's a TIE!");
            }
        }
        /*
        while (logic.isRunning()) {
            board.draw();
            logic.playOneTurn(board);
            if (logic.getTurn() == Type.FIRST) {
                logic.setTurn(Type.SECOND);
            } else {
                logic.setTurn(Type.FIRST);
            }
        }

        int blackScore = logic.checkScore(board.getTable(), board.getSize(), 1);
        int whiteScore = logic.checkScore(board.getTable(), board.getSize(), 2);
        if (blackScore > whiteScore) {
            System.out.println("X: Congrats, you win!");
        } else if (whiteScore > blackScore) {
            System.out.println("O: Congrats, you win!");
        } else {
            System.out.println("It's a TIE!");
        }
        */
    }

    @FXML
    protected void startSettings(ActionEvent event){
        loadFXML("settings.fxml", 400, 350, event);

    }

    @FXML
    protected void loadFXML(String fxml, int width, int height, ActionEvent event) {
        try {
            SettingsReader reader = new SettingsReader();
            Parent parent = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(parent, width, height);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void exit(ActionEvent event) {
        System.exit(0);
    }


}
