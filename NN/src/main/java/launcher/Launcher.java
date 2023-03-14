package launcher;

import nn.NN;

public class Launcher {

    public static void launchNNBenchmark() {
        NN nn = new NN(200000, 0.2, 0.1, 2, 2, 1, 1, 47, "sigmoid");
        nn.initializeNN();
        nn.runNN();
    }

    public static void main(String[] args) {
        launchNNBenchmark();
    }
}
