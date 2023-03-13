package nn;

import activationfunction.ActivationFunction;
import activationfunction.ActivationHelper;
import utils.Matrix;

import java.util.Random;

public class NN {

    private final int epochs;
    private final double rate;

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

    public NN(int epochs, int rate, int inputNodes, int hiddenNodes,
              int outputNodes, int hiddenLayers, long seed, String funcName) {
        this.epochs = epochs;
        this.rate = rate;
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
                weights[i] = new Matrix(hiddenNodes, inputNodes, lower,
                        upper, rand);
            } else if (i == hiddenLayers) {
                denom = StrictMath.sqrt(2 * hiddenNodes);
                upper = nom / denom;
                lower = upper * -1.0;
                weights[i] = new Matrix(hiddenNodes, hiddenNodes, lower,
                        upper, rand);
            } else {
                denom = StrictMath.sqrt(outputNodes + hiddenNodes);
                upper = nom / denom;
                lower = upper * -1.0;
                weights[i] = new Matrix(outputNodes, hiddenNodes, lower,
                        upper, rand);
            }
        }
        // Initialize biases
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == hiddenLayers) {
                // Biases for the output layer
                biases[i] = new Matrix(outputNodes, 1, 0, 1, rand);
            } else {
                // Biases for hidden layers
                biases[i] = new Matrix(hiddenNodes, 1, 0, 1, rand);
            }
        }
        // Initialize activation function
        activFunc = ActivationHelper.getFunction(funcName);
    }

    public void runNN() {
        feedforward(null);
        backpropagation(null, null);
    }

    public void feedforward(Matrix input) {
        for (int i = 0; i < hiddenLayers + 1; i++) {
            if (i == 0) {
                outputs[i] = weights[i].multiply(input);
            } else {
                outputs[i] = weights[i].multiply(outputs[i - 1]);
            }
            outputs[i].sum(biases[i]);
//            runActivationFunction(outputs[i]);
            activFunc.applyFunction(outputs[i]);
        }
    }

    public void backpropagation(Matrix input, Matrix target) {
        // Calculate error gradients
        for (int i = hiddenLayers; i >= 0; i--) {
            if (i == hiddenLayers) {
                Matrix error = target.subtract(outputs[i]);
//                gradients[i] = runDerivationFunction(outputs[i]);
                activFunc.applyDerivative(outputs[i]);
                gradients[i].elementMultiply(error);
            } else {
                Matrix weightedErrors = weights[i + 1].transpose().multiply(outputs[i + 1]);
//                gradients[i] = runDerivationFunction(outputs[i]);
                activFunc.applyDerivative(outputs[i]);
                gradients[i].elementMultiply(weightedErrors);
            }
        }
        // Calculate weight corrections & update weights
        Matrix delta;
        for (int i = 0; i < hiddenLayers; i++) {
            if (i == 0) {
                delta = gradients[i].multiply(input.transpose());
            } else {
                delta = gradients[i].multiply(outputs[i - 1].transpose());
            }
            delta.scale(rate);
            weights[i].sum(delta);
        }
        // Calculate bias corrections & update biases
        for (int i = 0; i < hiddenLayers + 1; i++) {
            delta = gradients[i].transpose();
            delta.scale(rate);
            biases[i].sum(delta);
        }
    }

//    private void runActivationFunction(Matrix m) {
//        double[][] data = m.getData();
//        for (int i = 0; i < m.getRowDimension(); i++) {
//            for (int j = 0; j < m.getColumnDimension(); j++) {
//                data[i][j] = 1.0 / (1.0 + Math.exp(data[i][j] * -1.0));
//            }
//        }
//    }
//
//    private Matrix runDerivationFunction(Matrix m) {
//        double val, res;
//        int rowDim = m.getRowDimension();
//        int colDim = m.getColumnDimension();
//        Matrix result = new Matrix(rowDim, colDim);
//        for (int i = 0; i < rowDim; i++) {
//            for (int j = 0; j < colDim; j++) {
//                val = m.getData(i, j);
//                res = val * (1 - val);
//                result.setData(i, j, res);
//            }
//        }
//        return result;
//    }

}
