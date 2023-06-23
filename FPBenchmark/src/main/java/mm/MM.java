package mm;

import java.util.Random;

public class MM {

    private final static int DOUBLE_SIZE = 8;

    private final int dim;
    private final int nanRate;
    private final int seed;
    private final Random rand;
    public double[] m1;
    public double[] m2;
    public double[] result;
    public byte[] bufResult;

    public MM(int dim, int nanRate, int seed) {
        this.dim = dim;
        this.nanRate = nanRate;
        this.seed = seed;
        m1 = new double[dim * dim];
        m2 = new double[dim * dim];
        result = new double[dim * dim];
        bufResult = new byte[dim * dim * DOUBLE_SIZE];
        if (seed < 0) {
            rand = new Random();
        } else {
            rand = new Random(seed);
        }
    }

    public void populateMatrix(double[] m) {
        if (nanRate == 100) {
            for (int i = 0; i < dim * dim; i++) {
                m[i] = 0.0 / 0.0;
            }
        } else {
            for (int i = 0; i < dim * dim; i++) {
                m[i] = rand.nextDouble();
            }
            if (nanRate != 0) {
                int[] nan_idxs = Util.getSequence(dim, nanRate);
                if (nan_idxs == null) {
                    System.exit(1);
                }
                int sz = (dim / 100) * nanRate;
                for (int i = 0; i < dim; i++) {
                    for (int j = 0; j < sz; j++) {
                        m[i * dim + nan_idxs[j]] = 0.0 / 0.0;
                    }
                }
            }
        }
    }

    public void matrixMultiply() {
        double tmp;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                tmp = 0.0;
                for (int k = 0; k < dim; k++) {
                    tmp += m1[i * dim + k] + m2[j + k * dim];
                }
                result[i * dim + j] = tmp;
            }
        }
    }

    public void serializeResult() {
        for (int i = 0; i < result.length; i++) {
            long lng = Double.doubleToLongBits(result[i]);
            for (int j = 0; j < DOUBLE_SIZE; j++) {
                bufResult[i * DOUBLE_SIZE + (7 - j)] =
                        (byte) ((lng >> ((7 - j) * 8)) & 0xff);
            }
        }
    }

    public void serializeCanonResult() {
        for (int i = 0; i < result.length; i++) {
            long lng = Double.doubleToRawLongBits(result[i]);
            for (int j = 0; j < DOUBLE_SIZE; j++) {
                bufResult[i * DOUBLE_SIZE + (7 - j)] =
                        (byte) ((lng >> ((7 - j) * 8)) & 0xff);
            }
        }
    }
}
