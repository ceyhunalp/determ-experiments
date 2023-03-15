package activationfunction;

public class ELUActivation implements ActivationFunction {

    private double alpha = 1.0;

    @Override
    public void applyFunction(double[][] m) {
        double val;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                val = m[i][j];
                if (val < 0) {
                    m[i][j] = alpha * (Math.exp(val) - 1.0);
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
                if (m[i][j] >= 0) {
                    result[i][j] = 1.0;
                } else {
                    result[i][j] = (alpha * (Math.exp(m[i][j]) - 1.0)) + alpha;
                }
            }
        }
        return result;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
}
