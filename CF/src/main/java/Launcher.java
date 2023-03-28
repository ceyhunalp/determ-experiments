import cf.CF;
import utils.Dataset;
import utils.Reader;

import java.io.IOException;
import java.util.ArrayList;

public class Launcher {

    public static void launchCFBenchmark(ArrayList<Dataset> datasets) {

        for (int i = 0; i < datasets.size(); i++) {
            CF cf = new CF(datasets.get(i));
            cf.calculateAverageRatings();
            cf.calculateSimilarityScores();
            cf.makePredictions();
            cf.calculateRMSE();
            System.out.println("(RMSE) Fold #" + (i + 1) + " : " + cf.error);
            cf.calculateMAE();
            System.out.println("(MAE) Fold #" + (i + 1) + " : " + cf.error);
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Dataset> datasets = Reader.readCVDataset(args[0], 5);
        launchCFBenchmark(datasets);
    }
}
