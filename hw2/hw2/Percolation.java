package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int topParent;
    private int bottomParent;
    private int topForCheck;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF topWeightedQUF;
    private int openSiteNum = 0;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Parameter cannot be a number <= 0");
        }

        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N + 2);
        topWeightedQUF = new WeightedQuickUnionUF(N * N + 1);
        this.topForCheck = N * N;
        this.topParent = N * N;
        this.bottomParent = N * N + 1;

        this.grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = false;
            }
        }
    }

    private int xyTo1D(int row, int col) {
        return (row * grid.length) + col;
    }


    private boolean isValid(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException();
        }
        if (row > grid.length - 1 || col > grid.length - 1) {
            throw new IndexOutOfBoundsException();
        }
        return true;
    }

    public void open(int row, int col) {
        if (isValid(row, col)) {
            grid[row][col] = true;
            openSiteNum += 1;
        }
        Position thePosition = new Position(row, col);
        Direction direction = new Direction(thePosition);

        if (row == 0) {
            weightedQuickUnionUF.union(xyTo1D(row, col), topParent);
            topWeightedQUF.union(xyTo1D(row, col), topForCheck);
        }

        if (row == grid.length - 1) {
            weightedQuickUnionUF.union(xyTo1D(row, col), bottomParent);
        }

        if (!(row == 0) && isOpen(direction.top.x, direction.top.y)) {
            weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(direction.top.x, direction.top.y));
            topWeightedQUF.union(xyTo1D(row, col), xyTo1D(direction.top.x, direction.top.y));
        }
        if (!(row == grid.length - 1) && isOpen(direction.bottom.x, direction.bottom.y)) {
            weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(direction.bottom.x, direction.bottom.y));
            topWeightedQUF.union(xyTo1D(row, col), xyTo1D(direction.bottom.x, direction.bottom.y));
        }
        if (!(col == 0) && isOpen(direction.left.x, direction.left.y)) {
            weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(direction.left.x, direction.left.y));
            topWeightedQUF.union(xyTo1D(row, col), xyTo1D(direction.left.x, direction.left.y));
        }
        if (!(col == grid.length - 1) && isOpen(direction.right.x, direction.right.y)) {
            weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(direction.right.x, direction.right.y));
            topWeightedQUF.union(xyTo1D(row, col), xyTo1D(direction.right.x, direction.right.y));
        }

    }

    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }


    public boolean isFull(int row, int col) {
        if (percolates()) {
            return topWeightedQUF.connected(xyTo1D(row, col), topForCheck) && isOpen(row, col);
        }
        return weightedQuickUnionUF.connected(xyTo1D(row, col), topParent) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openSiteNum;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(topParent, bottomParent);
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(3);
        test.open(0, 2);
        test.open(1, 2);
        test.open(2, 2);
        test.open(2, 0);
        System.out.println(test.percolates());
        System.out.println(test.isFull(2, 0));
    }


}
