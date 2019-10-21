package sensors;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import resource.ResourceFunctions;

public abstract class RotationInputter implements PIDSource {

	private double mOffsetDegrees;
	private boolean mAdd180;

	/**
	 * @param pOffsetDegrees offset of the encoder in degrees.
	 */
	public RotationInputter(double pOffsetDegrees) {
		mOffsetDegrees = pOffsetDegrees;
	}

	protected abstract double getRawAngleDegrees();

	public double getAngleDegreesNoAdd180() {
		double angle = getRawAngleDegrees();
		return ResourceFunctions.putAngleInRange(angle);
	}

	public double getAngleDegrees() {
		double angle = getRawAngleDegrees();
		angle -= mOffsetDegrees;
		if (mAdd180) {
			angle += 180; // when we reverse the direction, update the angle
		}
		angle = ResourceFunctions.putAngleInRange(angle);

		return angle;
	}

	public void setAdd180(boolean add) {
		mAdd180 = add;
	}

	public boolean getAdd180() {
		return mAdd180;
	}

	public void setOffset(double offset) {
		mOffsetDegrees = offset;
	}

	public double getOffset() {
		return mOffsetDegrees;
	}

	// Methods required by PIDSource interface
	public double pidGet() {
		return getAngleDegrees();
	}

	public void setPIDSourceType(PIDSourceType pidSource) {

	}

	public PIDSourceType getPIDSourceType() {
		return null;
	}

}
