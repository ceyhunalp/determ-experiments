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
        double[] times = new double[ec + wc];
        mm.matrixMultiply();
        for (int i = 0; i < 10000; i++) {
            mm.serializeCanonResult();
        }
        for (int i = 0; i < wc + ec; i++) {
            start = System.nanoTime();
            mm.matrixMultiply();
            mm.serializeCanonResult();
            end = System.nanoTime();
            times[i] = (end - start) / NANOSECS;
        }
        double[] execTimes = new double[ec];
        removeWarmupTimes(times, execTimes, wc);
        return execTimes;
    }

    public static double[] runMMBenchmark(MM mm, int wc, int ec) {
        long start, end;
        double[] times = new double[ec + wc];
        mm.matrixMultiply();
        for (int i = 0; i < 10000; i++) {
            mm.serializeResult();
        }
        for (int i = 0; i < wc + ec; i++) {
            start = System.nanoTime();
            mm.matrixMultiply();
            mm.serializeResult();
            end = System.nanoTime();
            times[i] = (end - start) / NANOSECS;
        }
        double[] execTimes = new double[ec];
        removeWarmupTimes(times, execTimes, wc);
        return execTimes;
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

    public static void removeWarmupTimes(double[] times, double[] execTimes, int wc) {
        for (int i = 0; i < execTimes.length; i++) {
            execTimes[i] = times[wc + i];
        }
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
    }

//    // No canonicalization
//    public static void serializeResult(double[] result, double[] bufResult) {
//        for (int i = 0; i < result.length; i++) {
//            long lng = Double.doubleToRawLongBits(result[i]);
//            for (int j = 0; j < 8; j++) {
//                bufResult[i * 8 + (7 - j)] =
//                        (byte) ((lng >> ((7 - j) * 8)) & 0xff);
//            }
//        }
//    }
//
//    // Canonicalized
//    public static void serializeCanonResult(double[] result, double[] bufResult) {
//        for (int i = 0; i < result.length; i++) {
//            long lng = Double.doubleToLongBits(result[i]);
//            for (int j = 0; j < 8; j++) {
//                bufResult[i * 8 + (7 - j)] =
//                        (byte) ((lng >> ((7 - j) * 8)) & 0xff);
//            }
//        }
//    }
//
//    public static void test() {
//        double[] result = new double[1000000];
//        Random rand = new Random();
//        for (int i = 0; i < 1000000; i++) {
//            result[i] = rand.nextDouble();
//        }
//        double[] bufResult = new double[1000000 * 8];
//        long start, end;
//        double[] times = new double[10];
//        for (int i = 0; i < 5010; i++) {
//            start = System.nanoTime();
//            serializeCanonResult(result, bufResult);
//            end = System.nanoTime();
//            if (i >= 5000) {
//                times[i - 5000] = (end - start) / NANOSECS;
//            }
//        }
//        for (double t :
//                times) {
//            System.out.printf("%.6f\n", t);
//        }
//        Stats stats = Stats.of(times);
//        System.out.printf(String.format("%.6f,%.6f,%.6f,%.6f\n",
//                stats.mean(),
//                stats.sampleStandardDeviation(),
//                stats.min(),
//                stats.max()));
//    }

    public static void main(String[] args) throws IOException {
        launchBenchmark(args);
    }
}
