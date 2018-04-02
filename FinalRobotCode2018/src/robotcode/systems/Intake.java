package robotcode.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import constants.IntakeConstants;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import simulator.DigitalInputInterface;
import simulator.talon.TalonInterface;

public class Intake {

	private TalonInterface mLeftWheel, mRightWheel;
	private simulator.solenoid.SolenoidInterface mLeftPiston, mRightPiston;
	private DigitalInputInterface mLimitSwitch, mBreakbeam;
	private IntakeState mIntakeState;
	private Joystick mJoystick;
	
	private long mTimeWhenEnteredFlip;

	private double mWheelSpeed;

	public Intake(TalonInterface pLeftWheel, TalonInterface pRightWheel, simulator.solenoid.SolenoidInterface pLeft, simulator.solenoid.SolenoidInterface pRight,
			DigitalInputInterface pLimitSwitch, DigitalInputInterface pBreakbeam,	Joystick pJoystick) {
		mLeftWheel = pLeftWheel;
		mRightWheel = pRightWheel;
		mLeftPiston = pLeft;
		mRightPiston = pRight;
		mLimitSwitch = pLimitSwitch;
		mBreakbeam = pBreakbeam;
		mJoystick = pJoystick;
		mIntakeState = IntakeState.CLOSED;
	}
	
	public void flap()
	{
		if (mIntakeState != IntakeState.FLAP) {
			mTimeWhenEnteredFlip = System.currentTimeMillis();
		}
		mIntakeState = IntakeState.FLAP;
		
		boolean flipSide = (System.currentTimeMillis() - mTimeWhenEnteredFlip) % IntakeConstants.FLIP_TIME_MS > IntakeConstants.FLIP_TIME_MS / 2;
		mRightPiston.set(flipSide ? Value.kForward : Value.kReverse);
		mLeftPiston.set((flipSide ? Value.kForward : Value.kReverse));
		//TZ test for all the different possibilities
		
		mLeftWheel.set(ControlMode.PercentOutput, IntakeConstants.INTAKE_WHEEL_SPEED);
		mRightWheel.set(ControlMode.PercentOutput, IntakeConstants.INTAKE_WHEEL_SPEED);
	}
	
	boolean button_pressed = false;
	//ATS wheels run every 5 seconds for 1 second to push cube back in
	public void hold() {
		mIntakeState = IntakeState.CLOSED;
		closeMovePistons();
	}
	
	public void hunt() {
		openMovePistons();
		mIntakeState = IntakeState.OPEN;
		mLeftWheel.set(ControlMode.PercentOutput, IntakeConstants.INTAKE_WHEEL_SPEED);
		mRightWheel.set(ControlMode.PercentOutput, IntakeConstants.INTAKE_WHEEL_SPEED);
	}
	
	public void eject() {
		closeMovePistons();
		mIntakeState = IntakeState.CLOSED;
		mLeftWheel.set(ControlMode.PercentOutput, IntakeConstants.EJECT_WHEEL_SPEED);
		mRightWheel.set(ControlMode.PercentOutput, IntakeConstants.EJECT_WHEEL_SPEED);
	}
	
	public void disable() {
		openMovePistons();
		mIntakeState = IntakeState.DISABLE;
		mLeftWheel.set(ControlMode.PercentOutput, 0);
		mRightWheel.set(ControlMode.PercentOutput, 0);
	}
	
	public enum IntakeState {
		CLOSED, DISABLE, FLAP, OPEN 
	}

	public void openMovePistons() {
		mLeftPiston.set(IntakeConstants.OPEN);
		mRightPiston.set(IntakeConstants.OPEN);
	}

	public void closeMovePistons() {
		mLeftPiston.set(IntakeConstants.CLOSED);
		mRightPiston.set(IntakeConstants.CLOSED);
	}

	public void setWheelSpeedJoystick() {
		double yPos = mJoystick.getY();
		mWheelSpeed = (Math.abs(yPos) > 0.25) ? (-1 * Math.signum(yPos) * yPos * yPos) : 0;
		
		SmartDashboard.putNumber("Secondary Joystick Y", yPos);
		SmartDashboard.putNumber("Intake Wheel Speed", mWheelSpeed);
		mLeftWheel.set(mWheelSpeed);
		mRightWheel.set(mWheelSpeed);
	}
	
	public IntakeState getIntakeState() {
		return mIntakeState;
	}
	
	public void setWheelSpeed(double pSpeed) {
		mLeftWheel.set(pSpeed);
		mRightWheel.set(pSpeed);
	}
	
	public void log() {
		SmartDashboard.putString("Right Intake Piston State", mRightPiston.get().toString());
		SmartDashboard.putString("Left Intake Piston State", mLeftPiston.get().toString());
		SmartDashboard.putBoolean("Limit Switch", mLimitSwitch.get());
		SmartDashboard.putBoolean("Breakbeam", mBreakbeam.get());
		SmartDashboard.putString("Intake State", mIntakeState.toString());
	}

}
/*
LOGS
IntakeState
Breakbeam value
Limit Switch Value
Secondary Joystick Y
Squishy Wheel Speed
Right piston state
Left piston state
 */