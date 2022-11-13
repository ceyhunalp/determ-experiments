package dedis.fp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.common.math.Stats;

public abstract class MathLib {
    int inCount, warmupCount, execCount;
    long[] times;
    String libName;
    String outfile;

    public MathLib(int inCount, int warmupCount, int execCount, String libName) {
        this.inCount = inCount;
        this.warmupCount = warmupCount;
        this.execCount = execCount;
        this.libName = libName;
        times = new long[execCount];
    }

    public abstract void initInputs(int seed);
    public abstract void log();
    public abstract void exp();
    public abstract void pow();
    public abstract void sin();
    public abstract void cos();
    public abstract void tan();

    public void execFunction(String fname) {
        switch (fname) {
            case "log":
                this.log();
                break;
            case "exp":
                this.exp();
                break;
            case "pow":
                this.pow();
                break;
            case "sin":
                this.sin();
                break;
            case "cos":
                this.cos();
                break;
            case "tan":
                this.tan();
                break;
        }
    }

    public void logStats(String fname, String dirpath) throws IOException {
        this.outfile = dirpath + this.libName + ".csv";
        Stats stats = Stats.of(times);
        FileWriter fw = new FileWriter(this.outfile, true);
        fw.write(String.format("%s, %.1f, (%f)\n",
                fname,
                stats.mean(),
                stats.sampleStandardDeviation()));
        fw.close();
    }
}
