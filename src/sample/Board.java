package sample;

public class Board {
    private int size;
    private Cell table[][];

    public Board(int size) {
        this.size = size;
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

    public void print() {
       System.out.print(" |");
        for (int i = 0; i < size; i++) {
            System.out.print(" " + (i + 1) + " |");
        }
        System.out.println();
        System.out.print("--");
        for (int j = 0; j < size; j++) {
            System.out.print("----");
        }
        System.out.println();
        for (int i = 1; i < size + 1; i++) {
            System.out.print(i + "|");
            for (int j = 1; j < size + 1; j++) {
                //prints an x in the proper places
                if (table[i][j].getStatus() == 1) {
                    System.out.print(" X |");
                }
                //prints an o in the proper places
                else if (table[i][j].getStatus() == 2) {
                    System.out.print(" O |");
                }
                else {
                    System.out.print( "   |");
                }
            }
            System.out.println();
            System.out.print("--");
            for (int z = 0; z < size; z++) {
                System.out.print("----");
            }
            System.out.println();
        }
    }

    public Cell[][] getTable() {
        return this.table;
    }

    public int getSize() {
        return this.size;
    }
}



