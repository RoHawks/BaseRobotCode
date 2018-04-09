package robotcode.driving;

import constants.DriveConstants;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import resource.ResourceFunctions;
import resource.Vector;
import robotcode.pid.GenericPIDOutput;
import sensors.RobotAngle;

public class DriveTrain {

	// output
	private SwerveDrive mSwerveDrive;
	private Wheel[] mWheels;

	// input
	private RobotAngle mRobotAngle;
	private XboxController mController;
	private double mJoystickAngle;

	// end products
	private Vector mDesiredRobotVel;
	private double mDesiredAngularVel;

	private boolean mIsFieldRelative;

	// pids
	private PIDController mGyroPID;
	private GenericPIDOutput mGyroOutput;

	private PIDController mDriftCompensationPID;
	private GenericPIDOutput mDriftCompensationOutput;

	// velocity modes
	private LinearVelocity mLinearVel;
	private LinearVelocity mPrevLinearVel;
	private RotationalVelocity mRotationalVel;

	public DriveTrain(Wheel[] pWheels, XboxController pController, RobotAngle pRobotAngle) {
		mWheels = pWheels;
		mController = pController;
		mSwerveDrive = new SwerveDrive(mWheels);

		mRobotAngle = pRobotAngle;

		mDesiredRobotVel = new Vector();
		mDesiredAngularVel = 0;

		mJoystickAngle = 0;
		mIsFieldRelative = false;

		mLinearVel = LinearVelocity.NONE;
		mPrevLinearVel = LinearVelocity.NONE;
		mRotationalVel = RotationalVelocity.NONE;

		pidInit();
	}

	public enum LinearVelocity {
		ANGLE_ONLY,
		NONE,
		NORMAL,
		NUDGE
	}

	public enum RotationalVelocity {
		NONE,
		NORMAL,
		NUDGE,
		POV
	}

	private void enactMovement() {
		enactMovement(mController.getPOV(), getStickAngle(Hand.kLeft), getLinearVelocityState(),
				getRotationalVelocityState(), getStickLinearVel());
	}

