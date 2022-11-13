package dedis.fp;

import java.util.Random;

public class LibJavaMath extends MathLib{
    double[] xs;
    double[] ys;

    public LibJavaMath(int inCount, int warmupCount, int execCount, String libName) {
        super(inCount, warmupCount, execCount, libName);
        xs = new double[inCount];
        ys = new double[inCount];
    }

    @Override
    public void initInputs(int seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < inCount; i++) {
            xs[i] = rand.nextDouble();
            ys[i] = rand.nextDouble();
        }
    }

    @Override
    public void log() {
        int idx;
        double val;
        long start, end;
        // Warmup for Math
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = Math.log(xs[idx]);
        }
        // Measure Math
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = Math.log(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void exp() {
        int idx;
        double val;
        long start, end;
        // Warmup for Math
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = Math.exp(xs[idx]);
        }
        // Measure Math
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = Math.exp(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void pow() {
        int idx;
        double val;
        long start, end;
        // Warmup for Math
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = Math.pow(xs[idx], ys[idx]);
        }
        // Measure Math
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = Math.pow(xs[idx], ys[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void sin() {
        int idx;
        double val;
        long start, end;
        // Warmup for Math
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = Math.sin(xs[idx]);
        }
        // Measure Math
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = Math.sin(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void cos() {
        int idx;
        double val;
        long start, end;
        // Warmup for Math
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = Math.cos(xs[idx]);
        }
        // Measure Math
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = Math.cos(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void tan() {
        int idx;
        double val;
        long start, end;
        // Warmup for Math
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = Math.tan(xs[idx]);
        }
        // Measure Math
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = Math.tan(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }
}
