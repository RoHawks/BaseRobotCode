package resource;

public class ResourceFunctions {

	public static double tickToAngle(int tick) {
		return (double) tick * 360.0 / 4096.0;
	}

	public static int angleToTick(double angle) {
		return (int) Math.round(angle * 4096.0 / 360.0);
	}

	public static double TickToCenti(double tick) {
		return tick * 12 / 4096;
	}

	public static double CentiToTick(double centi) {
		return centi * 4096 / 12;
	}

	public static double speedToPercent(double speed) {
		return speed / 1023;
	}

	/**
	 * gets angle between 0 and 360
	 * 
	 * @param angle
	 *            angle
	 * @return 0 <= angle < 360
	 */
	public static double putAngleInRange(double angle) {
		return (angle + 36000) % 360;
	}

	/**
	 * 
	 * returns the difference between two angles between -180 and 180
	 * 
	 * @param angle1
	 *            first angle
	 * @param angle2
	 *            second angle
	 * @return difference
	 */
	public static double continuousAngleDif(double angle1, double angle2) { // target - current?
		double dif = putAngleInRange(angle1) - putAngleInRange(angle2);
		dif = putAngleInRange(dif);
		if (dif > 180)
			dif = dif - 360;
		return dif;
	}

	/**
	 * if two doubles are within 0.001
	 * 
	 * @param a
	 *            first double
	 * @param b
	 *            second double
	 * @return almost equal to
	 */
	public static boolean equals(double a, double b) {
		return (Math.abs(a - b) < 0.001);
	}

	/**
	 * ensures a number is between min and max
	 * 
	 * @param val
	 *            number
	 * @param min
	 *            lowest number should be
	 * @param max
	 *            highest number should be
	 * @return number between the min and max
	 */
	public static double PutNumInAbsoluteRange(double val, double min, double max) {

		if (val > max) {
			return max;
		} else if (val < min) {
			return min;
		} else {
			return val;
		}
	}
}