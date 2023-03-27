import cf.CF;
import utils.*;

import java.io.IOException;
import java.util.ArrayList;

public class Launcher {

    public static void launchCFBenchmark(ArrayList<Dataset> datasets) {
        CF cf = new CF(datasets);
        cf.calculateAverageRatings();
        cf.calculateSimilarityScores();
        cf.makePredictions();
        cf.calculateError();
        for (Double err: cf.errors) {
            System.out.println(err.doubleValue());
        }

//        assert(datasets.get(0).actualRatings.size() == cf.predictions.get(0).size());

//        ArrayList<PredictionPair> testSet = datasets.get(0).testSet;
//        ArrayList<Integer> actualRatings = datasets.get(0).actualRatings;
//        ArrayList<Double> predictions = cf.predictions.get(0);

//        for (int i = 0; i < predictions.size(); i++) {
//            System.out.println(testSet.get(i).userID + " " + testSet.get(i).itemID +
//                    " XXXX Pred: " + predictions.get(i) +
//                    " || " +
//                    "Actual: " + actualRatings.get(i));
//        }

//        HashMap<UserPair, HashSet<Integer>> commons = cf.commonItems.get(0);
//        for (var entry: commons.entrySet()) {
//            System.out.println(">>>>> " + entry.getKey().firstID + " " + entry.getKey().secondID + " <<<<<");
//            for(Integer item : entry.getValue()) {
//                System.out.print(item + " ");
//            }
//            System.out.println();
//        }

//        HashMap<Integer, ArrayList<SimScore>> ratings = cf.similarityScores.get(0);
//        for (var entry: ratings.entrySet()) {
//            System.out.println(">>>>> " + entry.getKey() + " <<<<<");
//            for (SimScore r: entry.getValue()) {
//                System.out.println(r.userID + " " + r.score);
//            }
//        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Dataset> datasets = Reader.readCVDataset(args[0], 1);
        launchCFBenchmark(datasets);

//        UserPair p1 = new UserPair(1,4);
//        UserPair p2 = new UserPair(1,4);
//        UserPair p3 = new UserPair(4,1);
//        UserPair p4 = new UserPair(1,5);
//        System.out.println(p1.equals(p2) + " " + p2.equals(p3) + " " + p1.equals(p3));
//        HashMap<UserPair, Double> hm = new HashMap<>();
//        hm.put(p1, 0.3);
//        System.out.println(hm.get(p1));
//        hm.put(p2, 0.6);
//        System.out.println(hm.get(p1));
//        hm.put(p3, 0.9);
//        System.out.println(hm.get(p1));
//        System.out.println(hm.get(p4));

    }
}
