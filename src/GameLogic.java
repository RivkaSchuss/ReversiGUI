
import sample.Cell;

import java.util.List;

public interface GameLogic {
    void playOneTurn(Board board);
    int checkScore(Cell[][] table, int size);
    void setTurn(Type currentTurn);
    Type getTurn();
    boolean isRunning();
    List<sample.Location> getPossibleMoves(Cell[][] table, int size);
    List<sample.Location> clearMoveArea(Cell[][] table, int size, int rowPos, int colPos, int status);
    void flipDeadCell(int row, int col, Board board);
    Place whichPlace(int rowDif, int colDif);
    sample.Location removeOneDead(Place place, int row, int col, Board board);
    boolean moveExist(List<sample.Location> options, sample.Location location);
}
