
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

    /**
     * the start function
     * @param primaryStage the primary stage of the program
     * @throws Exception the exception thrown.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader((ClassLoader.getSystemClassLoader().getResource("res/menu.fxml")));
            GridPane root = loader.load();
            root.setId("pane");
            //GameController controller = loader.getController();
            //SettingsReader reader = new SettingsReader();
            Scene scene = new Scene(root, 450, 350);
            scene.getStylesheets().addAll(this.getClass().getResource("mainStyle.css").toExternalForm());
            primaryStage.setTitle("Reversi");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * the main, launches the game.
     * @param args arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
