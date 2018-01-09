import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class MenuController {

    @FXML
    protected void startGame(ActionEvent event){
        //loadFXML("game.fxml", event);
        GameSettings info = SettingsReader.fromReader();
        System.out.println(info.boardSize());
        System.out.println(info.disk1());
        System.out.println(info.disk2());
        System.out.println(info.startPlayer());
        int boardSize = info.boardSize();
        Color disk1 = info.disk1();
        Color disk2 = info.disk2();
        Type startPlayer = info.startPlayer();
        Board board = new Board(boardSize);
        DefaultGameLogic logic = new DefaultGameLogic(startPlayer);
        HBox root = new HBox();
        root.setPadding(new Insets(5));
        GridPane gameScreen = new GridPane();
        gameScreen.setPadding(new Insets(10,10,10,10));
        VBox gameStatus = new VBox();
        for (int i = 0;i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Rectangle rectangle = new Rectangle(50,50);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(Color.rgb(37,109,140));
                gameScreen.add(rectangle, i , j);
            }
        }
        Label current = new Label("Current player: Black");
        Label blackPlayer = new Label("Black player's score:");
        Label whitePlayer = new Label("White player's score:");
        gameStatus.getChildren().add(current);
        gameStatus.getChildren().add(blackPlayer);
        gameStatus.getChildren().add(whitePlayer);
        root.getChildren().addAll(gameScreen, gameStatus);
        Scene scene = new Scene(root, 600, 500);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void startSettings(ActionEvent event){
        loadFXML("settings.fxml", event);
    }

    @FXML
    private void loadFXML(String fxml, ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(parent, 400, 350);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
