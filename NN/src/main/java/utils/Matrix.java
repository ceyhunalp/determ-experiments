package utils;

import java.util.Random;

public class Matrix {

    public final int numRows;
    public final int numColumns;
    public final double[][] data;

    public Matrix(int r, int c) {
        this.numRows = r;
        this.numColumns = c;
        data = new double[r][c];
    }

    public Matrix(int r, int c, double lower, double upper, Random rand) {
        this.numRows = r;
        this.numColumns = c;
        data = new double[r][c];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                data[i][j] = (rand.nextDouble() * (upper - lower)) + lower;
            }
        }
    }

    public Matrix(double[][] data) {
        numRows = data.length;
        numColumns = data[0].length;
        this.data = new double[numRows][numColumns];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    public void sum(Matrix y) {
        Matrix x = this;
        assert (x.numRows == y.numRows && x.numColumns == y.numColumns);
        for (int i = 0; i < x.numRows; i++) {
            for (int j = 0; j < x.numColumns; j++) {
                x.data[i][j] += y.data[i][j];
            }
        }
    }

    public Matrix subtract(Matrix y) {
        Matrix x = this;
        assert (x.numRows == y.numRows && x.numColumns == y.numColumns);
        Matrix result = new Matrix(x.numRows, x.numColumns);
        for (int i = 0; i < result.numRows; i++) {
            for (int j = 0; j < result.numColumns; j++) {
                result.data[i][j] = x.data[i][j] - y.data[i][j];
            }
        }
        return result;
    }

    public Matrix multiply(Matrix y) {
        Matrix x = this;
        assert (x.numColumns == y.numRows);
        Matrix result = new Matrix(x.numRows, y.numColumns);
        for (int i = 0; i < x.numRows; i++) {
            for (int j = 0; j < y.numColumns; j++) {
                double sum = 0.0;
                for (int k = 0; k < x.numColumns; k++) {
                    sum += x.data[i][k] * y.data[k][j];
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    public void elementMultiply(Matrix y) {
        Matrix x = this;
        assert (x.numRows == y.numRows && x.numColumns == y.numColumns);
        for (int i = 0; i < x.numRows; i++) {
            for (int j = 0; j < x.numColumns; j++) {
                x.data[i][j] *= y.data[i][j];
            }
        }
    }

    public void scale(double factor) {
        Matrix x = this;
        for (int i = 0; i < x.numRows; i++) {
            for (int j = 0; j < x.numColumns; j++) {
                x.data[i][j] *= factor;
            }
        }
    }

    public Matrix transpose() {
        Matrix x = this;
        Matrix transpose = new Matrix(x.numColumns, x.numRows);
        for (int i = 0; i < transpose.numRows; i++) {
            for (int j = 0; j < transpose.numColumns; j++) {
                transpose.data[i][j] = x.data[j][i];
            }
        }
        return transpose;
    }

    public Matrix getRow(int r) {
        assert (r < this.numRows);
        Matrix result = new Matrix(1, this.numColumns);
        if (result.numColumns >= 0)
            System.arraycopy(this.data[r], 0, result.data[0], 0, result.numColumns);
        return result;
    }

//    public int getRowDimension() {
//        return this.numRows;
//    }
//
//    public int getColumnDimension() {
//        return this.numColumns;
//    }
//
//    public double[][] getData() {
//        return this.data;
//    }
//
//    public double getData(int r, int c) {
//        return this.data[r][c];
//    }
//
//    public void setData(int r, int c, double val) {
//        this.data[r][c] = val;
//    }
}