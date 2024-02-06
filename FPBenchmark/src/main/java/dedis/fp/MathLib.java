package dedis.fp;

import com.google.common.math.Stats;

import java.io.FileWriter;
import java.io.IOException;

public abstract class MathLib {
    int totalCount, warmupCount, execCount;
    long[] times;
    String libName;
    String outfile;
    double[] results;

    public MathLib(int warmupCount, int execCount, String libName) {
        this.totalCount = warmupCount + execCount;
        this.warmupCount = warmupCount;
        this.execCount = execCount;
        this.libName = libName;
        times = new long[totalCount];
        results = new double[totalCount];
    }

    public abstract void initInputs(int seed);

    public abstract double[] log();

    public abstract double[] exp();

    public abstract double[] pow();

    public abstract double[] sin();

    public abstract double[] cos();

    public abstract double[] tan();

    public double[] execFunction(String fname) {
        return switch (fname) {
            case "log" -> this.log();
            case "exp" -> this.exp();
            case "pow" -> this.pow();
            case "sin" -> this.sin();
            case "cos" -> this.cos();
            case "tan" -> this.tan();
            default -> null;
        };
    }

    public void logStats(String fname, String dirpath) throws IOException {
        long[] execTimes = new long[this.execCount];
        System.arraycopy(times, this.warmupCount, execTimes, 0, this.execCount);
        this.outfile = dirpath + this.libName + ".csv";
        Stats stats = Stats.of(execTimes);
        FileWriter fw = new FileWriter(this.outfile, true);
        fw.write("fname,avg,stddev,min,max\n");
        fw.write(String.format("%s,%.5f,%.5f,%f,%f\n",
                fname,
                stats.mean(),
                stats.sampleStandardDeviation(),
                stats.min(),
                stats.max()));
        fw.close();
    }
}
