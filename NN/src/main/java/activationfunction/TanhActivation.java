package activationfunction;

import utils.Matrix;

public class TanhActivation implements ActivationFunction {
    @Override
    public void applyFunction(Matrix m) {
        for (int i = 0; i < m.numRows; i++) {
            for (int j = 0; j < m.numColumns; j++) {
                m.data[i][j] = StrictMath.tanh(m.data[i][j]);
            }
        }
    }

    @Override
    public Matrix applyDerivative(Matrix m) {
        Matrix result = new Matrix(m.numRows, m.numColumns);
        for (int i = 0; i < m.numRows; i++) {
            for (int j = 0; j < m.numColumns; j++) {
                result.data[i][j] = 1.0 - Math.pow(Math.tanh(m.data[i][j]), 2);
            }
        }
        return result;
    }
}
