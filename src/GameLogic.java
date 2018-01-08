
import java.util.List;

public interface GameLogic {
    void playOneTurn(Board board);
    int checkScore(Cell[][] table, int size);
    void setTurn(Type currentTurn);
    Type getTurn();
    boolean isRunning();
    List<Location> getPossibleMoves(Cell[][] table, int size);
    List<Location> clearMoveArea(Cell[][] table, int size, int rowPos, int colPos, int status);
    void flipDeadCell(int row, int col, Board board);
    Place whichPlace(int rowDif, int colDif);
    Location removeOneDead(Place place, int row, int col, Board board);
    boolean moveExist(List<Location> options, Location location);
}
