package autonomous.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;

public class StraightLineDriveNoElevatorCommand extends BaseAutonomousCommand {

	private DriveTrain mDriveTrain;
	private double mAngle;
	private double mInitialVelocity;
	private double mFinalVelocity;
	private long mTotalMilliseconds;

	public StraightLineDriveNoElevatorCommand(DriveTrain pDriveTrain, double pAngle, double pInitialVelocity,
			double pFinalVelocity, long pTotalMilliseconds) {
		mDriveTrain = pDriveTrain;
		mAngle = pAngle;
		mInitialVelocity = pInitialVelocity;
		mFinalVelocity = pFinalVelocity;
		mTotalMilliseconds = pTotalMilliseconds;
	}

	@Override
	public boolean RunCommand() {

		// compute what percent into the command we are:
		double percentComplete = ((float) GetMillisecondsSinceStart()) / mTotalMilliseconds;
		SmartDashboard.putNumber("Millis since start", GetMillisecondsSinceStart());
		SmartDashboard.putNumber("Total millis", mTotalMilliseconds);
		double totalSpeedRange = mFinalVelocity - mInitialVelocity; // positive in acceleration, negative in deccel, 0
																	// in constant
		double portionIntoSpeedRange = totalSpeedRange * percentComplete;
		double speed = mInitialVelocity + portionIntoSpeedRange;
		SmartDashboard.putNumber("speed", speed);
		SmartDashboard.putNumber("percent complete", percentComplete);
		SmartDashboard.putNumber("total speed range", totalSpeedRange);

		// I don't think this works for reverse, but probably not a realistic use case

		boolean isThisCommandDone = GetMillisecondsSinceStart() > mTotalMilliseconds;

		// this should only happen for a split second, perhaps, but don't want to throw
		// it in reverse, for example
		double boundedSpeed = isThisCommandDone ? mFinalVelocity : speed;
		SmartDashboard.putNumber("Bounded speed", boundedSpeed);

		mDriveTrain.enactMovement(mDriveTrain.getRobotAngle(), mAngle, LinearVelocity.NORMAL, RotationalVelocity.NONE,
				boundedSpeed);
//		for(int i = 0; i<4; i++) {
//			mDriveTrain.returnWheels()[i].setLinearVelocity(boundedSpeed);
//		}

		return isThisCommandDone;

	}

}
