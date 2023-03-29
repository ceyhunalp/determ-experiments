package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Reader {

    public static Dataset readCVDataset(String dir, int foldIdx) throws IOException {
        HashMap<Integer, HashMap<Integer, Double>> trainingSet = new HashMap<>();
        HashSet<Integer> userIDSet = new HashSet<>();
        Path path = Paths.get(dir, String.format("r%d.train", foldIdx + 1));
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                String[] tokens = line.split("::");
                int userID = Integer.parseInt(tokens[0]);
                int itemID = Integer.parseInt(tokens[1]);
                double rating = Double.parseDouble(tokens[2]);
                if (!trainingSet.containsKey(userID)) {
                    // Add (item, rating) pair to the HashMap
                    HashMap<Integer, Double> itemRatings = new HashMap<>();
                    itemRatings.put(itemID, rating);
                    trainingSet.put(userID, itemRatings);
                } else {
                    trainingSet.get(userID).put(itemID, rating);
                }
                userIDSet.add(userID);
            }
        }
        ArrayList<PredictionPair> testSet = new ArrayList<>();
        ArrayList<Double> actualRatings = new ArrayList<>();
        path = Paths.get(dir, String.format("r%d.test", foldIdx + 1));
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("::");
                int userID = Integer.parseInt(tokens[0]);
                int itemID = Integer.parseInt(tokens[1]);
                double rating = Double.parseDouble(tokens[2]);
                testSet.add(new PredictionPair(userID, itemID));
                actualRatings.add(rating);
            }
        }
        ArrayList<Integer> userIDs = new ArrayList<>(userIDSet);
        Collections.sort(userIDs);
        return new Dataset(trainingSet, testSet, actualRatings, userIDs);
    }
}
