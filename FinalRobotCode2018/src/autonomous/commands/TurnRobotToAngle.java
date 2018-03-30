package autonomous.commands;

import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;

public class TurnRobotToAngle extends BaseAutonomousCommand
{
	private DriveTrain mDriveTrain;
	private double mAngle;
	
	public TurnRobotToAngle(DriveTrain pDriveTrain, double pAngle) {
		mDriveTrain = pDriveTrain;
		mAngle = pAngle;
	}
	
	@Override
	public boolean RunCommand() {
		mDriveTrain.enactMovement(mAngle, mDriveTrain.getDesiredRobotVel().getAngle(), LinearVelocity.NONE, RotationalVelocity.POV, 0.0);
		return false;
	}

}
