package benchmark;

import nn.NN;
import utils.CIFARReader;
import utils.Dataset;
import utils.MNISTReader;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NNBenchmark {

    final static double LRATE = 0.1;
    final static double NANOSECS = 1E9;

    public static void runNNBenchmark(NN nn, int wc, int ec,
                                      double[] trainTimes, double[] testTimes) {
        long start, end;
        int[] predictions;
        int success;
        for (int i = 0; i < wc + ec; i++) {
            nn.initializeNN();
            start = System.nanoTime();
            nn.runNNTrain();
            end = System.nanoTime();
            trainTimes[i] = (end - start) / NANOSECS;

            start = System.nanoTime();
            nn.runNNTest();
            predictions = nn.getPredictions();
            end = System.nanoTime();
            testTimes[i] = (end - start) / NANOSECS;

            success = 0;
            for (int prediction : predictions) {
                if (prediction == 1) {
                    success++;
                }
            }
//            System.out.println("Success: " + success + " Total: " + predictions.length);
        }
    }

    public static String[] getPaths(String dir, boolean isCnn) {
        String[] paths = new String[4];
        if (isCnn) {
            Path filePath = Paths.get(dir, "vgg_training");
            paths[0] = String.valueOf(filePath);
            filePath = Paths.get(dir, "vgg_testing");
            paths[1] = String.valueOf(filePath);
            filePath = Paths.get(dir, "label_train.csv");
            paths[2] = String.valueOf(filePath);
            filePath = Paths.get(dir, "label_test.csv");
            paths[3] = String.valueOf(filePath);
        } else {
            Path filePath = Paths.get(dir, "train_images");
            paths[0] = String.valueOf(filePath);
            filePath = Paths.get(dir, "train_labels");
            paths[1] = String.valueOf(filePath);
            filePath = Paths.get(dir, "test_images");
            paths[2] = String.valueOf(filePath);
            filePath = Paths.get(dir, "test_labels");
            paths[3] = String.valueOf(filePath);
        }
        return paths;
    }

    public static void removeWarmupTimes(double[] times, double[] execTimes, int wc) {
        System.arraycopy(times, wc, execTimes, 0, execTimes.length);
    }

    public static void writeTimes(String outdir, boolean isCnn, String func,
                                  int mbSize, double[] trainTimes, double[] testTimes) throws IOException {
        String ds = isCnn ? "cifar_" : "mnist_";
        String trainFile = ds + func + "_" + mbSize + "_train.csv";
        String testFile = ds + func + "_" + mbSize + "_test.csv";

        Path outPath = Paths.get(outdir, trainFile);
        String outfile = outPath.toString();
        FileWriter fw = new FileWriter(outfile, true);
        for (double time : trainTimes) {
            fw.write(String.format("%.6f\n", time));
        }
        fw.close();
        outPath = Paths.get(outdir, testFile);
        outfile = outPath.toString();
        fw = new FileWriter(outfile, true);
        for (double time : testTimes) {
            fw.write(String.format("%.6f\n", time));
        }
        fw.close();
    }

    public static void launchBenchmark(String[] args) throws IOException {
        if (args.length < 10) {
            System.err.println("missing argument(s)");
            System.exit(-1);
        }
        boolean useMB = false;
        boolean isCnn = args[0].equals("-cnn");
        int epochs = Integer.parseInt(args[1]);
        int seed = Integer.parseInt(args[2]);
        boolean useSoftmax = args[3].equals("-y");
        int mbSize = Integer.parseInt(args[4]);
        if (mbSize > 0) {
            useMB = true;
        }
        String func = args[5];
        int wc = Integer.parseInt(args[6]);
        int ec = Integer.parseInt(args[7]);
        String dir = args[8];
        String outdir = args[9];

        Dataset d;
        int[] nodeCounts;
        String[] paths = getPaths(dir, isCnn);
        if (isCnn) {
            d = CIFARReader.readDataset(paths[0], paths[1],
                    paths[2], paths[3]);
            nodeCounts = new int[]{CIFARReader.INPUT_SIZE, 128,
                    CIFARReader.CLASS_SIZE};
        } else {
            d = MNISTReader.readDataset(paths[0], paths[1],
                    paths[2], paths[3]);
            nodeCounts = new int[]{MNISTReader.IMG_SIZE, 64,
                    MNISTReader.CLASS_SIZE};
        }

        int totalCnt = wc + ec;
        double[] trainTimes = new double[ec];
        double[] testTimes = new double[ec];
        double[] allTrainTimes = new double[totalCnt];
        double[] allTestTimes = new double[totalCnt];

        NN nn = new NN(epochs, LRATE, 0.1, nodeCounts, 1, seed, useSoftmax,
                useMB, mbSize, func, d);
        runNNBenchmark(nn, wc, ec, allTrainTimes, allTestTimes);

        removeWarmupTimes(allTrainTimes, trainTimes, wc);
        removeWarmupTimes(allTestTimes, testTimes, wc);

        writeTimes(outdir, isCnn, func, mbSize, trainTimes, testTimes);
    }

    public static void main(String[] args) throws IOException {
        launchBenchmark(args);
    }
}
