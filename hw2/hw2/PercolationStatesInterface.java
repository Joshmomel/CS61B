package hw2;

public interface PercolationStatesInterface {
    public double mean();                                           // sample mean of percolation threshold
    public double stddev();                                         // sample standard deviation of percolation threshold
    public double confidenceLow();                                  // low endpoint of 95% confidence interval
    public double confidenceHigh();                                 // high endpoint of 95% confidence interval
}
