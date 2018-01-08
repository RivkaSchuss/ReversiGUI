
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Reversi");
        Label lbl = new Label("Hello World!");
        lbl.setFont(new Font("Arial", 30));
        StackPane root = new StackPane();
        root.getChildren().add(lbl);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        /*
        Board board = new Board(8);
        GameLogic logic = new DefaultGameLogic();
        Game game = new Game(board, logic);
        game.run();
        System.exit(0);
        */
    }
}
