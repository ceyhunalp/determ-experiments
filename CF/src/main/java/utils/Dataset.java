package utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Dataset {

    public HashMap<Integer, HashMap<Integer, Double>> trainingSet;
    public ArrayList<PredictionPair> testSet;
    public ArrayList<Double> actualRatings;
    public ArrayList<Integer> userIDs;

    public Dataset(HashMap<Integer, HashMap<Integer, Double>> trainingSet,
                   ArrayList<PredictionPair> testSet,
                   ArrayList<Double> actualRatings,
                   ArrayList<Integer> userIDs) {
        this.trainingSet = trainingSet;
        this.testSet = testSet;
        this.actualRatings = actualRatings;
        this.userIDs = userIDs;
    }
}
