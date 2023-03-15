package utils;

public class Dataset {

    public final double[][][] trainData;
    public final double[][][] targets;
    public final double[][][] testData;
    public final int[][][] testLabels;

    public Dataset(double[][][] train, double[][][] targets,
                   double[][][] test, int[][][] labels) {
        this.trainData = train;
        this.targets = targets;
        this.testData = test;
        this.testLabels = labels;
    }
}
