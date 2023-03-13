package activationfunction;

public class ActivationHelper {

    private static final String SIGMOID = "sigmoid";
    private static final String TANH = "tanh";
    private static final String ELU = "elu";

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
            default:
                throw new IllegalArgumentException("Unexpected value in " +
                        "switch: " + fname);
        }
        return func;
    }
}
