package robotcode.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import constants.ElevatorConstants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import sensors.ElevatorEncoder;
import simulator.talon.TalonInterface;

public class Elevator {

	private TalonInterface mMotor;
	private ElevatorEncoder mEncoder;
	private Joystick mJoystick;

	private ElevatorState mElevatorState;

	private ElevatorDirection mElevatorDir;

	private boolean mIsEnabled = true;

	public boolean IsEnabled() {
		return mIsEnabled;
	}

	public void enable(boolean mIsEnabled) {
		this.mIsEnabled = mIsEnabled;
	}

	public Elevator(TalonInterface mElevatorTalon, ElevatorEncoder pEncoder, Joystick pJoystick) {
		mEncoder = pEncoder;
		mMotor = mElevatorTalon;

		mElevatorState = ElevatorState.BOX_HEIGHT;

		mElevatorDir = ElevatorDirection.NONE;

		mJoystick = pJoystick;
	}

	public enum ElevatorState {
		GROUND, JOYSTICK, SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, BOX_HEIGHT, MID_HEIGHT;
	}

	public enum ElevatorDirection {
		UP, DOWN, NONE;
	}

	/**
	 * makes the elevator go vroom
	 */
	public void enactMovement() {
		SmartDashboard.putString("Elevator Height Mode", mElevatorState.toString());

		switch (mElevatorState) {
		case GROUND:
			setHeight(ElevatorConstants.Heights.GROUND);
			break;
		case JOYSTICK:
			setSpeed(Math.abs(mJoystick.getY()) > 0.25 ? -1 * (mJoystick.getY()) : 0);
			break;
		case SCALE_HIGH:
			setHeight(ElevatorConstants.Heights.SCALE_HEIGHT_HIGH);
			break;
		case SCALE_MID:
			setHeight(ElevatorConstants.Heights.SCALE_HEIGHT_MID);
			break;
		case SCALE_LOW:
			setHeight(ElevatorConstants.Heights.SCALE_HEIGHT_LOW);
			break;
		case SWITCH:
			setHeight(ElevatorConstants.Heights.SWITCH_HEIGHT);
			break;
		case BOX_HEIGHT:
			setHeight(ElevatorConstants.Heights.BOX_HEIGHT);
			break;
		case MID_HEIGHT:
			setHeight(ElevatorConstants.Heights.MID_HEIGHT);
			break;
		default:
			break;
		}
	}

	public void setPIDConstants() {
		setDirMode();
		SmartDashboard.putString("Elevator Dir", mElevatorDir.toString());
		switch (mElevatorDir) {
		case UP:
			mMotor.config_kP(0, ElevatorConstants.PID.ELEVATOR_UP_P, 10);
			mMotor.config_kI(0, ElevatorConstants.PID.ELEVATOR_UP_I, 10);
			mMotor.config_kD(0, ElevatorConstants.PID.ELEVATOR_UP_D, 10);
			break;
		case DOWN:
			mMotor.config_kP(0, ElevatorConstants.PID.ELEVATOR_DOWN_P, 10);
			mMotor.config_kI(0, ElevatorConstants.PID.ELEVATOR_DOWN_I, 10);
			mMotor.config_kD(0, ElevatorConstants.PID.ELEVATOR_DOWN_D, 10);
			break;
		default:
			mMotor.config_kP(0, 0, 10);
			mMotor.config_kI(0, 0, 10);
			mMotor.config_kD(0, 0, 10);
			break;
		}
	}

	/**
	 * set elevator speed
	 * 
	 * @param speed
	 */
	public void setSpeed(double speed) {
		if (mIsEnabled) {
			SmartDashboard.putNumber("set speed", speed);
			mMotor.set(ControlMode.PercentOutput, speed);
		} else {
			mMotor.set(ControlMode.PercentOutput, 0);
		}
	}

	public boolean IsAtBottom() {
		return mMotor.getSensorCollection_isRevLimitSwitchClosed();
	}

	public void stop() {
		setSpeed(0);
	}

	public void setGround() {
		if (mIsEnabled) {
			mMotor.set(ControlMode.PercentOutput, -0.6);
		} else {
			mMotor.set(ControlMode.PercentOutput, 0);
		}

		mElevatorState = ElevatorState.GROUND;
	}

	
	public void setMidHigh()
	{
		setHeight(ElevatorConstants.Heights.MID_HEIGHT);
		mElevatorState = ElevatorState.MID_HEIGHT;
	}
	
	public void setSwitch() {
		setHeight(ElevatorConstants.Heights.SWITCH_HEIGHT);
		mElevatorState = ElevatorState.SWITCH;
	}

