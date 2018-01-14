import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class SettingsController {
    private static final String fileName = "src/settings";
    @FXML
    private ColorPicker disk1Color;
    @FXML
    private ColorPicker disk2Color;
    @FXML
    private ChoiceBox<Integer> choiceBox;
    @FXML
    private  Map<String,String> infoMap;
    @FXML
    private GridPane pane;
    @FXML
    private Text title;
    @FXML
    private Label color1;
    @FXML
    private Label color2;
    @FXML
    private Label size;
    @FXML
    private Label message;

    /**
     * initializes the settings.
     */
    @FXML
    protected void initialize(){
        title.setFill(Color.WHITE);
        color1.setTextFill(Color.WHITE);
        color2.setTextFill(Color.WHITE);
        size.setTextFill(Color.WHITE);
        infoMap = new TreeMap<>();
        //sets the choice box's options for board size.
        choiceBox.getItems().addAll(4, 6, 8, 10, 12, 14, 16, 18, 20);
        //checks if there are previous settings
        GameSettings settings = SettingsReader.fromReader();
        if (settings != null) {
            choiceBox.setValue(settings.getBoardSize());
            disk1Color.setValue(settings.getDisk1Color());
            disk2Color.setValue(settings.getDisk2Color());
        } else {
            choiceBox.setValue(8);
            disk1Color.setValue(Color.BLACK);
            disk2Color.setValue(Color.WHITE);
        }
    }

    /**
     * applies the settings and returns to the menu.
     * @param event the save settings button being pressed.
     */
    @FXML
    protected void apply(ActionEvent event) {
        boolean valid = true;
        //fills the map with values.
        if (disk1Color.getValue().equals(Color.GRAY) || disk2Color.getValue().equals(Color.GRAY) ||
                disk1Color.getValue() == Color.rgb(10, 91, 53) ||
                disk2Color.getValue().equals(Color.rgb(10, 91, 53))
                || disk1Color.getValue().equals(disk2Color.getValue())){
            message.setText("Please pick a different color.");
            message.setTextFill(Color.RED);
            valid = false;
        }
        if (valid) {
            infoMap.put("diskColor1", disk1Color.getValue().toString());
            infoMap.put("diskColor2", disk2Color.getValue().toString());
            infoMap.put("size", choiceBox.getValue().toString());
            writeToFile();
            try {
                SettingsReader reader = new SettingsReader();
                Parent parent = FXMLLoader.load(getClass().getResource("res/menu.fxml"));
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
    }

    /**
     * writes the settings to the file.
     */
    @FXML
    private void writeToFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(fileName));
            for (Map.Entry<String, String> entry : this.infoMap.entrySet()) {
                writer.write(entry.getKey());
                writer.write(" ");
                writer.write(entry.getValue());
                writer.println();
            }
        }catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
