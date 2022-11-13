package dedis.fp;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.kframework.mpfr.BigFloat;

import java.util.Random;

public class LibApfloat extends MathLib{
    Apfloat[] apfXs;
    Apfloat[] apfYs;

    public LibApfloat(int inCount, int warmupCount, int execCount, String libName) {
        super(inCount, warmupCount, execCount, libName);
        apfXs = new Apfloat[inCount];
        apfYs = new Apfloat[inCount];
    }

    @Override
    public void initInputs(int seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < inCount; i++) {
            apfXs[i] = new Apfloat(rand.nextDouble());
            apfYs[i] = new Apfloat(rand.nextDouble());
        }
    }

    @Override
    public void log() {
        int idx;
        long start, end;
        Apfloat apfVal;

        // Warmup for Apfloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            apfVal = ApfloatMath.log(apfXs[idx]);
        }
        // Measure Apfloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            apfVal = ApfloatMath.log(apfXs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void exp() {
        int idx;
        long start, end;
        Apfloat apfVal;

        // Warmup for Apfloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            apfVal = ApfloatMath.exp(apfXs[idx]);
        }
        // Measure Apfloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            apfVal = ApfloatMath.exp(apfXs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void pow() {
        int idx;
        long start, end;
        Apfloat apfVal;

        // Warmup for Apfloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            apfVal = ApfloatMath.pow(apfXs[idx], apfYs[idx]);
        }
        // Measure Apfloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            apfVal = ApfloatMath.pow(apfXs[idx], apfYs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void sin() {
        int idx;
        long start, end;
        Apfloat apfVal;

        // Warmup for Apfloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            apfVal = ApfloatMath.sin(apfXs[idx]);
        }
        // Measure Apfloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            apfVal = ApfloatMath.sin(apfXs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void cos() {
        int idx;
        long start, end;
        Apfloat apfVal;

        // Warmup for Apfloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            apfVal = ApfloatMath.cos(apfXs[idx]);
        }
        // Measure Apfloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            apfVal = ApfloatMath.cos(apfXs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }

    @Override
    public void tan() {
        int idx;
        long start, end;
        Apfloat apfVal;

        // Warmup for Apfloat
        for (int i = 0; i < warmupCount; i++) {
            idx = i % inCount;
            apfVal = ApfloatMath.tan(apfXs[idx]);
        }
        // Measure Apfloat
        for (int i = 0; i < execCount; i++) {
            idx = i % inCount;
            start = System.nanoTime();
            apfVal = ApfloatMath.tan(apfXs[idx]);
            end = System.nanoTime();
            times[i] = end - start;
        }
    }
}
