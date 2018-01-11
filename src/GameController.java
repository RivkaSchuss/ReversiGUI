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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private static final int defaultBoardSize = 8;
    private static final int gameScreenWidth = 1000;
    private static final int gameScreenHeight = 700;
    private static final int menuScreenWidth = 400;
    private static final int menuScreenHeight = 350;
    @FXML
    private int boardSize;
    @FXML
    private Color disk1;
    @FXML
    private Color disk2;
    @FXML
    private Board board;
    @FXML
    private GameLogic logic;
    @FXML
    private HBox root;
    @FXML
    private Label currentPlayer;
    @FXML
    private Label player1Score;
    @FXML
    private Label player2Score;
    @FXML
    private Label message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void startGame(ActionEvent event) {
        try {
            GameSettings info = SettingsReader.fromReader();
            if (info == null) {
                boardSize = defaultBoardSize;
                disk1 = Color.BLACK;
                disk2 = Color.WHITE;
            } else {
                boardSize = info.getBoardSize();
                disk1 = info.getDisk1Color();
                disk2 = info.getDisk2Color();
            }
            board = new Board(boardSize, disk1, disk2);
            board.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                Location move = converter(e.getX(), e.getY());
                performMove(move);
            });
            logic = new GameLogic(board);
            root = new HBox();
            root.setPadding(new Insets(5));
            VBox gameStatus = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);
            currentPlayer = new Label("Current player: First");
            player1Score = new Label("First player's score: 2");
            player2Score = new Label("Second player's score: 2");
            message = new Label("Player 1: It's your move!");
            message.setFont(new Font(30));
            Button quit = new Button("Quit");
            quit.setOnAction(ev -> {
                loadFXML("menu.fxml", menuScreenWidth, menuScreenHeight, ev);
            });
            gameStatus.getChildren().addAll(currentPlayer, player1Score, player2Score, message, quit);
            root.getChildren().addAll(board, gameStatus);
            Scene scene = new Scene(root, gameScreenWidth, gameScreenHeight);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            board.setPossibleMoves(logic.getPossibleMoves());
            board.draw();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location converter(double x, double y) {
        int col = (int) x / ((gameScreenHeight - 100) / boardSize) + 1;
        int row = (int) y / ((gameScreenHeight - 100) / boardSize) + 1;
        return new Location(row, col);
    }

    public int otherTurn() {
        if(logic.getTurn() == Type.FIRST) {
            return 1;
        } else {
            return 2;
        }
    }

    public void performMove(Location move) {
        int result = logic.checkMove(move);
        int firstScore = logic.checkScore(board.getTable(), boardSize, 1);
        int secondScore = logic.checkScore(board.getTable(), boardSize, 2);
        if (result == 1) {
            board.getTable()[move.getRow()][move.getCol()].updateStatus(otherTurn());
            logic.flipDeadCell(move.getRow(), move.getCol(), board);
            System.out.println(move.getRow() + " " +move.getCol());
            System.out.println(board.getTable()[move.getRow()][move.getCol()].getStatus());
            if (logic.getTurn() == Type.FIRST) {
                logic.setTurn(Type.SECOND);
                currentPlayer.setText("Current Player: Second");
                message.setText("Player 2: It's your move!");
            } else {
                logic.setTurn(Type.FIRST);
                currentPlayer.setText("Current Player: First");
                message.setText("Player 1: It's your move!");
            }
            board.setPossibleMoves(logic.getPossibleMoves());
            board.draw();
            firstScore = logic.checkScore(board.getTable(), boardSize, 1);
            secondScore = logic.checkScore(board.getTable(), boardSize, 2);
            player1Score.setText("First player's score: " + firstScore);
            player2Score.setText("Second Player's score : " + secondScore);
        } else if (result == -1) {
            if (!logic.isRunning()) {
                if (firstScore > secondScore) {
                    message.setText("First player, You win!!!");
                } else if (secondScore > firstScore) {
                    message.setText("Second player, You win!!!");
                } else {
                    message.setText("It's a TIE!");
                }
            } else {
                if (logic.getTurn() == Type.FIRST) {
                    message.setText("Player 1: You have no more moves!");

                } else {
                    message.setText("Player 2: You have no more moves!");
                }
            }
        } else {
            message.setText("That isn't a move!");
        }
    }

        @FXML
        protected void startSettings (ActionEvent event){
            loadFXML("settings.fxml", menuScreenWidth, menuScreenHeight, event);

        }

        @FXML
        protected void loadFXML (String fxml,int width, int height, ActionEvent event){
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
        protected void exit (){
            System.exit(0);
        }

    }
