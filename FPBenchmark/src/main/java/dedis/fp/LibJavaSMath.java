package dedis.fp;

import java.util.Random;

public class LibJavaSMath extends MathLib {
    double[] xs;
    double[] ys;

    public LibJavaSMath(int warmupCount, int execCount, String libName) {
        super(warmupCount, execCount, libName);
        xs = new double[this.totalCount];
        ys = new double[this.totalCount];
    }

    @Override
    public void initInputs(int seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < this.totalCount; i++) {
            xs[i] = rand.nextDouble();
            ys[i] = rand.nextDouble();
        }
    }

    @Override
    public double[] log() {
        long start, end;
        for (int i = 0; i < this.totalCount; i++) {
            start = System.nanoTime();
            results[i] = StrictMath.log(xs[i]);
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }

    @Override
    public double[] exp() {
        long start, end;
        for (int i = 0; i < this.totalCount; i++) {
            start = System.nanoTime();
            results[i] = StrictMath.exp(xs[i]);
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }

    @Override
    public double[] pow() {
        long start, end;
        for (int i = 0; i < this.totalCount; i++) {
            start = System.nanoTime();
            results[i] = StrictMath.pow(xs[62], ys[87]);
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }

    @Override
    public double[] sin() {
        long start, end;
        for (int i = 0; i < this.totalCount; i++) {
            start = System.nanoTime();
            results[i] = StrictMath.sin(xs[i]);
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }

    @Override
    public double[] cos() {
        long start, end;
        for (int i = 0; i < this.totalCount; i++) {
            start = System.nanoTime();
            results[i] = StrictMath.cos(xs[i]);
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }

    @Override
    public double[] tan() {
        long start, end;
        for (int i = 0; i < this.totalCount; i++) {
            start = System.nanoTime();
            results[i] = StrictMath.tan(xs[i]);
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }
}
