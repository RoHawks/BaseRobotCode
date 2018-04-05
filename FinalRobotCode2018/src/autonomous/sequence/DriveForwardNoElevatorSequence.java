package autonomous.sequence;

import autonomous.AutonomousSequence;
import constants.AutoConstants;
import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;

public class DriveForwardNoElevatorSequence implements AutonomousSequence{
	
	private DriveTrain mDriveTrain;
	private long autoTimeStart = System.currentTimeMillis();

	public DriveForwardNoElevatorSequence (DriveTrain pDriveTrain) {
		mDriveTrain = pDriveTrain;
	}
	
	@Override
	public boolean run() {
		if (System.currentTimeMillis() - autoTimeStart < AutoConstants.Mobility.DRIVE_TIME) {
			mDriveTrain.enactMovement(0, 0, LinearVelocity.NORMAL, RotationalVelocity.NONE, AutoConstants.Mobility.DRIVE_SPEED);
			return false;
		} 
		else {
			mDriveTrain.enactMovement(0, 0, LinearVelocity.NONE, RotationalVelocity.NONE, 0);
			return true;
		}
	}

}
