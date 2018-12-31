package hw2;
import  edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats implements PercolationStatesInterface{
    private int T;
    private double[] stats;

    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Cannot use these parameters");
        }
        this.T = T;
        stats = new double[T];
        for (int i = 0; i < stats.length; i++) {
            Percolation perc = pf.make(N);
            stats[i] = generateStat(perc, N);
        }
    }

    private double generateStat(Percolation p, int N) {
        while (!p.percolates()) {
            int row = StdRandom.uniform(0, N);
            int col = StdRandom.uniform(0, N);
            p.open(row, col);
        }

        return (double) p.numberOfOpenSites() / (N * N);
    }

    @Override
    public double mean() {
        return StdStats.mean(this.stats);
    }

    @Override
    public double stddev() {
        return StdStats.stddev(this.stats);
    }

    @Override
    public double confidenceLow() {
        double secondary = (1.96 * stddev()) / (Math.sqrt(T));
        return mean() - secondary;
    }

    @Override
    public double confidenceHigh() {
        double secondary = (1.96 * stddev()) / (Math.sqrt(T));
        return mean() + secondary;
    }
}
