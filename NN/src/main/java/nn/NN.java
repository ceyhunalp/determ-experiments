package nn;

import activationfunction.ActivationFunction;
import activationfunction.ActivationHelper;
import activationfunction.SoftMax;
import utils.Dataset;
import utils.Matrix;

import java.util.Arrays;
import java.util.Random;

public class NN {
    private final long seed;
    private final Dataset dataset;
    private final int batchSize;

    private final int epochs;
    private final double errorThresh;
    private final int[] nodeCounts;
    private final int hiddenLayers;
    private final String funcName;
    private final boolean useSoftmax;
    private final boolean useMB;
    private final double[][][] weights;
    private final double[][][] biases;
    private final double[][][] outputs;
    private final double[][][] derivatives;
    private final double[][][] gradients;
    private final int[] predictions;
    private double lrate;
    private ActivationFunction activFunc;

    public NN(int epochs, double lrate, double errorThresh, int[] nodeCounts,
              int hiddenLayers, long seed, boolean useSoftmax, boolean useMB,
              int batchSize, String funcName, Dataset d) {
        this.seed = seed;
        this.dataset = d;
        this.epochs = epochs;
        this.lrate = lrate;
        this.errorThresh = errorThresh;
        this.nodeCounts = nodeCounts;
        this.hiddenLayers = hiddenLayers;
        this.funcName = funcName;
        weights = new double[hiddenLayers + 1][][];
        biases = new double[hiddenLayers + 1][][];
        outputs = new double[hiddenLayers + 1][][];
        derivatives = new double[hiddenLayers + 1][][];
        gradients = new double[hiddenLayers + 1][][];
        predictions = new int[d.testImages.length];
        this.useSoftmax = useSoftmax;
        this.useMB = useMB;
        this.batchSize = batchSize;
    }

    public void initializeNN() {
        Random rand = new Random(seed);
        // Initialize weights
        if (funcName == ActivationHelper.RELU) {
            heInitialization(rand);
        } else {
            xavierInitialization(rand);
        }
        // Initialize biases
        for (int i = 0; i < hiddenLayers + 1; i++) {
            biases[i] = Matrix.createMatrix(1, nodeCounts[i + 1], 0, 1, rand);
        }
        // Initialize activation function
        activFunc = ActivationHelper.getFunction(funcName);
    }

    private void xavierInitialization(Random rand) {
        double lower, upper;
        double nom = 6.0;
        for (int i = 0; i < hiddenLayers + 1; i++) {
            upper = Math.sqrt(nom / (nodeCounts[i] + nodeCounts[i + 1]));
            lower = upper * -1.0;
            weights[i] = Matrix.createMatrix(nodeCounts[i], nodeCounts[i + 1],
                    lower, upper, rand);
        }
    }

    private void heInitialization(Random rand) {
        double lower, upper;
        double nom = 2.0;
        for (int i = 0; i < hiddenLayers + 1; i++) {
            upper = Math.sqrt(nom / nodeCounts[i]);
            lower = upper * -1.0;
            weights[i] = Matrix.createMatrix(nodeCounts[i], nodeCounts[i + 1],
                    lower, upper, rand);
        }
    }

    public void runNNTrain() {
        if (useMB) {
            runMiniBatch();
        } else {
            runSGD();
        }
    }

    public void runNNTest() {
        for (int i = 0; i < dataset.testImages.length; i++) {
            feedforward(dataset.testImages[i]);
            int predIdx = getPredictionIndex(outputs[hiddenLayers][0]);
            if (predIdx == dataset.testLabels[i]) {
                predictions[i] = 1;
            } else {
                predictions[i] = 0;
            }
        }
    }

    public void runSGD() {
        int iter = 0;
        double globalError;
        do {
            globalError = 0.0;
            for (int i = 0; i < dataset.trainImages.length; i++) {
                feedforward(dataset.trainImages[i]);
                calculateGradients(dataset.trainImages[i], dataset.targets[i]);
                updateWeights(gradients);
                globalError += calculateLoss(i);
            }
//            System.out.println("Iteration: " + iter + " / Error: " + globalError + " (" + lrate + ")");
            iter++;
//            updateLearningRate(iter);
        } while (iter < epochs && globalError > errorThresh);
//        System.out.println("Training finished @ " + iter);
    }

