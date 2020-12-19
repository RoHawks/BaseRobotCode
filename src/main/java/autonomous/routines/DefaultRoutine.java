package autonomous.routines;

import java.util.ArrayList;

import frc.robot.Robot;

import autonomous.AutonomousRoutine;
import autonomous.commands.AutonomousCommand;
import autonomous.commands.ParameterizedPathDriveCommand;
import autonomous.commands.StopCommand;
import autonomous.commands.StraightLineDriveCommand;
import autonomous.commands.TurnRobotToAngleCommand;
import config.Config;

public class DefaultRoutine implements AutonomousRoutine {

	private Robot mRobot;

	public DefaultRoutine(Robot pRobot) {
		mRobot = pRobot;
	}

	@Override
	public ArrayList<AutonomousCommand> getAutonomousCommands() {
		ArrayList<AutonomousCommand> returnValue = new ArrayList<AutonomousCommand>();

		// // accelerate
		// returnValue.add(new StraightLineDriveCommand(
		// 		mRobot,
		// 		Config.AutoConstants.DefaultRoutine.WHEEL_ANGLE,
		// 		Config.AutoConstants.DefaultRoutine.MINIMUM_SPEED,
		// 		Config.AutoConstants.DefaultRoutine.MAXIMUM_SPEED,
		// 		Config.AutoConstants.DefaultRoutine.ACCELERATION_TIME));

		// // drive full speed
		// returnValue.add(new StraightLineDriveCommand(
		// 		mRobot,
		// 		Config.AutoConstants.DefaultRoutine.WHEEL_ANGLE,
		// 		Config.AutoConstants.DefaultRoutine.MAXIMUM_SPEED,
		// 		Config.AutoConstants.DefaultRoutine.MAXIMUM_SPEED,
		// 		Config.AutoConstants.DefaultRoutine.DRIVE_FULL_SPEED_TIME));

		// // slow down then stop
		// returnValue.add(new StraightLineDriveCommand(
		// 		mRobot,
		// 		Config.AutoConstants.DefaultRoutine.WHEEL_ANGLE,
		// 		Config.AutoConstants.DefaultRoutine.MAXIMUM_SPEED,
		// 		Config.AutoConstants.DefaultRoutine.MINIMUM_SPEED,
		// 		Config.AutoConstants.DefaultRoutine.DECELERATION_TIME));

		returnValue.add(new StopCommand(mRobot));
		returnValue.add(new TurnRobotToAngleCommand(mRobot, -90));
		returnValue.add(new ParameterizedPathDriveCommand(mRobot, 4, Math.PI/10000, 0, 4, Math.PI/10000, 0, 5000));
		returnValue.add(new StopCommand(mRobot));
		
		return returnValue;
	}

}
