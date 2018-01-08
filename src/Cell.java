package sample;


public class Cell {
    private int status;
    private sample.Location spot;

    public Cell() {
        this.status = 0;
        spot = new sample.Location(0, 0);
    }

    public int getStatus() {
        return this.status;
    }

    public void updateStatus(int status) {
        this.status = status;
    }

    public void setSpot(int row, int col) {
        this.spot = new sample.Location(row, col);
    }
}
