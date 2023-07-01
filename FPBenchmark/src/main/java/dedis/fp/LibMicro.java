package dedis.fp;

import net.dclausen.microfloat.MicroDouble;

import java.util.Random;

public class LibMicro extends MathLib {
    long[] lxs;
    long[] lys;

    public LibMicro(int warmupCount, int execCount, String libName) {
        super(warmupCount, execCount, libName);
        lxs = new long[this.totalCount];
        lys = new long[this.totalCount];
    }

    @Override
    public void initInputs(int seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < this.totalCount; i++) {
            lxs[i] = Double.doubleToLongBits(rand.nextDouble());
            lys[i] = Double.doubleToLongBits(rand.nextDouble());
        }
    }

    @Override
    public double[] log() {
        long start, end;
        for (int i = 0; i < this.totalCount; i++) {
            start = System.nanoTime();
            results[i] = Double.longBitsToDouble(MicroDouble.log(lxs[i]));
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
            results[i] = Double.longBitsToDouble(MicroDouble.exp(lxs[i]));
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
            results[i] = Double.longBitsToDouble(MicroDouble.pow(lxs[i],
                    lys[i]));
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
            results[i] = Double.longBitsToDouble(MicroDouble.sin(lxs[i]));
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
            results[i] = Double.longBitsToDouble(MicroDouble.cos(lxs[i]));
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
            results[i] = Double.longBitsToDouble(MicroDouble.tan(lxs[i]));
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }
}
