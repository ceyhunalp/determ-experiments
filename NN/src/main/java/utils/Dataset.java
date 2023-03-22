package utils;

public class Dataset {

    public double[][][] trainImages;
    public double[][][] targets;
    public double[][][] testImages;
    public int[] testLabels;

    public Dataset(double[][][] train, double[][][] targets,
                   double[][][] test, int[] labels) {
        this.trainImages = train;
        this.targets = targets;
        this.testImages = test;
        this.testLabels = labels;
    }
}
