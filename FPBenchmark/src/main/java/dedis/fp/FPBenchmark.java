package dedis.fp;

import java.io.IOException;

public class FPBenchmark {
    String libName;
    String[] funcNames;
    String dirpath;
    int seed, inCount, warmupCount, execCount;

    public FPBenchmark(String libName, String[] funcNames, String dirpath, int seed, int ic, int wc, int ec) {
        this.libName = libName;
        this.funcNames = funcNames;
        this.dirpath = dirpath;
        this.seed = seed;
        inCount = ic;
        warmupCount = wc;
        execCount = ec;
    }

    public void runBenchmarks() throws IOException {
        MathLib mlib = null;
        switch (libName) {
            case "math":
                mlib = new LibJavaMath(inCount, warmupCount, execCount, libName);
                break;
            case "smath":
                mlib = new LibJavaSMath(inCount, warmupCount, execCount, libName);
                break;
            case "mpfr":
                mlib = new LibMPFR(inCount, warmupCount, execCount, libName);
                break;
            case "mf":
                mlib = new LibMicro(inCount, warmupCount, execCount, libName);
                break;
            case "apf":
                mlib = new LibApfloat(inCount, warmupCount, execCount, libName);
                break;
        }
        if (mlib == null) {
            System.err.println("Invalid library name");
            System.exit(1);
        }
        mlib.initInputs(seed);
        for (String fname: funcNames) {
            mlib.execFunction(fname);
            mlib.logStats(fname, dirpath);
        }
    }
}