	public void enactMovement(double pGyroAngle, double pRobotDirectionAngle, LinearVelocity pLinearVel,
			RotationalVelocity pRotationalVel, double pSpecificLinearVelocity) {
		SmartDashboard.putNumber("Robot Angle", mRobotAngle.getAngleDegrees());

		double joystickAngle = pRobotDirectionAngle;
		double robotDirectionAngle = joystickAngle;

		mLinearVel = pLinearVel;
		mRotationalVel = pRotationalVel;
		
		SmartDashboard.putString("Linear Velocity State", mLinearVel.name());
		SmartDashboard.putString("Previous Linear Velocity", mPrevLinearVel.name());
		SmartDashboard.putString("Rotational Velocity State", mRotationalVel.name());

		if (mController.getStartButtonReleased()) {
			mIsFieldRelative = !mIsFieldRelative;
		}

		if (mIsFieldRelative) {
			robotDirectionAngle = ResourceFunctions.putAngleInRange(joystickAngle - mRobotAngle.getAngleDegrees());
		}

		SmartDashboard.putBoolean("Field Relative", mIsFieldRelative);

		Vector linearVel = new Vector();
		switch (mLinearVel) {
			case NORMAL:
				linearVel = Vector.createPolar(robotDirectionAngle, pSpecificLinearVelocity);
				break;
			case NUDGE:
				linearVel = nudgeMove();
				break;
			case ANGLE_ONLY:
				break;
			case NONE:
				break;
		}
		mDesiredRobotVel = new Vector(linearVel);
		SmartDashboard.putNumber("Linear Velocity after switch", mDesiredRobotVel.getMagnitude());

		switch (mRotationalVel) {
			case NORMAL:
				mGyroPID.disable();
				mDesiredAngularVel = angularVelStick();
				break;
			case NUDGE:
				mGyroPID.disable();
				mDesiredAngularVel = nudgeTurn();
				break;
			case NONE:
				mGyroPID.disable();
				mDesiredAngularVel = 0;
				break;
			case POV:
				mDesiredAngularVel = getAngularPIDVel(pGyroAngle); // TZ
				SmartDashboard.putNumber("gyro pid error", mGyroPID.getError());
				break;
		}

		SmartDashboard.putNumber("POV", mController.getPOV());
		SmartDashboard.putString("Rotational Velocity State", mRotationalVel.toString());

		for (int i = 0; i < 4; i++) {
			if (mLinearVel == LinearVelocity.ANGLE_ONLY && mRotationalVel == RotationalVelocity.NONE) {
				mWheels[i].set(robotDirectionAngle, 0);
				resetDriftCompensation();
				mDriftCompensationPID.setSetpoint(mRobotAngle.getAngleDegrees());
			}
			else if (mRotationalVel == RotationalVelocity.NONE && mLinearVel == LinearVelocity.NONE) {
				if (mPrevLinearVel == LinearVelocity.NUDGE) {
					mWheels[i].set(mSwerveDrive.getOutput(i).getAngle(), 0);
				} 
				else {
					mWheels[i].setLinearVelocity(0);
					mWheels[i].setTurnSpeed(0);
				}
				resetDriftCompensation();
				mDriftCompensationPID.setSetpoint(mRobotAngle.getAngleDegrees());
			}
			else if (mRotationalVel == RotationalVelocity.NONE
					&& (mLinearVel == LinearVelocity.NORMAL || mLinearVel == LinearVelocity.NUDGE)) {
				mDriftCompensationPID.enable();
				SmartDashboard.putNumber("drift comp error", mDriftCompensationPID.getError());
				SmartDashboard.putNumber("drift comp", mDriftCompensationOutput.getVal());
				mSwerveDrive.calculateHoldDirection(mDriftCompensationOutput.getVal(), getDesiredRobotVel());
				mWheels[i].set(mSwerveDrive.getOutput(i));
				SmartDashboard.putNumber("Linear velocity in else if", mSwerveDrive.getOutput(i).getMagnitude());
			}
			else {
				resetDriftCompensation();
				mDriftCompensationPID.setSetpoint(mRobotAngle.getAngleDegrees());
				mSwerveDrive.calculate(getDesiredAngularVel(), getDesiredRobotVel());
				mWheels[i].set(mSwerveDrive.getOutput(i));
				
			}
			SmartDashboard.putBoolean("drift comp enabled", mDriftCompensationPID.isEnabled());
			SmartDashboard.putNumber("Error " + i, robotDirectionAngle - mWheels[i].getAngle());
			SmartDashboard.putNumber("Angle " + i, mWheels[i].getAngle());
		}
	}

	public void driveSwerve() {
		enactMovement();
	}

	/**
	 * tank drive : right trigger = forward ; left trigger = turn ; right joystick x
	 * = turn
	 */
	public void driveTank() {
		double forwardVel = mController.getTriggerAxis(Hand.kRight) - mController.getTriggerAxis(Hand.kLeft);
		forwardVel *= 0.4;
		double angularVel = mController.getX(Hand.kRight);
		angularVel *= 0.1;

		double leftSpeed = forwardVel - angularVel;
		double rightSpeed = forwardVel + angularVel;

		SmartDashboard.putNumber("Tank Left Speed", leftSpeed);
		SmartDashboard.putNumber("Tank Right Speed", rightSpeed);

		mWheels[0].set(0, rightSpeed);
		mWheels[1].set(0, leftSpeed);
		mWheels[2].set(0, leftSpeed);
		mWheels[3].set(0, rightSpeed);
	}

	/**
	 * Crab Drive method
	 */
	public void driveCrab() {
		double linearVelocity = getStickLinearVel();
		double joystickAngle = getStickAngle(Hand.kLeft);
		for (int i = 0; i < 4; i++) {
			mWheels[i].set(joystickAngle, linearVelocity);
			SmartDashboard.putNumber("Angle " + i, mWheels[i].getAngle());
		}
	}

	/**
	 * Gets the angle of Xbox controller joystick
	 * 
	 * @param h
	 *            Joystick to get the angle for
	 * @return Angle of the stick in degrees, with 0 degrees pointing directly up on
	 *         the controller
	 */
	public double getStickAngle(Hand h) {
		double x = mController.getX(h);
		double y = -mController.getY(h);
		SmartDashboard.putNumber("X-Value: " + h.toString(), x);
		SmartDashboard.putNumber("Y-Yalue: " + h.toString(), y);
		if (Math.abs(y) >= DriveConstants.MIN_DIRECTION_MAG || Math.abs(x) >= DriveConstants.MIN_DIRECTION_MAG) {
			mJoystickAngle = -Math.toDegrees(Math.atan2(y, x)) + 90;
			mJoystickAngle = ResourceFunctions.putAngleInRange(mJoystickAngle);
			// puts angle between zero and 360
		}
		SmartDashboard.putNumber(h.toString() + " Joystick Angle", mJoystickAngle);
		return mJoystickAngle;
	}

