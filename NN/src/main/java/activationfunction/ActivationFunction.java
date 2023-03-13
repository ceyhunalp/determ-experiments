package activationfunction;

import utils.Matrix;

public interface ActivationFunction {

    void applyFunction(Matrix m);

    Matrix applyDerivative(Matrix m);
}
