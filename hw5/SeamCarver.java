import edu.princeton.cs.algs4.Picture;

import java.awt.Color;


public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.height = picture.height();
        this.width = picture.width();
    }

    // current picture
    public Picture picture() {
        return new Picture(this.picture);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        if (y < 0 || y >= height) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return calculateEnergiesX(x, y) + calculateEnergiesY(x, y);
    }


    private double calculateEnergiesX(int x, int y) {
        int leftX = change(x, -1, width);
        int rightX = change(x, 1, width);

        Color left = picture.get(leftX, y);
        Color right = picture.get(rightX, y);

        double rx = Math.abs(right.getRed() - left.getRed());
        double gx = Math.abs(right.getGreen() - left.getGreen());
        double bx = Math.abs(right.getBlue() - left.getBlue());

        return (rx * rx) + (gx * gx) + (bx * bx);
    }


    private double calculateEnergiesY(int x, int y) {
        int upY = change(y, -1, height);
        int downY = change(y, 1, height);

        Color up = picture.get(x, upY);
        Color down = picture.get(x, downY);

        double ry = Math.abs(down.getRed() - up.getRed());
        double gy = Math.abs(down.getGreen() - up.getGreen());
        double by = Math.abs(down.getBlue() - up.getBlue());

        return (ry * ry) + (gy * gy) + (by * by);
    }


    private int change(int cor, int i, int ref) {
        if (cor + i == ref) {
            return 0;
        }
        if (cor + i < 0) {
            return ref - 1;
        }
        return cor + i;
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] ans = findVerticalSeam();
        transpose();
        return ans;
    }


    private void transpose() {
        Picture temp = new Picture(height, width);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                temp.set(row, col, picture.get(col, row));
            }
        }

        picture = temp;
        int t = width;
        this.width = height;
        this.height = t;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] minCost = new double[height][width];
        int[] ans = new int[height];

        //initial minCost
        for (int w = 0; w < width; w++) {
            minCost[0][w] = energy(w, 0);
        }

        for (int h = 1; h < height; h++) {
            for (int w = 0; w < width; w++) {
                double energy = energy(w, h);
                double min = getMin(minCost, h, w);
                minCost[h][w] = energy + min;
            }
        }

        double minEnd = Double.MAX_VALUE;
        for (int w = 0; w < width; w++) {
            if (minCost[height - 1][w] < minEnd) {
                minEnd = minCost[height - 1][w];
                ans[height - 1] = w;
            }
        }

        //find root
        int countH = height - 2;
        while (countH >= 0) {
            ans[countH] = find(minCost, countH + 1, ans[countH + 1]);
            countH -= 1;
        }

        return ans;
    }

    private int find(double[][] minCost, int h, int w) {
        if (w == 0) {
            double cmp = minCost[h - 1][w + 1] - minCost[h - 1][w];
            if (cmp <= 0) {
                return w + 1;
            } else {
                return w;
            }
        }
        if (w == width - 1) {
            double cmp = minCost[h - 1][w - 1] - minCost[h - 1][w];
            if (cmp <= 0) {
                return w - 1;
            } else {
                return w;
            }
        }
        double smallest = Math.min(minCost[h - 1][w],
                Math.min(minCost[h - 1][w - 1], minCost[h - 1][w + 1]));
        if (smallest == minCost[h - 1][w]) {
            return w;
        } else if (smallest == minCost[h - 1][w - 1]) {
            return w - 1;
        }
        return w + 1;
    }

    private double getMin(double[][] minCost, int h, int w) {
        if (w == 0) {
            return Math.min(minCost[h - 1][w + 1], minCost[h - 1][w]);
        }
        if (w == width - 1) {
            return Math.min(minCost[h - 1][w - 1], minCost[h - 1][w]);
        }
        double temp = Math.min(minCost[h - 1][w - 1], minCost[h - 1][w]);
        return Math.min(temp, minCost[h - 1][w + 1]);
    }


    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length == 0) {
            return;
        }
        if (checkSeam(seam)) {
            this.picture = new Picture(SeamRemover.removeHorizontalSeam(this.picture, seam));
            height--;
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void removeVerticalSeam(int[] seam) {
        if (seam.length == 0) {
            return;
        }
        if (checkSeam(seam)) {
            this.picture = new Picture(SeamRemover.removeVerticalSeam(this.picture, seam));
            width--;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                return false;
            }
        }

        return true;
    }
}
