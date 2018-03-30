package autonomous.rountines;

import java.util.ArrayList;

import autonomous.AutonomousRoutine;
import autonomous.PlateAssignmentReader;
import autonomous.commands.AutonomousCommand;
import autonomous.commands.ScoreCommand;
import autonomous.commands.StopCommand;
import autonomous.commands.StraightLineDriveCommand;
import autonomous.commands.TurnWheelsToAngle;
import robotcode.driving.DriveTrain;
import robotcode.systems.Elevator;
import robotcode.systems.Grabber;

public class SwitchRoutine implements AutonomousRoutine
{//ATS Tune
	private final double WHEEL_ANGLE = 45;
	private final double ACCELERATE_TO_SPEED = 0.6;
	private final long ACCELERATION_TIME = 1000;
	private final long DRIVE_FULL_SPEED_TIME = 1000;
	private final long DECELERATION_TIME = 1000;	
	private final double DRIVE_BACKWARDS_SPEED = 0.3;//Going back by wheel angle instead
	private final long DRIVE_BACKWARDS_TIME = 2000;
	
	
	private DriveTrain mDriveTrain;
	private Elevator mElevator;
	
	private Grabber mGrabber;
	
	public SwitchRoutine(DriveTrain pDriveTrain, Elevator pElevator, Grabber pGrabber)
	{
		mDriveTrain = pDriveTrain;
		mElevator = pElevator;
		mGrabber = pGrabber;
	}
	
	
	@Override
	public ArrayList<AutonomousCommand> GetAutonomousCommands()
	{
		char nearbySwitchSide = PlateAssignmentReader.GetNearSwitchSide();
		
		double wheelAngle = nearbySwitchSide == 'R' ? 1.0 : -1.0 * WHEEL_ANGLE;
		
		ArrayList<AutonomousCommand> returnValue = new ArrayList<AutonomousCommand>();
		
		//point wheels towards correct plate
		returnValue.add(new TurnWheelsToAngle(
				mDriveTrain, 
				wheelAngle));
		
		//Accelerate
		returnValue.add(new StraightLineDriveCommand(
				mDriveTrain,
				mElevator,
				wheelAngle,
				0.0,
				ACCELERATE_TO_SPEED,
				ACCELERATION_TIME));
				
		//Drive full speed
				returnValue.add(new StraightLineDriveCommand(
						mDriveTrain,
						mElevator,
						wheelAngle,
						ACCELERATE_TO_SPEED,
						ACCELERATE_TO_SPEED,
						DRIVE_FULL_SPEED_TIME));
				
		//Slow down then stop
				returnValue.add(new StraightLineDriveCommand(
						mDriveTrain,
						mElevator,
						wheelAngle,
						ACCELERATE_TO_SPEED,
						0.0,
						DECELERATION_TIME));
				
				returnValue.add(new StopCommand(mDriveTrain));
				
		//next, deploy the grabber!
				returnValue.add(new ScoreCommand(mGrabber));
				
		//drive backwards a bit
				returnValue.add(new StraightLineDriveCommand(
						mDriveTrain,
						mElevator,
						wheelAngle + 180,
						0,
						DRIVE_BACKWARDS_SPEED,
						DRIVE_BACKWARDS_TIME));
				
				returnValue.add(new StopCommand(mDriveTrain));
		
				
		return returnValue;
		
	}
	

}
