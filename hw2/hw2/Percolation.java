package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation implements PercolationInterface {
    public boolean[][] grid;
    public int topParent;
    public int bottomParent;
    public WeightedQuickUnionUF weightedQuickUnionUF;
    public int openSiteNum = 0;

    public Percolation(int N) {
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
        return row + (grid.length - col - 1) * grid.length;
    }

    public int[] oneDtoXY(int num) {
        int total = grid.length * grid.length;
        int[] xy = new int[2];
        if (num < 0 || num > total) {
            xy[0] = -1;
            xy[1] = -1;
        }
        xy[0] = num % grid.length;
        xy[1] = num / grid.length;
        return xy;
    }

    private boolean isValid(int row, int col) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException();
        }
        if (row > grid.length || col > grid.length) {
            throw new IndexOutOfBoundsException();
        }
        return true;
    }

    @Override
    public void open(int row, int col) {
        if (isValid(row, col)) {
            grid[row][col] = true;
            openSiteNum += 1;
        }
        Position thePosition = new Position(row, col);
        Direction direction = new Direction(thePosition);

        if (!(col == 4) && isOpen(direction.top.x, direction.top.y)){
            weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(direction.top.x, direction.top.y));
        }
        if (!(col == 0) && isOpen(direction.bottom.x, direction.bottom.y)){
            weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(direction.bottom.x, direction.bottom.y));
        }
        if (!(row == 0) && isOpen(direction.left.x, direction.left.y)){
            weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(direction.left.x, direction.left.y));
        }
        if (!(row == 4) && isOpen(direction.right.x, direction.right.y)){
            weightedQuickUnionUF.union(xyTo1D(row, col), xyTo1D(direction.right.x, direction.right.y));
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

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(3, 3);
        System.out.println(percolation.xyTo1D(3, 3));
        percolation.open(3, 2);
        System.out.println(percolation.xyTo1D(3, 2));
        System.out.println(percolation.weightedQuickUnionUF.connected(8, 13));
        percolation.open(4,4);
        percolation.open(3, 4);
        
    }


}
