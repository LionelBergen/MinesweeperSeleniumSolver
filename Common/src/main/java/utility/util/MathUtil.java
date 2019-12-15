package utility.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apache.commons.math3.util.CombinatoricsUtils;

public class MathUtil {
	/**
	 * Returns a whole random number between the two numbers passed
	 * 
	 * @param lowerBound inclusive
	 * @param higherBound inclusive
	 * @return Random number between the 2 (inclusive)
	 */
	public static int getRandomNumber(int lowerBound, int higherBound) {
		higherBound = higherBound + 1;
		return (int) (Math.random() * (higherBound - lowerBound)) + (lowerBound);
	}
	
	public static float asFloat(long a, long b) {
		return (float) a / (float) b;
	}

	/**
	 * Returns the two numbers as the lowest fraction.
	 * E.G: asFraction(25, 50) returns as "1/2"
	 */
	public static String asFraction(long a, long b) {
	    long gcm = gcm(a, b);
	    return (a / gcm) + "/" + (b / gcm);
	}
	
	public static long nCr(int n, int r) {
	    return CombinatoricsUtils.binomialCoefficient(n, r);
	}
	
	public static BigDecimal divide(BigDecimal a, BigDecimal b) {
		MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
		
		return a.divide(b, mc);
	}
	
	public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
		MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
		
		return a.multiply(b, mc);
	}
	
	// TODO: find a library for calculating the Binomial value of two BigIntegers. This was stolen from: https://github.com/keedio/bigdecimal-math/blob/master/src/main/java/org/nevec/rjm/BigIntegerMath.java
	/**
     * @author Richard J. Mathar
     */
     public static BigDecimal binomial(final BigDecimal n, final BigDecimal k)
     {
             /* binomial(n,0) =1 
             */
             if ( k.compareTo(BigDecimal.ZERO) == 0 ) 
                     return(BigDecimal.ONE);

             BigDecimal bin = n;

             /* the following version first calculates n(n-1)(n-2)..(n-k+1)
             * in the first loop, and divides this product through k(k-1)(k-2)....2
             * in the second loop. This is rather slow and replaced by a faster version
             * below
             * BigInteger n2 = bin ;
             * BigInteger i= k.subtract(BigInteger.ONE) ;
             * for( ; i.compareTo(BigInteger.ONE) >= 0 ; i = i.subtract(BigInteger.ONE) )
             *       bin = bin.multiply(n2.subtract(i)) ;
             * i= BigInteger.valueOf(k) ;
             * for( ; i.compareTo(BigInteger.ONE) == 1 ; i = i.subtract(BigInteger.ONE) )
             *       bin = bin.divide(i) ;
             */

             /* calculate n then n(n-1)/2 then n(n-1)(n-2)(2*3) etc up to n(n-1)..(n-k+1)/(2*3*..k)
             * This is roughly the best way to keep the individual intermediate products small
             * and in the integer domain. First replace C(n,k) by C(n,n-k) if n-k<k.
             */
             BigDecimal truek = k ;
             if ( n.subtract(k).compareTo(k) < 0 )
                     truek = n.subtract(k) ;

             /* Calculate C(num,truek) where num=n and truek is the smaller of n-k and k.
             * Have already initialized bin=n=C(n,1) above. Start definining the factorial
             * in the denominator, named fden
             */
             BigDecimal i = BigDecimal.valueOf(2L) ;
             BigDecimal num = n ;
             /* a for-loop   (i=2;i<= truek;i++)
             */
             for( ; i.compareTo(truek) <= 0 ; i = i.add(BigDecimal.ONE) )
             {
                     /* num = n-i+1 after this operation
                     */
                     num = num.subtract(BigDecimal.ONE) ;
                     /* multiply by (n-i+1)/i
                     */
                     bin = (bin.multiply(num)).divide(i) ;
             }
             return ( bin) ;
     }
	
	private static long gcm(long a, long b) {
	    return b == 0 ? a : gcm(b, a % b);
	}
}
