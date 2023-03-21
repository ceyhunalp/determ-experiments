package activationfunction;

public class SoftMax {

    public static void softmax(double[][] m) {
        assert (m.length == 1 && m[0].length > 0);
        double max = m[0][0];
        for (int i = 1; i < m[0].length; i++) {
            double val = m[0][i];
            if (val > max) {
                max = val;
            }
        }
        double total = 0.0;
        for (int i = 0; i < m[0].length; i++) {
            double val = Math.exp(m[0][i] - max);
            m[0][i] = val;
            total += val;
        }
        for (int i = 0; i < m[0].length; i++) {
            m[0][i] /= total;
        }
    }
}

