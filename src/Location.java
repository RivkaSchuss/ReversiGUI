
public class Location {
    private int row;
    private int col;

    /**
     * constructor
     * @param row row of location
     * @param col column of location
     */
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * gets the row
     * @return the row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * gets the column
     * @return the column
     */
    public int getCol() {
        return this.col;
    }
}
