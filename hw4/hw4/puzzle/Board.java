package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private final int BLANK = 0;
    private int N;
    private int[][] tiles;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if ((i >= 0 || i <= size() - 1) && (j >= 0 || j <= size() - 1)) {
            return tiles[i][j];
        } else {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public int size() {
        return N;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int position = i * size() + j + 1;
                if (tileAt(i, j) != position && tileAt(i, j) != BLANK) {
                    count += 1;
                }
            }
        }

        return count;
    }


    public int manhattan() {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int curNum = tileAt(i, j);
                if (curNum != 0) {
                    int itsRow = (curNum - 1) / N;
                    int itsCol = (curNum - 1) % N;
                    count += (Math.abs(i - itsRow) + Math.abs(j - itsCol));
                }
            }
        }
        return count;
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (y.getClass() == this.getClass()) {
            Board compareBoard = (Board) y;
            if (compareBoard.size() != this.size()) {
                return false;
            }

            for (int i = 0; i < size(); i++) {
                for (int j = 0; j < size(); j++) {
                    if (compareBoard.tileAt(i, j) != this.tileAt(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public int hashCode() {
        return 1;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * @source: Josh Hug's solution online
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }
}
