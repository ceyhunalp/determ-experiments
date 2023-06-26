package benchmark;

import cf.CF;
import utils.Dataset;
import utils.Reader;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CFBenchmark {

    final static double NANOSECS = 1E9;

    public static double[] runCFBenchmark(CF cf, int wc, int ec) {
        long start, end;
        double[] times = new double[ec + wc];
        for (int i = 0; i < wc + ec; i++) {
            start = System.nanoTime();
            cf.calculateSimilarityScores();
            cf.makePredictions();
            end = System.nanoTime();
            times[i] = (end - start) / NANOSECS;
            cf.similarityScores.clear();
            cf.predictions.clear();
        }
        return times;
    }

    public static void removeWarmupTimes(double[] times, double[] execTimes, int wc) {
        System.arraycopy(times, wc + 0, execTimes, 0, execTimes.length);
    }

    public static void writeTimes(String outdir, String lib, double[] times, int foldNum) throws IOException {
        String fname = lib + "_" + foldNum + ".csv";
        Path outPath = Paths.get(outdir, fname);
        String outfile = outPath.toString();
        FileWriter fw = new FileWriter(outfile, true);
        for (double time : times) {
            fw.write(String.format("%.6f\n", time));
        }
        fw.close();
    }

    public static void launchBenchmark(String[] args) throws IOException {
        if (args.length != 6) {
            System.err.println("missing argument(s)");
            System.exit(-1);
        }
        String dsDir = args[0];
        int foldNum = Integer.parseInt(args[1]);
        int wc = Integer.parseInt(args[2]);
        int ec = Integer.parseInt(args[3]);
        String lib = args[4];
        String outdir = args[5];

        double[] allTimes;
        double[] execTimes = new double[ec];
        Dataset d = Reader.readCVDataset(dsDir, foldNum);
        CF cf = new CF(d);
        cf.calculateAverageRatings();

        allTimes = runCFBenchmark(cf, wc, ec);
        removeWarmupTimes(allTimes, execTimes, wc);
        writeTimes(outdir, lib, execTimes, foldNum);

//        for (double t : allTimes) {
//            System.out.printf("%.6f\n", t);
//        }
    }

    public static void main(String[] args) throws IOException {
        launchBenchmark(args);
    }
}
