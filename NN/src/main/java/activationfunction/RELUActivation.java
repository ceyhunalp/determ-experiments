package activationfunction;

public class RELUActivation implements ActivationFunction {
    @Override
    public void applyFunction(double[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] <= 0) {
                    m[i][j] = 0.0;
                }
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
                if (m[i][j] > 0) {
                    result[i][j] = 1.0;
                } else {
                    result[i][j] = 0.0;
                }
            }
        }
        return result;
    }
}
