package autonomous.sequence;

import autonomous.AutonomousSequence;
import constants.AutoConstants;
import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;
import robotcode.systems.Elevator;

public class DriveForwardSequence implements AutonomousSequence{
	
	private DriveTrain mDriveTrain;
	private Elevator mElevator;
	private long autoTimeStart = System.currentTimeMillis();

	public DriveForwardSequence (DriveTrain pDriveTrain, Elevator pElevator) {
		mDriveTrain = pDriveTrain;
		mElevator = pElevator;
	}
	
	@Override
	public boolean run() {
		mElevator.setSwitch();
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
