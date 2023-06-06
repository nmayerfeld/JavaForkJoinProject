package edu.yu.introtoalgs;

/** Implements PrimeCalculator using a "naive" serial computation.
 *
 * Students may not change the constructor signature or add any other
 * constructor!
 *
 * @author Avraham Leff
 */

public class SerialPrimes implements PrimeCalculator {

    /** Constructor
     *
     */
    public SerialPrimes() {
        // your code (if any) goes here
    }

    @Override
    public int nPrimesInRange(final long start, final long end) {
        int numPrimes=0;
        if(start<=1||start>Long.MAX_VALUE||end<=start){
            throw new IllegalArgumentException("inputs are problematic");
        }
        else{
            long myStart=start;
            if(start==Long.valueOf(2)){
                myStart++;
                numPrimes++;
            }
            else if(start%2==0){
                myStart+=Long.valueOf(1);
            }
            boolean foundFactor=false;
            for(long i=myStart;i<end;i+=Long.valueOf(2)){
                foundFactor=false;
                long sqrt=((long) Math.sqrt(i));
                for(long j = Long.valueOf(3); j<sqrt&&!foundFactor; j+=Long.valueOf(2)){
                    if(i%j==0){
                        foundFactor=true;
                    }
                }
                if(!foundFactor) {
                    numPrimes++;
                }
            }
        }
        if(numPrimes>0){
            return numPrimes;
        }
        return -1;
    }
}