    public void runMiniBatch() {
        int bSize;
        int bIndex;
        int iter = 0;
        double globalError, batchError;
        int[] batchSizes = getBatchSizes();
        double[][][] cumGradients = new double[hiddenLayers + 1][][];
        for (int i = 0; i < hiddenLayers + 1; i++) {
            cumGradients[i] = new double[nodeCounts[i]][nodeCounts[i + 1]];
        }
        do {
            bIndex = 0;
            globalError = 0.0;
            for (int i = 0; i < dataset.trainImages.length; ) {
                batchError = 0.0;
                bSize = batchSizes[bIndex];
                for (int j = 0; j < bSize; j++) {
                    feedforward(dataset.trainImages[i + j]);
                    calculateGradients(dataset.trainImages[i + j],
                            dataset.targets[i + j]);
                    for (int k = 0; k < hiddenLayers + 1; k++) {
                        Matrix.sum(cumGradients[k], gradients[k]);
                    }
                    batchError += calculateLoss(i + j);
                }
                for (int j = 0; j < hiddenLayers + 1; j++) {
                    Matrix.scale(cumGradients[j], 1 / (double) bSize);
                }
                updateWeights(cumGradients);
                for (int j = 0; j < hiddenLayers + 1; j++) {
                    Matrix.reset(cumGradients[j]);
                }
                globalError += (batchError / (double) bSize);
                bIndex++;
                i += bSize;
            }
//            System.out.println("Iteration: " + iter + " / Error: " + globalError + " (" + lrate + ")");
            iter++;
//            updateLearningRate(iter);
        } while (iter < epochs && globalError > errorThresh);
//        System.out.println("Training finished @ " + iter);
    }

    public void feedforward(double[][] input) {
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == 0) {
                outputs[i] = Matrix.multiply(input, weights[i]);
                Matrix.sum(outputs[i], biases[i]);
                activFunc.applyFunction(outputs[i]);
            } else {
                outputs[i] = Matrix.multiply(outputs[i - 1], weights[i]);
                Matrix.sum(outputs[i], biases[i]);
                if (i == hiddenLayers) {
                    if (useSoftmax) {
                        SoftMax.softmax(outputs[i]);
                    } else {
                        activFunc.applyFunction(outputs[i]);
                    }
                }
            }
        }
    }

    private void calculateGradients(double[][] input, double[][] target) {
        for (int i = hiddenLayers; i >= 0; i--) {
            if (i == hiddenLayers) {
                if (useSoftmax) {
                    derivatives[i] = Matrix.subtract(target, outputs[i]);
                } else {
                    double[][] loss = Matrix.subtract(target, outputs[i]);
                    derivatives[i] = activFunc.applyDerivative(outputs[i]);
                    Matrix.elementMultiply(derivatives[i], loss);
                }
            } else {
                double[][] weightedDerv = Matrix.multiply(derivatives[i + 1],
                        Matrix.transpose(weights[i + 1]));
                derivatives[i] = activFunc.applyDerivative(outputs[i]);
                Matrix.elementMultiply(derivatives[i], weightedDerv);
            }
        }
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == 0) {
                gradients[i] = Matrix.multiply(Matrix.transpose(input),
                        derivatives[i]);
            } else {
                gradients[i] = Matrix.multiply(Matrix.transpose(outputs[i - 1]),
                        derivatives[i]);
            }
        }
    }

    private void updateWeights(double[][][] gradients) {
        for (int i = 0; i < hiddenLayers + 1; i++) {
            Matrix.scale(gradients[i], lrate);
            Matrix.sum(weights[i], gradients[i]);
            Matrix.sum(biases[i], gradients[i]);
        }
    }

    private void updateLearningRate(int currEpoch) {
        double decay = 1 - (currEpoch / (double) epochs);
        double tmpRate = lrate * decay;
        lrate = Math.max(0.01, tmpRate);
    }

    private double calculateLoss(int targetIdx) {
        assert (outputs[hiddenLayers].length == 1);
        double[] output = outputs[hiddenLayers][0];
        double[] target = dataset.targets[targetIdx][0];
        assert (output.length == target.length);
        double loss = 0.0;
        if (useSoftmax) {
            for (int i = 0; i < output.length; i++) {
                loss += (Math.log(output[i] + 1e-9) * target[i]);
            }
            return (loss * -1.0) / (double) output.length;
        } else {
            for (int i = 0; i < output.length; i++) {
                loss += Math.pow(target[i] - output[i], 2);
            }
            loss = loss / (double) output.length;
            return loss;
        }
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

    private int[] getBatchSizes() {
        int[] batchSizes;
        int batchCount = dataset.trainImages.length / batchSize;
        int residual = dataset.trainImages.length % batchSize;
        if (residual > 0) {
            batchSizes = new int[batchCount + 1];
            Arrays.fill(batchSizes, batchSize);
            batchSizes[batchSizes.length - 1] = residual;
        } else {
            batchSizes = new int[batchCount];
            Arrays.fill(batchSizes, batchSize);
        }
        return batchSizes;
    }
}
