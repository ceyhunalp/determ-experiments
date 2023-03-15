package activationfunction;

public class SigmoidActivation implements ActivationFunction {
    @Override
    public void applyFunction(double[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                double val = 1.0 / (1.0 + Math.exp(m[i][j] * -1.0));
                m[i][j] = val;
            }
        }
    }

    @Override
    public double[][] applyDerivative(double[][] m) {
        int r = m.length;
        int c = m[0].length;
        double[][] result = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                double val = m[i][j];
                result[i][j] = val * (1 - val);
            }
        }
        return result;
    }
}