	public void setScaleLow() {
		setHeight(ElevatorConstants.Heights.SCALE_HEIGHT_LOW);
		mElevatorState = ElevatorState.SCALE_LOW;
	}

	public void setScaleMid() {
		setHeight(ElevatorConstants.Heights.SCALE_HEIGHT_MID);
		mElevatorState = ElevatorState.SCALE_MID;
	}

	public void setScaleHigh() {
		setHeight(ElevatorConstants.Heights.SCALE_HEIGHT_HIGH);
		mElevatorState = ElevatorState.SCALE_HIGH;
	}

	public void setBox() {
		setHeight(ElevatorConstants.Heights.BOX_HEIGHT);
		mElevatorState = ElevatorState.BOX_HEIGHT;
	}

	public void setJoystick() {
		if (Math.abs(mJoystick.getY()) > 0.25 && mIsEnabled) {
			mMotor.set(-mJoystick.getY() * mJoystick.getY() * Math.signum(mJoystick.getY()));
		} else {
			mMotor.set(0);
		}
		mElevatorState = ElevatorState.JOYSTICK;
	}

	/**
	 * sets the elevator to go to a certain height
	 * 
	 * @param height
	 *            :D
	 */
	public void setHeight(double height) {
		if (mIsEnabled) {
			double goal = ElevatorEncoder.InchToTick(height);
			setPIDConstants();
			SmartDashboard.putNumber("goal", goal);
			mMotor.set(ControlMode.Position, goal);
			SmartDashboard.putNumber("Elevator Raw Ticks", mEncoder.getRawTicks());
			SmartDashboard.putNumber("Elevator Error", ElevatorEncoder.TickToInch(mEncoder.getRawTicks() - goal));
			SmartDashboard.putNumber("Talon error value", mMotor.getClosedLoopError(0));
			SmartDashboard.putNumber("Elevator motor output", mMotor.getMotorOutputVoltage());
			SmartDashboard.putNumber("Elevator Talon Current", mMotor.getOutputCurrent());
		} else {
			mMotor.set(ControlMode.PercentOutput, 0);
		}
	}

	/**
	 * finds direction the elevator will move
	 */
	private void setDirMode() {
		if (mEncoder.getHeightInInchesFromElevatorBottom() > getHeightGoal(mElevatorState)) {
			mElevatorDir = ElevatorDirection.DOWN;
		} else if (mEncoder.getHeightInInchesFromElevatorBottom() < getHeightGoal(mElevatorState)) {
			mElevatorDir = ElevatorDirection.UP;
		} else {
			mElevatorDir = ElevatorDirection.NONE;
		}
	}

	/**
	 * height of the "target"
	 * 
	 * @param mode
	 *            Elevator mode
	 * @return height
	 */
	private double getHeightGoal(ElevatorState mode) {
		switch (mode) {
		case GROUND:
			return ElevatorConstants.Heights.GROUND;
		case JOYSTICK:
			return -999999999 * Math.signum(mJoystick.getY());
		case SCALE_HIGH:
			return ElevatorConstants.Heights.SCALE_HEIGHT_HIGH;
		case SCALE_MID:
			return ElevatorConstants.Heights.SCALE_HEIGHT_MID;
		case SCALE_LOW:
			return ElevatorConstants.Heights.SCALE_HEIGHT_LOW;
		case SWITCH:
			return ElevatorConstants.Heights.SWITCH_HEIGHT;
		case BOX_HEIGHT:
			return ElevatorConstants.Heights.BOX_HEIGHT;
		case MID_HEIGHT:
			return ElevatorConstants.Heights.MID_HEIGHT;
		default:
			return mEncoder.getHeightInInchesFromElevatorBottom();
		}
	}

	/**
	 * @return elevator mode
	 */
	public ElevatorState getElevatorMode() {
		return mElevatorState;
	}

	/**
	 * @return elevator direction
	 */
	public ElevatorDirection getElevatorDirection() {
		setDirMode();
		return mElevatorDir;
	}

	/**
	 * @return speed of elevator
	 */
	public double getSpeed() {
		return mMotor.getMotorOutputVoltage();
	}

	/**
	 * @return height of the elevator in inches
	 */
	public double getHeightInches() {
		return mEncoder.getHeightInInchesFromElevatorBottom();
	}

	public boolean isAboveTarget() 
	{
		return getHeightInches() > this.getHeightGoal(mElevatorState);
	}
	public boolean isCloseToTarget() {
		SmartDashboard.putNumber("Height Goal -- close to target", this.getHeightGoal(mElevatorState));
		SmartDashboard.putNumber("Height Inches -- close to target", getHeightInches());
		return Math.abs(this.getHeightGoal(mElevatorState) - getHeightInches()) < 2;
	}
}