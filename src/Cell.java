import javafx.scene.control.Button;

public class Cell extends Button {
    private int status;
    private Location spot;

    /**
     * constructor.
     */
    public Cell() {
        this.status = 0;
        spot = new Location(0, 0);
    }

    /**
     * gets the location of the cell.
     * @return the location
     */
    public Location getSpot() {
        return spot;
    }

    /**
     * gets the status of the cell.
     * @return the status
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * updates the status of the cell
     * @param status the new status
     */
    public void updateStatus(int status) {
        this.status = status;
    }

    /**
     * sets the spot of the cell
     * @param row the row of the new status
     * @param col the col of the new status
     */
    public void setSpot(int row, int col) {
        this.spot = new Location(row, col);
    }
}
