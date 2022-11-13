package dedis.fp;

import java.util.Random;

public class LibJavaSMath extends MathLib{
    double[] xs;
    double[] ys;

    public LibJavaSMath(int inCount, int warmupCount, int execCount, String libName) {
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
        // Warmup for StrictMath
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = StrictMath.log(xs[idx]);
        }
        // Measure StrictMath
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = StrictMath.log(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void exp() {
        int idx;
        double val;
        long start, end;
        // Warmup for StrictMath
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = StrictMath.exp(xs[idx]);
        }
        // Measure StrictMath
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = StrictMath.exp(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void pow() {
        int idx;
        double val;
        long start, end;
        // Warmup for StrictMath
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = StrictMath.pow(xs[idx], ys[idx]);
        }
        // Measure StrictMath
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = StrictMath.pow(xs[idx], ys[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void sin() {
        int idx;
        double val;
        long start, end;
        // Warmup for StrictMath
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = StrictMath.sin(xs[idx]);
        }
        // Measure StrictMath
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = StrictMath.sin(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void cos() {
        int idx;
        double val;
        long start, end;
        // Warmup for StrictMath
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = StrictMath.cos(xs[idx]);
        }
        // Measure StrictMath
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = StrictMath.cos(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void tan() {
        int idx;
        double val;
        long start, end;
        // Warmup for StrictMath
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            val = StrictMath.tan(xs[idx]);
        }
        // Measure StrictMath
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            val = StrictMath.tan(xs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }
}
