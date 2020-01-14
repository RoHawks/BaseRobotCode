package robotcode.driving;

import common.motors.interfaces.IMotor;
import common.motors.interfaces.IMotorWithEncoder;
import drivetrain.swerve.wheels.configs.interfaces.IWheelConfig;
import resource.ResourceFunctions;
import resource.Vector;

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
	 * @param pWheelVelocity
	 *            Vector of wheel velocity
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