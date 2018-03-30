package autonomous.commands;

import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;
import robotcode.systems.Elevator;

public class StraightLineDriveCommand extends BaseAutonomousCommand {

	private DriveTrain mDriveTrain;
	private Elevator mElevator;
	private double mAngle;
	private double mInitialVelocity;
	private double mFinalVelocity;
	private long mTotalMilliseconds;

	public StraightLineDriveCommand(DriveTrain pDriveTrain, Elevator pElevator, double pAngle, double pInitialVelocity,
			double pFinalVelocity, long pTotalMilliseconds) {
		mDriveTrain = pDriveTrain;
		mElevator = pElevator;
		mAngle = pAngle;
		mInitialVelocity = pInitialVelocity;
		mFinalVelocity = pFinalVelocity;
		mTotalMilliseconds = pTotalMilliseconds;
	}

	@Override
	public boolean RunCommand() {
		mElevator.setSwitch();

		// compute what percent into the command we are:
		double percentComplete = GetMillisecondsSinceStart() / mTotalMilliseconds;
		double totalSpeedRange = mFinalVelocity - mInitialVelocity; // positive in acceleration, negative in deccel, 0
																	// in constant
		double portionIntoSpeedRange = totalSpeedRange * percentComplete;
		double speed = mInitialVelocity + portionIntoSpeedRange;

		// I don't think this works for reverse, but probably not a realistic use case

		boolean isThisCommandDone = GetMillisecondsSinceStart() > mTotalMilliseconds;

		// this should only happen for a split second, perhaps, but don't want to throw
		// it in reverse, for example
		double boundedSpeed = isThisCommandDone ? mFinalVelocity : speed;

		mDriveTrain.enactMovement(mDriveTrain.getRobotAngle(), mAngle, LinearVelocity.NORMAL, RotationalVelocity.NONE, boundedSpeed);

		return isThisCommandDone;

	}

}
