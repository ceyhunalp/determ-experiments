package activationfunction;

public class TanhActivation implements ActivationFunction {
    @Override
    public void applyFunction(double[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                m[i][j] = Math.tanh(m[i][j]);
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
                result[i][j] = 1.0 - Math.pow(Math.tanh(m[i][j]), 2);
            }
        }
        return result;
    }
}
