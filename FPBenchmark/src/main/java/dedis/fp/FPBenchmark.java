package dedis.fp;

import java.io.IOException;

public class FPBenchmark {
    String libName;
    String[] funcNames;
    String dirpath;
    int seed, warmupCount, execCount;

    public FPBenchmark(String libName, String[] funcNames, String dirpath, int seed, int wc, int ec) {
        this.libName = libName;
        this.funcNames = funcNames;
        this.dirpath = dirpath;
        this.seed = seed;
        warmupCount = wc;
        execCount = ec;
    }

    public void runBenchmarks() throws IOException {
        MathLib mlib = null;
        switch (libName) {
            case "math":
                mlib = new LibJavaMath(warmupCount, execCount, libName);
                break;
            case "smath":
                mlib = new LibJavaSMath(warmupCount, execCount, libName);
                break;
            case "mpfr":
                mlib = new LibMPFR(warmupCount, execCount, libName);
                break;
            case "mf":
                mlib = new LibMicro(warmupCount, execCount, libName);
                break;
            case "apf":
                mlib = new LibApfloat(warmupCount, execCount, libName);
                break;
        }
        if (mlib == null) {
            System.err.println("Invalid library name");
            System.exit(1);
        }
        mlib.initInputs(seed);
        for (String fname : funcNames) {
            double[] results = mlib.execFunction(fname);
            mlib.logStats(fname, dirpath);
            System.out.println(results[0]);
        }
    }
}
