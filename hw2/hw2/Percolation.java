package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation implements PercolationInterface{
    public boolean[][] grid;
    public int topParent;
    public int bottomParent;
    public WeightedQuickUnionUF weightedQuickUnionUF;
    public int openSiteNum = 0;

    public Percolation(int N){
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N);
        this.grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = false;
            }
            weightedQuickUnionUF.union(xyTo1D(i, 0), topParent);
            weightedQuickUnionUF.union(xyTo1D(i, N - 1), bottomParent);
        }
    }

    private int xyTo1D(int row, int col) {
        return row + col * grid.length;
    }

    private boolean isValid(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException();
        }
        if (row > grid.length || col > grid.length) {
            throw  new IndexOutOfBoundsException();
        }
        return true;
    }

    @Override
    public void open(int row, int col) {
        if (isValid(row, col)) {
            grid[row][col] = true;
            openSiteNum += 1;
        }
    }

    @Override
    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    @Override
    public boolean isFull(int row, int col) {
        return weightedQuickUnionUF.connected(xyTo1D(row, col), topParent);
    }

    @Override
    public int numberOfOpenSites() {
        return openSiteNum;
    }

    @Override
    public boolean percolates() {
        return weightedQuickUnionUF.connected(topParent, bottomParent);
    }


}
