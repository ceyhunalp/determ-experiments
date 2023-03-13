package activationfunction;

import utils.Matrix;

public class SigmoidActivation implements ActivationFunction {
    @Override
    public void applyFunction(Matrix m) {
        double val;
        for (int i = 0; i < m.numRows; i++) {
            for (int j = 0; j < m.numColumns; j++) {
                val = 1.0 / (1.0 + Math.exp(m.data[i][j] * -1.0));
                m.data[i][j] = val;
            }
        }
    }

    @Override
    public Matrix applyDerivative(Matrix m) {
        double val;
        Matrix result = new Matrix(m.numRows, m.numColumns);
        for (int i = 0; i < m.numRows; i++) {
            for (int j = 0; j < m.numColumns; j++) {
                val = m.data[i][j];
                result.data[i][j] = val * (1 - val);
            }
        }
        return result;
    }
}
