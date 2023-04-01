package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class CIFARReader {

    public static final int INPUT_SIZE = 2048;
    public static final int CLASS_SIZE = 10;
    static final int TRAIN_DS_SIZE = 50000;
    static final int TEST_DS_SIZE = 10000;

    private static final String[] filenames = {"xaa", "xab", "xac", "xad",
            "xae", "xaf", "xag", "xah", "xai", "xaj"};

    public static Dataset readDataset(String trainBase, String testBase, String trainLabel, String testLabel) throws IOException {
        double[][][] trainData = new double[TRAIN_DS_SIZE][1][INPUT_SIZE];
        double[][][] targets = new double[TRAIN_DS_SIZE][1][CLASS_SIZE];
        double[][][] testData = new double[TEST_DS_SIZE][1][INPUT_SIZE];
        int[] testLabels = new int[TEST_DS_SIZE];

        int index = 0;
        for (int i = 0; i < filenames.length; i++) {
            Path filePath = Paths.get(trainBase, filenames[i]);
            try (FileInputStream inputStream = new FileInputStream(String.valueOf(filePath)); Scanner sc = new Scanner(inputStream)) {
                while (sc.hasNextLine()) {
                    int featIndex = 0;
                    String line = sc.nextLine();
                    String[] tokens = line.split(",");
                    for (int j = 0; j < tokens.length; j++) {
                        trainData[index][0][featIndex] = Double.parseDouble(tokens[j]);
                        featIndex++;
                    }
                    index++;
                }
                if (sc.ioException() != null) {
                    throw sc.ioException();
                }
            }
            System.out.println(filePath);
        }

        index = 0;
        for (int i = 0; i < filenames.length; i++) {
            Path filePath = Paths.get(testBase, filenames[i]);
            try (FileInputStream inputStream = new FileInputStream(String.valueOf(filePath)); Scanner sc = new Scanner(inputStream)) {
                while (sc.hasNextLine()) {
                    int featIndex = 0;
                    String line = sc.nextLine();
                    String[] tokens = line.split(",");
                    for (int j = 0; j < tokens.length; j++) {
                        testData[index][0][featIndex] = Double.parseDouble(tokens[j]);
                        featIndex++;
                    }
                    index++;
                }
                if (sc.ioException() != null) {
                    throw sc.ioException();
                }
            }
            System.out.println(filePath);
        }

        // Read train labels
        index = 0;
        try (FileInputStream inputStream = new FileInputStream(trainLabel); Scanner sc = new Scanner(inputStream)) {
            while (sc.hasNextLine()) {
                int target = Integer.parseInt(sc.nextLine().trim());
                targets[index][0][target] = 1.0;
                index++;
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        }

        // Read test labels
        index = 0;
        try (FileInputStream inputStream = new FileInputStream(testLabel); Scanner sc = new Scanner(inputStream)) {
            while (sc.hasNextLine()) {
                testLabels[index] = Integer.parseInt(sc.nextLine().trim());
                index++;
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        }

        return new Dataset(trainData, targets, testData, testLabels);
    }
}
