import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class SettingsController {
    private static final String fileName = "src/settings";
    @FXML
    private RadioButton player1;
    @FXML
    private RadioButton player2;
    @FXML
    private ColorPicker disk1Color;
    @FXML
    private ColorPicker disk2Color;
    @FXML
    private ChoiceBox<Integer> choiceBox;
    @FXML
    private  Map<String,String> infoMap;
    private Boolean legalInput;

    @FXML
    protected void initialize(){
        infoMap = new TreeMap<>();
        choiceBox.getItems().addAll(4, 8, 12, 16, 20);
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

    @FXML
    protected void apply(ActionEvent event) {
        infoMap.put("diskColor1", disk1Color.getValue().toString());
        infoMap.put("diskColor2", disk2Color.getValue().toString());
        infoMap.put("size", choiceBox.getValue().toString());
        writeToFile();
        try {
            SettingsReader reader = new SettingsReader();
            Parent parent = FXMLLoader.load(getClass().getResource("menu.fxml"));
            Scene scene = new Scene(parent, 400, 350);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
