package robotcode.pid;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * Utility class to capture the output of PID loops from WPI-based PID Controller classes
 * 
 * @author Alex Cohen
 */
public class GenericPIDOutput implements PIDOutput {
	private double mVal;

	@Override
	public void pidWrite(double pOutput) {
		mVal = pOutput;
	}

	public double getVal() {
		return mVal;
	}

}
