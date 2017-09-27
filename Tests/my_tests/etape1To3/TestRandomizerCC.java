package my_tests.etape1To3;

import java.util.Random;

public interface TestRandomizerCC {
    // Fix random seed to guarantee reproducibility.
    public final static long SEED = 2017;

    public final static int RANDOM_ITERATIONS = 500;

    public static Random newRandom() {
        return new Random(SEED);
    }
}
