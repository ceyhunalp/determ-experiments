package activationfunction;

import utils.Matrix;

public class ELUActivation implements ActivationFunction {

    private double alpha = 1.0;

    @Override
    public void applyFunction(Matrix m) {
        double val;
        for (int i = 0; i < m.numRows; i++) {
            for (int j = 0; j < m.numColumns; j++) {
                val = m.data[i][j];
                if (val < 0) {
                    m.data[i][j] = alpha * (Math.exp(val) - 1.0);
                }
            }
        }
    }

    @Override
    public Matrix applyDerivative(Matrix m) {
        Matrix result = new Matrix(m.numRows, m.numColumns);
        for (int i = 0; i < m.numRows; i++) {
            for (int j = 0; j < m.numColumns; j++) {
                if (m.data[i][j] >= 0) {
                    result.data[i][j] = 1.0;
                } else {
                    result.data[i][j] = (alpha * (Math.exp(m.data[i][j]) - 1.0)) + alpha;
                }
            }
        }
        return result;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
}
