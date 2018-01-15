package controllers;

import game.Board;
import game.GameLogic;
import game.Location;
import game.Type;
import initSettings.GameSettings;
import initSettings.SettingsReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameController {
    private static final int defaultBoardSize = 8;
    private static final int gameScreenWidth = 1050;
    private static final int gameScreenHeight = 700;
    private static final int menuScreenWidth = 450;
    private static final int menuScreenHeight = 350;
    private static final int labelSize = 30;
    private int boardSize;
    private Color disk1;
    private Color disk2;
    private Board board;
    private GameLogic logic;
    @FXML
    private HBox root;
    private Label currentPlayer;
    private Label player1Score;
    private Label player2Score;
    private Label message;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    private int running;
    @FXML
    private VBox gameStatus;
    @FXML
    private Alert alert;
    private int firstScore;
    private int secondScore;

    /**
     * initializes the game.
     */
    public void initialize() {
        //initializes the alert box
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update");
        alert.setHeaderText(null);
        running = 2;
        try {
            //reads from the settings, if there are no settings, sets defaults.
            setSettings();
            board = new Board(boardSize, disk1, disk2);
            //sets the entire board which is a grid pane to be an event listener, and every time clicked, a move
            // is performed.
            board.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                Location move = converter(e.getX(), e.getY());
                performMove(move);
            });
            logic = new GameLogic(board);
            //sets the main root of the screen to be an hbox
            root = new HBox();
            root.setPadding(new Insets(5));
            //sets the vbox for all of the labels
            gameStatus = new VBox();
            root.setAlignment(Pos.CENTER);
            //root.setSpacing(20);
            gameStatus.setSpacing(30);
            setLabels();
            //adds the gridpane and the vbox to the hbox
            root.widthProperty().addListener(((observable, oldValue, newValue) -> {
                double boardNewWidth = newValue.doubleValue() - 420;
                board.setPrefWidth(boardNewWidth);
                board.draw(logic.getPossibleMoves());
            }));
            root.heightProperty().addListener(((observable, oldValue, newValue) -> {
                board.setPrefHeight(newValue.doubleValue());
                board.draw(logic.getPossibleMoves());
            }));
            root.getChildren().addAll(board, gameStatus);
            //gets the background of the game screen
            root.setId("gamePane");
            scene = new Scene(root, gameScreenWidth, gameScreenHeight);
            scene.getStylesheets().add("gameStyle.css");
            board.draw(logic.getPossibleMoves());
            //draws the board
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * reads and sets the settings of the game.
     */
    public void setSettings() {
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
    }

    /**
     * initializes all of the labels.
     */
    public void setLabels() {
        currentPlayer = new Label("Current player: First");
        currentPlayer.setTextFill(disk1);
        player1Score = new Label("First player's score: 2");
        player2Score = new Label("Second player's score: 2");
        message = new Label("");
        currentPlayer.setFont(new Font(labelSize));
        player1Score.setFont(new Font(labelSize));
        player1Score.setTextFill(Color.WHITE);
        player2Score.setFont(new Font(labelSize));
        player2Score.setTextFill(Color.WHITE);
        message.setFont(new Font(labelSize));
        message.setTextFill(Color.WHITE);
        Button quit = new Button("Return to Menu");
        quit.setOnAction(ev -> {
            loadFXML("menu.fxml", menuScreenWidth, menuScreenHeight, ev);
        });
        //adds the labels to the vbox
        gameStatus.getChildren().addAll(currentPlayer, player1Score, player2Score, message, quit);
        gameStatus.setAlignment(Pos.TOP_CENTER);
    }

    /**
     * performs a move on a click.
     * @param move the move performed.
     */
    public void performMove(Location move) {
        message.setText("");
        boolean flag = false;
        //checks if the move was a valid move.
        int result = logic.checkMove(move);
        firstScore = logic.checkScore(board.getTable(), boardSize, 1);
        secondScore = logic.checkScore(board.getTable(), boardSize, 2);
        //if the move was a valid move:
        if (result == 1) {
            //flips the disk
            board.getTable()[move.getRow()][move.getCol()].updateStatus(otherTurn());
            logic.flipDeadCell(move.getRow(), move.getCol(), board);
            //switches turns
            if (logic.getTurn() == Type.FIRST) {
                logic.setTurn(Type.SECOND);
                //if the next player has no moves
                if (logic.getPossibleMoves().size() == 0) {
                    running -= 1;
                    flag = true;
                }
                currentPlayer.setText("Current Player: Second");
                currentPlayer.setTextFill(disk2);
            } else {
                logic.setTurn(Type.FIRST);
                if (logic.getPossibleMoves().size() == 0) {
                    running -= 1;
                    flag = true;
                }
                currentPlayer.setText("Current Player: First");
                currentPlayer.setTextFill(disk1);
            }
            //if the next player has no moves:
            if (flag) {
                noMoves();
            } else {
                board.draw(logic.getPossibleMoves());
                firstScore = logic.checkScore(board.getTable(), boardSize, 1);
                secondScore = logic.checkScore(board.getTable(), boardSize, 2);
                player1Score.setText("First player's score: " + firstScore);
                player2Score.setText("Second Player's score : " + secondScore);
                if (!flag) {
                    running = 2;
                }
            }
            //if the move wasn't valid
        } else if (result == 0) {
            message.setText("That isn't a move!");
        }
    }

    /**
     * deals with the case of the possible move list being empty.
     */
    public void noMoves() {
        int numOfDisks = logic.checkScore(board.getTable(), boardSize, 1)
                + logic.checkScore(board.getTable(), boardSize, 2);
        //if the game is over, display an alert and end the game
        if (running <= 0 || numOfDisks == boardSize * boardSize) {
            if (firstScore > secondScore) {
                Circle circle = new Circle(100, disk1);
                gameStatus.getChildren().add(circle);
                alert.setContentText("Player 1, You win!!!");
            } else if (secondScore > firstScore) {
                Circle circle = new Circle(100, disk2);
                gameStatus.getChildren().add(circle);
                alert.setContentText("Player 2, You win!!!");
            } else {
                alert.setContentText("It's a TIE!!!");
            }
            alert.showAndWait();
            message.setText("GAME OVER");
            //if the game isn't over, display an alert that there are no moves and switch players
        } else {
            board.draw(logic.getPossibleMoves());
            firstScore = logic.checkScore(board.getTable(), boardSize, 1);
            secondScore = logic.checkScore(board.getTable(), boardSize, 2);
            player1Score.setText("First player's score: " + firstScore);
            player2Score.setText("Second Player's score : " + secondScore);
            alert.setContentText("You have no more moves!!");
            alert.showAndWait();
            //switch players
            if (logic.getTurn() == Type.FIRST) {
                logic.setTurn(Type.SECOND);
                currentPlayer.setText("Current Player: Second");
                currentPlayer.setTextFill(disk2);
            } else {
                logic.setTurn(Type.FIRST);
                currentPlayer.setText("Current Player: First");
                currentPlayer.setTextFill(disk1);
            }
            board.draw(logic.getPossibleMoves());
        }
    }

    /**
     * sets the stage for the game.
     * @param stage the stage for the game.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * sets the scene and shows the stage.
     */
    public void show() {
        stage.setScene(scene);
        stage.show();
    }

    /**
     * converts the click to a defined point
     * @param x the x clicked.
     * @param y the y clicked.
     * @return a new location.
     */
    public Location converter(double x, double y) {
        int row = (int) x / ((int) board.getPrefWidth() / boardSize) + 1;
        int col = (int) y / ((int) board.getPrefHeight() / boardSize) + 1;
        return new Location(row, col);
    }

    /**
     * sets the other turn.
     * @return the other turn.
     */
    public int otherTurn() {
        if (logic.getTurn() == Type.FIRST) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * loads an fxml.
     * @param fxml the string of the fxml.
     * @param width the width of the screen to load
     * @param height the height of the screen to load
     * @param event the event of the click
     */
    @FXML
    protected void loadFXML(String fxml, int width, int height, ActionEvent event) {
        try {
            SettingsReader reader = new SettingsReader();
            Parent parent = FXMLLoader.load(ClassLoader.getSystemClassLoader().getResource(fxml));
            Scene scene = new Scene(parent, width, height);
            scene.getStylesheets().add("mainStyle.css");
            parent.setId("pane");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
