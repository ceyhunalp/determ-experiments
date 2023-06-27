package launcher;

import cf.CF;
import utils.Dataset;
import utils.Reader;

import java.io.IOException;

public class Launcher {

    public static void launchCFBenchmark(String dir, int foldNum) throws IOException {
        for (int i = 1; i <= foldNum; i++) {
            Dataset d = Reader.readCVDataset(dir, i);
            CF cf = new CF(d);
            cf.calculateAverageRatings();
            cf.calculateSimilarityScores();
            cf.makePredictions();
            cf.calculateRMSE();
            System.out.println("(RMSE) Fold #" + (i) + " : " + cf.error);
            cf.calculateMAE();
            System.out.println("(MAE) Fold #" + (i) + " : " + cf.error);
        }
    }

    public static void main(String[] args) throws IOException {
        launchCFBenchmark(args[0], Integer.parseInt(args[1]));
    }
}
