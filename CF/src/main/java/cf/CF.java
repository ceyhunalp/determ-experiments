package cf;

import utils.Dataset;
import utils.PredictionPair;
import utils.SimScore;

import java.util.*;

public class CF {

    final static double THRESHOLD = 0.0;

    public final Dataset dataset;
    public final HashMap<Integer, Double> avgUserRatings;
    public final HashMap<Integer, ArrayList<SimScore>> similarityScores;
    public final ArrayList<Double> predictions;
    public double error;

    public CF(Dataset dataset) {
        this.dataset = dataset;
        this.avgUserRatings = new HashMap<>();
        this.similarityScores = new HashMap<>();
        this.predictions = new ArrayList<>();
    }

    public void makePredictions() {
        HashMap<Integer, HashMap<Integer, Double>> trainingSet = dataset.trainingSet;
        for (PredictionPair pair : dataset.testSet) {
            double prediction = avgUserRatings.get(pair.userID);
            double numer = 0.0;
            double denom = 0.0;
            ArrayList<SimScore> simScores = similarityScores.get(pair.userID);
            if (simScores != null) {
                for (SimScore s : simScores) {
                    if (s.score > THRESHOLD && isCommonItem(s.userID, pair.itemID)) {
                        double otherAvg = avgUserRatings.get(s.userID);
                        double otherRating = trainingSet.get(s.userID).get(pair.itemID);
                        numer += (s.score * (otherRating - otherAvg));
                        denom += s.score;
                    }
                }
            }
            if (denom != 0.0) {
                prediction += (numer / denom);
            }
            this.predictions.add(prediction);
        }
    }

    private boolean isCommonItem(int userID, int itemID) {
        return dataset.trainingSet.get(userID).containsKey(itemID);
    }

    private double getScore(int firstUID, int secondUID) {
        HashMap<Integer, Double> u1Ratings = dataset.trainingSet.get(firstUID);
        HashMap<Integer, Double> u2Ratings = dataset.trainingSet.get(secondUID);

        Set<Integer> intersection = new HashSet<>(u1Ratings.keySet());
        Set<Integer> u2Items = u2Ratings.keySet();
        intersection.retainAll(u2Items);

        double firstAvg = avgUserRatings.get(firstUID);
        double secondAvg = avgUserRatings.get(secondUID);
        double numer = 0.0;
        double u1Denom = 0.0;
        double u2Denom = 0.0;
        for (Integer item : intersection) {
            double firstPair = u1Ratings.get(item) - firstAvg;
            double secondPair = u2Ratings.get(item) - secondAvg;
            numer += firstPair * secondPair;
            u1Denom += Math.pow(firstPair, 2);
            u2Denom += Math.pow(secondPair, 2);
        }
        double denom = Math.sqrt(u1Denom * u2Denom);
        return numer / denom;
    }

    public void calculateSimilarityScores() {
        int size = dataset.userIDs.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int firstUID = dataset.userIDs.get(i);
                int secondUID = dataset.userIDs.get(j);
                double score = getScore(firstUID, secondUID);
                if (!Double.isNaN(score)) {
                    SimScore simScore = new SimScore(secondUID, score);
                    if (!similarityScores.containsKey(firstUID)) {
                        ArrayList<SimScore> scores = new ArrayList<>();
                        scores.add(simScore);
                        similarityScores.put(firstUID, scores);
                    } else {
                        similarityScores.get(firstUID).add(simScore);
                    }
                    SimScore revSimScore = new SimScore(firstUID, score);
                    if (!similarityScores.containsKey(secondUID)) {
                        ArrayList<SimScore> scores = new ArrayList<>();
                        scores.add(revSimScore);
                        similarityScores.put(secondUID, scores);
                    } else {
                        similarityScores.get(secondUID).add(revSimScore);
                    }
                }
            }
        }
//        for (var entry : similarityScores.entrySet()) {
//            Collections.sort(entry.getValue(), new SimScoreComparator());
//            Collections.reverse(entry.getValue());
//        }
    }

    public void calculateAverageRatings() {
        for (var urEntry : dataset.trainingSet.entrySet()) {
            int userID = urEntry.getKey();
            int totalRating = 0;
            HashMap<Integer, Double> ratings = urEntry.getValue();
            for (Map.Entry<Integer, Double> entry : ratings.entrySet()) {
                totalRating += entry.getValue();
            }
            avgUserRatings.put(userID, totalRating / (double) ratings.size());
        }
    }

    public void calculateRMSE() {
        assert (dataset.actualRatings.size() == predictions.size());
        double sum = 0.0;
        for (int i = 0; i < predictions.size(); i++) {
            sum += Math.pow((double) dataset.actualRatings.get(i) - predictions.get(i), 2);
        }
        sum /= predictions.size();
        sum = Math.sqrt(sum);
        this.error = sum;
    }

    public void calculateMAE() {
        assert (dataset.actualRatings.size() == predictions.size());
        double sum = 0.0;
        for (int i = 0; i < predictions.size(); i++) {
            sum += Math.abs((double) dataset.actualRatings.get(i) - predictions.get(i));
        }
        sum /= predictions.size();
        this.error = sum;
    }
}