
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader((ClassLoader.getSystemClassLoader().getResource("menu.fxml")));
            GridPane root = loader.load();
            MenuController controller = loader.getController();
            SettingsReader reader = new SettingsReader();
            Scene scene = new Scene(root, 400, 350);
            primaryStage.setTitle("Reversi");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
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