	/**
	 * Gets magnitude of Xbox controller joystick
	 * 
	 * @param h
	 *            Joystick to get the magnitude for
	 * @return Magnitude of the depression; 0 is not used, 1 is fully depressed
	 */
	public double getStickMag(Hand h) {
		Vector v = new Vector(mController.getX(h), mController.getY(h));
		return v.getMagnitude();
	}

	/**
	 * Angular velocity using nudge bumpers
	 * 
	 * @return correct angular velocity
	 */
	private double nudgeTurn() {
		if (mController.getBumper(Hand.kLeft)) {
			return -DriveConstants.SwerveSpeeds.NUDGE_TURN_SPEED;
		} else if (mController.getBumper(Hand.kRight)) {
			return DriveConstants.SwerveSpeeds.NUDGE_TURN_SPEED;
		}

		return 0;
	}

	/**
	 * Get the direction vector for nudge driving using the letter buttons
	 * 
	 * @return correct direction vector
	 */
	private Vector nudgeMove() {
		double newAngle = 0;
		double robotAngle = mRobotAngle.getAngleDegrees();

		if (mController.getYButton()) {
			newAngle = 0;
		}
		else if (mController.getXButton()) {
			newAngle = 270;
		}
		else if (mController.getAButton()) {
			newAngle = 180;
		}
		else if (mController.getBButton()) {
			newAngle = 90;
		}

		if (mIsFieldRelative) {
			newAngle -= robotAngle;
		}

		return Vector.createPolar(newAngle, DriveConstants.SwerveSpeeds.NUDGE_MOVE_SPEED);
	}

	/**
	 * quadratic control of right trigger axis
	 * 
	 * @return trigger axis squared
	 */
	private double getStickLinearVel() {
		double speed = mController.getTriggerAxis(Hand.kRight);
		SmartDashboard.putNumber("Right Trigger Axis", speed);
		speed = Math.pow(speed, 2) * DriveConstants.SwerveSpeeds.SPEED_MULT;
		// quadratic control, finer control of lower speeds
		return speed;
	}

	/**
	 * Angular velocity calculated with the right joystick
	 * 
	 * @return angular velocity for swerve drive
	 */
	private double angularVelStick() {
		double joystickValue = mController.getX(Hand.kRight);
		SmartDashboard.putNumber("Right Joystick X", joystickValue);

		if (Math.abs(joystickValue) < DriveConstants.MIN_DIRECTION_MAG) {
			return 0;
		}
		double angularVel = joystickValue * Math.abs(joystickValue);
		angularVel *= DriveConstants.SwerveSpeeds.ANGULAR_SPEED_MULT;
		// angularVel = -angularVel; // correct the sign for clockwise/counter-clockwise
		SmartDashboard.putNumber("Angular Velocity", angularVel);
		return angularVel; // quadratic control for finer movements
	}

	/**
	 * disables and resets previous stuff
	 */
	private void resetDriftCompensation() {
		if (mDriftCompensationPID.isEnabled()) {
			mDriftCompensationPID.disable();
		}
		mDriftCompensationPID.reset();
	}

	/**
	 * gets linear velocity state
	 * 
	 * @return bumper --> nudge; no move --> angle only; else --> normal
	 */
	private LinearVelocity getLinearVelocityState() {
		LinearVelocity linVel = LinearVelocity.NONE;
		if (getLetterPressed()) {
			linVel = LinearVelocity.NUDGE;
		} 
		else if (getStickLinearVel() < DriveConstants.MIN_LINEAR_VELOCITY
				&& getStickMag(Hand.kLeft) > DriveConstants.MIN_DIRECTION_MAG) {
			linVel = LinearVelocity.ANGLE_ONLY;
		} 
		else if (getStickLinearVel() > DriveConstants.MIN_LINEAR_VELOCITY) {
			linVel = LinearVelocity.NORMAL;
		}

		if (linVel != mLinearVel) {
			mPrevLinearVel = mLinearVel;
		}
		
		return linVel;
	}

