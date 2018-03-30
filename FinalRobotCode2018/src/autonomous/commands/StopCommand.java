package autonomous.commands;

import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;

public class StopCommand extends BaseAutonomousCommand {

	private DriveTrain mDriveTrain;
	
	public StopCommand (DriveTrain pDriveTrain) {
		mDriveTrain = pDriveTrain;
	}


	@Override
	public boolean RunCommand() {
		mDriveTrain.enactMovement(mDriveTrain.getRobotAngle(), mDriveTrain.getDesiredRobotVel().getAngle(), LinearVelocity.NONE, RotationalVelocity.NONE, 0.0);
		return true;
	}

}
