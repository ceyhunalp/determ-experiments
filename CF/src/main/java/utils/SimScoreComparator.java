package utils;

import java.util.Comparator;

public class SimScoreComparator implements Comparator<SimScore> {

    @Override
    public int compare(SimScore o1, SimScore o2) {
        return Double.compare(o1.score, o2.score);
    }
}
