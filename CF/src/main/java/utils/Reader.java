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

    public static ArrayList<Dataset> readCVDataset(String dir, int count) throws IOException {
        ArrayList<Dataset> datasets = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            HashMap<Integer, HashMap<Integer, Integer>> trainingSet = new HashMap<>();
            HashSet<Integer> userIDSet = new HashSet<>();
            Path path = Paths.get(dir, String.format("r%d.train", i + 1));
            try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // process the line.
                    String[] tokens = line.split("::");
                    int userID = Integer.parseInt(tokens[0]);
                    int itemID = Integer.parseInt(tokens[1]);
                    int rating = Integer.parseInt(tokens[2]);
                    if (!trainingSet.containsKey(userID)) {
                        // Add (item, rating) pair to the HashMap
                        HashMap<Integer, Integer> itemRatings = new HashMap<>();
                        itemRatings.put(itemID, rating);
                        trainingSet.put(userID, itemRatings);
                    } else {
                        trainingSet.get(userID).put(itemID, rating);
                    }
                    userIDSet.add(userID);
                }
            }
            ArrayList<PredictionPair> testSet = new ArrayList<>();
            ArrayList<Integer> actualRatings = new ArrayList<>();
            path = Paths.get(dir, String.format("r%d.test", i + 1));
            try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split("::");
                    int userID = Integer.parseInt(tokens[0]);
                    int itemID = Integer.parseInt(tokens[1]);
                    int rating = Integer.parseInt(tokens[2]);
                    testSet.add(new PredictionPair(userID, itemID));
                    actualRatings.add(rating);
                }
            }
            ArrayList<Integer> userIDs = new ArrayList<>();
            userIDs.addAll(userIDSet);
            Collections.sort(userIDs);
            datasets.add(i, new Dataset(trainingSet, testSet, actualRatings,
                    userIDs));
        }
        return datasets;
    }
}
