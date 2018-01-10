import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Board extends GridPane {
    private int size;
    private Cell table[][];
    private Color disk1;
    private Color disk2;

    public Board(int size, Color disk1, Color disk2) {
        this.size = size;
        this.disk1 = disk1;
        this.disk2 = disk2;
        this.table = new Cell[size + 1][size + 1];
        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {
                table[i][j] = new Cell();
                table[i][j].setSpot(i, j);
            }
        }
        table[size / 2][size / 2].updateStatus(2);
        table[size / 2][size / 2 + 1].updateStatus(1);
        table[size / 2 + 1][size / 2].updateStatus(1);
        table[size / 2 + 1][size / 2 + 1].updateStatus(2);
    }

    public void draw() {
        this.getChildren().clear();
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                Rectangle rectangle = new Rectangle(600 / size,600 / size);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(Color.rgb(37,109,140));
                StackPane cell = new StackPane();
                cell.getChildren().add(rectangle);
                this.add(cell, i , j);
                if (table[i][j].getStatus() == 1) {
                    this.add(new Circle(cell.getWidth()/2, cell.getHeight()/2,
                            300/size ,disk1), i, j);
                } else if (table[i][j].getStatus() == 2) {
                    this.add(new Circle(cell.getWidth()/2, cell.getHeight()/2,
                            300/size ,disk2), i, j);
                }
            }
        }

    }

    public Cell[][] getTable() {
        return this.table;
    }

    public int getSize() {
        return this.size;
    }
}



