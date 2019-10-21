package sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSource;
import resource.ResourceFunctions;

public class RobotAngle extends RotationInputter implements PIDSource {
	AHRS mNavX; //gyro navx
	boolean mReversed;

	public RobotAngle(AHRS pNavX, boolean pReversed, double pOffset) {
		super(pOffset);
		//mNavX.setAngleAdjustment(adjustment);
		mReversed = pReversed;
		mNavX = pNavX;
	}

	public double getRawAngleDegrees() {
		return mNavX.getAngle();
	}

	public double getAngleDegrees(){
		return ResourceFunctions.putAngleInRange(getRawAngleDegrees());
	}

	public double getAngularVelocity() {
		return Math.toDegrees((mReversed ? -1 : 1) * mNavX.getRate());
	}

	public double pidGet() {
		return getAngleDegrees();
	}

	public void reset() {
		mNavX.reset();
	}

}
