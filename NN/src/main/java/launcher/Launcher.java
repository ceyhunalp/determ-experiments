package launcher;

import nn.NN;
import utils.CIFARReader;
import utils.Dataset;

import java.io.IOException;

public class Launcher {

    public static void launchNNBenchmark(Dataset d) {
//        int[] nodeCounts = {MNISTReader.IMG_SIZE, 64, MNISTReader.CLASS_SIZE};
        int[] nodeCounts = {CIFARReader.INPUT_SIZE, 128, CIFARReader.CLASS_SIZE};
        NN nn = new NN(25, 0.01, 0.1, nodeCounts, 1, 67, true, false, 0,
                "relu", d);
        nn.initializeNN();
        nn.runNNTrain();
        nn.runNNTest();
        int[] predictions = nn.getPredictions();
        int success = 0;
        for (int prediction : predictions) {
            if (prediction == 1) {
                success++;
            }
        }
        System.out.println("Success: " + success + " Total: " + predictions.length);
    }


    public static void main(String[] args) throws IOException {
//        Dataset d = MNISTReader.readReducedDataset(args[0], args[1]);
//        launchNNBenchmark(d);
//        Dataset d = MNISTReader.readDataset(args[0], args[1], args[2], args[3]);
//        launchNNBenchmark(d);
        Dataset d = CIFARReader.readDataset(args[0], args[1], args[2], args[3]);
        launchNNBenchmark(d);
    }


}