	/**
	 * gets rotational velocity state
	 * 
	 * @return bumper --> nudge; no move --> none; else --> normal
	 */
	private RotationalVelocity getRotationalVelocityState() {
		if (mController.getPOV() > 0) {
			return RotationalVelocity.POV;
		} 
		else if (mController.getBumper(Hand.kRight) || mController.getBumper(Hand.kLeft)) {
			return RotationalVelocity.NUDGE;
		} 
		else if (angularVelStick() != 0) {
			return RotationalVelocity.NORMAL;
		}
		return RotationalVelocity.NONE;
	}

	private boolean getLetterPressed() {
		return (mController.getAButton() || mController.getBButton() || mController.getXButton()
				|| mController.getYButton());
	}

	/**
	 * Calculate angular velocity to turn to a certain angle
	 * 
	 * @param setpointAngle
	 *            angle to turn to
	 * @return angular velocity required to turn to the angle
	 */
	public double getAngularPIDVel(double setpointAngle) {
		mGyroPID.setSetpoint(setpointAngle);

		if (!mGyroPID.isEnabled()) {
			mGyroPID.enable();
		}
		double vel = mGyroOutput.getVal();

		SmartDashboard.putNumber("Gyro PID Setpoint:", mGyroPID.getSetpoint());
		SmartDashboard.putNumber("Gyro PID Output:", vel);

		return vel;
	}

	public Vector getDesiredRobotVel() {
		return mDesiredRobotVel;
	}

	public double getDesiredAngularVel() {
		return mDesiredAngularVel;
	}

	private void pidInit() {
		mGyroOutput = new GenericPIDOutput();
		mGyroPID = new PIDController(DriveConstants.ActualRobot.GYRO_P, DriveConstants.ActualRobot.GYRO_I,
				DriveConstants.ActualRobot.GYRO_D, mRobotAngle, mGyroOutput);
		mGyroPID.setInputRange(0, 360.0);
		mGyroPID.setOutputRange(-DriveConstants.ActualRobot.GYRO_MAX_SPEED,
				DriveConstants.ActualRobot.GYRO_MAX_SPEED);
		mGyroPID.setAbsoluteTolerance(DriveConstants.ActualRobot.GYRO_TOLERANCE);
		mGyroPID.setContinuous(true);

		mDriftCompensationOutput = new GenericPIDOutput();
		mDriftCompensationPID = new PIDController(DriveConstants.ActualRobot.DRIFT_COMP_P,
				DriveConstants.ActualRobot.DRIFT_COMP_I, DriveConstants.ActualRobot.DRIFT_COMP_D, mRobotAngle,
				mDriftCompensationOutput);
		mDriftCompensationPID.setInputRange(0, 360);
		mDriftCompensationPID.setContinuous(true);
		mDriftCompensationPID.setSetpoint(0);
		mDriftCompensationPID.disable();
	}

	public boolean AllWheelsInRange(double pAngle) {
		for (int i = 0; i < 4; i++) {
			if (!mWheels[i].IsInRange(pAngle)) {
				return false;
			}
		}
		return true;
	}

	public boolean gyroInRange() {
		return mGyroPID.onTarget();
	}
	
	public double getRobotAngle() {
		return mRobotAngle.getAngleDegrees();
	}

	public void gyroOnly(double pAngle) {
		double angularVel = getAngularPIDVel(pAngle);
		mSwerveDrive.calculate(angularVel, new Vector());
		for (int i = 0; i < 4; i++) {
			mWheels[i].set(mSwerveDrive.getOutput(i));
		}
	}
	
	public Wheel[] returnWheels() {
		return mWheels;
	}
	
}

/*
LOGS
robotangle
fieldrelative
gyropiderror
pov
drift compensation error
drift compensatoin output
drift compensatoin enabled
wheel angle error
wheel angle
tank left
tank right
crab angle
joystick x value
joystick y value
joystick angle
right trigger
right joystick x
angular velocity
lin vel state
lin vel state prev
gyro pid setpoint
gyro pid output
rotational vel state
*/