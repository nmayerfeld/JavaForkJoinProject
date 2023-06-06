package edu.yu.introtoalgs;

/* Implements PrimeCalculator interface by using exactly two threads to
 * partition the range of primes between them.  Each thread uses the "naive"
 * SerialPrimes algorithm to solve its part of the problem.
 *
 * Students may not change the constructor signature or add any other
 * constructor!
 *
 * @author Avraham Leff
 */

public class TwoThreadPrimes implements PrimeCalculator{
    public class PrimeThread implements Runnable {
        private long low;
        private long high;
        int numPrimes;
        SerialPrimes sp;
        PrimeThread(long low,long high){
            this.low=low;
            this.high=high;
            this.sp=new SerialPrimes();
        }
        public void run(){
            numPrimes=sp.nPrimesInRange(low,high);
        }
    }

    private int numPrimes;
    /** Constructor
     *
     */
    public TwoThreadPrimes() {
        numPrimes=0;
    }

    @Override
    public int nPrimesInRange(final long start, final long end) {
        // your code (if any) goes here
        if(start<=1||start>Long.MAX_VALUE||end<=start){
            throw new IllegalArgumentException("inputs are problematic");
        }
        PrimeThread p1=new PrimeThread(start,end/2);
        //System.out.println("created one runnable from: "+start+" to: "+end/2);
        PrimeThread p2=new PrimeThread(end/2,end);
        //System.out.println("created second runnable from: "+end/2+" to: "+end);
        Thread t1=new Thread(p1);
        Thread t2=new Thread(p2);
        t1.start();
        t2.start();
        try {
            t1.join();
            this.numPrimes+=p1.numPrimes;
            t2.join();
            this.numPrimes+=p2.numPrimes;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(numPrimes>0){
            return this.numPrimes;
        }
        return -1;
    }
}
