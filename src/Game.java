

public class Game {
    private Board board;
    private GameLogic logic;

    public Game(Board board, GameLogic logic) {
        this.board = board;
        this.logic = logic;
    }

    public void run() {
        while (logic.isRunning()) {
            board.print();
            logic.playOneTurn(board);
            if (logic.getTurn() == Type.FIRST) {
                logic.setTurn(Type.SECOND);
            } else {
                logic.setTurn(Type.FIRST);
            }
        }
        if (logic.checkScore(board.getTable(), board.getSize()) == 1) {
            System.out.println("X: Congrats, you win!");
        } else if (logic.checkScore(board.getTable(), board.getSize()) == 2) {
            System.out.println("O: Congrats, you win!");
        } else {
            System.out.println("It's a TIE!");
        }
    }
}
