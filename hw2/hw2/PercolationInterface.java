package hw2;

public interface PercolationInterface {
    public void open(int row, int col);       // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col);  // is the site (row, col) open?
    public boolean isFull(int row, int col);  // is the site (row, col) full?
    public int numberOfOpenSites();           // number of open sites
    public boolean percolates();              // does the system percolate?
}
