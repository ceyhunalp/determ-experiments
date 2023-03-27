package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Dataset {

    public HashMap<Integer, HashMap<Integer, Integer>> trainingSet;
    public ArrayList<PredictionPair> testSet;
    public ArrayList<Integer> actualRatings;
    public HashSet<Integer> userIDs;

    public Dataset(HashMap<Integer, HashMap<Integer, Integer>> trainingSet,
                   ArrayList<PredictionPair> testSet,
                   ArrayList<Integer> actualRatings,
                   HashSet<Integer> userIDs) {
        this.trainingSet = trainingSet;
        this.testSet = testSet;
        this.actualRatings = actualRatings;
        this.userIDs = userIDs;
    }
}
