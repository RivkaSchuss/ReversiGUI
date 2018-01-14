import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.List;


public class Board extends GridPane {
    private int size;
    private Cell table[][];
    private Color disk1;
    private Color disk2;
    private Color possibleColor;
    private Color boardColor;

    /**
     * constructor for board.
     * @param size board size
     * @param disk1 color of the first disk
     * @param disk2 color of the second disk
     */
    public Board(int size, Color disk1, Color disk2) {
        this.size = size;
        this.disk1 = disk1;
        this.disk2 = disk2;
        possibleColor = Color.WHITE;
        //boardColor = Color.rgb(37,109,140);
        boardColor = Color.rgb(10, 91, 53);
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

    /**
     * draws the board.
     * @param possibleMoves the possible moves for the next turn.
     */
    public void draw(List<Location> possibleMoves) {
        //clears the drawings currently on the board.
        this.getChildren().clear();
        int width = (int)this.getPrefWidth();
        int height = (int)this.getPrefHeight();
        int cellWidth = width / size;
        int cellHeight = height / size;
        int radius = Math.min(width, height) / 2;
        //goes over the board
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                //adds a rectangle as a stack pane for each cell
                Rectangle rectangle = new Rectangle(cellWidth,cellHeight);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(boardColor);
                StackPane cell = new StackPane();
                cell.getChildren().add(rectangle);
                //if this cell has a disk from the first player, add a circle
                if (table[i][j].getStatus() == 1) {
                    Circle circle = new Circle(cell.getWidth() / 2, cellHeight,
                            radius / size, disk1);
                    cell.getChildren().add(circle);
                    this.add(cell, i, j);
                    //if this cell has a disk from the second player, add a circle
                } else if (table[i][j].getStatus() == 2) {
                    Circle circle = new Circle(cell.getPrefWidth() / 2 , cellHeight,
                            radius / size, disk2);
                    cell.getChildren().add(circle);
                    this.add(cell, i, j);
                    //checks where the possible moves are and draws an empty circle.
                } else {
                     for (int k = 0; k < possibleMoves.size(); k++) {
                         if (possibleMoves.get(k).getRow() == table[i][j].getSpot().getRow()
                                 && possibleMoves.get(k).getCol() == table[i][j].getSpot().getCol()) {
                             Circle possCircle = new Circle(Math.max(cellWidth,cellHeight) / 2, cellHeight,
                                     radius /size, boardColor);
                             possCircle.setStroke(possibleColor);
                             cell.getChildren().add(possCircle);
                         }
                     }
                     this.add(cell, i, j);
                }
            }
        }
    }

    /**
     * returns the table.
     * @return the table
     */
    public Cell[][] getTable() {
        return this.table;
    }

    /**
     * returns the board size
     * @return board size.
     */
    public int getSize() {
        return this.size;
    }
}



