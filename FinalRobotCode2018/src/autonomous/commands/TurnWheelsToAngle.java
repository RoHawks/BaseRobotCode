package autonomous.commands;

import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;

public class TurnWheelsToAngle extends BaseAutonomousCommand {
	private DriveTrain mDriveTrain;
	private double mAngle;

	public TurnWheelsToAngle(DriveTrain pDriveTrain, double pAngle) {
		mDriveTrain = pDriveTrain;
		mAngle = pAngle;
	}

	@Override
	public boolean RunCommand() {
		mDriveTrain.enactMovement(mDriveTrain.getRobotAngle(), mAngle, LinearVelocity.ANGLE_ONLY,
				RotationalVelocity.NONE, 0);
		return mDriveTrain.AllWheelsInRange(mAngle);
	}// TZ changed to 0

}
