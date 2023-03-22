package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MNISTReader {

    public static final int IMG_SIZE = 28 * 28;
    public static final int CLASS_SIZE = 10;
    static final int TRAIN_DS_SIZE = 60000;
    static final int TEST_DS_SIZE = 10000;

    public static Dataset readDataset(String trainData, String trainLabel, String testData, String testLabel) throws IOException {
        File fTrain = new File(trainData);
        File fTrainLabel = new File(trainLabel);
        File fTest = new File(testData);
        File fTestLabel = new File(testLabel);
        InputStream inTrain = new FileInputStream(fTrain);
        InputStream inTrainLabel = new FileInputStream(fTrainLabel);
        InputStream inTest = new FileInputStream(fTest);
        InputStream inTestLabel = new FileInputStream(fTestLabel);

        byte[] tmp = new byte[16];
        inTrain.read(tmp, 0, 16);
        inTrainLabel.read(tmp, 0, 8);
        inTest.read(tmp, 0, 16);
        inTestLabel.read(tmp, 0, 8);

        byte[] dataBuf = new byte[1];
        double[][][] trainImages = new double[TRAIN_DS_SIZE][1][IMG_SIZE];
        double[][][] targets = new double[TRAIN_DS_SIZE][1][CLASS_SIZE];
        double[][][] testImages = new double[TEST_DS_SIZE][1][IMG_SIZE];
        int[] testLabels = new int[TEST_DS_SIZE];

        for (int i = 0; i < TRAIN_DS_SIZE; i++) {
            inTrainLabel.read(dataBuf, 0, 1);
            int target = dataBuf[0] & 0xFF;
            targets[i][0][target] = 1.0;
            for (int j = 0; j < IMG_SIZE; j++) {
                inTrain.read(dataBuf, 0, 1);
                trainImages[i][0][j] = (dataBuf[0] & 0xFF) / 255.f;
            }
        }
        for (int i = 0; i < TEST_DS_SIZE; i++) {
            inTestLabel.read(dataBuf, 0, 1);
            testLabels[i] = dataBuf[0] & 0xFF;
            for (int j = 0; j < IMG_SIZE; j++) {
                inTest.read(dataBuf, 0, 1);
                testImages[i][0][j] = (dataBuf[0] & 0xFF) / 255.f;
            }
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(i + " " + Arrays.toString(targets[i][0]));
        }
        return new Dataset(trainImages, targets, testImages, testLabels);
    }

    public static Dataset readReducedDataset(String trainBase, String testBase) throws IOException {
        List<Path> trainPaths = Files.walk(Paths.get(trainBase), 2)
                .filter(Files::isRegularFile)
                .filter(path -> !path.toString().contains("extra"))
                .filter(path -> !path.toString().contains("DS_Store")).toList();
        List<Path> testPaths = Files.walk(Paths.get(testBase), 2)
                .filter(Files::isRegularFile)
                .filter(path -> !path.toString().contains("extra"))
                .filter(path -> !path.toString().contains("DS_Store")).toList();

        List<Path> trainList = new ArrayList<>(trainPaths);
        List<Path> testList = new ArrayList<>(testPaths);
        Collections.shuffle(trainList, new Random(87));
        Collections.shuffle(testList, new Random(87));

        int trainSize = trainList.size();
        int testSize = testList.size();
        double[][][] trainData = new double[trainSize][1][IMG_SIZE];
        double[][][] targets = new double[trainSize][1][CLASS_SIZE];
        double[][][] testData = new double[testSize][1][IMG_SIZE];
        int[] testLabels = new int[testSize];
        for (int i = 0; i < trainList.size(); i++) {
            Path p = trainList.get(i);
            BufferedImage image = ImageIO.read(p.toFile());
            final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            for (int j = 0; j < IMG_SIZE; j++) {
                trainData[i][0][j] = (pixels[j] & 0xFF) / 255.f;
            }
            getTarget(p.toString(), targets[i][0]);
        }

        for (int i = 0; i < testList.size(); i++) {
            Path p = testList.get(i);
            BufferedImage image = ImageIO.read(p.toFile());
            final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            for (int j = 0; j < IMG_SIZE; j++) {
                testData[i][0][j] = (pixels[j] & 0xFF) / 255.f;
            }
            testLabels[i] = getTestLabel(p.toString());
        }
        System.out.println(trainPaths.size() + " " + testPaths.size());
        return new Dataset(trainData, targets, testData, testLabels);
    }

    private static double[] getTarget(String path, double[] target) {
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

    private static int getTestLabel(String path) {
        if (path.contains("/0/")) return 0;
        else if (path.contains("/1/")) return 1;
        else if (path.contains("/2/")) return 2;
        else if (path.contains("/3/")) return 3;
        else if (path.contains("/4/")) return 4;
        else if (path.contains("/5/")) return 5;
        else if (path.contains("/6/")) return 6;
        else if (path.contains("/7/")) return 7;
        else if (path.contains("/8/")) return 8;
        else if (path.contains("/9/")) return 9;
        return -1;
    }
}
