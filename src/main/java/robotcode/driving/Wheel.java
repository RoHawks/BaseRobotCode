package robotcode.driving;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import constants.DriveConstants;
import config.Config;
import drivetrain.controllers.SparkMax;
import drivetrain.controllers.TalonSRX;
import drivetrain.controllers.TalonSRXWithEncoder;
import drivetrain.interfaces.IMotor;
import drivetrain.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;
import resource.Vector;
import sensors.TalonAbsoluteEncoder;

public class Wheel {

	private IMotorWithEncoder mTurn;
	private IMotor mDrive;

	public Wheel(IMotorWithEncoder pTurn, IMotor pDrive) 
	{
		mTurn = pTurn;
		mDrive = pDrive;
	}

	/**
	 * Set wheel angle & speed
	 * 
	 * @param pWheelVelocity
	 *            Vector of wheel velocity
	 */
	public void set(Vector pWheelVelocity) 
	{
		set(pWheelVelocity.getAngle(), pWheelVelocity.getMagnitude());
	}

	/**
	 * Set wheel angle & speed
	 * 
	 * @param angle
	 *            direction to point the wheel
	 * @param speed
	 *            magnitude to drive the wheel
	 */
	public void set(double angle, double speed) 
	{
		setAngle(angle);
		setLinearVelocity(speed);
	}

	public void setLinearVelocity(double pSpeed) 
	{
		double speed = Math.signum(pSpeed) * Math.min(Math.abs(pSpeed), Config.DriveConstants.MAX_LINEAR_VELOCITY);
		mDrive.setOutput(speed);
	}

	public void setAngle(double pAngle) 
	{
		TalonPID(pAngle);
	}

	private void TalonPID(double pTarget) 
	{
		double current = ResourceFunctions.tickToAngle(mTurn.getPosition());
		double realCurrent = mTurn.getAnglePosition();

		double error = ResourceFunctions.continuousAngleDif(pTarget, ResourceFunctions.putAngleInRange(realCurrent));

		if (Math.abs(error) > 90) {
			mTurn.setAdd180(!mTurn.getAdd180()); // TODO: fix flip tick position
			mDrive.setInverted(!mDrive.getInverted());
			error = ResourceFunctions.continuousAngleDif(pTarget, realCurrent);
		}
		mTurn.setPosition(ResourceFunctions.angleToTick(current + error));
	}

	public void setTurnSpeed(double pSpeed) 
	{
		mTurn.setOutput(pSpeed);
	}

	public double getAngle()
	{
		return mTurn.getAnglePosition();
	}
	
	public boolean IsInRange(double pTarget) 
	{
		double realCurrent = mTurn.getAnglePosition();
		double error = ResourceFunctions.continuousAngleDif(pTarget, ResourceFunctions.putAngleInRange(realCurrent));
		return Math.abs(error) < Config.DriveConstants.ROTATION_TOLERANCE[0];
	}
	
	/*
	 * Methods to run a self written PID (just proportional) on the roborio
	 * instead of the Talons Requires some constants to be changed
	 * 
	 * 
	 * public void setAngle(double pAngle) { 
	 * 		double speed = proportional(pAngle); 
	 * 		mTurn.set(ControlMode.PercentOutput, speed);
	 * }
	 * 
	 * private double proportional (double pTarget) { 
	 * 		double current = mEncoder.getAngleDegrees();
	 * 		double error = ResourceFunctions.continuousAngleDif(pTarget, current/*current, 360 - pTarget///);
	 * 		// 360-pTarget was quick fix, will think more later
	 * 		if (Math.abs(error) > 90) { 
	 * 			mEncoder.setAdd180(!mEncoder.getAdd180());
	 * 			setInverted(!mDrive.getInverted()); 
	 * 			error = ResourceFunctions.continuousAngleDif(pTarget, mEncoder.getAngleDegrees()); 
	 * 		} 
	 * 		if (Math.abs(error) < 5) { return 0; }
	 * 		SmartDashboard.putNumber("error", error); // Instance variable? double
	 * 		speed = error * NONTALON_P;// Temporary for onboard PID 
	 * 		speed = Math.signum(speed) * Math.min(Math.abs(speed), DriveConstants.MAX_TURN_VEL) * (mTurnInverted ? -1 : 1); 
	 * 		return speed; 
	 * }
	 */
}