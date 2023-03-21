package utils;

import java.util.Random;

public class Matrix {

    public static double[][] createMatrix(int r, int c, double lower,
                                          double upper, Random rand) {
        double[][] data = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                data[i][j] = (rand.nextDouble() * (upper - lower)) + lower;
            }
        }
        return data;
    }

    public static double[][] createMatrix(int r, int c) {
        return new double[r][c];
    }

    public static void sum(double[][] x, double[][] y) {
        assert (x.length == y.length && x[0].length == y[0].length);
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                x[i][j] += y[i][j];
            }
        }
    }

    public static double[][] subtract(double[][] x, double[][] y) {
        assert (x.length == y.length && x[0].length == y[0].length);
        int r = x.length;
        int c = x[0].length;
        double[][] result = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                result[i][j] = x[i][j] - y[i][j];
            }
        }
        return result;
    }

    public static double[][] multiply(double[][] x, double[][] y) {
        int rx = x.length;
        int cx = x[0].length;
        int ry = y.length;
        int cy = y[0].length;
        assert (cx == ry);
        double[][] result = new double[rx][cy];
        for (int i = 0; i < rx; i++) {
            for (int j = 0; j < cy; j++) {
                double sum = 0.0;
                for (int k = 0; k < cx; k++) {
                    sum += x[i][k] * y[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    public static void elementMultiply(double[][] x, double[][] y) {
        assert (x.length == y.length && x[0].length == y[0].length);
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                x[i][j] *= y[i][j];
            }
        }
    }

    public static void scale(double[][] x, double factor) {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                x[i][j] *= factor;
            }
        }
    }

    public static double[][] transpose(double[][] x) {
        int r = x.length;
        int c = x[0].length;
        double[][] transpose = new double[c][r];
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                transpose[i][j] = x[j][i];
            }
        }
        return transpose;
    }

    public static void reset(double[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) matrix[i][j] = 0.0;
        }
    }
}