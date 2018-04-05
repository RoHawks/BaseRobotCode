package autonomous.rountines;

import java.util.ArrayList;

import autonomous.AutonomousRoutine;
import autonomous.commands.AutonomousCommand;
import autonomous.commands.StopCommand;
import autonomous.commands.StraightLineDriveNoElevatorCommand;
import robotcode.driving.DriveTrain;

public class DriveForwardNoElevatorRoutine implements AutonomousRoutine {

	private DriveTrain mDriveTrain;

	private final double WHEEL_ANGLE = 0;
	private final double ACCELERATE_TO_SPEED = 0.5;
	private final long ACCELERATION_TIME = 1500;
	private final long DRIVE_FULL_SPEED_TIME = 2000;
	private final long DECELERATION_TIME = 1000;	

	public DriveForwardNoElevatorRoutine(DriveTrain pDriveTrain) {
		mDriveTrain = pDriveTrain;
	}

	@Override
	public ArrayList<AutonomousCommand> GetAutonomousCommands() {
		// TODO Auto-generated method stub
		ArrayList<AutonomousCommand> returnValue = new ArrayList<AutonomousCommand>();

		//Accelerate
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				WHEEL_ANGLE,
				0.0,
				ACCELERATE_TO_SPEED,
				ACCELERATION_TIME));

		//Drive full speed
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				WHEEL_ANGLE,
				ACCELERATE_TO_SPEED,
				ACCELERATE_TO_SPEED,
				DRIVE_FULL_SPEED_TIME));

		//Slow down then stop
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				WHEEL_ANGLE,
				ACCELERATE_TO_SPEED,
				0.0,
				DECELERATION_TIME));

		returnValue.add(new StopCommand(mDriveTrain));

		return returnValue;
	}

}
