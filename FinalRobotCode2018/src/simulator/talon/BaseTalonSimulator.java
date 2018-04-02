package simulator.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import resource.ResourceFunctions;

public abstract class BaseTalonSimulator implements TalonInterface {

	protected ControlMode mCurrentControlMode = ControlMode.PercentOutput;
	protected double mCurrentPercentOutput = 0;
	protected double mMaxForwardOutput = 0;
	protected double mMaxReverseOutput = 0;
	protected int mPeakMaxDuration = 0;
	protected int mPeakMaxCurrent = 0;
	protected int mContinuousMaxCurrent = 0;
	protected boolean mCurrentControl = false;
	protected boolean mInverted = false;
	protected boolean mSensorPhase = false;
	protected double mP = 0;
	protected double mI = 0;
	protected double mD = 0;
	protected double mAllowableClosedloopError = 0;
	protected double mIntegralZone = 0;
	protected double mPIDTarget;
	protected int mCurrentTicks;

	@Override
	public void set(double p) {
		if (mCurrentControlMode == ControlMode.PercentOutput) {
			// need to account for inverted here? mCurrentPercentOutput = p;
			mCurrentPercentOutput = mInverted ? -p : p;
		} else if (mCurrentControlMode == ControlMode.Position) {
			mPIDTarget = p;
		} else {
			throw new RuntimeException("Unimplemented control mode");
		}
	}

	@Override
	public void set(ControlMode pMode, double p) {
		mCurrentControlMode = pMode;
		set(p);
	}

	@Override
	public void setInverted(boolean p) {
		mInverted = p;
	}

	@Override
	public void configSelectedFeedbackSensor(FeedbackDevice p1, int p2, int p3) {

	}

	@Override
	public void setSensorPhase(boolean p) {
		mSensorPhase = p;
	}

	@Override
	public void setNeutralMode(NeutralMode p) {

	}

	@Override
	public void config_kP(int p1, double p2, int p3) {
		mP = p2;
	}

	@Override
	public void config_kI(int p1, double p2, int p3) {
		mI = p2;
	}

	@Override
	public void config_kD(int p1, double p2, int p3) {
		mD = p2;
	}

	@Override
	public void configAllowableClosedloopError(int p, int p2, int p3) {
		mAllowableClosedloopError = p2;
	}

	@Override
	public void config_IntegralZone(int p1, int p2, int p3) {
		mIntegralZone = p2;
	}

	@Override
	public double getMotorOutputPercent() {
		return mCurrentPercentOutput;
	}

	@Override
	public boolean getInverted() {
		return mInverted;
	}

	@Override
	public int getSelectedSensorPosition(int p) {
		return mCurrentTicks;
	}

	@Override
	public double getOutputCurrent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSensorCollection_getPulseWidthPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public abstract boolean getSensorCollection_isRevLimitSwitchClosed();

	@Override
	public abstract void setSelectedSensorPosition(int p1, int p2, int p3);

	@Override
	public double getClosedLoopError(int p1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMotorOutputVoltage() {
		return mCurrentPercentOutput * 12.0;
	}

	@Override
	public void configPeakOutputForward(double pOutput, int j) {
		mMaxForwardOutput = pOutput;
	}

	@Override
	public void configPeakOutputReverse(double pOutput, int j) {
		mMaxReverseOutput = pOutput;
	}

	public void configPeakCurrentDuration(int milliseconds, int timeoutMs) {
		mPeakMaxDuration = milliseconds;
	}

	public void configPeakCurrentLimit(int amps, int timeoutMs) {
		mPeakMaxCurrent = amps;
	}

	public void configContinuousCurrentLimit(int amps, int timeoutMs) {
		mContinuousMaxCurrent = amps;
	}

	public void enableCurrentLimit(boolean enable) {
		mCurrentControl = enable;
	}

	public abstract void simulate(long pMillisecondsSinceLastUpdate);

	protected void pidCalc() {
		mCurrentPercentOutput = mP * (mPIDTarget - mCurrentTicks);
		mCurrentPercentOutput = ResourceFunctions.PutNumInAbsoluteRange(mCurrentPercentOutput, -1, 1);
	}

	public boolean getSensorCollection_isFwdLimitSwitchClosed() {
		return false;
	}

	public WPI_TalonSRX getTalon() {
		return null;
	}

}
