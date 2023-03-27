package cf;

import utils.*;

import java.util.*;

public class CF {

    final static double THRESHOLD = 0.0;

    public final ArrayList<Dataset> datasets;
    public final ArrayList<HashMap<Integer, Double>> avgUserRatings;
    public final ArrayList<HashMap<Integer, ArrayList<SimScore>>> similarityScores;
    public final ArrayList<ArrayList<Double>> predictions;
    public final ArrayList<Double> errors;

    public CF(ArrayList<Dataset> datasets) {
        this.datasets = datasets;
        this.avgUserRatings = new ArrayList<>(datasets.size());
        this.similarityScores = new ArrayList<>(datasets.size());
        this.predictions = new ArrayList<>(datasets.size());
        this.errors = new ArrayList<>(datasets.size());
    }

    public void calculateError() {
        for (int i = 0; i < datasets.size(); i++) {
            Dataset d = datasets.get(i);
            ArrayList<Double> predictions = this.predictions.get(i);
            assert(d.actualRatings.size() == predictions.size());
            double sum = 0.0;
            for (int j = 0; j < predictions.size(); j++) {
                sum += Math.pow((double)d.actualRatings.get(j)-predictions.get(i),2);
            }
            sum /= predictions.size();
            sum = Math.sqrt(sum);
            errors.add(sum);
        }
    }

    public void makePredictions() {
        for (int i = 0; i < datasets.size(); i++) {
            ArrayList<PredictionPair> testSet = datasets.get(i).testSet;
            HashMap<Integer, HashMap<Integer, Integer>> trainingSet = datasets.get(i).trainingSet;
            HashMap<Integer, ArrayList<SimScore>> allScores = similarityScores.get(i);
            HashMap<Integer, Double> avgRatings = avgUserRatings.get(i);
            ArrayList<Double> predictions = new ArrayList<>();
            for (PredictionPair pair : testSet) {
                double prediction = avgRatings.get(pair.userID);
                double numer = 0.0;
                double denom = 0.0;
                ArrayList<SimScore> simScores = allScores.get(pair.userID);
                for(SimScore s : simScores) {
                    if (s.score > THRESHOLD &&isCommonItem(i, s.userID, pair.itemID)) {
                             double otherAvg = avgRatings.get(s.userID);
                             double otherRating = (double) trainingSet.get(s.userID).get(pair.itemID);
                             numer += (s.score * (otherRating - otherAvg));
                             denom += s.score;
                    }
                }
                if (denom != 0.0) {
                    prediction += (numer/denom);
                }
                predictions.add(prediction);
            }
            this.predictions.add(predictions);
        }
    }

    private boolean isCommonItem(int idx, int userID, int itemID) {
        return datasets.get(idx).trainingSet.get(userID).containsKey(itemID);
    }

    public void calculateSimilarityScores() {
        for (int i = 0; i < datasets.size(); i++) {
            HashSet<Integer> userIDs = datasets.get(i).userIDs;
            HashMap<UserPair, Double> cached = new HashMap<>();
            HashMap<Integer, ArrayList<SimScore>> similarityScores = new HashMap<>();
            for (Integer firstUID: userIDs) {
                for(Integer secondUID: userIDs) {
                    if (firstUID.intValue() != secondUID.intValue()) {
                        // Check if we have already calculated this score
                        Double score = cached.get(new UserPair(firstUID, secondUID));
                        if (score == null) {
                        // Haven't calculated this score before
                            score = getScore(i, firstUID, secondUID);
                            cached.put(new UserPair(secondUID, firstUID), score);
                        }
                        if (score.doubleValue() == score.doubleValue()) {
                            SimScore r = new SimScore(secondUID, score);
                            if (!similarityScores.containsKey(firstUID)) {
                                ArrayList<SimScore> scores = new ArrayList<>();
                                scores.add(r);
                                similarityScores.put(firstUID, scores);
                            } else {
                                similarityScores.get(firstUID).add(r);
                            }
                        }
                    }
                }
            }
            this.similarityScores.add(i, similarityScores);
        }
        for (int i = 0; i < datasets.size(); i++) {
            HashMap<Integer, ArrayList<SimScore>> scores = similarityScores.get(i);
            for (var entry : scores.entrySet()) {
                Collections.sort(entry.getValue(), new SimScoreComparator());
                Collections.reverse(entry.getValue());
            }
        }
    }

    private double getScore(int idx, int firstUID, int secondUID) {
        HashMap<Integer, Double> avgRatings = avgUserRatings.get(idx);
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
        for (Integer item: intersection) {
            double firstPair = (double) u1Ratings.get(item) - firstAvg;
            double secondPair = (double) u2Ratings.get(item) - secondAvg;
            numer += firstPair * secondPair;
            u1Denom += Math.pow(firstPair, 2);
            u2Denom += Math.pow(secondPair, 2);
        }
        double denom = Math.sqrt(u1Denom * u2Denom);
        return numer/denom;
    }

    public void calculateAverageRatings() {
        for (int i = 0; i < datasets.size(); i++) {
            Dataset d = datasets.get(i);
            HashMap<Integer, Double> avgUserRatings = new HashMap<>();
            for(var urEntry : d.trainingSet.entrySet()) {
                int userID = urEntry.getKey();
                int totalRating = 0;
                HashMap<Integer, Integer> ratings = urEntry.getValue();
                for (Map.Entry<Integer, Integer> entry : ratings.entrySet()) {
                    totalRating += entry.getValue();
                }
                avgUserRatings.put(userID,
                        totalRating / (double) ratings.size());
            }
            this.avgUserRatings.add(i, avgUserRatings);
        }
    }
}
