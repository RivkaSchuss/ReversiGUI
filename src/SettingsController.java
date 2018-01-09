import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class SettingsController {
    private static final String fileName = "src/settings";
    @FXML
    private CheckBox player1;
    @FXML
    private CheckBox player2;
    @FXML
    private Text message;
    @FXML
    private TextField disk1Color;
    @FXML
    private TextField disk2Color;
    @FXML
    private TextField boardSize;
    @FXML
    private  Map<String,String> infoMap;
    private Boolean legalInput;

    @FXML
    protected void initialize(){
        infoMap = new TreeMap<>();
    }

    @FXML
    protected void apply(ActionEvent event) {
        int playerChosen = 0;
        if (player1.isSelected() && player2.isSelected()) {
            message.setText("Please select only one player.");
        } else if(!player1.isSelected() && !player2.isSelected()) {
            message.setText("Please choose one player.");
        }
        else if (player1.isSelected()) {
            playerChosen = 1;
            legalInput = true;
        } else if (player2.isSelected()) {
            playerChosen = 2;
            legalInput = true;
        }
        infoMap.put("firstPlayer", Integer.toString(playerChosen));
        infoMap.put("diskColor1", disk1Color.getText());
        infoMap.put("diskColor2", disk2Color.getText());
        infoMap.put("size", boardSize.getText());
        if (legalInput) {
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
    }

    @FXML
    private void writeToFile() {
        BufferedWriter writer = null;
        try {
            System.out.println("write");
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            for (Map.Entry<String, String> entry : this.infoMap.entrySet()) {
                writer.write(entry.getKey());
                writer.write(" ");
                writer.write(entry.getValue());
                writer.newLine();
            }
        }catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
