package benchmark;

import com.google.common.math.Stats;
import mm.MM;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MMBenchmark {

    final static double NANOSECS = 1E9;
    final static String HEADER = "avg,stddev,min,max";

    public static double[] runCanonMMBenchmark(MM mm, int wc, int ec) {
        long start, end;
        double[] times = new double[ec];
        for (int i = 0; i < wc + ec; i++) {
            start = System.nanoTime();
            mm.matrixMultiply();
            mm.serializeCanonResult();
            end = System.nanoTime();
            if (i >= wc) {
                times[i - wc] = (end - start) / NANOSECS;
            }
        }
        return times;
    }

    public static double[] runMMBenchmark(MM mm, int wc, int ec) {
        long start, end;
        double[] times = new double[ec];
        for (int i = 0; i < wc + ec; i++) {
            start = System.nanoTime();
            mm.matrixMultiply();
            mm.serializeResult();
            end = System.nanoTime();
            if (i >= wc) {
                times[i - wc] = (end - start) / NANOSECS;
            }
        }
        return times;
    }

    public static void writeStats(String outdir, double[] times, int dim,
                                  int nanRate, boolean isCanon) throws IOException {
        String canonStr = "";
        if (isCanon) canonStr = "canon";
        else canonStr = "raw";
        String fname = canonStr + "_" + dim + "_" + nanRate + ".csv";
        Path outPath = Paths.get(outdir, fname);
        String outfile = outPath.toString();
        Stats stats = Stats.of(times);
        FileWriter fw = new FileWriter(outfile, true);
        fw.write(String.format("%s\n", HEADER));
        fw.write(String.format("%.6f,%.6f,%.6f,%.6f\n",
                stats.mean(),
                stats.sampleStandardDeviation(),
                stats.min(),
                stats.max()));
        fw.close();
    }

    public static void launchBenchmark(String[] args) throws IOException {
        if (args.length != 7) {
            System.err.println("missing argument(s)");
            System.exit(-1);
        }
        int dim = Integer.parseInt(args[0]);
        int nanRate = Integer.parseInt(args[1]);
        int wc = Integer.parseInt(args[2]);
        int ec = Integer.parseInt(args[3]);
        int seed = Integer.parseInt(args[4]);
        boolean isCanon = args[5].equals("-y");
        String outdir = args[6];

        assert (nanRate == 0 || nanRate == 1 || nanRate == 10 || nanRate == 100);

        double[] times;
        MM mm = new MM(dim, nanRate, seed);
        mm.populateMatrix(mm.m1);
        mm.populateMatrix(mm.m2);
        if (isCanon) {
            times = runCanonMMBenchmark(mm, wc, ec);
        } else {
            times = runMMBenchmark(mm, wc, ec);
        }

        writeStats(outdir, times, dim, nanRate, isCanon);

//        Stats stats = Stats.of(times);
//        System.out.printf("%.6f (%.6f) %.6f %.6f\n", stats.mean(),
//                stats.sampleStandardDeviation(), stats.min(),
//                stats.max());
//        for (double t : times) {
//            System.out.printf("%.6f\n", t);
//        }
    }

    public static void main(String[] args) throws IOException {
        launchBenchmark(args);
    }
}
