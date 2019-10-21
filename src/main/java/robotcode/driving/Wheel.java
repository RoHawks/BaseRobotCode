package robotcode.driving;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import constants.DriveConstants;
import resource.ResourceFunctions;
import resource.Vector;
import sensors.TalonAbsoluteEncoder;

/**
 * A class representing a swerve drive module with a turn motor, a drive motor,
 * and a turn encoder. Currently written purely for CTRE Talons, but can be
 * modified for Sparks or other motor controllers.
 * 
 * @author Tal Zussman
 */
public class Wheel {

	private WPI_TalonSRX mTurn;
	private WPI_TalonSRX mDrive;
	private TalonAbsoluteEncoder mEncoder;

	public Wheel(WPI_TalonSRX pTurn, WPI_TalonSRX pDrive, TalonAbsoluteEncoder pEncoder) {
		mTurn = pTurn;
		mDrive = pDrive;
		mEncoder = pEncoder;
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
	 * @param pAngle direction to point the wheel in degrees
	 * @param pSpeed magnitude to drive the wheel
	 */
	public void set(double pAngle, double pSpeed) {
		setAngle(pAngle);
		setLinearVelocity(pSpeed);
	}

	/**
	 * Sets linear velocity of the wheel, or speed of the drive motor.
	 * 
	 * @param pSpeed speed to set the wheel to, from -1 to +1
	 */
	public void setLinearVelocity(double pSpeed) {
		double speed = Math.signum(pSpeed) * Math.min(Math.abs(pSpeed), DriveConstants.MAX_LINEAR_VELOCITY);
		mDrive.set(ControlMode.PercentOutput, speed);
	}

	/**
	 * Sets the angle of the turn motor using the Talon Position PID ControlMode.
	 * 
	 * @param pAngle target angle in degrees
	 */
	public void setAngle(double pAngle) {
		setAngle(pAngle, ControlMode.Position);
	}

	/**
	 * Sets the angle of the turn motor using a specified {@code ControlMode}.
	 * 
	 * @param pAngle       target angle in degrees
	 * @param pControlMode {@code ControlMode} to be used. Either {@code Position}
	 *                     or {@code MotionMagic}.
	 */
	public void setAngle(double pAngle, ControlMode pControlMode) {
		if (pControlMode == ControlMode.Position) {
			mTurn.set(ControlMode.Position, calculateTalonTargetPosition(pAngle));
		}
		else if (pControlMode == ControlMode.MotionMagic) {
			mTurn.set(ControlMode.MotionMagic, calculateTalonTargetPosition(pAngle));
			// Have to set velocity, acceleration, and PIDF constants
		}
	}

	/**
	 * Helper method that calculates the value to pass to the Talon for its PID and
	 * MotionMagic processes.
	 * 
	 * @param pTarget target angle in degrees
	 * @return the target value of the Talon in ticks
	 */
	private int calculateTalonTargetPosition(double pTarget) {
		int rawCurrentPosition = mTurn.getSelectedSensorPosition(0);
		double cookedCurrentPosition = mEncoder.getAngleDegrees();

		double error = ResourceFunctions.continuousAngleDif(pTarget, cookedCurrentPosition);

		if (Math.abs(error) > 90) {
			mEncoder.setAdd180(!mEncoder.getAdd180());
			mDrive.setInverted(!mDrive.getInverted());

			cookedCurrentPosition = mEncoder.getAngleDegrees();
			error = ResourceFunctions.continuousAngleDif(pTarget, cookedCurrentPosition);
		}

		return rawCurrentPosition + ResourceFunctions.angleToTick(error);
	}

	/**
	 * Sets the speed of the turn motor
	 * 
	 * @param pSpeed target speed from -1 to +1
	 */
	public void setTurnSpeed(double pSpeed) {
		mTurn.set(ControlMode.PercentOutput, pSpeed);
	}

	/**
	 * Getter for angle of the wheel
	 * 
	 * @return angle of the wheel in degrees from the encoder
	 */
	public double getAngle() {
		return mEncoder.getAngleDegrees();
	}

	/**
	 * Checks whether the wheel is within some epsilon of its target angle.
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
