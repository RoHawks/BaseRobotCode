package autonomous.rountines;

import java.util.ArrayList;

import autonomous.AutonomousRoutine;
import autonomous.commands.AutonomousCommand;
import autonomous.commands.StopCommand;
import autonomous.commands.StraightLineDriveCommand;
import autonomous.commands.TurnWheelsToAngle;
import robotcode.driving.DriveTrain;
import robotcode.systems.Elevator;

public class DriveForwardRoutine implements AutonomousRoutine {

	private DriveTrain mDriveTrain;
	private Elevator mElevator;

	private final double WHEEL_ANGLE = 0;
	private final double WHEEL_TOLERANCE = 10;
	private final double ACCELERATE_TO_SPEED = 0.3;
	private final long ACCELERATION_TIME = 1000;
	private final long DRIVE_FULL_SPEED_TIME = 1000;
	private final long DECELERATION_TIME = 1000;	

	public DriveForwardRoutine(DriveTrain pDriveTrain, Elevator pElevator) {
		mDriveTrain = pDriveTrain;
		mElevator = pElevator;
	}

	@Override
	public ArrayList<AutonomousCommand> GetAutonomousCommands() {
		// TODO Auto-generated method stub
		ArrayList<AutonomousCommand> returnValue = new ArrayList<AutonomousCommand>();

		//Accelerate
		returnValue.add(new StraightLineDriveCommand(
				mDriveTrain,
				mElevator,
				WHEEL_ANGLE,
				0.0,
				ACCELERATE_TO_SPEED,
				ACCELERATION_TIME));

		//Drive full speed
		returnValue.add(new StraightLineDriveCommand(
				mDriveTrain,
				mElevator,
				WHEEL_ANGLE,
				ACCELERATE_TO_SPEED,
				ACCELERATE_TO_SPEED,
				DRIVE_FULL_SPEED_TIME));

		//Slow down then stop
		returnValue.add(new StraightLineDriveCommand(
				mDriveTrain,
				mElevator,
				WHEEL_ANGLE,
				ACCELERATE_TO_SPEED,
				0.0,
				DECELERATION_TIME));

		returnValue.add(new StopCommand(mDriveTrain));

		return returnValue;
	}

}
