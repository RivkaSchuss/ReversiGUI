package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*
public abstract class AbstractGameLogic implements GameLogic {
    private static final int BLACK = 1;
    private static final int WHITE = 2;
    protected int turn;
    protected int otherTurn;

    public AbstractGameLogic() {
        turn = BLACK;
        otherTurn = WHITE;
    }
    public int checkScore(Cell[][] table, int size) {
        int counterBlack = 0, counterWhite = 0;
        for (int i = 0; i <= size; ++i) {
            for (int j = 0; j <= size; ++j) {
                if (table[i][j].getStatus() == BLACK) {
                    counterBlack++;
                } else if (table[i][j].getStatus() == WHITE){
                    counterWhite++;
                }
            }
        }
        if (counterBlack > counterWhite) {
            return 1;
        } else if (counterWhite > counterBlack) {
            return 2;
        }
        return 0;
    }

    public void setTurn(int currentTurn) {
        this.turn = currentTurn;
        if (currentTurn == BLACK) {
            otherTurn = WHITE;
        } else {
            otherTurn = BLACK;
        }
    }


    public int getTurn() {
        return this.turn;
    }

    public List<Location> getPossibleMoves(Cell[][] table, int size) {
        //changed dynamically to be the options for the current
        // player's cell in the for loop.
        List<Location> subOptions;
        //ultimately will store all of the options for moves.
        List<Location> options = new ArrayList<>();
        int k = 0;
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                if (table[i][j].getStatus() == otherTurn) {
                    subOptions = this.clearMoveArea(table, size, i, j, 0);
                    if (subOptions.size() != 0) {
                        for (int l = 0; l < subOptions.size(); l++ ) {
                            if (!moveExist(options, subOptions.get(l))) {
                                options.add(subOptions.get(l));
                            }
                            l++;
                        }
                    }
                }
            }
        }
        //if there are no possible moves we'll return NULL.
        if (options.size() == 0) {
            return options;
        } else {
            Location option = new Location(0, 0);
            options.add(option);
            return options;
        }
    }


    abstract public List<Location> clearMoveArea(Cell[][] table, int size, int rowPos, int colPos, int status);

    public void flipDeadCell(int row, int col, Board board) {
        int boardSize = board.getSize();
        int player1 = -1, player2 = -1;
        if (turn  == BLACK) {
            player1 = 1;
            player2 = 2;
        } else if (turn == WHITE){
            player1 = 2;
            player2 = 1;
        }
        int currentSign;
        int currentRow = row;
        int currentCol = col;
        int tempRow = 0;
        int tempCol = 0;
        for (int rowDirection = -1; rowDirection <= 1; rowDirection++) {
            for (int colDirection = -1; colDirection <= 1; colDirection++) {
                tempRow = currentRow + rowDirection;
                tempCol = currentCol + colDirection;
                if (currentRow >= 0 && currentRow <= boardSize && currentCol >= 0 && currentCol <= boardSize
                        && tempCol > 0 && tempCol <= boardSize && tempRow > 0 && tempRow <= boardSize) {
                    currentSign = board.getTable()[tempRow][tempCol].getStatus();
                    boolean flag = true;
                    if (rowDirection == 0 && colDirection == 0) {
                    } else {
                        while (currentSign == player2 && flag) {
                            tempRow += rowDirection;
                            tempCol += colDirection;
                            if (tempRow >= 0 && tempRow <= board.getSize() && tempCol >= 0
                                    && tempCol <= board.getSize()) {
                                if (board.getTable()[tempRow][tempCol].getStatus() == player1) {
                                    flipChosenCell(tempRow, tempCol, row, col, (-1) * rowDirection,
                                            (-1) * colDirection,
                                            player1, board);
                                    flag = false;
                                }
                                currentSign = board.getTable()[tempRow][tempCol].getStatus();
                            } else {
                                flag = false;
                            }
                        }
                    }
                }

            }
        }

    }

    public void flipChosenCell(int rowOrigin,int colOrigin, int rowNew, int colNew,
                                            int rowDirection, int colDirection, int player1, Board board) {
        int currentRow = rowOrigin;
        int currentCol = colOrigin;
        currentRow += rowDirection;
        currentCol += colDirection;
        while (currentRow != rowNew || currentCol != colNew) {
            board.getTable()[currentRow][currentCol].updateStatus(player1);
            currentRow += rowDirection;
            currentCol += colDirection;
        }
    }


    public Place whichPlace(int rowDif, int colDif) {
        if (rowDif > 0) {
            if (colDif > 0) {
                return sample.Place.upLeft;
            } else if (colDif == 0) {
                return sample.Place.up;
            } else if (colDif < 0) {
                return sample.Place.upRight;
            }
        } else if (rowDif == 0) {
            if (colDif > 0) {
                return sample.Place.west;
            } else if (colDif < 0) {
                return sample.Place.east;
            }
        } else if (rowDif < 0) {
            if (colDif > 0) {
                return sample.Place.downLeft;
            } else if (colDif == 0) {
                return sample.Place.down;
            } else if (colDif < 0) {
                return sample.Place.downRight;
            }
        }
        return Place.up;
    }


    public Location removeOneDead(Place place, int row, int col, Board board) {
        int rowDead = 0, colDead = 0;
        switch(place) {
            case up:
                rowDead = row - 1;
                colDead = col;
                board.getTable()[rowDead][colDead].
                        updateStatus(otherTurn);
                break;
            case upRight:
                rowDead = row - 1;
                colDead = col + 1;
                board.getTable()[rowDead][colDead].
                        updateStatus(otherTurn);
                break;
            case east:
                rowDead = row;
                colDead = col + 1;
                board.getTable()[rowDead][colDead].
                        updateStatus(otherTurn);
                break;
            case downRight:
                rowDead = row + 1;
                colDead = col + 1;
                board.getTable()[rowDead][colDead].
                        updateStatus(otherTurn);
                break;
            case down:
                rowDead = row + 1;
                colDead = col;
                board.getTable()[rowDead][colDead].
                        updateStatus(otherTurn);
                break;
            case downLeft:
                rowDead = row + 1;
                colDead = col - 1;
                board.getTable()[rowDead][colDead].
                        updateStatus(otherTurn);
                break;
            case west:
                rowDead = row;
                colDead = col - 1;
                board.getTable()[rowDead][colDead].
                        updateStatus(otherTurn);
                break;
            case upLeft:
                rowDead = row - 1;
                colDead = col - 1;
                board.getTable()[rowDead][colDead].
                        updateStatus(otherTurn);
                break;
        }
        Location location = new Location(rowDead, colDead);
        return location;
    }


    public boolean moveExist(List<Location> options, Location location) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getRow() == location.getRow() &&
                    options.get(i).getCol() == location.getCol()) {
                return true;
            }
        }
        return false;
    }

}
*/
