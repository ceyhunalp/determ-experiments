package dedis.fp;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

import java.util.Random;

public class LibApfloat extends MathLib {
    Apfloat[] apfXs;
    Apfloat[] apfYs;

    public LibApfloat(int warmupCount, int execCount, String libName) {
        super(warmupCount, execCount, libName);
        apfXs = new Apfloat[this.totalCount];
        apfYs = new Apfloat[this.totalCount];
    }

    @Override
    public void initInputs(int seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < this.totalCount; i++) {
            apfXs[i] = new Apfloat(rand.nextDouble());
            apfYs[i] = new Apfloat(rand.nextDouble());
        }
    }

    @Override
    public double[] log() {
        long start, end;
        for (int i = 0; i < this.totalCount; i++) {
            start = System.nanoTime();
            results[i] = ApfloatMath.log(apfXs[i]).doubleValue();
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
            results[i] = ApfloatMath.exp(apfXs[i]).doubleValue();
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
            results[i] = ApfloatMath.pow(apfXs[i], apfYs[i]).doubleValue();
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
            results[i] = ApfloatMath.sin(apfXs[i]).doubleValue();
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
            results[i] = ApfloatMath.cos(apfXs[i]).doubleValue();
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
            results[i] = ApfloatMath.tan(apfXs[i]).doubleValue();
            end = System.nanoTime();
            times[i] = end - start;
        }
        return results;
    }
}
