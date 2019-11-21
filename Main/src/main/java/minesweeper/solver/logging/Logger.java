package minesweeper.solver.logging;

// Logger that doubles as a timer for timing events
public final class Logger {
	private static Long startTimeMilli;
	private static Long currentTimeMilli;
	
	private Logger() {}
	
	public static void setCurrentTime() {
		currentTimeMilli = getCurrentTimeMillis();
		
		if (startTimeMilli == null) {
			startTimeMilli = currentTimeMilli;
		}
	}
	
	public static void logMessage(String message) {
		System.out.println(message);
	}
	
	public static void logTimeTook(String action) {
		long beginningTime = currentTimeMilli.longValue();
		setCurrentTime();
		System.out.println(action + " took: " + getSecondsDisplayFromMillis(currentTimeMilli - beginningTime) + " seconds");
	}
	
	private static String getSecondsDisplayFromMillis(Long milliseconds) {
		return String.format("%.3f", milliseconds.longValue() / 1000.0f);
	}
	
	private static Long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
}
