package dedis.fp;

import java.util.Random;

public class LibJavaMath extends MathLib {
    double[] xs;
    double[] ys;

    public LibJavaMath(int warmupCount, int execCount, String libName) {
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
            results[i] = Math.log(xs[i]);
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
            results[i] = Math.exp(xs[i]);
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
            results[i] = Math.pow(xs[i], ys[i]);
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
            results[i] = Math.sin(xs[i]);
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
            results[i] = Math.cos(xs[i]);
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
            results[i] = Math.tan(xs[i]);
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }
}
