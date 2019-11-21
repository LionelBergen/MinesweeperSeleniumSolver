package minesweeper.solver.utility;

public final class Utility {
	private Utility() { }
	
	/**
	 * Returns a whole random number between the two numbers passed
	 * 
	 * @param lowerBound inclusive
	 * @param higherBound inclusive
	 * @return Random number between the 2 (includsive)
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
	
	private static long gcm(long a, long b) {
	    return b == 0 ? a : gcm(b, a % b);
	}
}
