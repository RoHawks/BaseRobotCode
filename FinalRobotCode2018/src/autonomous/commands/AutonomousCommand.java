package autonomous.commands;

public interface AutonomousCommand {

	void Startup();
	boolean RunCommand();//true if complete, false if still running
	
}
