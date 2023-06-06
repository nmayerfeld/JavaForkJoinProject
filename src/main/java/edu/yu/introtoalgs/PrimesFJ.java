package edu.yu.introtoalgs;

/* Implements PrimeCalculator interface with Java's Fork/Join parallelism
 * framework.  The implementation must determine what threshold value to use,
 * but should favor thresholds that produce good results for "end" values of
 * (at least) hundreds of millions).
 *
 * Students may not change the constructor signature or add any other
 * constructor!
 *
 * @author Avraham Leff
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class PrimesFJ implements PrimeCalculator {
    public static class DriverClass {
        public static Integer computePrimes(final long start, final long end) {
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

    /** Constructor
     *
     */
    public PrimesFJ() {
        // your code (if any) goes here
    }

    @Override
    public int nPrimesInRange(final long start, final long end) {
        if(start<=1||start>Long.MAX_VALUE||end<=start){
            throw new IllegalArgumentException("inputs are problematic");
        }
        int parallelism = Runtime.getRuntime().availableProcessors()*1;
        int threshold = 3000;
        final ForkJoinPool fjPool = new ForkJoinPool(8);
        ForkJoinTask<Integer> task=new ForkJoinPrimes(threshold,start,end);
        int primes=fjPool.invoke(task);
        if(primes==0){
            primes=-1;
        }
        fjPool.shutdown();
        return primes;
    }
    class ForkJoinPrimes extends RecursiveTask<Integer>{
        private int threshold;
        private long low;
        private long high;
        public ForkJoinPrimes(int threshold,long low, long high){
            this.threshold=threshold;
            this.low=low;
            this.high=high;
        }
        public Integer compute(){
            if(high-low<=threshold){
                //System.out.println("computing");
                return DriverClass.computePrimes(low,high);
            }
            else{
                ForkJoinPrimes leftSide=new ForkJoinPrimes(threshold,low,(low+high)/2);
                ForkJoinPrimes rightSide=new ForkJoinPrimes(threshold,(low+high)/2,high);
                leftSide.fork();
                int r=rightSide.compute();
                int l=leftSide.join();
                //System.out.println("joined");
                int result=r+l;
                return result;
            }
        }
    }
}   // PrimesFJ
