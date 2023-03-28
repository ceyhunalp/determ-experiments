package cf;

import utils.*;

import java.util.*;

public class CF {

    final static double THRESHOLD = 0.0;

    public final ArrayList<Dataset> datasets;
    public final HashMap<Integer, Double>[] avgUserRatings;
    public final HashMap<Integer, ArrayList<SimScore>>[] similarityScores;
    public final ArrayList<Double>[] predictions;
    public final double[] errors;

    public CF(ArrayList<Dataset> datasets) {
        this.datasets = datasets;
        this.avgUserRatings = new HashMap[datasets.size()];
        this.similarityScores = new HashMap[datasets.size()];
        this.predictions = new ArrayList[datasets.size()];
        this.errors = new double[datasets.size()];
    }

    public void makePredictions(int idx) {
        ArrayList<PredictionPair> testSet = datasets.get(idx).testSet;
        HashMap<Integer, HashMap<Integer, Integer>> trainingSet = datasets.get(idx).trainingSet;
        HashMap<Integer, ArrayList<SimScore>> allScores = similarityScores[idx];
        HashMap<Integer, Double> avgRatings = avgUserRatings[idx];
        ArrayList<Double> predictions = new ArrayList<>();
        for (PredictionPair pair : testSet) {
            double prediction = avgRatings.get(pair.userID);
            double numer = 0.0;
            double denom = 0.0;
            ArrayList<SimScore> simScores = allScores.get(pair.userID);
            if (simScores != null) {
                for (SimScore s : simScores) {
                    if (s.score > THRESHOLD && isCommonItem(idx, s.userID, pair.itemID)) {
                        double otherAvg = avgRatings.get(s.userID);
                        double otherRating = (double) trainingSet.get(s.userID).get(pair.itemID);
                        numer += (s.score * (otherRating - otherAvg));
                        denom += s.score;
                    }
                }
            }
            if (denom != 0.0) {
                prediction += (numer / denom);
            }
            predictions.add(prediction);
        }
        this.predictions[idx] = predictions;
    }

    private boolean isCommonItem(int idx, int userID, int itemID) {
        return datasets.get(idx).trainingSet.get(userID).containsKey(itemID);
    }

    public void calculateSimilarityScores(int idx) {
        ArrayList<Integer> userIDs = datasets.get(idx).userIDs;
        HashMap<UserPair, Double> cached = new HashMap<>();
        HashMap<Integer, ArrayList<SimScore>> similarityScores = new HashMap<>();
        for (int i = 0; i < userIDs.size(); i++) {
            for (int j = i + 1; j < userIDs.size(); j++) {
                int firstUID = userIDs.get(i);
                int secondUID = userIDs.get(j);
                double score = getScore(idx, firstUID, secondUID);
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
        for (var entry : similarityScores.entrySet()) {
            Collections.sort(entry.getValue(), new SimScoreComparator());
            Collections.reverse(entry.getValue());
        }
        this.similarityScores[idx] = similarityScores;
    }

    private double getScore(int idx, int firstUID, int secondUID) {
        HashMap<Integer, Double> avgRatings = avgUserRatings[idx];
        HashMap<Integer, Integer> u1Ratings = datasets.get(idx).trainingSet.get(firstUID);
        HashMap<Integer, Integer> u2Ratings = datasets.get(idx).trainingSet.get(secondUID);

        Set<Integer> intersection = new HashSet<>(u1Ratings.keySet());
        Set<Integer> u2Items = u2Ratings.keySet();
        intersection.retainAll(u2Items);

        double firstAvg = avgRatings.get(firstUID);
        double secondAvg = avgRatings.get(secondUID);
        double numer = 0.0;
        double u1Denom = 0.0;
        double u2Denom = 0.0;
        for (Integer item : intersection) {
            double firstPair = (double) u1Ratings.get(item) - firstAvg;
            double secondPair = (double) u2Ratings.get(item) - secondAvg;
            numer += firstPair * secondPair;
            u1Denom += Math.pow(firstPair, 2);
            u2Denom += Math.pow(secondPair, 2);
        }
        double denom = Math.sqrt(u1Denom * u2Denom);
        return numer / denom;
    }

    public void calculateAverageRatings(int idx) {
        Dataset d = datasets.get(idx);
        HashMap<Integer, Double> avgUserRatings = new HashMap<>();
        for (var urEntry : d.trainingSet.entrySet()) {
            int userID = urEntry.getKey();
            int totalRating = 0;
            HashMap<Integer, Integer> ratings = urEntry.getValue();
            for (Map.Entry<Integer, Integer> entry : ratings.entrySet()) {
                totalRating += entry.getValue();
            }
            avgUserRatings.put(userID,
                    totalRating / (double) ratings.size());
        }
        this.avgUserRatings[idx] = avgUserRatings;
    }

    public void calculateRMSE(int idx) {
        Dataset d = datasets.get(idx);
        ArrayList<Double> predictions = this.predictions[idx];
        assert (d.actualRatings.size() == predictions.size());
        double sum = 0.0;
        for (int j = 0; j < predictions.size(); j++) {
            sum += Math.pow((double) d.actualRatings.get(j) - predictions.get(idx), 2);
        }
        sum /= predictions.size();
        sum = Math.sqrt(sum);
        errors[idx] = sum;
    }

    public void calculateMAE(int idx) {
        for (int i = 0; i < datasets.size(); i++) {
            Dataset d = datasets.get(i);
            ArrayList<Double> predictions = this.predictions[idx];
            assert (d.actualRatings.size() == predictions.size());
            double sum = 0.0;
            for (int j = 0; j < predictions.size(); j++) {
                sum += Math.abs((double) d.actualRatings.get(j) - predictions.get(i));
            }
            sum /= predictions.size();
            errors[i] = sum;
        }
    }
}