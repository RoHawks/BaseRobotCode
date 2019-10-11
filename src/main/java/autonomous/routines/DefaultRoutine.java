package autonomous.routines;

import java.util.ArrayList;

import frc.robot.Robot;

import autonomous.AutonomousRoutine;
import autonomous.commands.AutonomousCommand;
import autonomous.commands.StopCommand;

public class DefaultRoutine implements AutonomousRoutine {

	private Robot mRobot;

	public DefaultRoutine(Robot pRobot) {
		mRobot = pRobot;
	}

	@Override
	public ArrayList<AutonomousCommand> getAutonomousCommands() {
		ArrayList<AutonomousCommand> returnValue = new ArrayList<AutonomousCommand>();

		returnValue.add(new StopCommand(mRobot));
		
		return returnValue;
	}

}
