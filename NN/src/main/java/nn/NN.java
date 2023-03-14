package nn;

import activationfunction.ActivationFunction;
import activationfunction.ActivationHelper;
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

    private final String funcName;
    private final Matrix[] weights;
    private final Matrix[] biases;
    private final Matrix[] outputs;
    private final Matrix[] gradients;
    private ActivationFunction activFunc;

    public NN(int epochs, double lrate, double errorThresh, int inputNodes,
              int hiddenNodes, int outputNodes, int hiddenLayers, long seed,
              String funcName) {
        this.epochs = epochs;
        this.lrate = lrate;
        this.errorThresh = errorThresh;
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;
        this.hiddenLayers = hiddenLayers;
        this.seed = seed;
        this.funcName = funcName;
        weights = new Matrix[hiddenLayers + 1];
        biases = new Matrix[hiddenLayers + 1];
        outputs = new Matrix[hiddenLayers + 1];
        gradients = new Matrix[hiddenLayers + 1];
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
                weights[i] = new Matrix(inputNodes, hiddenNodes, lower,
                        upper, rand);
            } else if (i == hiddenLayers) {
                denom = StrictMath.sqrt(hiddenNodes + outputNodes);
                upper = nom / denom;
                lower = upper * -1.0;
                weights[i] = new Matrix(hiddenNodes, outputNodes, lower,
                        upper, rand);
            } else {
                denom = StrictMath.sqrt(2 * hiddenNodes);
                upper = nom / denom;
                lower = upper * -1.0;
                weights[i] = new Matrix(hiddenNodes, hiddenNodes, lower,
                        upper, rand);
            }
        }
        // Initialize biases
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == hiddenLayers) {
                // Biases for the output layer
                biases[i] = new Matrix(1, outputNodes, 0, 1, rand);
            } else {
                // Biases for hidden layers
                biases[i] = new Matrix(1, hiddenNodes, 0, 1, rand);
            }
        }
        // Initialize activation function
        activFunc = ActivationHelper.getFunction(funcName);
    }

    public void runNN() {
        double[][] inputData = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };
        double[][] targetData = {
                {0},
                {1},
                {1},
                {0}
        };
        double[][] testData = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };
        Matrix input = new Matrix(inputData);
        Matrix target = new Matrix(targetData);
        Matrix test = new Matrix(testData);

        int iter = 0;
        double globalError;
        do {
            globalError = 0.0;
            for (int j = 0; j < input.numRows; j++) {
                Matrix inVector = input.getRow(j);
                Matrix tVector = target.getRow(j);
                feedforward(inVector);
                backpropagation(inVector, tVector);
                globalError += 0.5 * (Math.pow(tVector.data[0][0] - outputs[hiddenLayers].data[0][0], 2));
            }
            iter++;
        } while (iter < epochs && globalError > errorThresh);

        System.out.println("Training finished @ " + iter);

        for (int i = 0; i < test.numRows; i++) {
            Matrix v = test.getRow(i);
            feedforward(v);
            Matrix o = outputs[hiddenLayers];
            for (int j = 0; j < o.numColumns; j++) {
                System.out.println("output: " + o.data[0][j]);
            }
        }
    }

    public void feedforward(Matrix input) {
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == 0) {
                outputs[i] = input.multiply(weights[i]);
            } else {
                outputs[i] = outputs[i - 1].multiply(weights[i]);
            }
            outputs[i].sum(biases[i]);
            activFunc.applyFunction(outputs[i]);
        }
    }

    public void backpropagation(Matrix input, Matrix target) {
        // Calculate error gradients
        for (int i = hiddenLayers; i >= 0; i--) {
            if (i == hiddenLayers) {
                Matrix error = target.subtract(outputs[i]);
                gradients[i] = activFunc.applyDerivative(outputs[i]);
                gradients[i].elementMultiply(error);
            } else {
                Matrix weightedErrors = gradients[i + 1].multiply(weights[i + 1].transpose());
                gradients[i] = activFunc.applyDerivative(outputs[i]);
                gradients[i].elementMultiply(weightedErrors);
            }
        }
        // Calculate weight corrections & update weights
        Matrix delta;
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == 0) {
                delta = input.transpose().multiply(gradients[i]);
            } else {
                delta = outputs[i - 1].transpose().multiply(gradients[i]);
            }
            delta.scale(lrate);
            weights[i].sum(delta);
        }
        // Calculate bias corrections & update biases
        for (int i = 0; i < hiddenLayers + 1; i++) {
            gradients[i].scale(lrate);
            biases[i].sum(gradients[i]);
        }
    }
}
