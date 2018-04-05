package autonomous.rountines;

import java.util.ArrayList;

import autonomous.AutonomousRoutine;
import autonomous.PlateAssignmentReader;
import autonomous.commands.AutonomousCommand;
import autonomous.commands.ParameterizedPathCommand;
import autonomous.commands.ScoreCommand;
import autonomous.commands.StopCommand;
import autonomous.commands.StraightLineDriveCommand;
import autonomous.commands.StraightLineDriveNoElevatorCommand;
import autonomous.commands.TurnRobotToAngle;
import autonomous.commands.TurnWheelsToAngle;
import constants.AutoConstants;
import robotcode.driving.DriveTrain;
import robotcode.systems.Elevator;
import robotcode.systems.Grabber;

public class SwitchRoutineNoElevator implements AutonomousRoutine {// ATS Tune
	
	private DriveTrain mDriveTrain;
	
	public SwitchRoutineNoElevator(DriveTrain pDriveTrain) {
		mDriveTrain = pDriveTrain;
	}

	@Override
	public ArrayList<AutonomousCommand> GetAutonomousCommands() {

		double actualAngle;
		long actualDriveTime;
		double goBackAngle;
		double scaleAngle;
		if (autonomous.PlateAssignmentReader.GetNearSwitchSide() == 'L') {
			actualAngle = AutoConstants.SwitchMiddle.LEFT_ANGLE;
			actualDriveTime = AutoConstants.SwitchMiddle.SIDEWAYS_FORWARD_FULL_SPEED_DRIVE_TIME_LEFT;
			goBackAngle = - AutoConstants.SwitchMiddle.GO_BACK_ANGLE;
			scaleAngle = -90.0;
		} 
		else {
			actualAngle = AutoConstants.SwitchMiddle.RIGHT_ANGLE;
			actualDriveTime = AutoConstants.SwitchMiddle.SIDEWAYS_FORWARD_FULL_SPEED_DRIVE_TIME_RIGHT;
			goBackAngle = AutoConstants.SwitchMiddle.GO_BACK_ANGLE;
			scaleAngle = -270.0;
		}


		ArrayList<AutonomousCommand> returnValue = new ArrayList<AutonomousCommand>();

		// point wheels towards correct plate
		returnValue.add(new TurnWheelsToAngle(mDriveTrain, actualAngle));

		//Accelerate
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				actualAngle,
				0.0,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				AutoConstants.SwitchMiddle.FORWARD_ACCELERATION_TIME));
				
		//Drive full speed
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				actualAngle,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				actualDriveTime));

		//Slow down then stop
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				actualAngle,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				0.0,
				AutoConstants.SwitchMiddle.FORWARD_DECELERATION_TIME));

		returnValue.add(new StopCommand(mDriveTrain));

		//drive backwards to align with cube pyramid
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				goBackAngle,
				0,
				AutoConstants.SwitchMiddle.BACKWARDS_SPEED,
				AutoConstants.SwitchMiddle.BACKWARDS_ACCELERATION_TIME));
		
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				goBackAngle,
				AutoConstants.SwitchMiddle.BACKWARDS_SPEED,
				AutoConstants.SwitchMiddle.BACKWARDS_SPEED,
				AutoConstants.SwitchMiddle.BACKWARDS_FULL_SPEED_DRIVE_TIME));
		
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				goBackAngle,
				AutoConstants.SwitchMiddle.BACKWARDS_SPEED,
				0,
				AutoConstants.SwitchMiddle.BACKWARDS_DECELERATION_TIME));
		
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				0,
				0,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				AutoConstants.SwitchMiddle.FORWARD_ACCELERATION_TIME));
		
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				0,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				AutoConstants.SwitchMiddle.STRAIGHT_FORWARD_FULL_SPEED_DRIVE_TIME));
		
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				0,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				0,
				AutoConstants.SwitchMiddle.FORWARD_DECELERATION_TIME));

		returnValue.add(new StopCommand(mDriveTrain));
		returnValue.add(new TurnWheelsToAngle(mDriveTrain, scaleAngle));
		
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				scaleAngle,
				0,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				AutoConstants.SwitchMiddle.FORWARD_ACCELERATION_TIME));

		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				scaleAngle,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				AutoConstants.SwitchMiddle.STRAIGHT_FORWARD_FULL_SPEED_DRIVE_TIME));
		
		returnValue.add(new StraightLineDriveNoElevatorCommand(
				mDriveTrain,
				scaleAngle,
				AutoConstants.SwitchMiddle.FORWARD_SPEED,
				0,
				AutoConstants.SwitchMiddle.FORWARD_DECELERATION_TIME));
		
		returnValue.add(new StopCommand(mDriveTrain));
		
		returnValue.add(new TurnRobotToAngle(mDriveTrain, -scaleAngle));
		
		//returnValue.add(new ParameterizedPathCommand(mDriveTrain, 4, Math.PI/10000, 0, 4, Math.PI/10000, 0, 5000));
		
		returnValue.add(new StopCommand(mDriveTrain));
		
		return returnValue;
		
	}
	
}
