package edu.yu.introtoalgs;

import java.math.BigDecimal;

public class Driver {
    /**
     * Computes the sum of squares of the specified
     * array using " vanilla " sequential processing .
     * Must use BigDecimal because of overflow issues
     * <p>
     * NOTE : No attempt is made to be clever . This
     * method levels the playing field since all of
     * the approaches are supposed to use it to do
     * the base computation , varying only the number
     * of threads used to do the computation .
     */
    public static BigDecimal
    computeSequentialSum(final int[] input,
                         final int low, final int high) {
        BigDecimal sequentialSum = new BigDecimal(0);
        for (int i = low; i < high; i++) {
            sequentialSum = sequentialSum.
                    add(new BigDecimal(Math.pow(input[i], 2)));
        }
        return sequentialSum;
    }
}
