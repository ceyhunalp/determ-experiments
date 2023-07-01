package dedis.fp;

import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

import java.math.RoundingMode;
import java.util.Random;

public class LibMPFR extends MathLib {
    BinaryMathContext mc;
    BigFloat[] bfXs;
    BigFloat[] bfYs;

    public LibMPFR(int warmupCount, int execCount, String libName) {
        super(warmupCount, execCount, libName);
        mc = BinaryMathContext.BINARY64.withRoundingMode(RoundingMode.HALF_EVEN);
        bfXs = new BigFloat[this.totalCount];
        bfYs = new BigFloat[this.totalCount];
    }

    @Override
    public void initInputs(int seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < this.totalCount; i++) {
            bfXs[i] = new BigFloat(rand.nextDouble(), mc);
            bfYs[i] = new BigFloat(rand.nextDouble(), mc);
        }
    }

    @Override
    public double[] log() {
        long start, end;
        for (int i = 0; i < this.totalCount; i++) {
            start = System.nanoTime();
            results[i] = bfXs[i].log(mc).doubleValue();
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
            results[i] = bfXs[i].exp(mc).doubleValue();
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
            results[i] = bfXs[i].pow(bfYs[i], mc).doubleValue();
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
            results[i] = bfXs[i].sin(mc).doubleValue();
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
            results[i] = bfXs[i].cos(mc).doubleValue();
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
            results[i] = bfXs[i].tan(mc).doubleValue();
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }
}
