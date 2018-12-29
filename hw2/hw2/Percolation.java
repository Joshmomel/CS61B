package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation implements PercolationInterface {
    public boolean[][] grid;
    public int topParent;
    public int bottomParent;
    public WeightedQuickUnionUF weightedQuickUnionUF;
    public int openSiteNum = 0;

    public Percolation(int N) {
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N + 2);
        this.topParent = N * N;
        this.bottomParent = N * N + 1;

        this.grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = false;
            }
            System.out.println("bottomParent xyTo1D(i, 0) :: " + xyTo1D(i, 0));
            System.out.println("topParent xyTo1D(i, N - 1) ::" + xyTo1D(i, N - 1));
            weightedQuickUnionUF.union(xyTo1D(i, 0), bottomParent);
            weightedQuickUnionUF.union(xyTo1D(i, N - 1), topParent);
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
        xy[1] = grid.length - num / grid.length - 1;
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
        if (percolates()) {

        }
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

        percolation.open(percolation.oneDtoXY(12)[0], percolation.oneDtoXY(12)[1]);
        System.out.println("12: " +percolation.oneDtoXY(12)[0] + " , " + percolation.oneDtoXY(12)[1]);
        percolation.open(percolation.oneDtoXY(14)[0], percolation.oneDtoXY(14)[1]);
        System.out.println("14: " +percolation.oneDtoXY(14)[0] + " , " + percolation.oneDtoXY(14)[1]);
        percolation.open(percolation.oneDtoXY(19)[0], percolation.oneDtoXY(19)[1]);
        System.out.println("19: " + percolation.oneDtoXY(19)[0] + " , " + percolation.oneDtoXY(19)[1]);
//        System.out.println(percolation.weightedQuickUnionUF.connected(19, 12));
        percolation.open(percolation.oneDtoXY(13)[0], percolation.oneDtoXY(13)[1]);
        System.out.println("13: " +percolation.oneDtoXY(13)[0] + " , " + percolation.oneDtoXY(13)[1]);
//        System.out.println(percolation.weightedQuickUnionUF.connected(19, 12));

        percolation.open(percolation.oneDtoXY(2)[0], percolation.oneDtoXY(2)[1]);
        System.out.println("2: " +percolation.oneDtoXY(2)[0] + " , " + percolation.oneDtoXY(2)[1]);
        percolation.open(percolation.oneDtoXY(7)[0], percolation.oneDtoXY(7)[1]);
        System.out.println("7: " +percolation.oneDtoXY(7)[0] + " , " + percolation.oneDtoXY(7)[1]);
        System.out.println("isOpen(4 ,0): " + percolation.isOpen(4 ,0));
//        System.out.println("24: " +percolation.oneDtoXY(24)[0] + " , " + percolation.oneDtoXY(24)[1]);


        System.out.println("isFull(4, 0): " + percolation.xyTo1D(4,0) + " : "  + percolation.isFull(4, 0));
        System.out.println(percolation.percolates());
        percolation.open(4, 0);
        System.out.println(percolation.percolates());
        System.out.println("-------------------------");
        percolation.open(percolation.oneDtoXY(16)[0], percolation.oneDtoXY(16)[1]);
        percolation.open(percolation.oneDtoXY(21)[0], percolation.oneDtoXY(21)[1]);
        percolation.open(percolation.oneDtoXY(22)[0], percolation.oneDtoXY(22)[1]);
        percolation.open(percolation.oneDtoXY(20)[0], percolation.oneDtoXY(20)[1]);

        System.out.println("connect 16 : " + percolation.weightedQuickUnionUF.connected(16, 21));
        System.out.println(percolation.weightedQuickUnionUF.find(24));
        System.out.println(percolation.weightedQuickUnionUF.find(16));





    }


}
