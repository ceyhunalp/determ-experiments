package activationfunction;

public class ActivationHelper {

    public static final String SIGMOID = "sigmoid";
    public static final String TANH = "tanh";
    public static final String ELU = "elu";
    public static final String RELU = "relu";

    public static ActivationFunction getFunction(String fname) {
        ActivationFunction func;
        switch (fname) {
            case SIGMOID:
                func = new SigmoidActivation();
                break;
            case TANH:
                func = new TanhActivation();
                break;
            case ELU:
                func = new ELUActivation();
                break;
            case RELU:
                func = new RELUActivation();
                break;
            default:
                throw new IllegalArgumentException("Unexpected value in " +
                        "switch: " + fname);
        }
        return func;
    }
}
