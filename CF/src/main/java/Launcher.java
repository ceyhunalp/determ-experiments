import cf.CF;
import utils.Dataset;
import utils.Reader;

import java.io.IOException;
import java.util.ArrayList;

public class Launcher {

    public static void launchCFBenchmark(ArrayList<Dataset> datasets) {
        CF cf = new CF(datasets);

        int i = 3;
        cf.calculateAverageRatings(i);
        System.out.println("before sim scores");
        cf.calculateSimilarityScores(i);
        System.out.println("before making predictions");
        cf.makePredictions(i);
        cf.calculateRMSE(i);
        System.out.println(cf.errors[i]);

//        for (int i = 0; i < datasets.size(); i++) {
//            cf.calculateAverageRatings(i);
//            cf.calculateSimilarityScores(i);
//            cf.makePredictions(i);
//            cf.calculateRMSE(i);
//            System.out.println(cf.errors[i]);
//        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Dataset> datasets = Reader.readCVDataset(args[0], 5);
        launchCFBenchmark(datasets);
    }
}
