package sensors;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Base class for relative encoders. If ever in doubt about the difference
 * between relative and absolute encoders, ask Adam! He loves explaining the
 * difference in great depth and technical detail!
 * 
 * <p>
 * For our purposes, relative encoders in their base form are more useful for
 * velocity measurements, since they typically have a greater rate of update
 * than absolute encoders. As such, this class only contains methods for
 * velocity measurements.
 * 
 * <p>
 * However, some mechanisms, such as the elevator in 2018 and leadscrew in 2019,
 * required using relative encoders as absolute encoders by zeroing them at the
 * beginning of the game at a reference point and treating them as absolute. In
 * order to do this, you'd have to write a custom encoder class. Examples can be
 * found in LeadscrewEncoder and ElevEncoder.
 */
public abstract class RelativeEncoder implements PIDSource {
	private double mTicksToRPS;

	/**
	 * @param pTicksToRPS conversion factor from ticks (native units of sensor) per
	 *                    100 ms to revolutions per second.
	 */
	public RelativeEncoder(double pTicksToRPS) {
		mTicksToRPS = pTicksToRPS;
	}

	/**
	 * @return Velocity of the encoder in units of ticks per 100 ms.
	 */
	public abstract double getRawTicksPerSecond();

	/**
	 * @return Velocity of the encoder in revolutions per second.
	 */
	public double getRPS() {
		return getRawTicksPerSecond() * mTicksToRPS;
	}

	// Methods required by PIDSource interface
	public double pidGet() {
		return getRPS();
	}

	public void setPIDSourceType(PIDSourceType pidSource) {
		
	}

	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kRate;
	}
}
