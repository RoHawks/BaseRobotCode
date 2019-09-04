package resource;

/**
 * Contains methods related to math and of general use for FRC-specific
 * applications.
 * 
 * @author Alex Cohen
 * @author Tal Zussman
 */
public class ResourceFunctions {

	/**
	 * {@code double} value equal to 360.0 degrees/4096.0 ticks.
	 */
	public static final double TICK_TO_ANGLE_FACTOR = 0.087890625;

	/**
	 * {@code double} value approximately equal to 4096.0 ticks/360.0 degrees.
	 */
	public static final double ANGLE_TO_TICK_FACTOR = 11.37777777778;

	/**
	 * {@code int} value representing CTRE maximal positive motor output. Values
	 * range from -1023 to 1023.
	 */
	public static final int SPEED_FACTOR = 1023;

	/**
	 * {@code double} value equal to (1000 ms / 1 s) * (1 rev / 4096 ticks). Talons
	 * give velocity in units of ticks / 100 ms.
	 */
	public static final double TALON_NATIVE_SPEED_TO_RPS_FACTOR = 0.244140625;

	/**
	 * {@code double} value for threshold of comparison for equality of two doubles.
	 */
	public static final double EPSILON = 1E-3;

	/**
	 * Converts from CTRE MagEncoder tick values to angles.
	 * 
	 * @param pTick value to convert in ticks
	 * @return converted value in angles.
	 */
	public static double tickToAngle(int pTick) {
		return pTick * TICK_TO_ANGLE_FACTOR;
	}

	/**
	 * Converts from angle to CTRE MagEncoder tick values.
	 * 
	 * @param pAngle value to convert in degrees
	 * @return converted value in ticks.
	 */
	public static int angleToTick(double pAngle) {
		return (int) Math.round(pAngle * ANGLE_TO_TICK_FACTOR);
	}

	/**
	 * Converts Talon motor output units to a percent.
	 * 
	 * @param speed value in motor output units (from -1023 to +1023)
	 * @return percent value (from -1.0 to +1.0)
	 */
	public static double speedToPercent(double speed) {
		return speed / SPEED_FACTOR;
	}

	/**
	 * Puts angle between 0 and 360 degrees.
	 * 
	 * @param angle angle in degrees
	 * @return 0 <= angle < 360
	 */
	public static double putAngleInRange(double angle) {
		/*
		 * The Java % operator gives the remainder, not the modulus, so x % n returns
		 * negative values for negative x. As such, we want to take the remainder once,
		 * then add 360 to force the argument to become positive, then take the
		 * remainder again to get the proper value.
		 */
		return ((angle % 360) + 360) % 360;

		/*
		 * TODO delete this comment if above implementation works. Previous
		 * implementation: (angle + 36000) % 360; Drawbacks of previous implementation:
		 * if angle is less than -36000, value is still neg
		 */
	}

	/**
	 * Returns the difference between two angles between -180 and 180. If using for
	 * PID to find the error, target angle minus current angle.
	 * 
	 * @param angle1 First angle. For error purposes, target angle.
	 * @param angle2 Second angle. For error purposes, current angle.
	 * @return the continuous angle difference
	 */
	public static double continuousAngleDif(double angle1, double angle2) {
		double dif = putAngleInRange(angle1) - putAngleInRange(angle2);
		// Puts angles in same reference frame of 0 to 360 degrees

		dif = putAngleInRange(dif);
		if (dif > 180) {
			dif = dif - 360;
		}
		return dif;
	}

	/**
	 * Equality of two doubles within a threshold value
	 * 
	 * @param a first double
	 * @param b second double
	 * @return almost equal to
	 */
	public static boolean fuzzyEquals(double a, double b) {
		return (Math.abs(a - b) < EPSILON);
	}

	/**
	 * Ensures a number is between a min and max value
	 * 
	 * @param val number
	 * @param min lowest value the number can be
	 * @param max highest value the number can be
	 * @return number between the min and max
	 */
	public static double clamp(double val, double min, double max) {
		return Math.max(Math.min(val, max), min);
	}

	/**
	 * returns derivative of the cosine function of the form a*cos(b*t + c) at a
	 * time t
	 * 
	 * <p>
	 * b*t is in radians
	 * 
	 * @param a leading coefficient
	 * @param b coefficient of t
	 * @param c initial condition
	 * @param t time
	 * @return derivative of the function at the point t
	 */
	public static double cosineDerivative(double a, double b, double c, double t) {
		return -a * b * Math.sin(b * t + c);
	}

	/**
	 * returns derivative of the sine function of the form a*sin(b*t + c) at a time t
	 * 
	 * <p>
	 * b*t is in radians
	 * 
	 * @param a leading coefficient
	 * @param b coefficient of t
	 * @param c initial condition
	 * @param t time
	 * @return derivative of the function at the point t
	 */
	public static double sineDerivative(double a, double b, double c, double t) {
		return a * b * Math.cos(b * t + c);
	}
}
