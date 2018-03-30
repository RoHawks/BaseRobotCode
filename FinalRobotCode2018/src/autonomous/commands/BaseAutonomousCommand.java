package autonomous.commands;

public abstract class BaseAutonomousCommand implements AutonomousCommand 
{
	protected long mTimeStartMillis;
	
	public void Startup() 
	{
		mTimeStartMillis = System.currentTimeMillis();
	}
	
	protected long GetMillisecondsSinceStart()
	{
		return System.currentTimeMillis() - mTimeStartMillis;
	}

}
