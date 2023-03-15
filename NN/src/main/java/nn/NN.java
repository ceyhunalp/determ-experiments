package nn;

import activationfunction.ActivationFunction;
import activationfunction.ActivationHelper;
import utils.Dataset;
import utils.Matrix;

import java.util.Random;

public class NN {

    private final int epochs;
    private final double lrate;
    private final double errorThresh;

    private final int inputNodes;
    private final int hiddenNodes;
    private final int outputNodes;
    private final int hiddenLayers;

    private final long seed;

    private final Dataset dataset;
    private final String funcName;
    private final double[][][] weights;
    private final double[][][] biases;
    private final double[][][] outputs;
    private final double[][][] gradients;
    private final int[] predictions;
    private ActivationFunction activFunc;

    public NN(int epochs, double lrate, double errorThresh, int inputNodes,
              int hiddenNodes, int outputNodes, int hiddenLayers, long seed,
              String funcName, Dataset d) {
        this.epochs = epochs;
        this.lrate = lrate;
        this.errorThresh = errorThresh;
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;
        this.hiddenLayers = hiddenLayers;
        this.seed = seed;
        this.funcName = funcName;
        this.dataset = d;
        weights = new double[hiddenLayers + 1][][];
        biases = new double[hiddenLayers + 1][][];
        outputs = new double[hiddenLayers + 1][][];
        gradients = new double[hiddenLayers + 1][][];
        predictions = new int[d.testData.length];
    }

    public void initializeNN() {
        double lower, upper, denom;
        double nom = StrictMath.sqrt(6.0);
        Random rand = new Random(seed);
        // Initialize weights
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == 0) {
                denom = StrictMath.sqrt(inputNodes + hiddenNodes);
                upper = nom / denom;
                lower = upper * -1.0;
                weights[i] = Matrix.createMatrix(inputNodes, hiddenNodes, lower,
                        upper, rand);
            } else if (i == hiddenLayers) {
                denom = StrictMath.sqrt(hiddenNodes + outputNodes);
                upper = nom / denom;
                lower = upper * -1.0;
                weights[i] = Matrix.createMatrix(hiddenNodes, outputNodes, lower,
                        upper, rand);
            } else {
                denom = StrictMath.sqrt(2 * hiddenNodes);
                upper = nom / denom;
                lower = upper * -1.0;
                weights[i] = Matrix.createMatrix(hiddenNodes, hiddenNodes, lower,
                        upper, rand);
            }
        }
        // Initialize biases
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == hiddenLayers) {
                // Biases for the output layer
                biases[i] = Matrix.createMatrix(1, outputNodes, 0, 1, rand);
            } else {
                // Biases for hidden layers
                biases[i] = Matrix.createMatrix(1, hiddenNodes, 0, 1, rand);
            }
        }
        // Initialize activation function
        activFunc = ActivationHelper.getFunction(funcName);
    }

    public void runNN() {
        int iter = 0;
        double globalError;
        do {
            System.out.println("Iteration: " + iter);
            globalError = 0.0;
            for (int i = 0; i < dataset.trainData.length; i++) {
                feedforward(dataset.trainData[i]);
                backpropagation(dataset.trainData[i], dataset.targets[i]);
                globalError += 0.5 * (Math.pow(dataset.targets[i][0][0] - outputs[hiddenLayers][0][0], 2));
            }
            iter++;
        } while (iter < epochs && globalError > errorThresh);

        System.out.println("Training finished @ " + iter);

        for (int i = 0; i < dataset.testData.length; i++) {
            feedforward(dataset.testData[i]);
            double[][] o = outputs[hiddenLayers];
            int predIdx = getPredictionIndex(o[0]);
            int labelIdx = getLabelIndex(dataset.testLabels[i][0]);
            if (predIdx == labelIdx) {
                predictions[i] = 1;
            } else {
                predictions[i] = 0;
            }
        }
    }

    public void feedforward(double[][] input) {
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == 0) {
                outputs[i] = Matrix.multiply(input, weights[i]);
            } else {
                outputs[i] = Matrix.multiply(outputs[i - 1], weights[i]);
            }
            Matrix.sum(outputs[i], biases[i]);
            activFunc.applyFunction(outputs[i]);
        }
    }

    public void backpropagation(double[][] input, double[][] target) {
        // Calculate error gradients
        for (int i = hiddenLayers; i >= 0; i--) {
            if (i == hiddenLayers) {
                double[][] error = Matrix.subtract(target, outputs[i]);
                gradients[i] = activFunc.applyDerivative(outputs[i]);
                Matrix.elementMultiply(gradients[i], error);
            } else {
                double[][] weightedErrors = Matrix.multiply(gradients[i + 1],
                        Matrix.transpose(weights[i + 1]));
                gradients[i] = activFunc.applyDerivative(outputs[i]);
                Matrix.elementMultiply(gradients[i], weightedErrors);
            }
        }
        // Calculate weight corrections & update weights
        double[][] delta;
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == 0) {
                delta = Matrix.multiply(Matrix.transpose(input), gradients[i]);
            } else {
                delta = Matrix.multiply(Matrix.transpose(outputs[i - 1]), gradients[i]);
            }
            Matrix.scale(delta, lrate);
            Matrix.sum(weights[i], delta);
        }
        // Calculate bias corrections & update biases
        for (int i = 0; i < hiddenLayers + 1; i++) {
            Matrix.scale(gradients[i], lrate);
            Matrix.sum(biases[i], gradients[i]);
        }
    }

    private int getLabelIndex(int[] labels) {
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    private int getPredictionIndex(double[] prediction) {
        int maxIdx = 0;
        double maxVal = prediction[0];
        for (int i = 1; i < prediction.length; i++) {
            if (prediction[i] > maxVal) {
                maxVal = prediction[i];
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    public int[] getPredictions() {
        return this.predictions;
    }
}
