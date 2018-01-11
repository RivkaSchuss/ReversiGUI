import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    protected void startGame(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SettingsReader reader = new SettingsReader();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent root = loader.load();
            GameController controller = loader.getController();
            controller.setStage(stage);
            controller.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void startSettings (ActionEvent event){
        try {
            SettingsReader reader = new SettingsReader();
            Parent parent = FXMLLoader.load(getClass().getResource("settings.fxml"));
            Scene scene = new Scene(parent, 400, 350);
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
