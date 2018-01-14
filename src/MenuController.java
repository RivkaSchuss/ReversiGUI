import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController{
    @FXML
    private Text title;

    /**
     * initializes the menu controller.
     */
    public void initialize() {
        title.setFont(new Font(60));
        title.setFill(Color.WHITE);
    }

    /**
     * loads the game controller when the button is pressed.
     * @param event the button being pressed.
     */
    @FXML
    protected void startGame(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SettingsReader reader = new SettingsReader();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("res/game.fxml"));
            Parent root = loader.load();
            GameController controller = loader.getController();
            controller.setStage(stage);
            controller.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * loads the settings controller when the button is pressed.
     * @param event the button being pressed.
     */
    @FXML
    protected void startSettings (ActionEvent event){
        try {
            SettingsReader reader = new SettingsReader();
            Parent parent = FXMLLoader.load(getClass().getResource("res/settings.fxml"));
            parent.setId("pane");
            Scene scene = new Scene(parent, 400, 350);
            scene.getStylesheets().add("mainStyle.css");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * quits the game.
     */
    @FXML
    protected void exit (){
        System.exit(0);
    }

}
