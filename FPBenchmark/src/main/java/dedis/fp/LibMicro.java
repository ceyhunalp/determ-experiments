package dedis.fp;

import net.dclausen.microfloat.MicroDouble;

import java.util.Random;

public class LibMicro extends MathLib{
    long[] lxs;
    long[] lys;

    public LibMicro(int inCount, int warmupCount, int execCount, String libName) {
        super(inCount, warmupCount, execCount, libName);
        lxs = new long[inCount];
        lys = new long[inCount];
    }

    @Override
    public void initInputs(int seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < inCount; i++) {
            lxs[i] = Double.doubleToLongBits(rand.nextDouble());
            lys[i] = Double.doubleToLongBits(rand.nextDouble());
        }
    }

    @Override
    public void log() {
        int idx;
        long lval;
        long start, end;

        // Warmup for MicroFloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            lval = MicroDouble.log(lxs[idx]);
        }
        // Measure MicroFloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            lval = MicroDouble.log(lxs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void exp() {
        int idx;
        long  lval;
        long start, end;

        // Warmup for MicroFloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            lval = MicroDouble.exp(lxs[idx]);
        }
        // Measure MicroFloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            lval = MicroDouble.exp(lxs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void pow() {
        int idx;
        long lval;
        long start, end;

        // Warmup for MicroFloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            lval = MicroDouble.pow(lxs[idx], lys[idx]);
        }
        // Measure MicroFloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            lval = MicroDouble.pow(lxs[idx], lys[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void sin() {
        int idx;
        long lx, lval;
        long start, end;

        // Warmup for MicroFloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            lval = MicroDouble.sin(lxs[idx]);
        }
        // Measure MicroFloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            lval = MicroDouble.sin(lxs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void cos() {
        int idx;
        long lval;
        long start, end;

        // Warmup for MicroFloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            lval = MicroDouble.cos(lxs[idx]);
        }
        // Measure MicroFloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            lval = MicroDouble.cos(lxs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void tan() {
        int idx;
        long lx, lval;
        long start, end;

        // Warmup for MicroFloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            lval = MicroDouble.tan(lxs[idx]);
        }
        // Measure MicroFloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            lval = MicroDouble.tan(lxs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }
}
