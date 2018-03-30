package autonomous.rountines;

import java.util.ArrayList;

import autonomous.AutonomousRoutine;
import autonomous.commands.AutonomousCommand;

public class DoNothingRoutine implements AutonomousRoutine
{
	public ArrayList<AutonomousCommand> GetAutonomousCommands()
	{
		return new ArrayList<AutonomousCommand>();
		
	}
}
