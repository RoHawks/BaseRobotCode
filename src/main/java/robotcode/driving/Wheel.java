package robotcode.driving;

import common.motors.interfaces.IMotor;
import common.motors.interfaces.IMotorWithEncoder;
import drivetrain.swerve.wheels.configs.interfaces.IWheelConfig;
import resource.ResourceFunctions;
import resource.Vector;

/**
 * A class representing a swerve drive module with a turn motor, a drive motor,
 * and a turn encoder. Currently written purely for CTRE Talons, but can be
 * modified for Sparks or other motor controllers.
 * 
 * @author Tal Zussman
 */
public class Wheel {
	public final IWheelConfig config;
	public IMotorWithEncoder Turn;
	public IMotor Drive;
	private boolean initialDriveInverted;

	public Wheel(IWheelConfig config) {
		this.config = config;
		Turn = config.getTurnConfig().build();
		Drive = config.getDriveConfig().build();
		if(Turn.getReversed()) {
			Drive.setInverted(!Drive.getInverted());
		}
		initialDriveInverted = Turn.getReversed() ^ Drive.getInverted();
	}

	/**
	 * Set wheel angle & speed
	 * 
	 * @param pWheelVelocity Vector of wheel velocity
	 */
	public void set(Vector pWheelVelocity) {
		set(pWheelVelocity.getAngle(), pWheelVelocity.getMagnitude());
	}

	/**
	 * Set wheel angle & speed
	 * 
	 * @param angle direction to point the wheel
	 * @param speed magnitude to drive the wheel
	 */
	public void set(double angle, double speed) {
		setAngle(angle);
		setLinearVelocity(speed);
	}

	public void setLinearVelocity(double pSpeed) {
		double speed = Math.signum(pSpeed) * Math.min(Math.abs(pSpeed), config.getMaxLinearVelocity());
		Drive.setOutput(speed);
	}

	public void setAngle(double pTarget) {
		pTarget = ResourceFunctions.putAngleInRange(pTarget);
		Turn.setReversedOffsetAngle(pTarget);
		Drive.setInverted(Turn.getReversed() ^ initialDriveInverted);
	}

	public void setTurnSpeed(double pSpeed) {
		Turn.setOutput(pSpeed);
	}

	public double getAngle() {
		return Turn.getReversedOffsetAngle();
	}

	public boolean IsInRange(double pTarget) {
		double realCurrent = Turn.getOffsetAngle();
		double error = ResourceFunctions.continuousAngleDif(pTarget, ResourceFunctions.putAngleInRange(realCurrent));
		return Math.abs(error) < config.getRotationTolerance();
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
	 * @param pTarget target angle in degrees
	 * @return if the wheel is in range
	 */
	public boolean isInRange(double pTarget) {
		double currentPosition = mEncoder.getAngleDegrees();
		double error = ResourceFunctions.continuousAngleDif(pTarget, currentPosition);
		return Math.abs(error) < DriveConstants.ActualRobot.ROTATION_TOLERANCE[0];
		// TODO change tolerance value. Same value is used for ticks and angles for some reason...
	}

}
