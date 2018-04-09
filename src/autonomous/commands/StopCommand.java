package autonomous.commands;

import org.usfirst.frc.team3419.robot.Robot;

import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;

/**
 * 
 * @author 3419
 *	Auto command that makes the Robot stops driving
 */
public class StopCommand extends BaseAutonomousCommand {

	private Robot mRobot;
	
	public StopCommand (Robot pRobot) 
	{
		mRobot = pRobot;
	}

	@Override
	public boolean runCommand() 
	{
		DriveTrain mDriveTrain = mRobot.getDriveTrain();
		mDriveTrain.enactMovement(mDriveTrain.getRobotAngle(), 
								  mDriveTrain.getDesiredRobotVel().getAngle(), 
								  LinearVelocity.NONE, 
								  RotationalVelocity.NONE, 
								  0.0);
		
		return true;
	}

}
