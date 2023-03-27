package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Reader {

    public static ArrayList<Dataset> readCVDataset(String dir, int count) throws IOException {
        ArrayList<Dataset> datasets = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            HashMap<Integer, HashMap<Integer, Integer>> trainingSet = new HashMap<>();
            HashSet<Integer> userIDs = new HashSet<>();
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
                    userIDs.add(userID);
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
            datasets.add(i, new Dataset(trainingSet, testSet, actualRatings,
                    userIDs));
        }
        return datasets;
    }

//    public static HashMap<Integer, HashMap<Integer, Integer>> readDataset(String fPath) throws IOException {
//        // User-based CF
//        HashMap<Integer, HashMap<Integer, Integer>> userRatings = new HashMap<>();
//        HashMap<Integer, Double> avgUserRatings = new HashMap<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(fPath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                // process the line.
////                System.out.println(i);
//                String[] tokens = line.split("\t");
//                int userID = Integer.parseInt(tokens[0]);
//                int itemID = Integer.parseInt(tokens[1]);
//                int rating = Integer.parseInt(tokens[2]);
//                if (!userRatings.containsKey(userID)) {
//                    // Add (item, rating) pair to the HashMap
//                    HashMap<Integer, Integer> itemRatings = new HashMap<>();
//                    itemRatings.put(itemID, rating);
//                    userRatings.put(userID, itemRatings);
//                } else {
//                    userRatings.get(userID).put(itemID, rating);
//                }
//            }
//        }
//        for(var urEntry : userRatings.entrySet()) {
//            int userID = urEntry.getKey();
//            int totalRating = 0;
//            HashMap<Integer, Integer> ratings = urEntry.getValue();
//            for(Map.Entry<Integer,Integer> entry : ratings.entrySet()) {
//                totalRating += entry.getValue();
//            }
//            avgUserRatings.put(userID, totalRating / (double)ratings.size());
//        }
//
//        System.out.println(calculateSimilarity(userRatings, avgUserRatings, 678
//                , 681));
//
////        for (Integer key : avgUserRatings.keySet()) {
////            System.out.println(key + " " + avgUserRatings.get(key));
////        }
//
////        System.out.println(userRatings.size());
////        System.out.println("------");
////        int i = 0;
//        for(Map.Entry<Integer, HashMap<Integer, Integer>> entry : userRatings.entrySet()) {
////            System.out.println("UserID: " + entry.getKey() + " - # ratings: " + entry.getValue().size());
////            i += entry.getValue().size();
////            if (entry.getKey() == 631) {
////                HashMap<Integer, Integer> ratings = entry.getValue();
////                for(Map.Entry<Integer, Integer> ratEntry : ratings.entrySet()) {
////                    System.out.print(ratEntry.getValue() + " ");
////                }
////                System.out.println();
////            }
//        }
////        System.out.println("Total: " + i);
//        return userRatings;
//    }

}
