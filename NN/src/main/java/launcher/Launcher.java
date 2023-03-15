package launcher;

import nn.NN;
import utils.Dataset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Launcher<target> {

    public static void launchNNBenchmark(Dataset d) {
        NN nn = new NN(1000, 0.2, 0.1, 28 * 28, 28 * 28, 10, 1, 47, "sigmoid",
                d);
        nn.initializeNN();
        nn.runNN();
        int[] predictions = nn.getPredictions();
        int success = 0;
        for (int i = 0; i < predictions.length; i++) {
            if (predictions[i] == 1) {
                success++;
            }
        }
        System.out.println("Success: " + success + " Total: " + predictions.length);
    }

//    public static void readMNISTFile() throws IOException {
//        String file = "/Users/alp/Downloads/Reduced MNIST Data/Reduced " +
//                "Trainging data/8/5079.jpg";
//
//        BufferedImage inImage = ImageIO.read(new File(file));
//        final byte[] pixels = ((DataBufferByte) inImage.getRaster().getDataBuffer()).getData();
//        int width = inImage.getWidth();
//        int height = inImage.getHeight();
//
//        int[] result = new int[height * width];
//        for (int i = 0; i < width * height; i++) {
//            result[i] = 255 - (pixels[i] & 0xFF);
//            System.out.print(result[i] + " ");
//        }
//        System.out.println();
//
//        BufferedImage image = new BufferedImage(width, height,
//                BufferedImage.TYPE_BYTE_GRAY);
//        WritableRaster raster = image.getRaster();
//        raster.setPixels(0, 0, width, height, result);
//        File outfile = new File("./saved.jpg");
//        ImageIO.write(image, "jpg", outfile);
//    }

    public static Dataset readMNISTDataset(String trainBase, String testBase) throws IOException {
        List<Path> trainFiles = Files.walk(Paths.get(trainBase), 2)
                .filter(Files::isRegularFile)
                .toList();
        List<Path> testFiles = Files.walk(Paths.get(testBase), 2)
                .filter(Files::isRegularFile)
                .toList();

        int trainSize = trainFiles.size();
        int testSize = testFiles.size();
        double[][][] trainData = new double[trainSize][1][];
        double[][][] targets = new double[trainSize][1][];
        double[][][] testData = new double[testSize][1][];
        int[][][] testLabels = new int[testSize][1][];
        for (int i = 0; i < trainSize; i++) {
            Path p = trainFiles.get(i);
            BufferedImage image = ImageIO.read(p.toFile());
            final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            int width = image.getWidth();
            int height = image.getHeight();
            double[] result = new double[height * width];
            for (int j = 0; j < width * height; j++) {
                result[j] = (pixels[j] & 0xFF) / 255.f;
            }
            trainData[i][0] = result;
            targets[i][0] = getTarget(p.toString());
        }

        for (int i = 0; i < testSize; i++) {
            Path p = testFiles.get(i);
            BufferedImage image = ImageIO.read(p.toFile());
            final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            int width = image.getWidth();
            int height = image.getHeight();
            double[] result = new double[height * width];
            for (int j = 0; j < width * height; j++) {
                result[j] = (pixels[j] & 0xFF) / 255.f;
            }
            testData[i][0] = result;
            testLabels[i][0] = getTestLabels(p.toString());
        }
        return new Dataset(trainData, targets, testData, testLabels);
    }

    public static void main(String[] args) throws IOException {
        Dataset d = readMNISTDataset(args[0], args[1]);
        launchNNBenchmark(d);
    }

    private static double[] getTarget(String path) {
        double[] target = new double[10];
        if (path.contains("/0/")) target[0] = 1;
        else if (path.contains("/1/")) target[1] = 1;
        else if (path.contains("/2/")) target[2] = 1;
        else if (path.contains("/3/")) target[3] = 1;
        else if (path.contains("/4/")) target[4] = 1;
        else if (path.contains("/5/")) target[5] = 1;
        else if (path.contains("/6/")) target[6] = 1;
        else if (path.contains("/7/")) target[7] = 1;
        else if (path.contains("/8/")) target[8] = 1;
        else if (path.contains("/9/")) target[9] = 1;
        return target;
    }

    private static int[] getTestLabels(String path) {
        int[] label = new int[10];
        if (path.contains("/0/")) label[0] = 1;
        else if (path.contains("/1/")) label[1] = 1;
        else if (path.contains("/2/")) label[2] = 1;
        else if (path.contains("/3/")) label[3] = 1;
        else if (path.contains("/4/")) label[4] = 1;
        else if (path.contains("/5/")) label[5] = 1;
        else if (path.contains("/6/")) label[6] = 1;
        else if (path.contains("/7/")) label[7] = 1;
        else if (path.contains("/8/")) label[8] = 1;
        else if (path.contains("/9/")) label[9] = 1;
        return label;
    }
}
