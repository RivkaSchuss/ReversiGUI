
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLogic {
    private Type turn = Type.FIRST;
    private int otherTurn = 1;
    private Board board;

    public GameLogic(Board board) {
        this.board = board;
    }

    /**
     * checks if the move is valid.
     * @param move the move to check
     * @return 1 or 0 if there is a move or not.
     */
    public int checkMove(Location move) {
        List<Location> moves;
        moves = getPossibleMoves();
        for (int i = 0; i < moves.size(); i++) {
            if (move.getRow() == moves.get(i).getRow() && move.getCol() == moves.get(i).getCol()) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * checks around the point.
     * @param table the board table
     * @param size the board size
     * @param rowPos the row position to check
     * @param colPos the column position to check
     * @param status the status to check against.
     * @return a list of sub options
     */
    public List<Location> clearMoveArea(Cell[][] table, int size, int rowPos, int colPos, int status) {
        int l = 0;
        List<Location> currentOptions = new ArrayList<>();
        if (rowPos - 2 > 0 || rowPos + 2 <= size) {
            //checking the upper side
            if (rowPos - 2 > 0) {
                if (table[rowPos - 1][colPos].getStatus() != otherTurn
                        && table[rowPos - 1][colPos].getStatus() > 0) {
                    if (table[rowPos - 2][colPos].getStatus() == status) {
                        Location option = new Location(rowPos - 2, colPos);
                        currentOptions.add(option);
                        l++;
                    } else {
                        Location option = getFromUp(table, size, rowPos - 1, colPos, status);
                        if (option.getRow() != 0) {
                            currentOptions.add(option);
                            l++;
                        }
                    }
                }
            }
            //checking the lower side
            if (rowPos + 2 <= size) {
                if (table[rowPos + 1][colPos].getStatus() != otherTurn
                        && table[rowPos + 1][colPos].getStatus() > 0) {
                    if (table[rowPos + 2][colPos].getStatus() == status) {
                        Location option = new Location(rowPos + 2, colPos);
                        currentOptions.add(option);
                        l++;
                    } else {
                        Location option = getFromDown(table, size, rowPos + 1, colPos, status);
                        if (option.getRow() != 0) {
                            currentOptions.add(option);
                            l++;
                        }
                    }
                }
            }
        }
        //checking whether the diagonal moves are possible.
        if (rowPos - 2 > 0 || rowPos + 2 <= size
                || colPos - 2 > 0 || colPos + 2 <= size) {
            //checking the upper left side
            if (rowPos - 2 > 0 && colPos - 2 > 0) {
                if (table[rowPos - 1][colPos - 1].getStatus() != otherTurn
                        && table[rowPos - 1][colPos - 1].getStatus() > 0) {
                    if (table[rowPos - 2][colPos - 2].getStatus() == status) {
                        Location option = new Location(rowPos - 2, colPos - 2);
                        currentOptions.add(option);
                        l++;
                    } else {
                        Location option = getFromUpLeft(table, size, rowPos - 1, colPos - 1, status);
                        if (option.getRow() != 0) {
                            currentOptions.add(option);
                            l++;
                        }
                    }
                }
            }
            //checking the upper right side
            if (rowPos - 2 > 0 && colPos + 2 <= size) {
                if (table[rowPos - 1][colPos + 1].getStatus() != otherTurn
                        && table[rowPos - 1][colPos + 1].getStatus() > 0) {
                    if (table[rowPos - 2][colPos + 2].getStatus() == status) {
                        Location option = new Location(rowPos - 2, colPos + 2);
                        currentOptions.add(option);
                        l++;
                    } else {
                        Location option = getFromUpRight(table, size, rowPos - 1, colPos + 1, status);
                        if (option.getRow() != 0) {
                            currentOptions.add(option);
                            l++;
                        }
                    }
                }
            }
            //checking the lower left side
            if (rowPos + 2 <= size && colPos - 2 > 0) {
                if (table[rowPos + 1][colPos - 1].getStatus() != otherTurn
                        && table[rowPos + 1][colPos - 1].getStatus() > 0) {
                    if (table[rowPos + 2][colPos - 2].getStatus() == status) {
                        Location option = new Location(rowPos + 2, colPos - 2);
                        currentOptions.add(option);
                        l++;
                    } else {
                        Location option = getFromDownLeft(table, size, rowPos + 1, colPos - 1, status);
                        if (option.getRow() != 0) {
                            currentOptions.add(option);
                            l++;
                        }
                    }
                }
            }
            //checking the lower right side
            if (rowPos + 2 <= size && colPos + 2 <= size) {
                if (table[rowPos + 1][colPos + 1].getStatus() != otherTurn
                        && table[rowPos + 1][colPos + 1].getStatus() > 0) {
                    if (table[rowPos + 2][colPos + 2].getStatus() == status) {
                        Location option = new Location(rowPos + 2, colPos + 2);
                        currentOptions.add(option);
                        l++;
                    } else {
                        Location option = getFromDownRight(table, size, rowPos + 1, colPos + 1, status);
                        if (option.getRow() != 0) {
                            currentOptions.add(option);
                            //delete option;
                            l++;
                        }
                    }
                }
            }
        }
        //checking whether the horizontal moves are possible.
        if (colPos - 2 > 0 || colPos + 2 <= size) {
            //checking the left side
            if (colPos - 2 > 0) {
                if (table[rowPos][colPos - 1].getStatus() != otherTurn
                        && table[rowPos][colPos - 1].getStatus() > 0) {
                    if (table[rowPos][colPos - 2].getStatus() == status) {
                        Location option = new Location(rowPos, colPos - 2);
                        currentOptions.add(option);
                        //delete option;
                        l++;
                    } else {
                        Location option = getFromLeft(table, size, rowPos, colPos - 1, status);
                        if (option.getRow() != 0) {
                            currentOptions.add(option);
                            //delete option;
                            l++;
                        }
                    }
                }
            }
            //checking the right side
            if (colPos + 2 <= size) {
                if (table[rowPos][colPos + 1].getStatus() != otherTurn
                        && table[rowPos][colPos + 1].getStatus() > 0) {
                    if (table[rowPos][colPos + 2].getStatus() == status) {
                        Location option = new Location(rowPos, colPos + 2);
                        currentOptions.add(option);
                        l++;
                    } else {
                        Location option = getFromRight(table, size, rowPos, colPos + 1, status);
                        if (option.getRow() != 0) {
                            currentOptions.add(option);
                            l++;
                        }
                    }
                }
            }
        }
        if (l == 0) {
            currentOptions.clear();
            return currentOptions;
        } else {
            return currentOptions;
        }
    }

    public int checkScore(Cell[][] table, int size, int type) {
        int counter = 0;
        for (int i = 0; i <= size; ++i) {
            for (int j = 0; j <= size; j++) {
                if (table[i][j].getStatus() == type) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * sets the turn of the player
     * @param currentTurn the current turn to change to
     */
    public void setTurn(Type currentTurn) {
        this.turn = currentTurn;
        if (currentTurn == Type.FIRST) {
            otherTurn = 1;
        } else {
            otherTurn = 2;
        }
    }

    /**
     * returns the game turn
     * @return the game turn
     */
    public Type getTurn() {
        return this.turn;
    }

    /**
     * gets a list of the possible moves.
     * @return a list of possible moves.
     */
    public List<Location> getPossibleMoves() {
        //changed dynamically to be the options for the current
        // player's cell in the for loop.
        List<Location> subOptions = new ArrayList<>();
        //ultimately will store all of the options for moves.
        List<Location> options = new ArrayList<>();
        for (int i = 0; i <= board.getSize(); i++) {
            for (int j = 0; j <= board.getSize(); j++) {
                if (board.getTable()[i][j].getStatus() == otherTurn) {
                    subOptions = this.clearMoveArea(board.getTable(), board.getSize(), i, j, 0);
                    if (subOptions.size() != 0) {
                        for (int l = 0; l < subOptions.size(); l++) {
                            if (!moveExist(options, subOptions.get(l))) {
                                options.add(subOptions.get(l));
                            }
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

    /**
     * flips the cell
     * @param row the row to flip
     * @param col the column to flip
     * @param board the board
     */
    public void flipDeadCell(int row, int col, Board board) {
        int boardSize = board.getSize();
        int player1 = -1, player2 = -1;
        if (turn == Type.FIRST) {
            player1 = 1;
            player2 = 2;
        } else if (turn == Type.SECOND) {
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

    /**
     * flips the specific cell
     * @param rowOrigin the row to change
     * @param colOrigin the column to change
     * @param rowNew the new row
     * @param colNew the new col
     * @param rowDirection the row direction
     * @param colDirection the column direction
     * @param player1 the player
     * @param board the board
     */
    public void flipChosenCell(int rowOrigin, int colOrigin, int rowNew, int colNew,
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

    /**
     * checks if the move exists.
     * @param options the list of options
     * @param location the location to check
     * @return true if it exists, false if it doesnt.
     */
    public boolean moveExist(List<Location> options, Location location) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getRow() == location.getRow() &&
                    options.get(i).getCol() == location.getCol()) {
                return true;
            }
        }
        return false;
    }


    public Location getFromUp(Cell[][] table, int size, int rowPos, int colPos, int status) {
        if (rowPos - 1 == 0) {
            return new Location(0, 0);
        }
        int value = table[rowPos - 1][colPos].getStatus();
        if (value == status) {
            Location option = new Location(rowPos - 1, colPos);
            return option;
        }
        if (value == otherTurn) {
            return new Location(0, 0);
        }
        return getFromUp(table, size, rowPos - 1, colPos, status);
    }

    public Location getFromUpRight(Cell[][] table, int size, int rowPos, int colPos, int status) {
        if (rowPos - 1 == 0 || colPos + 1 > size) {
            return new Location(0, 0);
        }
        int value = table[rowPos - 1][colPos + 1].getStatus();
        if (value == status) {
            Location option = new Location(rowPos - 1, colPos + 1);
            return option;
        }
        if (value == otherTurn) {
            return new Location(0, 0);
        }
        return getFromUpRight(table, size, rowPos - 1, colPos + 1, status);
    }

    public Location getFromRight(Cell[][] table, int size, int rowPos, int colPos, int status) {
        if (colPos + 1 > size) {
            return new Location(0, 0);
        }
        int value = table[rowPos][colPos + 1].getStatus();
        if (value == status) {
            Location option = new Location(rowPos, colPos + 1);
            return option;
        }
        if (value == otherTurn) {
            return new Location(0, 0);
        }
        return getFromRight(table, size, rowPos, colPos + 1, status);
    }

    public Location getFromDownRight(Cell[][] table, int size, int rowPos, int colPos, int status) {
        if (rowPos + 1 > size || colPos + 1 > size) {
            return new Location(0, 0);
        }
        int value = table[rowPos + 1][colPos + 1].getStatus();
        if (value == status) {
            Location option = new Location(rowPos + 1, colPos + 1);
            return option;
        }
        if (value == otherTurn) {
            return new Location(0, 0);
        }
        return getFromDownRight(table, size, rowPos + 1, colPos + 1, status);
    }

    public Location getFromDown(Cell[][] table, int size, int rowPos, int colPos, int status) {
        if (rowPos + 1 > size) {
            return new Location(0, 0);
        }
        int value = table[rowPos + 1][colPos].getStatus();
        if (value == status) {
            Location option = new Location(rowPos + 1, colPos);
            return option;
        }
        if (value == otherTurn) {
            return new Location(0, 0);
        }
        return getFromDown(table, size, rowPos + 1, colPos, status);
    }

    public Location getFromDownLeft(Cell[][] table, int size, int rowPos, int colPos, int status) {
        if (rowPos + 1 > size || colPos - 1 == 0) {
            return new Location(0, 0);
        }
        int value = table[rowPos + 1][colPos - 1].getStatus();
        if (value == status) {
            Location option = new Location(rowPos + 1, colPos - 1);
            return option;
        }
        if (value == otherTurn) {
            return new Location(0, 0);
        }
        return getFromDownLeft(table, size, rowPos + 1, colPos - 1, status);
    }

    public Location getFromLeft(Cell[][] table, int size, int rowPos, int colPos, int status) {
        if (colPos - 1 == 0) {
            return new Location(0, 0);
        }
        int value = table[rowPos][colPos - 1].getStatus();
        if (value == status) {
            Location option = new Location(rowPos, colPos - 1);
            return option;
        }
        if (value == otherTurn) {
            return new Location(0, 0);
        }
        return getFromLeft(table, size, rowPos, colPos - 1, status);
    }

    public Location getFromUpLeft(Cell[][] table, int size, int rowPos, int colPos, int status) {
        if (rowPos - 1 == 0 || colPos - 1 == 0) {
            return new Location(0, 0);
        }
        int value = table[rowPos - 1][colPos - 1].getStatus();
        if (value == status) {
            Location option = new Location(rowPos - 1, colPos - 1);
            return option;
        }
        if (value == otherTurn) {
            return new Location(0, 0);
        }
        return getFromUpLeft(table, size, rowPos - 1, colPos - 1, status);
    }
}
