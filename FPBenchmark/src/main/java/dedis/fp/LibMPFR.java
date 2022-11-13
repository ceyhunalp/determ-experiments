package dedis.fp;

import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

import java.math.RoundingMode;
import java.util.Random;

public class LibMPFR extends MathLib{
    BinaryMathContext mc;
    BigFloat[] bfXs;
    BigFloat[] bfYs;

    public LibMPFR(int inCount, int warmupCount, int execCount, String libName) {
        super(inCount, warmupCount, execCount, libName);
        mc = BinaryMathContext.BINARY64.withRoundingMode(RoundingMode.HALF_EVEN);
        bfXs = new BigFloat[inCount];
        bfYs = new BigFloat[inCount];
    }

    @Override
    public void initInputs(int seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < inCount; i++) {
            bfXs[i] = new BigFloat(rand.nextDouble(), mc);
            bfYs[i] = new BigFloat(rand.nextDouble(), mc);
        }
    }

    @Override
    public void log() {
        int idx;
        long start, end;
        BigFloat bfVal;

        // Warmup for MPFR
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            bfVal = bfXs[idx].log(mc);
        }
        // Measure MPFR
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            bfVal = bfXs[idx].log(mc);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void exp() {
        int idx;
        long start, end;
        BigFloat bfVal;

        // Warmup for MPFR
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            bfVal = bfXs[idx].exp(mc);
        }
        // Measure MPFR
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            bfVal = bfXs[idx].exp(mc);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void pow() {
        int idx;
        long start, end;
        BigFloat bfVal;

        // Warmup for MPFR
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            bfVal = bfXs[idx].pow(bfYs[idx], mc);
        }
        // Measure MPFR
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            bfVal = bfXs[idx].pow(bfYs[idx], mc);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void sin() {
        int idx;
        long start, end;
        BigFloat bfVal;

        // Warmup for MPFR
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            bfVal = bfXs[idx].sin(mc);
        }
        // Measure MPFR
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            bfVal = bfXs[idx].sin(mc);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void cos() {
        int idx;
        long start, end;
        BigFloat bfVal;

        // Warmup for MPFR
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            bfVal = bfXs[idx].cos(mc);
        }
        // Measure MPFR
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            bfVal = bfXs[idx].cos(mc);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void tan() {
        int idx;
        long start, end;
        BigFloat bfVal;

        // Warmup for MPFR
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            bfVal = bfXs[idx].tan(mc);
        }
        // Measure MPFR
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            bfVal = bfXs[idx].tan(mc);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }
}
