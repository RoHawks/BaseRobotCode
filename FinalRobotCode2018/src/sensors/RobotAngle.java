 package sensors;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import simulator.gyro.GyroInterface;

public class RobotAngle extends RotationInputter implements PIDSource {
	GyroInterface mNavX;
	boolean mReversed;
	PIDSourceType mPIDSourceType = PIDSourceType.kDisplacement;
	
	public RobotAngle (GyroInterface pNavX, boolean pReversed, double pOffset) {
		super (pOffset);
		mReversed = pReversed;
		mNavX = pNavX;
	}
	
	public double getRawAngleDegrees() {
		return mNavX.getAngle();
	}
	
	public double getAngularVelocity() {
		return Math.toDegrees ((mReversed ? -1 : 1) * mNavX.getRate());
	}

	public double pidGet() {
		return getAngleDegrees();
	}
	
	public PIDSourceType getPIDSourceType(){
		return mPIDSourceType;
	}
	
	public void setPIDSourceType (PIDSourceType pPIDSourceType){
		
	}
	
	public void reset() {
		mNavX.reset();
	}
}
