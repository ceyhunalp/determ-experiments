package utils;

public class Dataset {

    public double[][][] trainData;
    public double[][][] targets;
    public double[][][] testData;
    public int[][][] testLabels;

    public Dataset(double[][][] train, double[][][] targets,
                   double[][][] test, int[][][] labels) {
        this.trainData = train;
        this.targets = targets;
        this.testData = test;
        this.testLabels = labels;
    }
}
