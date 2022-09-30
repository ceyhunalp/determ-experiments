package dedis.fp;

import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.Random;

import org.kframework.mpfr.BigFloat;
import org.kframework.mpfr.BinaryMathContext;

public class DetermNan {

    static final int RUN_COUNT = 1000;

    private BigFloat getMPFRResult(double x, double y) {
        BinaryMathContext mc = BinaryMathContext.BINARY64.withRoundingMode(RoundingMode.HALF_EVEN);
        BigFloat f1 = new BigFloat(x, mc);
        BigFloat f2 = new BigFloat(y, mc);
//        return f1.pow(f2,mc);
        return f1.sin(mc);
    }

    public void compareStrictAndMPFR() {
        final double[] vs = new double[RUN_COUNT];
        final double[] xs = new double[RUN_COUNT];
        final double[] ys = new double[RUN_COUNT];
        final Random random = new Random();

        for (int i = 0; i < RUN_COUNT; i++) {
            xs[i] = random.nextDouble();
            ys[i] = random.nextDouble();
            vs[i] = Math.sin(xs[i]);
//            vs[i] = StrictMath.sin(xs[i], ys[i]);
        }

        int diffCount = 0;
        for (int idx = 0; idx < RUN_COUNT; idx++) {
            double res = getMPFRResult(xs[idx], ys[idx]).doubleValueExact();
            if (vs[idx] != res && (!Double.isNaN(res) || !Double.isNaN(vs[idx]))) {
                System.out.println(idx + ":\tMPFR: " + res + ":\tStrict: " + vs[idx]);
                diffCount++;
            }
        }
        System.out.println(diffCount + " / " + RUN_COUNT);
    }

    double testIt(double x, double y) {
        return Math.pow(x, y);
    }

    public void testStrict() {
        final double[] vs = new double[100];
        final double[] xs = new double[100];
        final double[] ys = new double[100];
        final Random random = new Random();

        // compute StrictMath.pow results;
        for (int i = 0; i<100; i++) {
            xs[i] = random.nextDouble();
            ys[i] = random.nextDouble();
            vs[i] = StrictMath.pow(xs[i], ys[i]);
        }
        boolean printed_compiled = false;
        boolean ever_diff = false;
        long len = 1000000;
        long start;
        long elapsed;
        while (true) {
            start = System.currentTimeMillis();
            double blackhole = 0;
            for (int i = 0; i < len; i++) {
                int idx = i % 100;
                double res = testIt(xs[idx], ys[idx]);
                if (i >= 0 && i<100) {
                    //presumably interpreted
                    if (vs[idx] != res && (!Double.isNaN(res) || !Double.isNaN(vs[idx]))) {
                        System.out.println(idx + ":\tInterpreted: " + xs[idx] + "^" + ys[idx] + "=" + res);
                        System.out.println(idx + ":\tStrict pow : " + xs[idx] + "^" + ys[idx] + "=" + vs[idx] + "\n");
                    }
                }
                if (i >= 250000 && i<250100 && !printed_compiled) {
                    //presumably compiled at this time
                    if (vs[idx] != res && (!Double.isNaN(res) || !Double.isNaN(vs[idx]))) {
                        System.out.println(idx + ":\tcompiled   :" + xs[idx] + "^" + ys[idx] + "=" + res);
                        System.out.println(idx + ":\tStrict pow :" + xs[idx] + "^" + ys[idx] + "=" + vs[idx] + "\n");
                        ever_diff = true;
                    }
                }
            }
            elapsed = System.currentTimeMillis() - start;
            System.out.println(elapsed + " ms ");
            if (!printed_compiled && ever_diff) {
                printed_compiled = true;
                return;
            }
        }
    }

    public static void testNaN() {
        double myNaN = Double.longBitsToDouble(0x7ff1234512345678L);
//        double myNaN = Double.longBitsToDouble(0x7ff4000000000000L);
//        myNaN = myNaN + 1;
//        System.out.println(Double.isNaN(myNaN));
//        myNaN = foo(myNaN);
//        long nanBits = Double.doubleToRawLongBits(myNaN);
//        System.out.println(Long.toHexString(nanBits));

        double myOtherNaN = Double.longBitsToDouble(0x7ff8000100100000L);

        final double zeroDivNaN = 0.0 / 0.0;
        double infNaN = Double.POSITIVE_INFINITY - Double.POSITIVE_INFINITY;

        System.out.println("myNaN: " + myNaN);
        System.out.println("DoubleToRawLongBits: " + Long.toHexString(Double.doubleToRawLongBits(myNaN)));
        System.out.println("DoubleToLongBits: " + Long.toHexString(Double.doubleToLongBits(myNaN)));
        System.out.println("Is NaN: " + Double.isNaN(myNaN));
        System.out.println("----");

        double xx = myNaN / 0.0;
        System.out.println("XX: " + Long.toHexString(Double.doubleToRawLongBits(xx)));
        System.out.println("----");

        System.out.println("myOtherNaN");
        System.out.println("DoubleToRawLongBits: " + Long.toHexString(Double.doubleToRawLongBits(myOtherNaN)));
        System.out.println("Is NaN: " + Double.isNaN(myOtherNaN));
        System.out.println("----");

        double z = myNaN + myOtherNaN;
        double zz = myOtherNaN + myNaN;
        System.out.printf("myNaN + myOtherNaN -> DoubleToRawLongBits: %s\n", Long.toHexString(Double.doubleToRawLongBits(z)));
        System.out.println("Is NaN: " + Double.isNaN(z));
        System.out.printf("myOtherNaN + myNaN -> DoubleToRawLongBits: %s\n", Long.toHexString(Double.doubleToRawLongBits(zz)));
        System.out.println("Is NaN: " + Double.isNaN(zz));
        System.out.println("----");

        System.out.println("0/0");
        System.out.println("DoubleToRawLongBits: " + Long.toHexString(Double.doubleToRawLongBits(zeroDivNaN)));
        System.out.println("Is NaN: " + Double.isNaN(zeroDivNaN));
        System.out.println("----");

        System.out.println("+Inf - (+Inf)");
        System.out.println("DoubleToRawLongBits: " + Long.toHexString(Double.doubleToRawLongBits(infNaN)));
        System.out.println("Is NaN: " + Double.isNaN(infNaN));
        System.out.println("----");

        double x = infNaN + myNaN;
        double y = myNaN + infNaN;

        System.out.printf("infNaN + myNaN -> DoubleToRawLongBits: %s %s\n", Long.toHexString(Double.doubleToRawLongBits(x)), Double.isNaN(x));
        System.out.printf("myNaN + infNaN -> DoubleToRawLongBits: %s %s\n", Long.toHexString(Double.doubleToRawLongBits(y)), Double.isNaN(y));
        System.out.println("-----");

        System.out.println((int)myNaN);
        System.out.println((int)myOtherNaN);
        System.out.println((int)zeroDivNaN);
    }

    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }
    private long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    private double foo(double y) {
        return y;
    }
}
